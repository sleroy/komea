/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.project;

import org.komea.product.database.model.Project;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class ProjectEditAction implements IEditAction<Project> {

    private final ProjectPage projectPage;

    public ProjectEditAction(ProjectPage projectPage) {
        this.projectPage = projectPage;
    }

    @Override
    public void selected(Project _object) {
        this.projectPage.setResponsePage(new ProjectEditPage(this.projectPage.getPageParameters(),_object));
    }

}
