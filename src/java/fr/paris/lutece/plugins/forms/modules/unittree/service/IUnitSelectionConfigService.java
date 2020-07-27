package fr.paris.lutece.plugins.forms.modules.unittree.service;

import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfig;

public interface IUnitSelectionConfigService
{
    /**
     * Load the config associated to a task.
     * @param nITask
     * @return
     */
    UnitSelectionConfig loadConfigByTask( int nITask );
    
    /**
     * Delete the config associated to a task.
     * @param nITask
     * @return
     */
    void deleteConfigByTask( int nITask );
    
    /**
     * Save the config.
     * @param config
     */
    void createConfig( UnitSelectionConfig config );
    
    /**
     * Update the config.
     * @param config
     */
    void updateConfig( UnitSelectionConfig config );
}
