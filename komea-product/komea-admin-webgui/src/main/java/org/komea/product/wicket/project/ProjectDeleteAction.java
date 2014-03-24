/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.project;

import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class ProjectDeleteAction implements IDeleteAction<Project> {

    private final IProjectService projectService;

    public ProjectDeleteAction(IProjectService projectService) {
        this.projectService = projectService;
    }
    
    
    
    @Override
    public void delete(Project _object) {
        projectService.deleteProject(_object);
    }
    
}
