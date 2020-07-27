package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import java.sql.Statement;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

public class UnitSelectionConfigDao implements IUnitSelectionConfigDao
{
    public static final String BEAN_NAME = "forms-unittree.unitSelectionConfigDao";
    
    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_config,id_form,id_task FROM forms_unittree_unit_selection_config ";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECT_ALL + " WHERE id_config = ?";
    private static final String SQL_QUERY_SELECT_BY_TASK = SQL_QUERY_SELECT_ALL + " WHERE id_task = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO forms_unittree_unit_selection_config ( id_form,id_task ) VALUES ( ? , ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM forms_unittree_unit_selection_config WHERE id_config = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE forms_unittree_unit_selection_config SET id_config = ? , id_form = ? , id_task = ? WHERE id_config = ?";

    @Override
    public void insert( UnitSelectionConfig configValue, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, configValue.getIdForm( ) );
            daoUtil.setInt( ++nIndex, configValue.getIdTask( ) );
            daoUtil.executeUpdate( );
            
            if ( daoUtil.nextGeneratedKey( ) )
            {
                configValue.setIdConfig( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
    }
    
    @Override
    public UnitSelectionConfig load( int nKey, Plugin plugin )
    {
        UnitSelectionConfig config = null;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, nKey );
            daoUtil.executeQuery( );
            
            if ( daoUtil.next( ) )
            {
                config = dataToObject( daoUtil );
            }
        }
        return config;
    }
    
    @Override
    public UnitSelectionConfig selectByTaskId( int nTaskId, Plugin plugin )
    {
        UnitSelectionConfig config = null;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_TASK, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, nTaskId );
            daoUtil.executeQuery( );
            
            if ( daoUtil.next( ) )
            {
                config = dataToObject( daoUtil );
            }
        }
        return config;
    }
    
    @Override
    public void store( UnitSelectionConfig configValue, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, configValue.getIdConfig( ) );
            daoUtil.setInt( ++nIndex, configValue.getIdForm( ) );
            daoUtil.setInt( ++nIndex, configValue.getIdTask( ) );
            daoUtil.setInt( ++nIndex, configValue.getIdConfig( ) );
            daoUtil.executeUpdate( );
        }
        
    }
    
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, nKey );
            daoUtil.executeUpdate( );
        }
        
    }
    
    private UnitSelectionConfig dataToObject( DAOUtil daoUtil )
    {
        UnitSelectionConfig config = new UnitSelectionConfig( );
        int nIndex = 0;
        config.setIdConfig( daoUtil.getInt( ++nIndex ) );
        config.setIdForm( daoUtil.getInt( ++nIndex ) );
        config.setIdTask( daoUtil.getInt( ++nIndex ) );
        return config;
    }
}
