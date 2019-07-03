package fr.paris.lutece.plugins.forms.modules.unittree.web.form.panel.display.initializer.impl;

import java.util.ArrayList;
import java.util.List;
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

public class UnittreeFormPanelDisplayInitialiser extends FormPanelFormResponseIdFilterDisplayInitialiser {

	@Override
	protected List<Integer> getIdList(HttpServletRequest request) {
		List<Integer> formReponseIdList = new ArrayList<>( );
		AdminUser currentUser = AdminUserService.getAdminUser( request );
		if ( currentUser != null ) {
			List<Unit> unitList = UnitHome.findByIdUser( currentUser.getUserId( ) );
			
			for ( Unit unit : unitList )
			{
				List<UnitAssignment> assignmentList = UnitAssignmentHome.findByUnit( unit.getIdUnit( ) );
				formReponseIdList.addAll(
						assignmentList.stream( )
						.filter( assignement -> FormResponse.RESOURCE_TYPE.equals( assignement.getResourceType( ) ) )
						.filter( UnitAssignment::isActive )
						.map( UnitAssignment::getIdResource )
						.distinct( )
						.collect( Collectors.toList( ) ) );
			}
		}
		return formReponseIdList;
	}
}
