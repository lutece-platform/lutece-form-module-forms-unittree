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
package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import java.util.List;

import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.Step;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.business.unit.UnitHome;
import fr.paris.lutece.test.LuteceTestCase;

public class UnitSelectionConfigValueBusinessTest extends LuteceTestCase
{

    public void testCRUD( )
    {
        Unit unit = new Unit( );
        unit.setCode( "code" );
        unit.setDescription( "descr" );
        unit.setLabel( "strLabel" );
        UnitHome.create( unit );

        Step step = new Step( );
        step.setTitle( "title" );
        step.setDescription( "step" );
        step.setIdForm( 1 );
        StepHome.create( step );

        Question question = new Question( );
        question.setTitle( "question" );
        question.setColumnTitle( "title" );
        question.setCode( "code" );
        question.setIdStep( step.getId( ) );
        QuestionHome.create( question );

        UnitSelectionConfigValue configValue = new UnitSelectionConfigValue( );
        configValue.setIdConfig( 1 );
        configValue.setStep( step );
        configValue.setQuestion( question );
        configValue.setValue( "toto" );
        configValue.setUnit( unit );
        UnitSelectionConfigValueHome.create( configValue );

        UnitSelectionConfigValue loaded = UnitSelectionConfigValueHome.findById( configValue.getIdConfigValue( ) );
        assertEquals( configValue.getStep( ).getDescription( ), loaded.getStep( ).getDescription( ) );
        assertEquals( configValue.getQuestion( ).getTitle( ), loaded.getQuestion( ).getTitle( ) );
        assertEquals( configValue.getUnit( ).getDescription( ), loaded.getUnit( ).getDescription( ) );
        assertEquals( configValue.getValue( ), loaded.getValue( ) );

        List<UnitSelectionConfigValue> list = UnitSelectionConfigValueHome.findByConfig( 1 );
        assertEquals( 1, list.size( ) );
        assertEquals( configValue.getStep( ).getDescription( ), list.get( 0 ).getStep( ).getDescription( ) );
        assertEquals( configValue.getQuestion( ).getTitle( ), list.get( 0 ).getQuestion( ).getTitle( ) );
        assertEquals( configValue.getUnit( ).getDescription( ), list.get( 0 ).getUnit( ).getDescription( ) );
        assertEquals( configValue.getValue( ), list.get( 0 ).getValue( ) );

        configValue.setValue( "tata" );
        UnitSelectionConfigValueHome.update( configValue );

        loaded = UnitSelectionConfigValueHome.findById( configValue.getIdConfigValue( ) );
        assertEquals( configValue.getStep( ).getDescription( ), loaded.getStep( ).getDescription( ) );
        assertEquals( configValue.getQuestion( ).getTitle( ), loaded.getQuestion( ).getTitle( ) );
        assertEquals( configValue.getUnit( ).getDescription( ), loaded.getUnit( ).getDescription( ) );

        UnitSelectionConfigValueHome.removeByConfig( 1 );
        loaded = UnitSelectionConfigValueHome.findById( configValue.getIdConfigValue( ) );
        assertNull( loaded );
    }
}
