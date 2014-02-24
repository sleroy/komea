/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.project;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.kpis.KpiForm;

/**
 *
 * @author rgalerme
 */
public class ProjectEditPage extends LayoutPage {

    @SpringBean
    private IProjectService projectService;

    public ProjectEditPage(PageParameters _parameters) {
        this(_parameters, new Project());
    }

    public ProjectEditPage(PageParameters pageParameters, Project _object) {
        super(pageParameters);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
//        final KpiForm KpiForm = null;
//        new KpiForm(PARENT_PATH, _kpi, feedbackPanel, null)

        final ProjectForm projectForm = new ProjectForm("form",this.projectService, feedbackPanel, new CompoundPropertyModel<Project>(_object), this);
        add(projectForm);
    }
}
