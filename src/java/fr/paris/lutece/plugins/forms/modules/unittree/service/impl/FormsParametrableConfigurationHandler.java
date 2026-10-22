/*
 * Copyright (c) 2002-2021, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.forms.modules.unittree.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfig;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValue;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValueHome;
import fr.paris.lutece.plugins.forms.modules.unittree.service.IUnitSelectionConfigService;
import fr.paris.lutece.plugins.forms.service.entrytype.EntryTypeCheckBox;
import fr.paris.lutece.plugins.forms.service.entrytype.EntryTypeRadioButton;
import fr.paris.lutece.plugins.forms.service.entrytype.EntryTypeSelect;
import fr.paris.lutece.plugins.genericattributes.business.Field;
import fr.paris.lutece.plugins.genericattributes.service.entrytype.EntryTypeServiceManager;
import fr.paris.lutece.plugins.genericattributes.service.entrytype.IEntryTypeService;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.business.unit.UnitHome;
import fr.paris.lutece.plugins.unittree.service.selection.IParametrableConfigurationHandler;
import fr.paris.lutece.plugins.unittree.service.selection.IParametrableUnitSelection;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

@ApplicationScoped
@Named( FormsParametrableConfigurationHandler.BEAN_NAME )
public class FormsParametrableConfigurationHandler implements IParametrableConfigurationHandler
{
    public static final String BEAN_NAME = "forms-unittree.formsParametrableConfigurationHandler";

    private static final String MARK_FORM_LIST = "form_list";
    private static final String MARK_ID_FORM = "id_form";
    private static final String MARK_STEP_LIST = "step_list";
    private static final String MARK_ID_STEP = "id_step";
    private static final String MARK_QUESTION_LIST = "question_list";
    private static final String MARK_ID_QUESTION = "id_question";
    private static final String MARK_RESPONSE_LIST = "response_list";
    private static final String MARK_RESPONSE_VALUE = "response_value";
    private static final String MARK_UNIT_LIST = "unit_list";
    private static final String MARK_CODE = "code";
    private static final String MARK_ID_UNIT = "id_unit";

    private static final String MARK_MAPPING_LIST = "mapping_list";
    private static final String MARK_ORDER_LIST = "order_list";
    private static final String MARK_MULTIFORM = "multiform";
    private static final String MARK_CODE_LIST = "code_list";
    private static final String MARK_HTML_EDIT_SETTING = "html_edit_setting";

    private static final String PARAMETER_ACTION = "apply";
    private static final String PARAMETER_FORM = "form_select";
    private static final String PARAMETER_STEP = "step_select";
    private static final String PARAMETER_QUESTION = "question_select";
    private static final String PARAMETER_RESPONSE = "response_select";
    private static final String PARAMETER_UNIT = "unit_select";
    private static final String PARAMETER_MAPPING_ID = "mapping_id";
    private static final String PARAMETER_MAPPING_ORDER = "mapping_order_";
    private static final String PARAMETER_MULTIFORM = "multiform";
    private static final String PARAMETER_CODE = "code_select";
    private static final String PARAMETER_EDIT = "edit_setting";
    
    private static final String ACTION_SELECT_FORM = "select_form_config";
    private static final String ACTION_SELECT_STEP = "select_step_config";
    private static final String ACTION_SELECT_QUESTION = "select_question_config";
    private static final String ACTION_SELECT_RESPONSE = "select_response_config";
    private static final String ACTION_SELECT_UNIT = "select_unit_config";
    private static final String ACTION_REMOVE_MAPPING = "delete_mapping";
    private static final String ACTION_CHANGE_ORDER = "change_order";
    private static final String ACTION_SELECT_MULTIFORM = "select_multiform";
    private static final String ACTION_SELECT_CODE = "select_code";
    private static final String ACTION_COPY_SETTING = "copy_setting";
    private static final String ACTION_EDIT_SETTING = "edit_setting";
    private static final String ACTION_UPDATE_SETTING = "update_setting";

    private static final String TEMPLATE_CONFIGURATION = "admin/plugins/forms/modules/unittree/unitselection/config/forms_unit_selection_parametrable_configuration.html";
    private static final String TEMPLATE_EDIT_SETTING = "admin/plugins/forms/modules/unittree/unitselection/config/forms_unit_selection_parametrable_edit_setting.html";

    private static final String ATTRIBUTE_EDITING_STATE = FormsParametrableConfigurationHandler.class.getName( ) + ".editingState.";

    @Inject
    private IUnitSelectionConfigService _unitSelectionConfigService;

    @Inject
    private IParametrableUnitSelection _parametrableUnitSelection;

    @Inject
    private HttpServletRequest _request;

    /**
     * Mapping being entered by a user on the configuration of a task: kept in the session of the user, for the task, so that two users or two tasks
     * never share it
     */
    static final class EditingState implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private UnitSelectionConfigValue _unitSelectionConfigValue = new UnitSelectionConfigValue( );
        private boolean _bIsEditSetting;

        /**
         * Gets the mapping being entered
         *
         * @return the mapping being entered
         */
        UnitSelectionConfigValue getUnitSelectionConfigValue( )
        {
            return _unitSelectionConfigValue;
        }
    }

    /**
     * Gets the mapping being entered by the user of the request on the configuration of the task, created if needed
     *
     * @param request
     *            the request
     * @param nIdTask
     *            the task id
     * @return the editing state
     */
    static EditingState getEditingState( HttpServletRequest request, int nIdTask )
    {
        HttpSession session = request.getSession( true );
        String strAttribute = ATTRIBUTE_EDITING_STATE + nIdTask;
        EditingState state = (EditingState) session.getAttribute( strAttribute );
        if ( state == null )
        {
            state = new EditingState( );
            session.setAttribute( strAttribute, state );
        }
        return state;
    }

    /**
     * Saves the editing state in the session, so that its changes are kept by a distributed session
     *
     * @param request
     *            the request
     * @param nIdTask
     *            the task id
     * @param state
     *            the editing state
     */
    private static void saveEditingState( HttpServletRequest request, int nIdTask, EditingState state )
    {
        request.getSession( true ).setAttribute( ATTRIBUTE_EDITING_STATE + nIdTask, state );
    }

    @Override
    public String getCustomConfigForm( Locale locale, ITask task )
    {
        UnitSelectionConfig config = _unitSelectionConfigService.loadConfigByTask( task.getId( ) );
        if ( config == null )
        {
            config = new UnitSelectionConfig( );
        }
        EditingState state = getEditingState( _request, task.getId( ) );
        UnitSelectionConfigValue unitSelectionConfigValue = state._unitSelectionConfigValue;
        if ( unitSelectionConfigValue.getQuestion( ) != null && QuestionHome.findByPrimaryKey( unitSelectionConfigValue.getQuestion( ).getId( ) ) == null )
        {
            unitSelectionConfigValue.setQuestion( null );
            unitSelectionConfigValue.setValue( null );
            saveEditingState( _request, task.getId( ), state );
        }

        Map<String, Object> model = new HashMap<>( );
        model.put( MARK_MULTIFORM, config.isMultiform( ) ); 
        if ( config.isMultiform( ) )
        {
            model.put( MARK_CODE_LIST, getCodeList( ) );
        }

        model.put( MARK_FORM_LIST, FormHome.getFormsReferenceList( ) );
        if ( config.getIdForm( ) != -1 )
        {
            model.put( MARK_ID_FORM, config.getIdForm( ) );
            model.put( MARK_STEP_LIST, StepHome.getStepReferenceListByForm( config.getIdForm( ) ) );
        }
        if ( unitSelectionConfigValue.getStep( ) != null )
        {
            model.put( MARK_ID_STEP, unitSelectionConfigValue.getStep( ).getId( ) );
            model.put( MARK_QUESTION_LIST, getQuestionReferenceList( unitSelectionConfigValue.getStep( ).getId( ) ) );
        }
        if ( unitSelectionConfigValue.getQuestion( ) != null )
        {
            model.put( MARK_ID_QUESTION, unitSelectionConfigValue.getQuestion( ).getId( ) );
            model.put( MARK_RESPONSE_LIST, getResponseReferenceList( unitSelectionConfigValue.getQuestion( ).getId( ) ) );
        }
        if ( StringUtils.isNotEmpty( unitSelectionConfigValue.getValue( ) ) )
        {
            model.put( MARK_RESPONSE_VALUE, unitSelectionConfigValue.getValue( ) );
        }
        if ( StringUtils.isNotEmpty( unitSelectionConfigValue.getCode( ) ) )
        {
            model.put( MARK_CODE, unitSelectionConfigValue.getCode( ) );
        }
        model.put( MARK_UNIT_LIST, getUnitReferenceList( ) );
        model.put( MARK_MAPPING_LIST, config.getListConfigValues( ) );
        model.put( MARK_ORDER_LIST, getOrderReferenceList( config ) );
        
        if ( state._bIsEditSetting )
        {
        	if ( unitSelectionConfigValue.getUnit() != null )
        	{
        		model.put( MARK_ID_UNIT, unitSelectionConfigValue.getUnit().getIdUnit( ) );
        	}
        	model.put( MARK_HTML_EDIT_SETTING, AppTemplateService.getTemplate( TEMPLATE_EDIT_SETTING, locale, model ).getHtml() );
        	state._bIsEditSetting = false;
        	saveEditingState( _request, task.getId( ), state );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CONFIGURATION, locale, model );
        return template.getHtml( );
    }
    
    private ReferenceList getCodeList( )
    {
        ReferenceList refList = new ReferenceList( );
        List<Question> questionList = QuestionHome.getQuestionsList( ).stream( ).filter( this::canQuestionBeCondition ).collect( Collectors.toList( ) );
        List<String> codeList = questionList.stream( ).map( Question::getCode ).distinct( ).collect( Collectors.toList( ) );
        codeList.sort( Comparator.naturalOrder( ) );
        for ( String code : codeList )
        {
            refList.addItem( code, code );
        }
        return refList;
    }
    
    /**
     * Builds the list of the orders of the mappings of a configuration
     *
     * @param config
     *            the configuration of the task
     * @return the orders
     */
    private ReferenceList getOrderReferenceList( UnitSelectionConfig config )
    {
        ReferenceList refList = new ReferenceList( );
        for ( int i = 0; i < config.getListConfigValues( ).size( ); i++ )
        {
            int order = i + 1;
            refList.addItem( order, String.valueOf( order ) );
        }
        return refList;
    }

    private ReferenceList getQuestionReferenceList( int idStep )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( -1, "" );
        if ( idStep != -1 )
        {
            List<Question> questionList = QuestionHome.getQuestionsListByStep( idStep );
            for ( Question question : questionList )
            {
                if ( canQuestionBeCondition( question ) )
                {
                    refList.addItem( question.getId( ), question.getTitle( ) );
                }
            }
        }

        return refList;
    }

    /**
     * Builds the list of the answer choices of a question; empty when the question no longer exists
     *
     * @param idQuestion
     *            the question id
     * @return the answer choices
     */
    static ReferenceList getResponseReferenceList( int idQuestion )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( "", "" );
        Question question = ( idQuestion != -1 ) ? QuestionHome.findByPrimaryKey( idQuestion ) : null;
        if ( question != null && question.getEntry( ) != null && question.getEntry( ).getFields( ) != null )
        {
            for ( Field field : question.getEntry( ).getFields( ) )
            {
                if ( IEntryTypeService.FIELD_ANSWER_CHOICE.equals( field.getCode( ) ) )
                {
                    refList.addItem( field.getValue( ), field.getTitle( ) );
                }
            }
        }
        return refList;
    }

    private ReferenceList getUnitReferenceList( )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( -1, "" );

        List<Unit> unitList = UnitHome.findAll( );
        for ( Unit unit : unitList )
        {
            refList.addItem( unit.getIdUnit( ), unit.getLabel( ) );
        }

        return refList;
    }

    private boolean canQuestionBeCondition( Question question )
    {
        IEntryTypeService entryTypeService = EntryTypeServiceManager.getEntryTypeService( question.getEntry( ) );

        return entryTypeService instanceof EntryTypeSelect || entryTypeService instanceof EntryTypeCheckBox || entryTypeService instanceof EntryTypeRadioButton;
    }

    @Override
    public String saveConfiguration( HttpServletRequest request, ITask task )
    {
        UnitSelectionConfig config = _unitSelectionConfigService.loadConfigByTask( task.getId( ) );
        boolean create = config == null;
        if ( create )
        {
            config = new UnitSelectionConfig( );
            config.setIdTask( task.getId( ) );
        }

        String action = request.getParameter( PARAMETER_ACTION );
        if ( action != null )
        {
            EditingState state = getEditingState( request, task.getId( ) );
            doProcessAction( action, request, config, state );
            saveEditingState( request, task.getId( ), state );
        }

        if ( create )
        {
            _unitSelectionConfigService.createConfig( config );
        }
        else
        {
            _unitSelectionConfigService.updateConfig( config );
        }
        return null;
    }

    /**
     * Applies an action of the configuration form to the configuration of the task and to the mapping being entered
     *
     * @param action
     *            the action
     * @param request
     *            the request
     * @param config
     *            the configuration of the task
     * @param state
     *            the mapping being entered by the user
     */
    private void doProcessAction( String action, HttpServletRequest request, UnitSelectionConfig config, EditingState state )
    {
    	state._bIsEditSetting = request.getParameter( PARAMETER_EDIT ) != null;
    	
        switch( action )
        {
            case ACTION_SELECT_MULTIFORM:
                config.setMultiform( request.getParameter( PARAMETER_MULTIFORM ) != null );
                config.setIdForm( 0 );
                state._unitSelectionConfigValue.setCode( null );
                state._unitSelectionConfigValue.setQuestion( null );
                state._unitSelectionConfigValue.setValue( null );
                state._unitSelectionConfigValue.setUnit( null );
                state._unitSelectionConfigValue = new UnitSelectionConfigValue( );
                config.setListConfigValues( new ArrayList<>( ) );
                break;
            case ACTION_SELECT_CODE:
                state._unitSelectionConfigValue.setCode( request.getParameter( PARAMETER_CODE ) );
                break;
            case ACTION_SELECT_FORM:
                config.setIdForm( Integer.valueOf( request.getParameter( PARAMETER_FORM ) ) );
                break;
            case ACTION_SELECT_STEP:
                state._unitSelectionConfigValue.setStep( StepHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_STEP ) ) ) );
                state._unitSelectionConfigValue.setQuestion( null );
                state._unitSelectionConfigValue.setValue( null );
                state._unitSelectionConfigValue.setUnit( null );
                break;
            case ACTION_SELECT_QUESTION:
                state._unitSelectionConfigValue.setQuestion( QuestionHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_QUESTION ) ) ) );
                state._unitSelectionConfigValue.setValue( null );
                state._unitSelectionConfigValue.setUnit( null );
                break;
            case ACTION_SELECT_RESPONSE:
                state._unitSelectionConfigValue.setValue( request.getParameter( PARAMETER_RESPONSE ) );
                state._unitSelectionConfigValue.setUnit( null );
                break;
            case ACTION_SELECT_UNIT:
                if ( config.isMultiform( ) )
                {
                    state._unitSelectionConfigValue.setValue( request.getParameter( PARAMETER_RESPONSE ) );
                }
                state._unitSelectionConfigValue.setUnit( UnitHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_UNIT ) ) ) );
                config.addConfigValue( state._unitSelectionConfigValue );
                state._unitSelectionConfigValue = new UnitSelectionConfigValue( );
                break;
            case ACTION_REMOVE_MAPPING:
                int idToRemove = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ID ) );
                List<UnitSelectionConfigValue> newList = config.getListConfigValues( ).stream( )
                        .filter( configValue -> configValue.getIdConfigValue( ) != idToRemove ).collect( Collectors.toList( ) );
                config.setListConfigValues( newList );
                break;
            case ACTION_CHANGE_ORDER:
                int idToMove = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ID ) );
                int newOrder = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ORDER + idToMove ) );
                doChangeOrder( config, idToMove, newOrder );
                break;
            case ACTION_COPY_SETTING:
            	int idToCopy = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ID ) );
            	UnitSelectionConfigValue configValueToCopy = config.getListConfigValues( ).stream( ).filter ( c -> c.getIdConfigValue( ) == idToCopy ).findFirst( ).orElse( null );
            	if ( configValueToCopy != null )
            	{
            		UnitSelectionConfigValue configValueCopy = copyUnitSelectionConfigValue( configValueToCopy );
            	    config.addConfigValue( configValueCopy );
            	}
                break;
            case ACTION_EDIT_SETTING:
            	int idMapping = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ID ) );
            	UnitSelectionConfigValue unitConfigValue = UnitSelectionConfigValueHome.findById( idMapping );
            	if ( unitConfigValue != null )
            	{
                	state._unitSelectionConfigValue = unitConfigValue;
                	state._bIsEditSetting = true;
            	}
            	break;
            case ACTION_UPDATE_SETTING:
            	state._unitSelectionConfigValue.setUnit( UnitHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_UNIT ) ) ) );
                config.updateConfigValue( state._unitSelectionConfigValue );
                state._unitSelectionConfigValue = new UnitSelectionConfigValue( );
                state._bIsEditSetting = false;
                break;
            default:
                break;
        }
    }

    /**
     * Moves a mapping of a configuration to a new order, shifting the others
     *
     * @param config
     *            the configuration of the task
     * @param idToMove
     *            the id of the mapping to move
     * @param newOrder
     *            the new order of the mapping
     */
    private void doChangeOrder( UnitSelectionConfig config, int idToMove, int newOrder )
    {
        List<UnitSelectionConfigValue> newList = config.getListConfigValues( );
        UnitSelectionConfigValue configToChangeOrder = newList.stream( ).filter( cv -> cv.getIdConfigValue( ) == idToMove ).findFirst( ).orElse( null );
        if ( configToChangeOrder == null )
        {
            return;
        }
        int oldOrder = configToChangeOrder.getOrder( );
        boolean changeOrderDown = newOrder < configToChangeOrder.getOrder( );

        for ( UnitSelectionConfigValue configValue : newList )
        {
            if ( configValue.getIdConfigValue( ) == idToMove )
            {
                configValue.setOrder( newOrder );
                continue;
            }
            if ( changeOrderDown )
            {
                if ( configValue.getOrder( ) >= newOrder && configValue.getOrder( ) < oldOrder )
                {
                    configValue.setOrder( configValue.getOrder( ) + 1 );
                }
            }
            else
            {
                if ( configValue.getOrder( ) <= newOrder && configValue.getOrder( ) > oldOrder )
                {
                    configValue.setOrder( configValue.getOrder( ) - 1 );
                }
            }
        }
        config.setListConfigValues( newList );
    }

    @Override
    public void removeConfiguration( ITask task )
    {
        _unitSelectionConfigService.deleteConfigByTask( task.getId( ) );
    }

    @Override
    public IParametrableUnitSelection getParametrableUnitSelection( )
    {
        return _parametrableUnitSelection;
    }

    @Override
    public String getBeanName( )
    {
        return BEAN_NAME;
    }

    @Override
    public String getTitle( Locale locale )
    {
        return I18nService.getLocalizedString( "module.forms.unittree." + BEAN_NAME, locale );
    }
    
    /**
     * create a copy of a UnitSelectionConfigValue object
     * 
     * @param configValue the UnitSelectionConfigValue to copy
     * @return the copy
     */
    private UnitSelectionConfigValue copyUnitSelectionConfigValue( UnitSelectionConfigValue configValue )
    {
    	UnitSelectionConfigValue configValueCopy = new UnitSelectionConfigValue( );

    	if ( configValue != null )
    	{
    		configValueCopy.setIdConfig( configValue.getIdConfig( ) );
        	configValueCopy.setStep( configValue.getStep( ) );
        	configValueCopy.setQuestion( configValue.getQuestion( ) );
        	configValueCopy.setValue( configValue.getValue( ) );
        	configValueCopy.setUnit( configValue.getUnit( ) );
        	configValueCopy.setCode( configValue.getCode( ) );
        	configValueCopy.setOrder( configValue.getOrder( ) );	
    	}

    	return configValueCopy;
    }
}
