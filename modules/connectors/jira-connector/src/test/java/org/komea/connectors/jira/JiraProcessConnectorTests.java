package org.komea.connectors.jira;

import groovy.transform.stc.FirstParam;
import java.io.IOException;
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
import org.komea.connectors.jira.utils.JiraServerFactory;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.software.model.impl.MinimalCompanySchema;
import org.komea.core.schema.IEntityType;
import org.mockito.Matchers;
import static org.mockito.Mockito.*;

public class JiraProcessConnectorTests {

    @Test
    public void testUpdateProcess() throws BadConfigurationException, IOException {

        IKomeaGraphStorage storage = mock(IKomeaGraphStorage.class);
        IKomeaEntity entity = mock(IKomeaEntity.class);
        when(storage.create(Matchers.any(IEntityType.class))).thenReturn(entity);

        JiraConfiguration config = new JiraConfiguration("https://jira.mongodb.org/");
        JiraSchema schema = new JiraSchema(new MinimalCompanySchema());

//        IKomeaGraphStorage storage = new OKomeaGraphStorage(schema.getSchema(), sessions.getGraphTx());
        JiraClient jiraClient = mock(JiraClient.class);
        IJiraServerFactory factory = mock(IJiraServerFactory.class);
        JiraServerContext jcontext = new JiraServerContext(jiraClient);
        when(factory.getNewJiraServerContext(config)).thenReturn(jcontext);

        List<Project> createProject = createProject(5, 0);
        try {
            when(jiraClient.getProjects()).thenReturn(createProject);
        } catch (JiraException ex) {
            Logger.getLogger(JiraProcessConnectorTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        JiraProcessConnector jc = new JiraProcessConnector(storage, schema, factory);

        jc.push(config);
        verify(storage, times(85)).create(Matchers.any(IEntityType.class));
        verify(entity, times(295)).set(Matchers.anyString(), Matchers.any());

    }

    private List<Project> createProject(int nb, int start) {
        List<Project> result = new ArrayList<>();
        for (int i = start; i < nb + start; i++) {
            Project projet = mock(Project.class);
            when(projet.getName()).thenReturn("nameproject" + i);
            when(projet.getDescription()).thenReturn("descriptionproject" + i);
            when(projet.getKey()).thenReturn("keyproject" + i);
            when(projet.getAssigneeType()).thenReturn("typeproject" + i);
            List<Component> createComponent = createComponent(nb, i + nb + start);
            when(projet.getComponents()).thenReturn(createComponent);
            User createUser = createUser("nameuser" + i);
            when(projet.getLead()).thenReturn(createUser);
            Map<String, String> createRoles = createRoles(nb, +i + nb + start);
            when(projet.getRoles()).thenReturn(createRoles);
            List<Version> createVersion = createVersion(nb, i + nb + start);
            when(projet.getVersions()).thenReturn(createVersion);
            result.add(projet);
        }
        return result;
    }

    private List<Component> createComponent(int nb, int start) {
        List<Component> result = new ArrayList<>();
        for (int i = start; i < nb + start; i++) {
            Component composant = mock(Component.class);
            when(composant.getName()).thenReturn("namecomponent" + i);
            when(composant.getDescription()).thenReturn("descriptioncomponent" + i);
            when(composant.isAssigneeTypeValid()).thenReturn(true);
            result.add(composant);
        }
        return result;
    }

    private List<Version> createVersion(int nb, int start) {
        List<Version> result = new ArrayList<>();
        for (int i = start; i < nb + start; i++) {
            Version version = mock(Version.class);
            when(version.getName()).thenReturn("nameversion" + i);
            when(version.getReleaseDate()).thenReturn("dateversion" + i);
            when(version.getDescription()).thenReturn("descriptionversion" + i);
            when(version.isArchived()).thenReturn(false);
            when(version.isReleased()).thenReturn(true);
            result.add(version);
        }
        return result;
    }

    private Map<String, String> createRoles(int nb, int start) {
        Map<String, String> result = new HashMap<String, String>();
        for (int i = start; i < nb + start; i++) {
            result.put("keyrole" + i, "namerole" + i);
        }
        return result;
    }

    private User createUser(String name) {
        User user = mock(User.class);
        when(user.getName()).thenReturn(name);
        when(user.getDisplayName()).thenReturn(name);
        when(user.isActive()).thenReturn(true);
        when(user.getEmail()).thenReturn(name + "@jojo.com");
        return user;
    }
}
