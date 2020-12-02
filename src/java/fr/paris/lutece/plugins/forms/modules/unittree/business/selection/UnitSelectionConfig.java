package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import java.util.ArrayList;
import java.util.List;

public class UnitSelectionConfig
{
    private int _nIdConfig = -1;
    private int _nIdForm = -1;
    private int _nIdTask = -1;
    private List<UnitSelectionConfigValue> _listConfigValues = new ArrayList<>( );
    
    /**
     * @return the nIdConfig
     */
    public int getIdConfig( )
    {
        return _nIdConfig;
    }
    /**
     * @param nIdConfig the nIdConfig to set
     */
    public void setIdConfig( int nIdConfig )
    {
        _nIdConfig = nIdConfig;
    }
    /**
     * @return the nIdForm
     */
    public int getIdForm( )
    {
        return _nIdForm;
    }
    /**
     * @param nIdForm the nIdForm to set
     */
    public void setIdForm( int nIdForm )
    {
        _nIdForm = nIdForm;
    }
    /**
     * @return the nIdTask
     */
    public int getIdTask( )
    {
        return _nIdTask;
    }
    /**
     * @param nIdTask the nIdTask to set
     */
    public void setIdTask( int nIdTask )
    {
        _nIdTask = nIdTask;
    }
    /**
     * @return the listConfigValues
     */
    public List<UnitSelectionConfigValue> getListConfigValues( )
    {
        return new ArrayList<>( _listConfigValues );
    }
    /**
     * @param listConfigValues the listConfigValues to set
     */
    public void setListConfigValues( List<UnitSelectionConfigValue> listConfigValues )
    {
        this._listConfigValues = new ArrayList<>( listConfigValues );
    }
    
    public void addConfigValue( UnitSelectionConfigValue configValue )
    {
        configValue.setOrder( this._listConfigValues.size( ) + 1 );
        this._listConfigValues.add( configValue );
    }
}
