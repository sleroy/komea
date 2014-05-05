/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.CustomUpdater;
import org.komea.product.wicket.utils.DataListSelectDialogBuilder;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
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
    private final boolean isNew;
    private List<IHasKey> currentPersonGroupList;
    private List<IHasKey> selectedPersonGroup;
    private List<IHasKey> currentPersonList;
    private List<IHasKey> selectedPerson;
    private List<IHasKey> teamPersonList;
    private List<IHasKey> projectPersonList;

    public ProjectForm(boolean _isNew,
            String form,
            IPersonService _personService,
            IPersonGroupService _personGroupService,
            IProjectService projectService,
            CustomerDao _customerService,
            FeedbackPanel feedbackPanel,
            CompoundPropertyModel<Project> compoundPropertyModel,
            ProjectEditPage aThis) {

        super(form, compoundPropertyModel);
        this.prService = projectService;
        this.isNew = _isNew;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.project = compoundPropertyModel.getObject();
        this.personGroupService = _personGroupService;
        this.personService = _personService;
        feedBack.setVisible(false);
        selectedPersonGroup = new ArrayList<IHasKey>();
        currentPersonGroupList = new ArrayList<IHasKey>();
        selectedPerson = new ArrayList<IHasKey>();
        currentPersonList = new ArrayList<IHasKey>();
        teamPersonList = new ArrayList<IHasKey>();
        projectPersonList = new ArrayList<IHasKey>();
        this.customerName = new NameGeneric("");
        //field
        add(TextFieldBuilder.<String>createRequired("name", this.project, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("global.field.tooltip.name")).build());

        TextFieldBuilder<String> keyField = TextFieldBuilder.<String>createRequired("projectKey", this.project, "projectKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip(getString("global.field.tooltip.key"));

        if (isNew) {
            keyField.UniqueStringValidator(getString("global.field.key"), prService);
        } else {
            keyField.buildTextField().add(new AttributeModifier("readonly", "readonly"));
        }

        add(keyField.build());

        add(TextAreaBuilder.<String>create("description", this.project, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip(getString("global.field.tooltip.description")).build());

        Customer selectByPrimaryKey = _customerService.selectByPrimaryKey(this.project.getIdCustomer());
        if (selectByPrimaryKey != null) {
            customerName.setName(selectByPrimaryKey.getName());
        }
        this.customerFiel = TextFieldBuilder.<String>create("idCustomer", customerName, "name").withTooltip(getString("project.form.tooltpis.customer")).buildTextField();
        this.customerFiel.setOutputMarkupId(true);
        add(customerFiel);

        if (this.project.getId() != null) {
            projectPersonList.addAll(personService.getPersonsOfProject(this.project.getId()));
            currentPersonGroupList.addAll((List<IHasKey>) (List<?>) personGroupService.getTeamsOfProject(this.project.getId()));
            for (IHasKey personGroup : currentPersonGroupList) {
                teamPersonList.addAll(personService.getPersonsOfPersonGroup(personGroup.getId()));
            }
            teamPersonList.removeAll(projectPersonList);
            currentPersonList.addAll(projectPersonList);
            currentPersonList.addAll(teamPersonList);
        }

        ListMultipleChoice<IHasKey> listUser = new ListMultipleChoice<IHasKey>("tablePerson", new PropertyModel<List<IHasKey>>(this, "selectedPerson"), this.currentPersonList) {

            @Override
            protected boolean isDisabled(IHasKey object, int index, String selected) {
                if (teamPersonList.contains(object)) {
                    return true;
                }
                return super.isDisabled(object, index, selected);
            }

        };
        listUser.setChoiceRenderer(DialogFactory.getChoiceRendenerEntity());
        listUser.setMaxRows(8);
        listUser.setOutputMarkupId(true);
        CustomUpdater cupdater = new CustomUpdater(listUser) {

            @Override
            public void update() {
               
                currentPersonList.removeAll(teamPersonList);
                projectPersonList.clear();
                projectPersonList.addAll(currentPersonList);
                currentPersonList.clear();
                teamPersonList.clear();
                for (IHasKey persong : currentPersonGroupList) {
                    List<Person> personsOfPersonGroup = personService.getPersonsOfPersonGroup(persong.getId());
                    teamPersonList.addAll(personsOfPersonGroup);
                }
                currentPersonList.addAll(teamPersonList);
                for (IHasKey key : projectPersonList) {
                   if(!currentPersonList.contains(key))
                   {
                   currentPersonList.add(key);
                   }
                }
               
            }
        };

        DataListSelectDialogBuilder dataMember = new DataListSelectDialogBuilder();
        dataMember.setPage(this);
        dataMember.setListEntite(listUser);
        dataMember.setIdDialog("dialogAddPerson");
        dataMember.setIdBtnAdd("btnAddPerson");
        dataMember.setIdBtnDel("btnDelPerson");
        dataMember.setDisplayDialogMessage(getString("project.form.field.tooltip.members"));
        dataMember.setCurrentEntityList(currentPersonList);
        dataMember.setChoiceEntityList(selectedPerson);
        dataMember.setSelectDialogList((List<IHasKey>) (List<?>) this.personService.selectAll());
        dataMember.setService(personService);

//        dataMember.addFilter(DialogFactory.getPersonWithoutPersonGroupFilter(personGroup.getId()));
        dataMember.setTooltips(getString("project.form.field.multiple.member"));
        DialogFactory.addMultipleListDialog(dataMember);

        DataListSelectDialogBuilder dataTeam = new DataListSelectDialogBuilder();
        dataTeam.setPage(this);
        dataTeam.setIdList("tablePersonGroup");
        dataTeam.setIdDialog("dialogAddPersonGroup");
        dataTeam.setIdBtnAdd("btnAddPersonGroup");
        dataTeam.setIdBtnDel("btnDelPersonGroup");
        dataTeam.setNameFieldResult("selectedPersonGroup");
        dataTeam.setDisplayDialogMessage(getString("project.form.field.popup.title.team"));
        dataTeam.setCurrentEntityList(currentPersonGroupList);
        dataTeam.setChoiceEntityList(selectedPersonGroup);
        dataTeam.setSelectDialogList((List<IHasKey>) (List<?>) this.personGroupService.getAllTeamsPG());
        dataTeam.setService(personGroupService);
        dataTeam.addUpdater(cupdater);
//        dataProject.addFilter(DialogFactory.getPersonWithoutPersonGroupFilter(personGroup.getId()));
        dataTeam.setTooltips(getString("project.form.field.multiple.team"));
        DialogFactory.addMultipleListDialog(dataTeam);

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
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                currentPersonList.removeAll(teamPersonList);
                prService.saveOrUpdateProject(project, new ArrayList(), (List<Person>) (List<?>) currentPersonList, new ArrayList(), (List<PersonGroup>) (List<?>) currentPersonGroupList);
                page.setResponsePage(new ProjectPage(page.getPageParameters()));

            }
        });

    }

    public List<IHasKey> getSelectedPersonGroup() {
        return selectedPersonGroup;
    }

    public void setSelectedPersonGroup(List<IHasKey> selectedPersonGroup) {
        this.selectedPersonGroup = selectedPersonGroup;
    }

    public List<IHasKey> getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(List<IHasKey> selectedPerson) {
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
