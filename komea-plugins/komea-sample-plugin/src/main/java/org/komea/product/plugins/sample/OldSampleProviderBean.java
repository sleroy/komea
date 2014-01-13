package org.komea.product.plugins.sample;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tocea.scertify.ci.flow.app.alert.AlertBuilder;
import com.tocea.scertify.ci.flow.app.api.plugin.IAlertReactor;
import com.tocea.scertify.ci.flow.app.api.service.model.IDepartmentService;
import com.tocea.scertify.ci.flow.app.api.service.model.IProjectService;
import com.tocea.scertify.ci.flow.app.api.service.model.ITeamService;
import com.tocea.scertify.ci.flow.app.api.service.model.IUserService;
import com.tocea.scertify.ci.flow.app.model.Project;

@Component
public class OldSampleProviderBean {

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

	@Autowired
	private InitialisationCustomDataBean initialisation;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OldSampleProviderBean.class);

	public OldSampleProviderBean() {

		super();
		LOGGER.info("Initialisation de données d'exemple");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.data.IDataModelDAO#newCategory(java.lang.Object
	 * )
	 */

	@Scheduled(fixedRate = 4500)
	public void buildJenkinsEvents() {

		final List<Project> findAll = projectR.findAll();
		if (findAll.isEmpty()) {
			return;
		}
		final int nb = Math.max(1, findAll.size());

		AlertBuilder
				.create()
				.setProvider("JENKINS")
				.setIcon("/static/pics/jenkins.png")
				.setCategory("BUILD")
				.setDate(new Date())
				.setCriticity("INFO")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setBothMessage(
						"Build started at "
								+ new PrettyTime().format(new Date()))
				.setAlertType("BUILD_STARTED")
				.setValue(new Random().nextInt(50000)).send(esperEngine);
		if (new Random().nextBoolean()) {
			AlertBuilder
					.create()
					.setProvider("JENKINS")
					.setIcon("/static/pics/jenkins.png")
					.setCategory("BUILD")
					.setDate(new Date())
					.setCriticity("INFO")
					.setProject(findAll.get(new Random().nextInt(nb)).getName())
					.setBothMessage(
							"Build finished at "
									+ new PrettyTime().format(new Date()))
					.setAlertType("BUILD_PERFORMED")
					.setValue(new Random().nextInt(50000)).send(esperEngine);
		} else {
			AlertBuilder
					.create()
					.setProvider("JENKINS")
					.setCategory("BUILD")
					.setDate(new Date())
					.setCriticity("INFO")
					.setIcon("/static/pics/jenkins.png")
					.setProject(findAll.get(new Random().nextInt(nb)).getName())
					.setBothMessage(
							"Build failed at "
									+ new PrettyTime().format(new Date()))
					.setAlertType("BUILD_FAILED")
					.setValue(new Random().nextInt(50000)).send(esperEngine);
		}
	}

	@Scheduled(fixedRate = 4500)
	public void buildScmEvents() {

		final List<Project> findAll = projectR.findAll();
		if (findAll.isEmpty()) {
			return;
		}
		final int nb = Math.max(1, findAll.size());

		AlertBuilder
				.create()
				.setProvider("GIT")
				.setCategory("SCM")
				.setDate(new Date())
				.setCriticity("INFO")
				.setIcon("/static/pics/git.png")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setBothMessage(
						"Commit performed on git at "
								+ new PrettyTime().format(new Date()))
				.setAlertType("GIT_INDEX_CHANGED")
				.setValue(new Random().nextInt(50000)).send(esperEngine);

		AlertBuilder.create().setProvider("GIT").setCategory("SCM")
				.setIcon("/static/pics/git.png").setDate(new Date())
				.setCriticity("INFO")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setBothMessage("Stat arrived for number of branches")
				.setAlertType("GIT_NUMBER_BRANCHES")
				.setValue(new Random().nextInt(10)).send(esperEngine);
		AlertBuilder.create().setProvider("GIT").setCategory("SCM")
				.setDate(new Date()).setCriticity("INFO")
				.setBothMessage("Stat arrived for number of customer branches")
				.setIcon("/static/pics/git.png")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setAlertType("GIT_NUMBER_CUSTOMER_BRANCHES")
				.setValue(new Random().nextInt(10)).send(esperEngine);
		AlertBuilder.create().setProvider("GIT").setCategory("SCM")
				.setDate(new Date()).setCriticity("INFO")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setIcon("/static/pics/git.png")
				.setBothMessage("Stat arrived for customer tags")
				.setAlertType("GIT_NUMBER_CUSTOMER_TAGS")
				.setValue(new Random().nextInt(10)).send(esperEngine);
	}

	@Scheduled(fixedRate = 5000)
	public void buildSonarEvents() {

		final List<Project> findAll = projectR.findAll();
		if (findAll.isEmpty()) {
			return;
		}
		final int nb = Math.max(1, findAll.size());

		AlertBuilder.create().setProvider("SONAR").setCategory("QUALITY")
				.setDate(new Date())
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setCriticity("INFO").setIcon("/static/pics/sonar.png")
				.setBothMessage("Audit performed with SONAR")
				.setAlertType("AUDIT_PERFORMED").send(esperEngine);

		final int value = new Random().nextInt(20) + 80;
		AlertBuilder.create().setProvider("SONAR").setCategory("QUALITY")
				.setDate(new Date()).setCriticity("INFO")
				.setIcon("/static/pics/sonar.png")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setAlertType("QUALITY_VALUE")
				.setBothMessage("Quality of the project is " + value)
				.setValue(value).send(esperEngine);

		AlertBuilder.create().setProvider("SONAR").setCategory("QUALITY")
				.setDate(new Date()).setCriticity("INFO")
				.setIcon("/static/pics/sonar.png")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setBothMessage("Testability of the project is " + value)
				.setAlertType("TESTABILITY_VALUE").setValue(value)
				.send(esperEngine);

		final int value2 = 100 - new Random().nextInt(200);
		AlertBuilder
				.create()
				.setProvider("SONAR")
				.setIcon("/static/pics/sonar.png")
				.setIcon("/static/pics/sonar.png")
				.setCategory("QUALITY")
				.setDate(new Date())
				.setCriticity("INFO")
				.setProject(findAll.get(new Random().nextInt(nb)).getName())
				.setBothMessage("Bug Progression for this project is " + value2)
				.setAlertType("BUG_PROGRESSION").setValue(value2)
				.send(esperEngine);
	}

	public IProjectService getProjectrepository() {

		return projectrepository;
	}

	//
	// public AlertProviderPlugin newSonarProvider(final String _string) {
	// final AlertProviderPlugin alertProviderPlugin = new
	// AlertProviderPlugin();
	// alertProviderPlugin.setName("SONAR");
	// alertProviderPlugin.setCategory(categoryrepository
	// .findByName("QUALITY"));
	// final ArrayList<AlertType> alertTypes = new ArrayList<AlertType>();
	// alertTypes.add(new AlertType(alertProviderPlugin, "AUDIT_PERFORMED"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "QUALITY_VALUE"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "TESTABILITY_VALUE"));
	// alertTypes.add(new AlertType(alertProviderPlugin, "BUG_PROGRESSION"));
	//
	// alertProviderPlugin.setAlertTypes(alertTypes);
	// return alertProviderPlugin;
	// }

	public IUserService getUserrepository() {

		return userrepository;
	}

	public void setProjectrepository(final IProjectService _projectrepository) {

		projectrepository = _projectrepository;
	}

	public void setUserrepository(final IUserService _userrepository) {

		userrepository = _userrepository;
	}
}
