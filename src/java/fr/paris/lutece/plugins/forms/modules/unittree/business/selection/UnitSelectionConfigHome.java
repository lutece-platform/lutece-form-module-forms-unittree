/*
 * Copyright (c) 2002-2020, City of Paris
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
     * 
     * @param config
     */
    public static void create( UnitSelectionConfig config )
    {
        _dao.insert( config, _plugin );
    }

    /**
     * Update the record in the table.
     * 
     * @param config
     */
    public static void update( UnitSelectionConfig config )
    {
        _dao.store( config, _plugin );
    }

    /**
     * Delete a record from the table
     * 
     * @param nIdConfig
     */
    public static void remove( int nIdConfig )
    {
        _dao.delete( nIdConfig, _plugin );
    }

    /**
     * Load the data from the table.
     * 
     * @param nTaskId
     * @return
     */
    public static UnitSelectionConfig findByTaskId( int nTaskId )
    {
        return _dao.selectByTaskId( nTaskId, _plugin );
    }

    /**
     * Load the data from the table.
     * 
     * @param nKey
     * @return
     */
    public static UnitSelectionConfig findById( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
}
