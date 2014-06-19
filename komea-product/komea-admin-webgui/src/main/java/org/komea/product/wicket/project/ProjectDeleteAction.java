/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.project;

import java.util.List;

import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 *
 * @author rgalerme
 */
public class ProjectDeleteAction extends AbstractDeleteAction<Project> {

    private final IProjectService projectService;
    private final List<Project> projectAffichage;

    public ProjectDeleteAction(IProjectService projectService , List<Project> _ProjectAffichage,StatelessLayoutPage page) {
         super(page, "dialogdelete");
        this.projectService = projectService;
        this.projectAffichage = _ProjectAffichage;
    }
    @Override
    public void deleteAction() {
         projectService.deleteProject(getObject());
        this.projectAffichage.remove(getObject());
    }
    
}
