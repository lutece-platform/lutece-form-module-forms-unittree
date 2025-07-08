/*
 * Copyright (c) 2002-2021, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.unittree.business.unit.UnitHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * Implements {@link IUnitSelectionConfigValueDao}
 */
@ApplicationScoped
@Named( UnitSelectionConfigValueDao.BEAN_NAME )
public class UnitSelectionConfigValueDao implements IUnitSelectionConfigValueDao
{
    public static final String BEAN_NAME = "forms-unittree.unitSelectionConfigValueDao";

    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_config_value,id_config,id_step,id_question,response_value,id_unit,id_order,code FROM forms_unittree_unit_selection_config_value ";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECT_ALL + " WHERE id_config_value = ?";
    private static final String SQL_QUERY_SELECT_BY_CONFIG = SQL_QUERY_SELECT_ALL + " WHERE id_config = ? order by id_order asc";
    private static final String SQL_QUERY_INSERT = "INSERT INTO forms_unittree_unit_selection_config_value ( id_config,id_step,id_question,response_value,id_unit,id_order,code ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM forms_unittree_unit_selection_config_value WHERE id_config_value = ?";
    private static final String SQL_QUERY_DELETE_BY_CONFIG = "DELETE FROM forms_unittree_unit_selection_config_value WHERE id_config = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE forms_unittree_unit_selection_config_value SET id_config_value=? ,id_config=? ,id_step=? ,id_question=? ,response_value=? ,id_unit=?,id_order=?,code=? WHERE id_config_value = ?";

    @Override
    public void insert( UnitSelectionConfigValue configValue, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, configValue.getIdConfig( ) );
            if ( configValue.getStep( ) != null )
            {
                daoUtil.setInt( ++nIndex, configValue.getStep( ).getId( ) );
            }
            else
            {
                daoUtil.setInt( ++nIndex, -1 );
            }
            if ( configValue.getQuestion( ) != null )
            {
                daoUtil.setInt( ++nIndex, configValue.getQuestion( ).getId( ) );
            }
            else
            {
                daoUtil.setInt( ++nIndex, -1 );
            }
            daoUtil.setString( ++nIndex, configValue.getValue( ) );
            daoUtil.setInt( ++nIndex, configValue.getUnit( ).getIdUnit( ) );
            daoUtil.setInt( ++nIndex, configValue.getOrder( ) );
            daoUtil.setString( ++nIndex, configValue.getCode( ) );
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
            if ( configValue.getStep( ) != null )
            {
                daoUtil.setInt( ++nIndex, configValue.getStep( ).getId( ) );
            }
            else
            {
                daoUtil.setInt( ++nIndex, -1 );
            }
            if ( configValue.getQuestion( ) != null )
            {
                daoUtil.setInt( ++nIndex, configValue.getQuestion( ).getId( ) );
            }
            else
            {
                daoUtil.setInt( ++nIndex, -1 );
            }
            daoUtil.setString( ++nIndex, configValue.getValue( ) );
            daoUtil.setInt( ++nIndex, configValue.getUnit( ).getIdUnit( ) );
            daoUtil.setInt( ++nIndex, configValue.getOrder( ) );
            daoUtil.setString( ++nIndex, configValue.getCode( ) );
            
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
        config.setCode( daoUtil.getString( ++nIndex ) );
        return config;
    }
}
