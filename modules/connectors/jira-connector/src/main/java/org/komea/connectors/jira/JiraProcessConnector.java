
package org.komea.connectors.jira;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.Component;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Project;
import net.rcarz.jiraclient.User;
import net.rcarz.jiraclient.Version;

import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.schema.JiraSchema;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;

public class JiraProcessConnector
{
    
    private final IKomeaGraphStorage storage;
    private final JiraSchema         schema;
    
    public JiraProcessConnector(final IKomeaGraphStorage storage, final JiraSchema schema) {
    
        super();
        this.storage = storage;
        this.schema = schema;
        
    }
    
    public void push(final JiraConfiguration configuration) throws BadConfigurationException {
    
       
        try {
            JiraServerContext jira = new JiraServerContext(configuration);
            List<Project> projects = jira.getClient().getProjects();
            for (Project project : projects) {
                IKomeaEntity entityProjet = createProject(project);
                User lead = project.getLead();
                if (lead != null) {
                    IKomeaEntity entityUser = createUser(lead);
                    entityProjet.set("lead", entityUser);
                }
                
                List<Component> components = project.getComponents();
                for (Component component : components) {
                    IKomeaEntity createComponent = createComponent(component);
                    entityProjet.add("components", createComponent);
                }
                
                List<Version> versions = project.getVersions();
                for (Version version : versions) {
                    IKomeaEntity createVersion = createVersion(version);
                    entityProjet.add("verions", createVersion);
                }
                
                Map<String, String> roles = project.getRoles();
                Set<Map.Entry<String, String>> entrySet = roles.entrySet();
                for (Map.Entry<String, String> rolesSet : entrySet) {
                    IKomeaEntity createRole = createRole(rolesSet.getKey(), rolesSet.getValue());
                    entityProjet.add("roles", createRole);
                }
                
            }
            
        } catch (JiraException ex) {
            Logger.getLogger(JiraProcessConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private String getString(final String chaine) {
    
        if (chaine == null) {
            return "";
        } else {
            return chaine;
        }
    }
    
    private IKomeaEntity createProject(final Project projet) {
    
        IKomeaEntity entityProjet = this.storage.create(this.schema.getProject());
        entityProjet.set("key", getString(projet.getKey()));
        entityProjet.set("name", getString(projet.getName()));
        entityProjet.set("description", getString(projet.getDescription()));
        entityProjet.set("assigneeType", getString(projet.getAssigneeType()));
        return entityProjet;
    }
    
    private IKomeaEntity createUser(final User user) {
    
        IKomeaEntity entityUser = this.storage.create(this.schema.getUser());
        entityUser.set("name", getString(user.getName()));
        entityUser.set("email", getString(user.getEmail()));
        entityUser.set("displayName", getString(user.getDisplayName()));
        entityUser.set("active", user.isActive());
        return entityUser;
    }
    
    private IKomeaEntity createComponent(final Component component) {
    
        IKomeaEntity entityComponent = this.storage.create(this.schema.getComponent());
        entityComponent.set("name", getString(component.getName()));
        entityComponent.set("description", getString(component.getDescription()));
        entityComponent.set("isAssignedTypeValid", component.isAssigneeTypeValid());
        
        return entityComponent;
    }
    
    private IKomeaEntity createRole(final String roleKey, final String roleValue) {
    
        IKomeaEntity entityRole = this.storage.create(this.schema.getRoles());
        entityRole.set("roleId", roleKey);
        entityRole.set("name", roleValue);
        return entityRole;
    }
    
    private IKomeaEntity createVersion(final Version version) {
    
        IKomeaEntity entityVersion = this.storage.create(this.schema.getVersion());
        entityVersion.set("name", getString(version.getName()));
        entityVersion.set("releaseDate", getString(version.getReleaseDate()));
        entityVersion.set("description", getString(version.getDescription()));
        entityVersion.set("archived", version.isArchived());
        entityVersion.set("released", version.isReleased());
        return entityVersion;
    }
}
