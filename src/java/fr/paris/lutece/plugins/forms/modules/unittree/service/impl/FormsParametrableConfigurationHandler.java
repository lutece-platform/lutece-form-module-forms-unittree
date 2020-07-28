/*
 * Copyright (c) 2002-2020, City of Paris
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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfig;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValue;
import fr.paris.lutece.plugins.forms.modules.unittree.service.FormsParametrableUnitSelection;
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
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

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

    private static final String MARK_MAPPING_LIST = "mapping_list";
    private static final String MARK_ORDER_LIST = "order_list";

    private static final String PARAMETER_ACTION = "apply";
    private static final String PARAMETER_FORM = "form_select";
    private static final String PARAMETER_STEP = "step_select";
    private static final String PARAMETER_QUESTION = "question_select";
    private static final String PARAMETER_RESPONSE = "response_select";
    private static final String PARAMETER_UNIT = "unit_select";
    private static final String PARAMETER_MAPPING_ID = "mapping_id";
    private static final String PARAMETER_MAPPING_ORDER = "mapping_order_";

    private static final String ACTION_SELECT_FORM = "select_form_config";
    private static final String ACTION_SELECT_STEP = "select_step_config";
    private static final String ACTION_SELECT_QUESTION = "select_question_config";
    private static final String ACTION_SELECT_RESPONSE = "select_response_config";
    private static final String ACTION_SELECT_UNIT = "select_unit_config";
    private static final String ACTION_REMOVE_MAPPING = "delete_mapping";
    private static final String ACTION_CHANGE_ORDER = "change_order";

    private static final String TEMPLATE_CONFIGURATION = "admin/plugins/forms/modules/unittree/unitselection/config/forms_unit_selection_parametrable_configuration.html";

    @Inject
    private IUnitSelectionConfigService _unitSelectionConfigService;

    private UnitSelectionConfigValue _unitSelectionConfigValue;
    private UnitSelectionConfig _config;

    @Override
    public String getCustomConfigForm( Locale locale, ITask task )
    {
        _config = _unitSelectionConfigService.loadConfigByTask( task.getId( ) );
        if ( _unitSelectionConfigValue == null )
        {
            _unitSelectionConfigValue = new UnitSelectionConfigValue( );
        }
        if ( _config == null )
        {
            _config = new UnitSelectionConfig( );
        }

        Map<String, Object> model = new HashMap<>( );
        model.put( MARK_FORM_LIST, FormHome.getFormsReferenceList( ) );
        if ( _config.getIdForm( ) != -1 )
        {
            model.put( MARK_ID_FORM, _config.getIdForm( ) );
            model.put( MARK_STEP_LIST, StepHome.getStepReferenceListByForm( _config.getIdForm( ) ) );
        }
        if ( _unitSelectionConfigValue.getStep( ) != null )
        {
            model.put( MARK_ID_STEP, _unitSelectionConfigValue.getStep( ).getId( ) );
            model.put( MARK_QUESTION_LIST, getQuestionReferenceList( _unitSelectionConfigValue.getStep( ).getId( ) ) );
        }
        if ( _unitSelectionConfigValue.getQuestion( ) != null )
        {
            model.put( MARK_ID_QUESTION, _unitSelectionConfigValue.getQuestion( ).getId( ) );
            model.put( MARK_RESPONSE_LIST, getResponseReferenceList( _unitSelectionConfigValue.getQuestion( ).getId( ) ) );
        }
        if ( StringUtils.isNotEmpty( _unitSelectionConfigValue.getValue( ) ) )
        {
            model.put( MARK_RESPONSE_VALUE, _unitSelectionConfigValue.getValue( ) );
        }
        model.put( MARK_UNIT_LIST, getUnitReferenceList( ) );
        model.put( MARK_MAPPING_LIST, _config.getListConfigValues( ) );
        model.put( MARK_ORDER_LIST, getOrderReferenceList( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CONFIGURATION, locale, model );
        return template.getHtml( );
    }

    private ReferenceList getOrderReferenceList( )
    {
        ReferenceList refList = new ReferenceList( );
        for ( int i = 0; i < _config.getListConfigValues( ).size( ); i++ )
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

    private ReferenceList getResponseReferenceList( int idQuestion )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( "", "" );
        if ( idQuestion != -1 )
        {
            Question question = QuestionHome.findByPrimaryKey( idQuestion );
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
        _config = _unitSelectionConfigService.loadConfigByTask( task.getId( ) );
        boolean create = _config == null;
        if ( create )
        {
            _config = new UnitSelectionConfig( );
            _config.setIdTask( task.getId( ) );
        }

        String action = request.getParameter( PARAMETER_ACTION );
        if ( action != null )
        {
            doProcessAction( action, request );
        }

        if ( create )
        {
            _unitSelectionConfigService.createConfig( _config );
        }
        else
        {
            _unitSelectionConfigService.updateConfig( _config );
        }
        return null;
    }

    private void doProcessAction( String action, HttpServletRequest request )
    {
        switch( action )
        {
            case ACTION_SELECT_FORM:
                _config.setIdForm( Integer.valueOf( request.getParameter( PARAMETER_FORM ) ) );
                _unitSelectionConfigValue = new UnitSelectionConfigValue( );
                break;
            case ACTION_SELECT_STEP:
                _unitSelectionConfigValue.setStep( StepHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_STEP ) ) ) );
                _unitSelectionConfigValue.setQuestion( null );
                _unitSelectionConfigValue.setValue( null );
                _unitSelectionConfigValue.setUnit( null );
                break;
            case ACTION_SELECT_QUESTION:
                _unitSelectionConfigValue.setQuestion( QuestionHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_QUESTION ) ) ) );
                _unitSelectionConfigValue.setValue( null );
                _unitSelectionConfigValue.setUnit( null );
                break;
            case ACTION_SELECT_RESPONSE:
                _unitSelectionConfigValue.setValue( request.getParameter( PARAMETER_RESPONSE ) );
                _unitSelectionConfigValue.setUnit( null );
                break;
            case ACTION_SELECT_UNIT:
                _unitSelectionConfigValue.setUnit( UnitHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_UNIT ) ) ) );
                _config.addConfigValue( _unitSelectionConfigValue );
                _unitSelectionConfigValue = new UnitSelectionConfigValue( );
                break;
            case ACTION_REMOVE_MAPPING:
                int idToRemove = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ID ) );
                List<UnitSelectionConfigValue> newList = _config.getListConfigValues( ).stream( )
                        .filter( configValue -> configValue.getIdConfigValue( ) != idToRemove ).collect( Collectors.toList( ) );
                _config.setListConfigValues( newList );
                break;
            case ACTION_CHANGE_ORDER:
                int idToMove = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ID ) );
                int newOrder = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ORDER + idToMove ) );
                doChangeOrder( idToMove, newOrder );
                break;
            default:
                break;
        }
    }

    private void doChangeOrder( int idToMove, int newOrder )
    {
        List<UnitSelectionConfigValue> newList = _config.getListConfigValues( );
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
        _config.setListConfigValues( newList );
    }

    @Override
    public void removeConfiguration( ITask task )
    {
        _unitSelectionConfigService.deleteConfigByTask( task.getId( ) );
    }

    @Override
    public IParametrableUnitSelection getParametrableUnitSelection( )
    {
        return SpringContextService.getBean( FormsParametrableUnitSelection.BEAN_NAME );
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
}
