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

import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.Step;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;

public class UnitSelectionConfigValue
{
    private int _nIdConfigValue;
    private int _nIdConfig;
    private Step _step;
    private Question _question;
    private String _strValue;
    private Unit _unit;
    private int _nOrder;

    /**
     * @return the nIdConfigValue
     */
    public int getIdConfigValue( )
    {
        return _nIdConfigValue;
    }

    /**
     * @param nIdConfigValue
     *            the nIdConfigValue to set
     */
    public void setIdConfigValue( int nIdConfigValue )
    {
        _nIdConfigValue = nIdConfigValue;
    }

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
     * @return the strValue
     */
    public String getValue( )
    {
        return _strValue;
    }

    /**
     * @param strValue
     *            the strValue to set
     */
    public void setValue( String strValue )
    {
        _strValue = strValue;
    }

    /**
     * @return the Order
     */
    public int getOrder( )
    {
        return _nOrder;
    }

    /**
     * @param nOrder
     *            the Order to set
     */
    public void setOrder( int nOrder )
    {
        _nOrder = nOrder;
    }

    /**
     * @return the step
     */
    public Step getStep( )
    {
        return _step;
    }

    /**
     * @param step
     *            the step to set
     */
    public void setStep( Step step )
    {
        _step = step;
    }

    /**
     * @return the question
     */
    public Question getQuestion( )
    {
        return _question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion( Question question )
    {
        _question = question;
    }

    /**
     * @return the unit
     */
    public Unit getUnit( )
    {
        return _unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit( Unit unit )
    {
        _unit = unit;
    }
}
