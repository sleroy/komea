/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.team;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class TeamForm extends Form<PersonGroup> {

    private final IPersonGroupService prService;
    private final Component feedBack;
    private final LayoutPage page;
    private final PersonGroup personGroup;
    private final NameGeneric parentName;
    private final TextField parentField;
    private final IPersonService personService;
    private final IProjectService projectService;
    private final boolean isNew;

    private List<IHasKey> selectedPerson;
    private List<IHasKey> currentPersonList;

    private List<IHasKey> selectedProject;
    private List<IHasKey> currentProjectList;

    TeamForm(boolean _isNew, String form, IProjectService _projectService, IPersonService _personService, IPersonGroupService _prService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, TeamEditPage aThis) {

        super(form, compoundPropertyModel);
        this.prService = _prService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
        isNew = _isNew;
        feedBack.setVisible(false);
        parentName = new NameGeneric("");
        personService = _personService;
        this.projectService = _projectService;

        selectedPerson = new ArrayList<IHasKey>();
        currentPersonList = new ArrayList<IHasKey>();

        selectedProject = new ArrayList<IHasKey>();
        currentProjectList = new ArrayList<IHasKey>();

        add(TextFieldBuilder.<String>createRequired("name", this.personGroup, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("global.field.tooltip.name")).build());
        TextFieldBuilder<String> keyFieldBuilder = TextFieldBuilder.<String>createRequired("personGroupKey", this.personGroup, "personGroupKey")
                .simpleValidator(0, 255)
                .highlightOnErrors()
                .withTooltip(getString("global.field.tooltip.key"));
        if (isNew) {
            keyFieldBuilder.UniqueStringValidator(getString("global.field.key"), prService);
        } else {
            keyFieldBuilder.buildTextField().setEnabled(false);
        }

        add(keyFieldBuilder.build());

        add(TextAreaBuilder.<String>create("description", this.personGroup, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip(getString("global.field.tooltip.description")).build());

        if (this.personGroup.getIdPersonGroupParent() != null) {
            PersonGroup selectByPrimaryKey = this.prService.selectByPrimaryKey(this.personGroup.getIdPersonGroupParent());
            if (selectByPrimaryKey != null) {
                this.parentName.setName(selectByPrimaryKey.getName());
            }
        }

        this.parentField = TextFieldBuilder.<String>create("parent", this.parentName, "name").withTooltip(getString("teampage.save.form.field.tooltip.department")).buildTextField();
        this.parentField.setOutputMarkupId(true);
        add(this.parentField);

        if (this.personGroup.getId() != null) {
            currentPersonList = (List<IHasKey>) (List<?>) personService.getPersonsOfPersonGroup(this.personGroup.getId());
            currentProjectList = (List<IHasKey>) (List<?>) projectService.getProjectsOfPersonGroup(this.personGroup.getId());
        }
        initSelectDepartment();
        DialogFactory.addListWithSelectDialog(this,
                "table",
                "dialogAddPerson",
                "btnAddPerson",
                "btnDelPerson",
                "selectedPerson",
                getString("teampage.save.form.field.tooltip.members"),
                currentPersonList,
                selectedPerson,
                (List<IHasKey>) (List<?>) this.personService.selectAll(),
                personService);

        DialogFactory.addListWithSelectDialog(this,
                "tableProject",
                "dialogAddProject",
                "btnAddProject",
                "btnDelProject",
                "selectedProject",
                getString("teampage.save.form.field.tooltip.projects"),
                currentProjectList,
                selectedProject,
                (List<IHasKey>) (List<?>) this.projectService.selectAll(),
                projectService);

        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new TeamPage(page.getPageParameters()));
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
                personGroup.setType(PersonGroupType.TEAM);
                prService.saveOrUpdatePersonGroup(personGroup, null, (List<Project>) (List<?>) currentProjectList, (List<Person>) (List<?>) currentPersonList);
                page.setResponsePage(new TeamPage(page.getPageParameters()));

            }
        });
    }

    public void initSelectDepartment() {
        List<IHasKey> allDepartmentsPG = (List<IHasKey>) (List<?>) prService.getAllDepartmentsPG();
        final SelectDialog dialogPersonGroup = new SelectDialog("dialogParent", getString("teampage.save.form.field.popup.title.department"), allDepartmentsPG) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                IHasKey selectedPersonGroup = getSelected();
                if (selectedPersonGroup != null) {
                    personGroup.setIdPersonGroupParent(selectedPersonGroup.getId());
                    parentName.setName(selectedPersonGroup.getDisplayName());
                } else {
                    personGroup.setIdPersonGroupParent(null);
                    parentName.setName("");
                }
                parentField.clearInput();
                target.add(parentField);
            }

        };
        add(dialogPersonGroup);
        add(new AjaxLinkLayout<LayoutPage>("btnParent", null) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogPersonGroup.open(art);

            }
        });
    }

    public List<IHasKey> getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(List<IHasKey> selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public List<IHasKey> getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(List<IHasKey> selectedProject) {
        this.selectedProject = selectedProject;
    }

    public PersonGroup getPersonGroup() {
        return personGroup;
    }

    public NameGeneric getParentName() {
        return parentName;
    }

    public TextField getParentField() {
        return parentField;
    }

}
