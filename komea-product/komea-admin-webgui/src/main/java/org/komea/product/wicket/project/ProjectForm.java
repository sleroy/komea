/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.project;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.kpis.KpiPage;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.utils.SelectMultipleDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class ProjectForm extends Form<Project> {

    private final IProjectService prService;
    private final Component feedBack;
    private final LayoutPage page;
    private final Project project;
    private final NameGeneric customerName;
    private final TextField customerFiel;
    private final IPersonService personService;
    private final IPersonGroupService personGroupService;
    private List<IEntity> currentPersonGroupList;
    private List<IEntity> selectedPersonGroup;
    private List<IEntity> currentPersonList;
    private List<IEntity> selectedPerson;

    public ProjectForm(String form,
            IPersonService _personService,
            IPersonGroupService _personGroupService,
            IProjectService projectService,
            CustomerDao _customerService,
            FeedbackPanel feedbackPanel,
            CompoundPropertyModel<Project> compoundPropertyModel,
            ProjectEditPage aThis) {

        super(form, compoundPropertyModel);
        this.prService = projectService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.project = compoundPropertyModel.getObject();
        this.personGroupService = _personGroupService;
        this.personService = _personService;
        feedBack.setVisible(false);
        selectedPersonGroup = new ArrayList<IEntity>();
        currentPersonGroupList = new ArrayList<IEntity>();
        selectedPerson = new ArrayList<IEntity>();
        currentPersonList = new ArrayList<IEntity>();
        this.customerName = new NameGeneric("");
        //field
        add(TextFieldBuilder.<String>createRequired("name", this.project, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Project requires a name").build());

        add(TextFieldBuilder.<String>createRequired("projectKey", this.project, "projectKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("Project requires a Key").build());

        add(TextAreaBuilder.<String>create("description", this.project, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description can be add").build());

        Customer selectByPrimaryKey = _customerService.selectByPrimaryKey(this.project.getIdCustomer());
        if (selectByPrimaryKey != null) {
            customerName.setName(selectByPrimaryKey.getName());
        }
        this.customerFiel = TextFieldBuilder.<String>create("idCustomer", customerName, "name").withTooltip("customer can be affected").buildTextField();
        add(customerFiel);

        if (this.project.getId() != null) {
            currentPersonGroupList = (List<IEntity>) (List<?>) personGroupService.getTeamsOfProject(this.project.getId());
            currentPersonList =(List<IEntity>) (List<?>)personService.getPersonsOfProject(this.project.getId());
        }

        DialogFactory.addListWithSelectDialog(this,
                "tablePersonGroup",
                "dialogAddPersonGroup",
                "btnAddPersonGroup",
                "btnDelPersonGroup",
                "selectedPersonGroup",
                "Choose Team",
                currentPersonGroupList,
                this.selectedPersonGroup,
                (List<IEntity>) (List<?>) this.personGroupService.getAllTeamsPG(),
                this.personGroupService);

        DialogFactory.addListWithSelectDialog(this,
                "tablePerson",
                "dialogAddPerson",
                "btnAddPerson",
                "btnDelPerson",
                "selectedPerson",
                "Choose Person",
                currentPersonList,
                this.selectedPerson,
                (List<IEntity>) (List<?>) this.personService.selectAll(),
                this.personService);

        //button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new ProjectPage(page.getPageParameters()));
            }
        });

        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                prService.saveOrUpdateProject(project, null, (List<Person>) (List<?>) currentPersonList, null, (List<PersonGroup>) (List<?>) currentPersonGroupList);
                page.setResponsePage(new ProjectPage(page.getPageParameters()));

            }
        });

    }

    public List<IEntity> getSelectedPersonGroup() {
        return selectedPersonGroup;
    }

    public void setSelectedPersonGroup(List<IEntity> selectedPersonGroup) {
        this.selectedPersonGroup = selectedPersonGroup;
    }

    public List<IEntity> getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(List<IEntity> selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public NameGeneric getCustomerName() {
        return customerName;
    }

    public Project getProject() {
        return project;
    }

    public TextField getCustomerFiel() {
        return customerFiel;
    }
}
