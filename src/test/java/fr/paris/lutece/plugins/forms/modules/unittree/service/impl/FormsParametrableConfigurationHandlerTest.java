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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.mocks.MockHttpServletRequest;
import fr.paris.lutece.util.ReferenceList;

/**
 * Configuration of the parametrable unit selection of forms
 */
public class FormsParametrableConfigurationHandlerTest extends LuteceTestCase
{
    private static final int ID_TASK = 1;
    private static final int ID_OTHER_TASK = 2;
    private static final int ID_MISSING_QUESTION = 987654321;

    /**
     * The mapping being entered is kept per user and per task: two users, or two tasks of the same user, never share it
     */
    @Test
    public void testEditingStateIsPerUserAndPerTask( )
    {
        MockHttpServletRequest requestUser1 = new MockHttpServletRequest( );
        MockHttpServletRequest requestUser2 = new MockHttpServletRequest( );

        FormsParametrableConfigurationHandler.EditingState state = FormsParametrableConfigurationHandler.getEditingState( requestUser1, ID_TASK );
        state.getUnitSelectionConfigValue( ).setValue( "user1" );

        assertSame( state, FormsParametrableConfigurationHandler.getEditingState( requestUser1, ID_TASK ) );
        assertNotSame( state, FormsParametrableConfigurationHandler.getEditingState( requestUser2, ID_TASK ) );
        assertNull( FormsParametrableConfigurationHandler.getEditingState( requestUser2, ID_TASK ).getUnitSelectionConfigValue( ).getValue( ) );
        assertNotSame( state, FormsParametrableConfigurationHandler.getEditingState( requestUser1, ID_OTHER_TASK ) );
    }

    /**
     * A question deleted while it was selected gives an empty list of answers instead of failing the configuration screen
     */
    @Test
    public void testResponsesOfMissingQuestion( )
    {
        ReferenceList list = FormsParametrableConfigurationHandler.getResponseReferenceList( ID_MISSING_QUESTION );

        assertEquals( 1, list.size( ) );
    }
}
