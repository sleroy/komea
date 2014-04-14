/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.project;

import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class ProjectDeleteAction implements IDeleteAction<Project> {

    private final IProjectService projectService;
    private final List<Project> projectAffichage;

    public ProjectDeleteAction(IProjectService projectService , List<Project> _ProjectAffichage) {
        this.projectService = projectService;
        this.projectAffichage = _ProjectAffichage;
    }

    @Override
    public void delete(Project _object, AjaxRequestTarget _target) {
        projectService.deleteProject(_object);
        this.projectAffichage.remove(_object);
    }
    
}
