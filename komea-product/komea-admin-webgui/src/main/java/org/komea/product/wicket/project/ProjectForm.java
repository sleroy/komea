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
    private final IPersonGroupService personGroupService;
    private List<PersonGroup> currentEntityList;
    private List<PersonGroup> selectedEntity;
    private List<PersonGroup> entityNeedUpdate;

    public ProjectForm(String form, IPersonGroupService _personGroupService, IProjectService projectService, CustomerDao _customerService, FeedbackPanel feedbackPanel, CompoundPropertyModel<Project> compoundPropertyModel, ProjectEditPage aThis) {
        super(form, compoundPropertyModel);
        this.prService = projectService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.project = compoundPropertyModel.getObject();
        this.personGroupService = _personGroupService;
        feedBack.setVisible(false);
        entityNeedUpdate = new ArrayList<PersonGroup>();
        selectedEntity = new ArrayList<PersonGroup>();
        currentEntityList = new ArrayList<PersonGroup>();
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
        this.customerFiel = TextFieldBuilder.<String>create("idCustomer", customerName, "name").withTooltip("customer can be affected").build();
        add(customerFiel);

        if(this.project.getId() != null) {
        currentEntityList = personGroupService.getTeamsOfProject(this.project.getId());
        }
        IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();

        final ListMultipleChoice<IEntity> listEntite = new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this, "selectedEntity"), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        add(listEntite);

        List<IEntity> allTeamsPG = (List<IEntity>) (List<?>) this.personGroupService.getAllTeamsPG();
        final SelectMultipleDialog<IEntity> dialogPersonGroup = new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose team", allTeamsPG) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                List<IEntity> selected = getSelected();
                for (IEntity iEntity : selected) {
                    if (iEntity != null) {
                        PersonGroup selectByPrimaryKey1 = personGroupService.selectByPrimaryKey(iEntity.getId());
                        if (!currentEntityList.contains(selectByPrimaryKey1)) {
//                            selectByPrimaryKey1.setIdPersonGroupParent(selectByPrimaryKey1.getId());
//                            entityNeedUpdate.add(selectByPrimaryKey1);
//                            currentEntityList.add(selectByPrimaryKey1);
                        }
                        target.add(listEntite);
                    }
                }

            }

        };
        add(dialogPersonGroup);
        dialogPersonGroup.setFilter((List<IEntity>) (List<?>) currentEntityList);
        add(new AjaxLinkLayout<Object>("btnAddPerson", null) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogPersonGroup.open(art);
            }
        });

        add(new AjaxButton("btnDelPerson") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                for (PersonGroup person : selectedEntity) {
//                    person.set
//                    currentEntityList.remove(person);
//                    entityNeedUpdate.add(person);
                }

                target.add(listEntite);
            }
        });

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
                final Project projectInsert = new Project();
                projectInsert.setId(project.getId());
                projectInsert.setIdCustomer(project.getIdCustomer());
                projectInsert.setDescription(project.getDescription());
                projectInsert.setName(project.getName());
                projectInsert.setProjectKey(project.getProjectKey());
                if (projectInsert.getId() != null) {
                    prService.updateByPrimaryKey(projectInsert);
                } else {
                    prService.insert(projectInsert);
                }
                for (PersonGroup person : entityNeedUpdate) {
                    personGroupService.saveOrUpdate(person);
                }
                page.setResponsePage(new ProjectPage(page.getPageParameters()));

            }
        });

    }

    public List<PersonGroup> getPersonGroupList() {
        return currentEntityList;
    }

    public void setPersonGroupList(List<PersonGroup> personGroupList) {
        this.currentEntityList = personGroupList;
    }

    public List<PersonGroup> getSelectedPerson() {
        return selectedEntity;
    }

    public void setSelectedPerson(List<PersonGroup> selectedPerson) {
        this.selectedEntity = selectedPerson;
    }

    public List<PersonGroup> getPersonNeedUpdate() {
        return entityNeedUpdate;
    }

    public void setPersonNeedUpdate(List<PersonGroup> personNeedUpdate) {
        this.entityNeedUpdate = personNeedUpdate;
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
