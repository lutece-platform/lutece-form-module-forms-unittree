package fr.paris.lutece.plugins.forms.modules.unittree.business.form.panel.configuration;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import fr.paris.lutece.plugins.forms.business.form.panel.configuration.FormPanelConfiguration;
import fr.paris.lutece.plugins.forms.business.form.panel.configuration.IFormPanelConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class FormUnittreePanelConfigurationProducer
{
	@Produces
	@Named( "forms-unittree.formsUnittreePanel.panelConfiguration" )
	@ApplicationScoped
    public IFormPanelConfiguration produceFormsUnittreePanelConfiguration( @ConfigProperty( name = "forms-unittree.formsUnittreePanel.panelConfiguration.technicalCode" ) String strTechnicalCode,
            @ConfigProperty( name = "forms-unittree.formsUnittreePanel.panelConfiguration.position" ) int nPosition,
            @ConfigProperty( name = "forms-unittree.formsUnittreePanel.panelConfiguration.title" ) String strTitle,
            @ConfigProperty( name = "forms-unittree.formsUnittreePanel.panelConfiguration.listFormPanelInitializerName" ) String strListFormPanelInitializerName )
    {
		List<String> listFormPanelInitializerName = StringUtils.isBlank( strListFormPanelInitializerName ) ? List.of( ) : Arrays.asList( StringUtils.split( strListFormPanelInitializerName, "," ) );
		
        return new FormPanelConfiguration( strTechnicalCode, nPosition, strTitle, listFormPanelInitializerName );
    }
	
	@Produces
	@Named( "forms-unittree.formsUnittreePanel.subunit.panelConfiguration" )
	@ApplicationScoped
    public IFormPanelConfiguration produceFormsUnittreeSubUnitPanelConfiguration( @ConfigProperty( name = "forms-unittree.formsUnittreePanel.subunit.panelConfiguration.technicalCode" ) String strTechnicalCode,
            @ConfigProperty( name = "forms-unittree.formsUnittreePanel.subunit.panelConfiguration.position" ) int nPosition,
            @ConfigProperty( name = "forms-unittree.formsUnittreePanel.subunit.panelConfiguration.title" ) String strTitle,
            @ConfigProperty( name = "forms-unittree.formsUnittreePanel.subunit.panelConfiguration.listFormPanelInitializerName" ) String strListFormPanelInitializerName )
    {
		List<String> listFormPanelInitializerName = StringUtils.isBlank( strListFormPanelInitializerName ) ? List.of( ) : Arrays.asList( StringUtils.split( strListFormPanelInitializerName, "," ) );
		
        return new FormPanelConfiguration( strTechnicalCode, nPosition, strTitle, listFormPanelInitializerName );
    }
}