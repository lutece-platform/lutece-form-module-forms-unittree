package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.unittree.business.unit.UnitHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * Implements {@link IUnitSelectionConfigValueDao}
 */
public class UnitSelectionConfigValueDao implements IUnitSelectionConfigValueDao
{
    public static final String BEAN_NAME = "forms-unittree.unitSelectionConfigValueDao";

    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_config_value,id_config,id_step,id_question,response_value,id_unit,id_order FROM forms_unittree_unit_selection_config_value ";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECT_ALL + " WHERE id_config_value = ?";
    private static final String SQL_QUERY_SELECT_BY_CONFIG = SQL_QUERY_SELECT_ALL + " WHERE id_config = ? order by id_order asc";
    private static final String SQL_QUERY_INSERT = "INSERT INTO forms_unittree_unit_selection_config_value ( id_config,id_step,id_question,response_value,id_unit,id_order ) VALUES ( ?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM forms_unittree_unit_selection_config_value WHERE id_config_value = ?";
    private static final String SQL_QUERY_DELETE_BY_CONFIG = "DELETE FROM forms_unittree_unit_selection_config_value WHERE id_config = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE forms_unittree_unit_selection_config_value SET id_config_value=? ,id_config=? ,id_step=? ,id_question=? ,response_value=? ,id_unit=?,id_order=? WHERE id_config_value = ?";

    @Override
    public void insert( UnitSelectionConfigValue configValue, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, configValue.getIdConfig( ) );
            daoUtil.setInt( ++nIndex, configValue.getStep( ).getId( ) );
            daoUtil.setInt( ++nIndex, configValue.getQuestion( ).getId( ) );
            daoUtil.setString( ++nIndex, configValue.getValue( ) );
            daoUtil.setInt( ++nIndex, configValue.getUnit( ).getIdUnit( ) );
            daoUtil.setInt( ++nIndex, configValue.getOrder( ) );
            daoUtil.executeUpdate( );
            
            if ( daoUtil.nextGeneratedKey( ) )
            {
                configValue.setIdConfigValue( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    @Override
    public void store( UnitSelectionConfigValue configValue, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, configValue.getIdConfigValue( ) );
            daoUtil.setInt( ++nIndex, configValue.getIdConfig( ) );
            daoUtil.setInt( ++nIndex, configValue.getStep( ).getId( ) );
            daoUtil.setInt( ++nIndex, configValue.getQuestion( ).getId( ) );
            daoUtil.setString( ++nIndex, configValue.getValue( ) );
            daoUtil.setInt( ++nIndex, configValue.getUnit( ).getIdUnit( ) );
            daoUtil.setInt( ++nIndex, configValue.getOrder( ) );
            daoUtil.setInt( ++nIndex, configValue.getIdConfigValue( ) );
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

    @Override
    public List<UnitSelectionConfigValue> selectByConfigId( int nConfigId, Plugin plugin )
    {
        List<UnitSelectionConfigValue> list = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CONFIG, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, nConfigId );
            daoUtil.executeQuery( );
            while ( daoUtil.next( ) )
            {
                UnitSelectionConfigValue config = dataToObject( daoUtil );
                list.add( config );
            }
        }
        return list;
    }

    @Override
    public UnitSelectionConfigValue load( int nKey, Plugin plugin )
    {
        UnitSelectionConfigValue config = null;
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
    public void deleteByConfigId( int nConfigId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_CONFIG, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, nConfigId );
            daoUtil.executeUpdate( );
        }

    }
    
    private UnitSelectionConfigValue dataToObject( DAOUtil daoUtil )
    {
        UnitSelectionConfigValue config = new UnitSelectionConfigValue( );
        int nIndex = 0;
        config.setIdConfigValue( daoUtil.getInt( ++nIndex ) );
        config.setIdConfig( daoUtil.getInt( ++nIndex ) );
        config.setStep( StepHome.findByPrimaryKey( daoUtil.getInt( ++nIndex ) ) );
        config.setQuestion( QuestionHome.findByPrimaryKey( daoUtil.getInt( ++nIndex ) ) );
        config.setValue( daoUtil.getString( ++nIndex ) );
        config.setUnit( UnitHome.findByPrimaryKey( daoUtil.getInt( ++nIndex ) ) );
        config.setOrder( daoUtil.getInt( ++nIndex ) );
        
        return config;
    }
}
