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
package fr.paris.lutece.plugins.forms.modules.unittree.web.form.panel.display.initializer.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.web.form.panel.display.initializer.impl.FormPanelFormResponseIdFilterDisplayInitialiser;
import fr.paris.lutece.plugins.unittree.business.assignment.UnitAssignment;
import fr.paris.lutece.plugins.unittree.business.assignment.UnitAssignmentHome;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.business.unit.UnitHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;

public class SubunitFormPanelDisplayInitialiser extends FormPanelFormResponseIdFilterDisplayInitialiser
{

    @Override
    protected List<Integer> getIdList( HttpServletRequest request )
    {
        Set<Integer> formReponseIdList = new HashSet<>( );
        AdminUser currentUser = AdminUserService.getAdminUser( request );
        if ( currentUser != null )
        {
            List<Unit> unitList = UnitHome.findByIdUser( currentUser.getUserId( ) );
            Set<Integer> setSubUnitId = new HashSet<>( );
            for ( Unit unit : unitList )
            {
                setSubUnitId.addAll( UnitHome.getAllSubUnitsId( unit.getIdUnit( ) ) );
            }

            for ( Integer id : setSubUnitId )
            {
                List<UnitAssignment> assignmentList = UnitAssignmentHome.findByUnit( id );
                formReponseIdList.addAll( assignmentList.stream( ).filter( assignement -> FormResponse.RESOURCE_TYPE.equals( assignement.getResourceType( ) ) )
                        .filter( UnitAssignment::isActive ).map( UnitAssignment::getIdResource ).distinct( ).collect( Collectors.toList( ) ) );
            }
        }
        return new ArrayList<>( formReponseIdList );
    }
}
