/**
 *
 */

package org.komea.product.backend.api;

import org.komea.product.wicket.LoginPage;
import org.komea.product.wicket.UnauthorizedPage;
import org.komea.product.wicket.adminactions.AdminActionPage;
import org.komea.product.wicket.alert.AlertEditPage;
import org.komea.product.wicket.alert.AlertPage;
import org.komea.product.wicket.console.ConsolePage;
import org.komea.product.wicket.cronpage.CronPage;
import org.komea.product.wicket.customer.CustomerEditPage;
import org.komea.product.wicket.customer.CustomerPage;
import org.komea.product.wicket.events.EventsPage;
import org.komea.product.wicket.kpichart.KpiChartPage;
import org.komea.product.wicket.kpis.KpiEditPage;
import org.komea.product.wicket.kpis.KpiPage;
import org.komea.product.wicket.kpivalues.KpiValuesPage;
import org.komea.product.wicket.person.PersonAddPage;
import org.komea.product.wicket.person.PersonPage;
import org.komea.product.wicket.persongroup.department.DepartmentEditPage;
import org.komea.product.wicket.persongroup.department.DepartmentPage;
import org.komea.product.wicket.persongroup.team.TeamEditPage;
import org.komea.product.wicket.persongroup.team.TeamPage;
import org.komea.product.wicket.project.ProjectEditPage;
import org.komea.product.wicket.project.ProjectPage;
import org.komea.product.wicket.providers.ProviderPage;
import org.komea.product.wicket.settings.SettingsPage;
import org.komea.product.wicket.statistics.StatPage;

/**
 * This class defines the web pages of the administration web site
 * 
 * @author sleroy
 */
@MountAdminPages({ @MountPage(mount = "/departments", page = DepartmentPage.class),
        @MountPage(mount = "/saveDepartment", page = DepartmentEditPage.class),
        @MountPage(mount = "/teams", page = TeamPage.class),
        @MountPage(mount = "/saveTeam", page = TeamEditPage.class), @MountPage(mount = "/kpis", page = KpiPage.class),
        @MountPage(mount = "/saveKpi", page = KpiEditPage.class),
        @MountPage(mount = "/saveProject", page = ProjectEditPage.class),
        @MountPage(mount = "/projects", page = ProjectPage.class),
        @MountPage(mount = "/settings", page = SettingsPage.class),
        @MountPage(mount = "/viewlog", page = ConsolePage.class), @MountPage(mount = "/stats", page = StatPage.class),
        @MountPage(mount = "/login", page = LoginPage.class), @MountPage(mount = "/cronpage", page = CronPage.class),
        @MountPage(mount = "/accessdenied", page = UnauthorizedPage.class),
        @MountPage(mount = "/users", page = PersonPage.class),
        @MountPage(mount = "/saveMember", page = PersonAddPage.class),
        @MountPage(mount = "/events", page = EventsPage.class),
        @MountPage(mount = "/plugins", page = ProviderPage.class),
        @MountPage(mount = "/customers", page = CustomerPage.class),
        @MountPage(mount = "/saveCustomer", page = CustomerEditPage.class),
        @MountPage(mount = "/alerts", page = AlertPage.class),
        @MountPage(mount = "/saveAlert", page = AlertEditPage.class),
        @MountPage(mount = "/kpiview", page = KpiChartPage.class),
        @MountPage(mount = "/kpivalues", page = KpiValuesPage.class),
        @MountPage(mount = "/adminactions", page = AdminActionPage.class) })
public class AdministrationWebSite {
	//
}
