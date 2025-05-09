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

import java.util.ArrayList;
import java.util.List;

public class UnitSelectionConfig
{
    private int _nIdConfig = -1;
    private int _nIdForm = -1;
    private int _nIdTask = -1;
    private boolean _bMultiform;
    private List<UnitSelectionConfigValue> _listConfigValues = new ArrayList<>( );

    /**
     * @return the nIdConfig
     */
    public int getIdConfig( )
    {
        return _nIdConfig;
    }

    /**
     * @param nIdConfig
     *            the nIdConfig to set
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
     * @param nIdForm
     *            the nIdForm to set
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
     * @param nIdTask
     *            the nIdTask to set
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
     * @param listConfigValues
     *            the listConfigValues to set
     */
    public void setListConfigValues( List<UnitSelectionConfigValue> listConfigValues )
    {
        this._listConfigValues = new ArrayList<>( listConfigValues );
    }

    /**
     * @return the bMultiform
     */
    public boolean isMultiform( )
    {
        return _bMultiform;
    }

    /**
     * @param bMultiform the bMultiform to set
     */
    public void setMultiform( boolean bMultiform )
    {
        _bMultiform = bMultiform;
    }

    public void addConfigValue( UnitSelectionConfigValue configValue )
    {
        configValue.setOrder( this._listConfigValues.size( ) + 1 );
        this._listConfigValues.add( configValue );
    }
    
    /**
     * update the config value
     * 
     * @param configValue the config value to update
     */
    public void updateConfigValue( UnitSelectionConfigValue configValue )
    {
        for ( int i = 0; i < this._listConfigValues.size( ); i++ ) 
        {
            if ( this._listConfigValues.get( i ).getOrder( ) == configValue.getOrder( ) ) 
			{
			    this._listConfigValues.set( i, configValue );
			    break;
            }
        }
    }
}
