package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import fr.paris.lutece.plugins.forms.modules.unittree.util.FormsUnittreePlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class UnitSelectionConfigHome
{
    private static IUnitSelectionConfigDao _dao = SpringContextService.getBean( UnitSelectionConfigDao.BEAN_NAME );
    private static Plugin _plugin = FormsUnittreePlugin.getPlugin( );
    
    /**
     * Private constructor
     */
    private UnitSelectionConfigHome( )
    {
    }
    
    /**
     * Insert a new record in the table.
     * @param config
     */
    public static void create( UnitSelectionConfig config )
    {
        _dao.insert( config, _plugin );
    }
    
    /**
     * Update the record in the table.
     * @param config
     */
    public static void update( UnitSelectionConfig config )
    {
        _dao.store( config, _plugin );
    }
    
    /**
     * Delete a record from the table
     * @param nIdConfig
     */
    public static void remove( int nIdConfig )
    {
        _dao.delete( nIdConfig, _plugin );   
    }
    
    /**
     * Load the data from the table.
     * @param nTaskId
     * @return
     */
    public static UnitSelectionConfig findByTaskId( int nTaskId )
    {
        return _dao.selectByTaskId( nTaskId, _plugin );
    }
    
    /**
     * Load the data from the table.
     * @param nKey
     * @return
     */
    public static UnitSelectionConfig findById( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
}
