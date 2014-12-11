/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.service;

import com.tocea.jiraconnector.core.JiraServerContext;
import com.tocea.jiraconnector.core.KomeaServerContext;
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
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;

/**
 *
 * @author rgalerme
 */
public class JiraProcessConnector {

    private final JiraServerContext jiraContext;
    private final KomeaServerContext komeaContext;

    public JiraProcessConnector(JiraServerContext jiraContext, KomeaServerContext komeaContext) {
        this.jiraContext = jiraContext;
        this.komeaContext = komeaContext;
    }

    public void updateJiraOrganisation() {
        IKomeaGraphStorage newCompanyStorage = komeaContext.getNewCompanyStorage();
        try {
            List<Project> projects = jiraContext.getClient().getProjects();
            for (Project project : projects) {
                IKomeaEntity entityProjet = createProject(project, newCompanyStorage);
                User lead = project.getLead();
                if (lead != null) {
                    IKomeaEntity entityUser = createUser(lead, newCompanyStorage);
                    entityProjet.set("lead", entityUser);
                }

                List<Component> components = project.getComponents();
                for (Component component : components) {
                    IKomeaEntity createComponent = createComponent(component, newCompanyStorage);
                    entityProjet.add("components", createComponent);
                }

                List<Version> versions = project.getVersions();
                for (Version version : versions) {
                    IKomeaEntity createVersion = createVersion(version, newCompanyStorage);
                    entityProjet.add("verions", createVersion);
                }

                Map<String, String> roles = project.getRoles();
                Set<Map.Entry<String, String>> entrySet = roles.entrySet();
                for (Map.Entry<String, String> rolesSet : entrySet) {
                    IKomeaEntity createRole = createRole(rolesSet.getKey(), rolesSet.getValue(), newCompanyStorage);
                    entityProjet.add("roles", createRole);
                }

            }

        } catch (JiraException ex) {
            Logger.getLogger(JiraProcessConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String getString(String chaine) {
        if (chaine == null) {
            return "";
        } else {
            return chaine;
        }
    }
    
   

    private IKomeaEntity createProject(Project projet, IKomeaGraphStorage storage) {
        IKomeaEntity entityProjet = storage.create(komeaContext.getSchemaAPI().getProject());
        entityProjet.set("key", getString(projet.getKey()));
        entityProjet.set("name", getString(projet.getName()));
        entityProjet.set("description", getString(projet.getDescription()));
        entityProjet.set("assigneeType", getString(projet.getAssigneeType()));
        return entityProjet;
    }

    private IKomeaEntity createUser(User user, IKomeaGraphStorage storage) {
        IKomeaEntity entityUser = storage.create(komeaContext.getSchemaAPI().getUser());
        entityUser.set("name", getString(user.getName()));
        entityUser.set("email", getString(user.getEmail()));
        entityUser.set("displayName", getString(user.getDisplayName()));
        entityUser.set("active", user.isActive());
        return entityUser;
    }

    private IKomeaEntity createComponent(Component component, IKomeaGraphStorage storage) {
        IKomeaEntity entityComponent = storage.create(komeaContext.getSchemaAPI().getComponent());
        entityComponent.set("name", getString(component.getName()));
        entityComponent.set("description", getString(component.getDescription()));
        entityComponent.set("isAssignedTypeValid", component.isAssigneeTypeValid());

        return entityComponent;
    }

    private IKomeaEntity createRole(String roleKey, String roleValue, IKomeaGraphStorage storage) {
        IKomeaEntity entityRole = storage.create(komeaContext.getSchemaAPI().getRoles());
        entityRole.set("roleId", roleKey);
        entityRole.set("name", roleValue);
        return entityRole;
    }

    private IKomeaEntity createVersion(Version version, IKomeaGraphStorage storage) {
        IKomeaEntity entityVersion = storage.create(komeaContext.getSchemaAPI().getVersion());
        entityVersion.set("name", getString(version.getName()));
        entityVersion.set("releaseDate", getString(version.getReleaseDate()));
        entityVersion.set("description", getString(version.getDescription()));
        entityVersion.set("archived", version.isArchived());
        entityVersion.set("released", version.isReleased());
        return entityVersion;
    }

}
