package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import java.util.List;

import fr.paris.lutece.plugins.forms.modules.unittree.util.FormsUnittreePlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class UnitSelectionConfigValueHome
{
    private static IUnitSelectionConfigValueDao _dao = SpringContextService.getBean( UnitSelectionConfigValueDao.BEAN_NAME );
    private static Plugin _plugin = FormsUnittreePlugin.getPlugin( );
    
    /**
     * Private constructor
     */
    private UnitSelectionConfigValueHome( )
    {
    }
    
    /**
     * Insert a new record in the table.
     * @param configValue
     */
    public static void create( UnitSelectionConfigValue configValue )
    {
        _dao.insert( configValue, _plugin );
    }
    
    /**
     * Update the record in the table.
     * @param configValue
     */
    public static void update( UnitSelectionConfigValue configValue )
    {
        _dao.store( configValue, _plugin );
    }
    
    /**
     * Delete a record from the table
     * @param nIdConfigValue
     */
    public static void remove( int nIdConfigValue )
    {
        _dao.delete( nIdConfigValue, _plugin );   
    }
    
    /**
     * Delete a record from the table
     * @param nIdConfig
     */
    public static void removeByConfig( int nIdConfig )
    {
        _dao.deleteByConfigId( nIdConfig, _plugin );
    }
    
    /**
     * Load the data from the table.
     * @param nConfigId
     * @return
     */
    public static List<UnitSelectionConfigValue> findByConfig( int nConfigId )
    {
        return _dao.selectByConfigId( nConfigId, _plugin );
    }
    
    /**
     * Load the data from the table.
     * @param nKey
     * @return
     */
    public static UnitSelectionConfigValue findById( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
}
