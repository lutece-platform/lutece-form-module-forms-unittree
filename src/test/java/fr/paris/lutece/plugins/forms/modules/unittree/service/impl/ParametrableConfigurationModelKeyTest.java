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

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * The parametrable configuration reads the chosen code under a key that no core macro shadows: the core macro
 * library, included in the main namespace, defines a macro named "code", so "code??" is always true.
 */
public class ParametrableConfigurationModelKeyTest
{
    private static final String TEMPLATE = "webapp/WEB-INF/templates/admin/plugins/forms/modules/unittree/unitselection/config/forms_unit_selection_parametrable_configuration.html";
    private static final Pattern MACRO_NAME_AS_VALUE = Pattern.compile( "(?<![\\w.@])code\\s*(\\?\\?|!|\\})" );

    @Test
    public void testTemplateDoesNotReadTheShadowedKey( ) throws IOException
    {
        String strSource = Files.readString( Path.of( TEMPLATE ), StandardCharsets.UTF_8 );
        assertFalse( MACRO_NAME_AS_VALUE.matcher( strSource ).find( ), "the template reads code, which is the core macro" );
    }
}
