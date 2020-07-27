package fr.paris.lutece.plugins.forms.modules.unittree.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponseStep;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfig;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValue;
import fr.paris.lutece.plugins.unittree.exception.AssignmentNotPossibleException;
import fr.paris.lutece.plugins.unittree.service.selection.IParametrableUnitSelection;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;

public class FormsParametrableUnitSelection implements IParametrableUnitSelection
{
    public static final String BEAN_NAME = "forms-unittree.formsParametrableUnitSelection";
    
    @Inject
    private IUnitSelectionConfigService _unitSelectionConfigService;
    
    @Override
    public int select( int nIdResource, String strResourceType, HttpServletRequest request, ITask task ) throws AssignmentNotPossibleException
    {
       UnitSelectionConfig config = _unitSelectionConfigService.loadConfigByTask( task.getId( ) );
       FormResponse formResponse = FormResponseHome.findByPrimaryKey( nIdResource );
       if ( config == null || formResponse == null )
       {
           return -1;
       }
       
       for ( UnitSelectionConfigValue configValue : config.getListConfigValues( ) )
       {
           if ( configValue.getValue( ).equals( findResponseValue( formResponse, configValue.getQuestion( ) ) ) )
           {
               return configValue.getUnit( ).getIdUnit( );
           }
       }
       return -1;
    }

    private String findResponseValue( FormResponse formResponse, Question question )
    {
        String response = null;
        
        for ( FormResponseStep step : formResponse.getSteps( ) )
        {
            FormQuestionResponse formQuestionResponse = step.getQuestions( ).stream( ).filter( fqr -> fqr.getQuestion( ).getId( ) == question.getId( ) ).findFirst( ).orElse( null );
            if ( formQuestionResponse != null )
            {
                return formQuestionResponse.getEntryResponse( ).get( 0 ).getResponseValue( );
            }
        }
        
        return response;
    }
}
