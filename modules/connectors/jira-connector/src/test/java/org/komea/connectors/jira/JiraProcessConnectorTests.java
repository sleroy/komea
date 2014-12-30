package org.komea.connectors.jira;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.Component;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Project;
import net.rcarz.jiraclient.User;
import net.rcarz.jiraclient.Version;

import org.junit.Test;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.schema.JiraSchema;
import org.komea.connectors.jira.utils.IJiraServerFactory;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.software.model.impl.MinimalCompanySchema;
import org.mockito.Matchers;

public class JiraProcessConnectorTests {

	@Test
	public void testUpdateProcess() throws BadConfigurationException {

		final IKomeaGraphStorage storage = mock(IKomeaGraphStorage.class);
		final IKomeaEntity entity = mock(IKomeaEntity.class);
		when(storage.create(Matchers.any(IEntityType.class))).thenReturn(entity);

		final JiraConfiguration config = new JiraConfiguration("https://jira.mongodb.org/");
		final JiraSchema schema = new JiraSchema(new MinimalCompanySchema());

		// IKomeaGraphStorage storage = new
		// OKomeaGraphStorage(schema.getSchema(), sessions.getGraphTx());
		final JiraClient jiraClient = mock(JiraClient.class);
		final IJiraServerFactory factory = mock(IJiraServerFactory.class);
		final JiraServerContext jcontext = new JiraServerContext(jiraClient);
		when(factory.getNewJiraServerContext(config)).thenReturn(jcontext);

		final List<Project> createProject = createProject(5, 0);
		try {
			when(jiraClient.getProjects()).thenReturn(createProject);
		} catch (final JiraException ex) {
			Logger.getLogger(JiraProcessConnectorTests.class.getName()).log(Level.SEVERE, null, ex);
		}

		final JiraProcessConnector jc = new JiraProcessConnector(storage, schema, factory);

		jc.push(config);
		verify(storage, times(85)).create(Matchers.any(IEntityType.class));
		verify(entity, times(295)).set(Matchers.anyString(), Matchers.any());

	}

	private List<Component> createComponent(int nb, int start) {
		final List<Component> result = new ArrayList<>();
		for (int i = start; i < nb + start; i++) {
			final Component composant = mock(Component.class);
			when(composant.getName()).thenReturn("namecomponent" + i);
			when(composant.getDescription()).thenReturn("descriptioncomponent" + i);
			when(composant.isAssigneeTypeValid()).thenReturn(true);
			result.add(composant);
		}
		return result;
	}

	private List<Project> createProject(int nb, int start) {
		final List<Project> result = new ArrayList<>();
		for (int i = start; i < nb + start; i++) {
			final Project projet = mock(Project.class);
			when(projet.getName()).thenReturn("nameproject" + i);
			when(projet.getDescription()).thenReturn("descriptionproject" + i);
			when(projet.getKey()).thenReturn("keyproject" + i);
			when(projet.getAssigneeType()).thenReturn("typeproject" + i);
			final List<Component> createComponent = createComponent(nb, i + nb + start);
			when(projet.getComponents()).thenReturn(createComponent);
			final User createUser = createUser("nameuser" + i);
			when(projet.getLead()).thenReturn(createUser);
			final Map<String, String> createRoles = createRoles(nb, +i + nb + start);
			when(projet.getRoles()).thenReturn(createRoles);
			final List<Version> createVersion = createVersion(nb, i + nb + start);
			when(projet.getVersions()).thenReturn(createVersion);
			result.add(projet);
		}
		return result;
	}

	private Map<String, String> createRoles(int nb, int start) {
		final Map<String, String> result = new HashMap<String, String>();
		for (int i = start; i < nb + start; i++) {
			result.put("keyrole" + i, "namerole" + i);
		}
		return result;
	}

	private User createUser(String name) {
		final User user = mock(User.class);
		when(user.getName()).thenReturn(name);
		when(user.getDisplayName()).thenReturn(name);
		when(user.isActive()).thenReturn(true);
		when(user.getEmail()).thenReturn(name + "@jojo.com");
		return user;
	}

	private List<Version> createVersion(int nb, int start) {
		final List<Version> result = new ArrayList<>();
		for (int i = start; i < nb + start; i++) {
			final Version version = mock(Version.class);
			when(version.getName()).thenReturn("nameversion" + i);
			when(version.getReleaseDate()).thenReturn("dateversion" + i);
			when(version.getDescription()).thenReturn("descriptionversion" + i);
			when(version.isArchived()).thenReturn(false);
			when(version.isReleased()).thenReturn(true);
			result.add(version);
		}
		return result;
	}
}
