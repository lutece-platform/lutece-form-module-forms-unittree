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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named( UnitSelectionConfigDao.BEAN_NAME )
public class UnitSelectionConfigDao implements IUnitSelectionConfigDao
{
    public static final String BEAN_NAME = "forms-unittree.unitSelectionConfigDao";

    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_config,id_form,id_task,is_multiform FROM forms_unittree_unit_selection_config ";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECT_ALL + " WHERE id_config = ?";
    private static final String SQL_QUERY_SELECT_BY_TASK = SQL_QUERY_SELECT_ALL + " WHERE id_task = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO forms_unittree_unit_selection_config ( id_form,id_task,is_multiform ) VALUES ( ? , ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM forms_unittree_unit_selection_config WHERE id_config = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE forms_unittree_unit_selection_config SET id_config = ? , id_form = ? , id_task = ?, is_multiform = ? WHERE id_config = ?";

    @Override
    public void insert( UnitSelectionConfig configValue, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, configValue.getIdForm( ) );
            daoUtil.setInt( ++nIndex, configValue.getIdTask( ) );
            daoUtil.setBoolean( ++nIndex, configValue.isMultiform( ) );
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
            daoUtil.setBoolean( ++nIndex, configValue.isMultiform( ) );
            
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
        config.setMultiform( daoUtil.getBoolean( ++nIndex ) );
        return config;
    }
}
