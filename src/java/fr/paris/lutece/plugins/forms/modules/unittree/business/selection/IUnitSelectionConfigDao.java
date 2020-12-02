package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import fr.paris.lutece.portal.service.plugin.Plugin;

/**
 * Dao Interface for {@link UnitSelectionConfig}
 */
public interface IUnitSelectionConfigDao
{
    /**
     * Insert a new record in the table.
     * @param configValue
     * @param plugin
     */
    void insert( UnitSelectionConfig configValue, Plugin plugin );
    
    /**
     * Update the record in the table.
     * @param configValue
     * @param plugin
     */
    void store( UnitSelectionConfig configValue, Plugin plugin );
    
    /**
     * Delete a record from the table
     * @param nKey
     * @param plugin
     */
    void delete( int nKey, Plugin plugin );
    
    /**
     * Load the data from the table.
     * @param nConfigId
     * @param plugin
     * @return
     */
    UnitSelectionConfig selectByTaskId( int nTaskId, Plugin plugin );
    
    /**
     * Load the data from the table.
     * @param nKey
     * @param plugin
     * @return
     */
    UnitSelectionConfig load( int nKey, Plugin plugin );
}
