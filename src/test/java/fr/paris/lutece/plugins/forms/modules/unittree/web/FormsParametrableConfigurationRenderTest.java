/*
 * Copyright (c) 2002-2026, City of Paris
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
package fr.paris.lutece.plugins.forms.modules.unittree.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.Step;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValue;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.portal.service.template.HtmlMarkup;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Renders the configuration form of the forms parametrable unit selection with HTML auto-escaping (LUT-33153), from the
 * model FormsParametrableConfigurationHandler.getCustomConfigForm builds: the edit setting modal marked with HtmlMarkup
 * (html_edit_setting) keeps its tags, the step, question, response and unit labels of the mappings are escaped, a modal
 * left as a String would print as text, and the mapping buttons carry their id in a data attribute instead of an onclick
 * handler. The core macros the templates call are replaced by stand-ins that write their arguments (escaped) and their
 * nested content: only the FreeMarker engine is needed.
 */
public class FormsParametrableConfigurationRenderTest
{
    private static final String TEMPLATES_ROOT = "webapp/WEB-INF/templates";
    private static final String TEMPLATE_DIR = "admin/plugins/forms/modules/unittree/unitselection/config/";
    private static final String TEMPLATE_CONFIGURATION = TEMPLATE_DIR + "forms_unit_selection_parametrable_configuration.html";
    private static final String TEMPLATE_EDIT_SETTING = TEMPLATE_DIR + "forms_unit_selection_parametrable_edit_setting.html";

    /**
     * Stand-ins of the core macros the templates call: they write their arguments (escaped) and their nested content. The
     * button writes its params, which must be a map.
     */
    private static final String MACROS = "macros.ftl";
    private static final String MACROS_SOURCE = "<#macro formGroup rest...><div class=\"form-group\"><#nested></div></#macro>"
            + "<#macro inputGroup rest...><#nested></#macro>" + "<#macro inputGroupItem rest...><#nested></#macro>"
            + "<#macro checkBox rest...><input type=\"checkbox\"></#macro>"
            + "<#macro input name='' value='' rest...><input name=\"${name}\" value=\"${value}\"></#macro>"
            + "<#macro select rest...><select></select></#macro>"
            + "<#macro button value='' params={} rest...><button value=\"${value}\"<#list params as key, param> ${key}=\"${param}\"</#list>></button></#macro>"
            + "<#macro table rest...><table><#nested></table></#macro>" + "<#macro tr rest...><tr><#nested></tr></#macro>"
            + "<#macro th rest...><th><#nested></th></#macro>" + "<#macro td rest...><td><#nested></td></#macro>";

    private static final String EDIT_SETTING = "<div class=\"modal fade\" id=\"editSettingModal\"><select name=\"unit_select\"><option value=\"12\">Unit &lt;b&gt;</option></select></div>";
    private static final String LABEL = "Step <b>x</b> \"q\"";
    private static final String LABEL_ESCAPED = "Step &lt;b&gt;x&lt;/b&gt; &quot;q&quot;";

    /**
     * Renders a template of the module with the core configuration of the output format.
     *
     * @param strTemplate
     *            the template path
     * @param model
     *            the model
     * @return the HTML
     * @throws IOException
     *             if the template cannot be read
     * @throws TemplateException
     *             if the rendering fails
     */
    private static String render( String strTemplate, Map<String, Object> model ) throws IOException, TemplateException
    {
        StringTemplateLoader macros = new StringTemplateLoader( );
        macros.putTemplate( MACROS, MACROS_SOURCE );
        Configuration cfg = new Configuration( Configuration.VERSION_2_3_0 );
        cfg.setOutputFormat( HTMLOutputFormat.INSTANCE );
        cfg.setAutoEscapingPolicy( Configuration.ENABLE_IF_DEFAULT_AUTO_ESCAPING_POLICY );
        cfg.setTemplateLoader( new MultiTemplateLoader( new TemplateLoader [ ] {
                macros, new FileTemplateLoader( new File( TEMPLATES_ROOT ) )
        } ) );
        cfg.addAutoInclude( MACROS );
        StringWriter writer = new StringWriter( );
        cfg.getTemplate( strTemplate ).process( model, writer );

        return writer.toString( );
    }

    /**
     * The model of the single form configuration with one mapping, as getCustomConfigForm builds it in edit mode.
     *
     * @param editSetting
     *            the edit setting modal
     * @return the model
     */
    private static Map<String, Object> configurationModel( Object editSetting )
    {
        Step step = new Step( );
        step.setId( 5 );
        step.setTitle( LABEL );
        Question question = new Question( );
        question.setId( 7 );
        question.setTitle( LABEL );
        Unit unit = new Unit( );
        unit.setIdUnit( 12 );
        unit.setLabel( LABEL );
        UnitSelectionConfigValue mapping = new UnitSelectionConfigValue( );
        mapping.setIdConfigValue( 1021 );
        mapping.setOrder( 1 );
        mapping.setStep( step );
        mapping.setQuestion( question );
        mapping.setValue( LABEL );
        mapping.setUnit( unit );

        Map<String, Object> model = new HashMap<>( );
        model.put( "multiform", false );
        model.put( "form_list", List.of( ) );
        model.put( "unit_list", List.of( ) );
        model.put( "order_list", List.of( ) );
        model.put( "mapping_list", List.of( mapping ) );
        model.put( "html_edit_setting", editSetting );

        return model;
    }

    /**
     * The configuration form writes the edit setting modal as it is, escapes the labels of the mappings, and gives the
     * mapping buttons their id in data-mapping-id, without any onclick handler.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testConfigurationKeepsTheEditSettingModal( ) throws Exception
    {
        String strHtml = render( TEMPLATE_CONFIGURATION, configurationModel( HtmlMarkup.of( EDIT_SETTING ) ) );

        assertTrue( strHtml.contains( EDIT_SETTING ), strHtml );
        assertTrue( strHtml.contains( "<td>" + LABEL_ESCAPED + "</td>" ), strHtml );
        assertFalse( strHtml.contains( LABEL ), strHtml );
        for ( String strAction : List.of( "change_order", "edit_setting", "copy_setting", "delete_mapping" ) )
        {
            assertTrue( strHtml.contains( "<button value=\"" + strAction + "\" data-mapping-id=\"1021\">" ), strHtml );
        }
        assertFalse( strHtml.contains( "onclick" ), strHtml );
    }

    /**
     * An edit setting modal left as a String is escaped: the reason the handler marks it.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testAStringEditSettingModalIsEscaped( ) throws Exception
    {
        String strHtml = render( TEMPLATE_CONFIGURATION, configurationModel( EDIT_SETTING ) );

        assertFalse( strHtml.contains( EDIT_SETTING ), strHtml );
        assertTrue( strHtml.contains( "&lt;div class=&quot;modal fade&quot;" ), strHtml );
    }

    /**
     * The edit setting modal posts its edit_setting field with the value true, written as two attributes of the macro and
     * no longer smuggled in the name.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testEditSettingModalPostsItsEditField( ) throws Exception
    {
        String strHtml = render( TEMPLATE_EDIT_SETTING, Map.of( ) );

        assertTrue( strHtml.contains( "<input name=\"edit_setting\" value=\"true\">" ), strHtml );
    }
}
