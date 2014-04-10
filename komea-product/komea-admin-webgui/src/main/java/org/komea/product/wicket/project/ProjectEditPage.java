/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.project;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.CustomerCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

/**
 *
 * @author rgalerme
 */
public class ProjectEditPage extends LayoutPage {

    @SpringBean
    private IProjectService projectService;

    @SpringBean
    private CustomerDao customerDao;

    @SpringBean
    private IPersonService personService;

    @SpringBean
    private IPersonGroupService personGroupService;

    public ProjectEditPage(PageParameters _parameters) {
        this(_parameters, new Project(), true);
    }

    public ProjectEditPage(PageParameters pageParameters, Project _object) {
        this(pageParameters, _object, false);
    }

    private ProjectEditPage(PageParameters pageParameters, Project _object, boolean _isNew) {
        super(pageParameters);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
//        final KpiForm KpiForm = null;
//        new KpiForm(PARENT_PATH, _kpi, feedbackPanel, null)

        final ProjectForm projectForm = new ProjectForm(_isNew, "form", personService, personGroupService, this.projectService, customerDao, feedbackPanel, new CompoundPropertyModel<Project>(_object), this);
        add(projectForm);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Dialog provider //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<IHasKey> selectByCriteria = (List<IHasKey>) (List<?>) customerDao.selectByCriteria(new CustomerCriteria());
        final SelectDialog dialogCustomer;
        dialogCustomer = new SelectDialog("dialogCustomer", "Choose a customer", selectByCriteria) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
//                target.add(kpiForm);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                IHasKey selectedCustomer = getSelected();
                if (selectedCustomer != null) {
                    projectForm.getProject().setIdCustomer(selectedCustomer.getId());
                    projectForm.getCustomerName().setName(selectedCustomer.getDisplayName());
                } else {
                    projectForm.getProject().setIdCustomer(null);
                    projectForm.getCustomerName().setName("");
                }
                projectForm.getCustomerFiel().clearInput();
                target.add(projectForm.getCustomerFiel());
            }

        };

        this.add(dialogCustomer);
        // Buttons //
        projectForm.add(new AjaxLinkLayout<LayoutPage>("openDialogCustomer", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogCustomer.open(art);

            }
        });
    }
}
