package org.komea.product.plugins.sample;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tocea.scertify.ci.flow.app.api.plugin.IAlertReactor;
import com.tocea.scertify.ci.flow.app.api.service.model.IDepartmentService;
import com.tocea.scertify.ci.flow.app.api.service.model.IProjectService;
import com.tocea.scertify.ci.flow.app.api.service.model.ITeamService;
import com.tocea.scertify.ci.flow.app.api.service.model.IUserService;
import com.tocea.scertify.ci.flow.app.model.Department;
import com.tocea.scertify.ci.flow.app.model.Project;
import com.tocea.scertify.ci.flow.app.model.Team;
import com.tocea.scertify.ci.flow.app.model.User;

@Component
public class InitialisationCustomDataBean {

	@Autowired
	private IAlertReactor esperEngine;

	@Autowired
	private IUserService repository;

	@Autowired
	private IProjectService projectR;

	@Autowired
	private IUserService userrepository;

	@Autowired
	private IProjectService projectrepository;

	@Autowired
	private ITeamService teamService;

	@Autowired
	private IDepartmentService depService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(InitialisationCustomDataBean.class);

	/**
	 * Initialises the data model
	 */
	@Transactional
	@PostConstruct
	public void init() {

		LOGGER.info("Initialisation with default data");

		saveProject(newProject(1, "SCERTIFY"));
		saveProject(newProject(1, "XSTREAM"));
		saveProject(newProject(1, "SRA"));
		saveProject(newProject(1, "FRAMEWORKS"));
		saveProject(newProject(1, "CIFLOW"));

		saveUser("sleroy", "Sylvain", "Leroy");
		saveUser("afloch", "Antoine", "Floch");

		final Team team = saveTeam("Team1");

		final Department saveDepartment = saveDepartment("Production", false);
		saveDepartment.getTeams().add(team);
		team.setDepartment(saveDepartment);

		depService.save(saveDepartment);
		teamService.save(team);

		// basicStorage.getStorage().getTypes()
		// .put(1, newAlertType(1, 2, "GIT_INDEX_CHANGED"));
		// basicStorage.getStorage().getTypes()
		// .put(2, newAlertType(2, 1, "BUILD_PERFORMED"));
		// basicStorage.getStorage().getTypes()
		// .put(1, newAlertType(3, 2, "GIT_CONFIG_CHANGED"));
		// basicStorage.getStorage().getTypes()
		// .put(1, newAlertType(4, 2, "GIT_REFS_CHANGED"));
		//
		// basicStorage.getStorage().getTypes()
		// .put(3, newAlertType(5, 1, "BUILD_STILL_FAILING"));
		// basicStorage.getStorage().getTypes()
		// .put(4, newAlertType(6, 3, "AUDIT_FAILED"));
		// basicStorage.getStorage().getTypes()
		// .put(7, newAlertType(7, 5, "NO_INFORMATION"));
	}

	// public AlertType newAlertType(final AlertProviderPlugin _provider,
	// final String _alertType) {
	// final AlertType item = new AlertType();
	// item.setProvider(_provider);
	// item.setName(_alertType);
	// return item;
	// }
	//
	// public AlertProviderPlugin newBugGenieProvider(final String _string) {
	// final AlertProviderPlugin alertProviderPlugin = new
	// AlertProviderPlugin();
	// alertProviderPlugin.setName("THEBUGGENIE");
	// alertProviderPlugin.setCategory(categoryrepository
	// .findByName("BUGTRACKER"));
	// final ArrayList<AlertType> alertTypes = new ArrayList<AlertType>();
	// alertTypes.add(new AlertType(alertProviderPlugin, "NEW_TICKET"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "TICKET_FINISHED"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "TICKET_UPDATED"));
	// alertTypes.add(new AlertType(alertProviderPlugin,
	// "NUMBER_PROJECT_RELEASES"));
	// alertTypes.add(new AlertType(alertProviderPlugin,
	// "NEW_PROJECT_RELEASES"));
	// alertTypes.add(new AlertType(alertProviderPlugin,
	// "NUMBER_PROJECT_VERSIONS"));
	// alertTypes.add(new AlertType(alertProviderPlugin,
	// "NEW_PROJECT_VERSIONS"));
	// alertTypes.add(new AlertType(alertProviderPlugin,
	// "NUMBER_PROJECT_TICKET"));
	//
	// alertProviderPlugin.setAlertTypes(alertTypes);
	// return alertProviderPlugin;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// *
	// com.tocea.scertify.ci.flow.data.IDataModelDAO#newProject(java.lang.Object
	// * )
	// */
	//
	// public AlertCategory newCategory(final Object... args) {
	// final AlertCategory alertCategory = new AlertCategory();
	// alertCategory.setProviderPlugins(new HashSet<AlertProviderPlugin>());
	// alertCategory.setName((String) args[1]);
	// return alertCategory;
	// }
	//
	// public Criticity newCriticity(final Object... args) {
	// final Criticity item = new Criticity();
	// item.setCriticityValue((Integer) args[0]);
	// item.setName((String) args[1]);
	// return item;
	// }
	//
	// public AlertProviderPlugin newIntegrationProvider(final String _string) {
	//
	// final AlertProviderPlugin alertProviderPlugin = new
	// AlertProviderPlugin();
	// alertProviderPlugin.setName("AGILEFANT");
	// alertProviderPlugin.setCategory(categoryrepository
	// .findByName("FUNCTIONAL"));
	// final ArrayList<AlertType> alertTypes = new ArrayList<AlertType>();
	// alertTypes.add(new AlertType(alertProviderPlugin, "NEW_STORY"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "STORY_MODIFIED"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "STORY_FINISHED"));
	//
	// alertProviderPlugin.setAlertTypes(alertTypes);
	// return alertProviderPlugin;
	// }
	//
	// public AlertProviderPlugin newJenkinsProvider() {
	// final AlertProviderPlugin alertProviderPlugin = new
	// AlertProviderPlugin();
	// alertProviderPlugin.setName("JENKINS");
	// alertProviderPlugin.setCategory(categoryrepository.findByName("BUILD"));
	// final ArrayList<AlertType> alertTypes = new ArrayList<AlertType>();
	// alertTypes.add(new AlertType(alertProviderPlugin, "BUILD_STARTED"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "BUILD_PERFORMED"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "BUILD_FAILED"));
	//
	// alertProviderPlugin.setAlertTypes(alertTypes);
	// return alertProviderPlugin;
	// }
	//
	public Project newProject(final Object... args) {

		final Project item = new Project();
		item.setName((String) args[1]);
		if (args.length > 2) {
			item.setDashboardURL((String) args[2]);
		}
		if (args.length > 3) {
			item.setCiURL((String) args[3]);
		}
		if (args.length > 4) {
			item.setBugTrackerURL((String) args[4]);
		}

		return item;
	}

	public void saveProject(final Project _newProject) {

		projectrepository.save(_newProject);
	}

	public void saveUser(final String _username, final String _firstname,
			final String _lastname) {

		final User entity = new User();
		entity.setUserName(_username);
		entity.setFirstName(_firstname);
		entity.setLastName(_lastname);
		;

		userrepository.save(entity);

	}

	private Department saveDepartment(final String _departmentName,
			final boolean _accros) {

		final Department departmentRefDTO = new Department();
		departmentRefDTO.setName(_departmentName);
		departmentRefDTO.setAccross(_accros);
		return departmentRefDTO;

	}

	private Team saveTeam(final String _string) {

		final Team team = new Team();
		team.setName("Team 1");
		team.setAccross(false);
		team.getUsers().add(userrepository.findByUserName("sleroy"));
		team.getUsers().add(userrepository.findByUserName("afloch"));

		teamService.save(team);
		return team;

	}

}
