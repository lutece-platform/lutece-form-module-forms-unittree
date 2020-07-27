package fr.paris.lutece.plugins.forms.modules.unittree.service.impl;

import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfig;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigHome;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValue;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValueHome;
import fr.paris.lutece.plugins.forms.modules.unittree.service.IUnitSelectionConfigService;

public class UnitSelectionConfigService implements IUnitSelectionConfigService
{

    public static final String BEAN_NAME = "forms-unittree.unitSelectionConfigService";
    
    @Override
    public UnitSelectionConfig loadConfigByTask( int nITask )
    {
        UnitSelectionConfig config = UnitSelectionConfigHome.findByTaskId( nITask );
        if ( config != null )
        {
            config.setListConfigValues( UnitSelectionConfigValueHome.findByConfig( config.getIdConfig( ) ) );
        }
        return config;
    }

    @Override
    public void deleteConfigByTask( int nITask )
    {
        UnitSelectionConfig config = UnitSelectionConfigHome.findByTaskId( nITask );
        if ( config != null )
        {
            UnitSelectionConfigValueHome.removeByConfig( config.getIdConfig( ) );
            UnitSelectionConfigHome.remove( config.getIdConfig( ) );
        }
    }
    
    @Override
    public void createConfig( UnitSelectionConfig config )
    {
        UnitSelectionConfigHome.create( config );
        for ( UnitSelectionConfigValue configValue : config.getListConfigValues( ) )
        {
            configValue.setIdConfig( config.getIdConfig( ) );
            UnitSelectionConfigValueHome.create( configValue );
        }
    }
    
    @Override
    public void updateConfig( UnitSelectionConfig config )
    {
        UnitSelectionConfigValueHome.removeByConfig( config.getIdConfig( ) );
        UnitSelectionConfigHome.update( config );
        for ( UnitSelectionConfigValue configValue : config.getListConfigValues( ) )
        {
            configValue.setIdConfig( config.getIdConfig( ) );
            UnitSelectionConfigValueHome.create( configValue );
        }
    }
}
