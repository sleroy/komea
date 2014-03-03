/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.department;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.project.ProjectForm;

/**
 *
 * @author rgalerme
 */
public final class DepartmentEditPage extends LayoutPage {

    @SpringBean
    private IPersonGroupService prService;

    public DepartmentEditPage(PageParameters _parameters) {
        this(_parameters, new PersonGroup());
    }

    public DepartmentEditPage(PageParameters _parameters, PersonGroup _personGroup) {
        super(_parameters);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        final DepartmentForm departmentForm = new DepartmentForm("form", prService, feedbackPanel, new CompoundPropertyModel<PersonGroup>(_personGroup), this);
        add(departmentForm);
    }

}
