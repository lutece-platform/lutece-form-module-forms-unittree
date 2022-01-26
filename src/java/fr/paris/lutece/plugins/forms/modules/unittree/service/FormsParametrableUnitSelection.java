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
package fr.paris.lutece.plugins.forms.modules.unittree.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponseStep;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.Step;
import fr.paris.lutece.plugins.forms.business.StepHome;
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
            if ( configValue.getValue( ).equals( findResponseValue( formResponse, config.isMultiform( ), configValue ) ) )
            {
                return configValue.getUnit( ).getIdUnit( );
            }
        }
        return -1;
    }

    private String findResponseValue( FormResponse formResponse, boolean isMultiform, UnitSelectionConfigValue configValue )
    {
        String response = null;
        Question question = null;
        
        if ( isMultiform )
        {
            for ( Question q : QuestionHome.findByCode( configValue.getCode( ) ) )
            {
                Step step = StepHome.findByPrimaryKey( q.getIdStep( ) );
                if ( step.getIdForm( ) == formResponse.getFormId( ) )
                {
                    question = q;
                    break;
                }
            }
        }
        else
        {
            question = configValue.getQuestion( );
        }
        int questionId = question != null ? question.getId( ) : -1;
        for ( FormResponseStep step : formResponse.getSteps( ) )
        {
            FormQuestionResponse formQuestionResponse = step.getQuestions( ).stream( ).filter( fqr -> fqr.getQuestion( ).getId( ) == questionId )
                    .findFirst( ).orElse( null );
            if ( formQuestionResponse != null )
            {
                return formQuestionResponse.getEntryResponse( ).get( 0 ).getResponseValue( );
            }
        }
        return response;
    }
}
