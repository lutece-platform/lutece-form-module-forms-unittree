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
package fr.paris.lutece.plugins.forms.modules.unittree.service.impl;

import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfig;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigHome;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValue;
import fr.paris.lutece.plugins.forms.modules.unittree.business.selection.UnitSelectionConfigValueHome;
import fr.paris.lutece.plugins.forms.modules.unittree.service.IUnitSelectionConfigService;

public class UnitSelectionConfigService implements IUnitSelectionConfigService
{

    public static final String BEAN_NAME = "forms-unittree.unitSelectionConfigService";

    @Override
    public UnitSelectionConfig loadConfigByTask( int nITask )
    {
        UnitSelectionConfig config = UnitSelectionConfigHome.findByTaskId( nITask );
        if ( config != null )
        {
            config.setListConfigValues( UnitSelectionConfigValueHome.findByConfig( config.getIdConfig( ) ) );
        }
        return config;
    }

    @Override
    public void deleteConfigByTask( int nITask )
    {
        UnitSelectionConfig config = UnitSelectionConfigHome.findByTaskId( nITask );
        if ( config != null )
        {
            UnitSelectionConfigValueHome.removeByConfig( config.getIdConfig( ) );
            UnitSelectionConfigHome.remove( config.getIdConfig( ) );
        }
    }

    @Override
    public void createConfig( UnitSelectionConfig config )
    {
        UnitSelectionConfigHome.create( config );
        for ( UnitSelectionConfigValue configValue : config.getListConfigValues( ) )
        {
            configValue.setIdConfig( config.getIdConfig( ) );
            UnitSelectionConfigValueHome.create( configValue );
        }
    }

    @Override
    public void updateConfig( UnitSelectionConfig config )
    {
        UnitSelectionConfigValueHome.removeByConfig( config.getIdConfig( ) );
        UnitSelectionConfigHome.update( config );
        for ( UnitSelectionConfigValue configValue : config.getListConfigValues( ) )
        {
            configValue.setIdConfig( config.getIdConfig( ) );
            UnitSelectionConfigValueHome.create( configValue );
        }
    }
}
