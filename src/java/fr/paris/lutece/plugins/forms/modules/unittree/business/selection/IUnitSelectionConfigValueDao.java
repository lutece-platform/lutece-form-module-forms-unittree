package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;

/**
 * Dao Interface for {@link UnitSelectionConfigValue}
 */
public interface IUnitSelectionConfigValueDao
{
    /**
     * Insert a new record in the table.
     * @param configValue
     * @param plugin
     */
    void insert( UnitSelectionConfigValue configValue, Plugin plugin );
    
    /**
     * Update the record in the table.
     * @param configValue
     * @param plugin
     */
    void store( UnitSelectionConfigValue configValue, Plugin plugin );
    
    /**
     * Delete a record from the table
     * @param nKey
     * @param plugin
     */
    void delete( int nKey, Plugin plugin );
    
    /**
     * Delete records from the table
     * @param nConfigId
     * @param plugin
     */
    void deleteByConfigId( int nConfigId, Plugin plugin );
    
    /**
     * Load all {@link UnitSelectionConfigValue} with the given nConfigId.
     * @param nConfigId
     * @param plugin
     * @return
     */
    List<UnitSelectionConfigValue> selectByConfigId( int nConfigId, Plugin plugin );
    
    /**
     * Load the data from the table.
     * @param nKey
     * @param plugin
     * @return
     */
    UnitSelectionConfigValue load( int nKey, Plugin plugin );
}
