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
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IEntity;
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

    private List<IEntity> selectedPerson;
    private List<IEntity> currentPersonList;

    private List<IEntity> selectedProject;
    private List<IEntity> currentProjectList;

    TeamForm(String form, IProjectService _projectService, IPersonService _personService, IPersonGroupService _prService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, TeamEditPage aThis) {

        super(form, compoundPropertyModel);
        this.prService = _prService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
//        feedBack.setVisible(false);
        parentName = new NameGeneric("");
        personService = _personService;
        this.projectService = _projectService;

        selectedPerson = new ArrayList<IEntity>();
        currentPersonList = new ArrayList<IEntity>();

        selectedProject = new ArrayList<IEntity>();
        currentProjectList = new ArrayList<IEntity>();

        add(TextFieldBuilder.<String>createRequired("name", this.personGroup, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Departement requires a name").build());

        add(TextFieldBuilder.<String>createRequired("personGroupKey", this.personGroup, "personGroupKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("Departement requires a Key").build());

        add(TextAreaBuilder.<String>create("description", this.personGroup, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description can be add").build());

        if (this.personGroup.getIdPersonGroupParent() != null) {
            PersonGroup selectByPrimaryKey = this.prService.selectByPrimaryKey(this.personGroup.getIdPersonGroupParent());
            if (selectByPrimaryKey != null) {
                this.parentName.setName(selectByPrimaryKey.getName());
            }
        }
        
        this.parentField = TextFieldBuilder.<String>create("parent", this.parentName, "name").withTooltip("Parent can be affected").buildTextField();
       this.parentField.setOutputMarkupId(true);
        add(this.parentField);

        if (this.personGroup.getId() != null) {
            currentPersonList = (List<IEntity>) (List<?>) personService.getPersonsOfPersonGroup(this.personGroup.getId());
            currentProjectList = (List<IEntity>) (List<?>) projectService.getProjectsOfPersonGroup(this.personGroup.getId());
        }
        initSelectDepartment();
        DialogFactory.addListWithSelectDialog(this,
                "table",
                "dialogAddPerson",
                "btnAddPerson",
                "btnDelPerson",
                "selectedPerson",
                "Choose person",
                currentPersonList,
                selectedPerson,
                (List<IEntity>) (List<?>) this.personService.selectAll(),
                personService);

        DialogFactory.addListWithSelectDialog(this,
                "tableProject",
                "dialogAddProject",
                "btnAddProject",
                "btnDelProject",
                "selectedProject",
                "Choose Project",
                currentProjectList,
                selectedProject,
                (List<IEntity>) (List<?>) this.projectService.selectAll(),
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
                personGroup.setType(PersonGroupType.TEAM);
                prService.saveOrUpdatePersonGroup(personGroup, null, (List<Project>) (List<?>) currentProjectList, (List<Person>) (List<?>) currentPersonList);
                page.setResponsePage(new TeamPage(page.getPageParameters()));

            }
        });
    }
    
    public void initSelectDepartment()
    {
            IChoiceRenderer<PersonGroup> iChoiceRenderer = new IChoiceRenderer<PersonGroup>() {

            @Override
            public Object getDisplayValue(PersonGroup t) {
                return t.getName();
            }

            @Override
            public String getIdValue(PersonGroup t, int i) {
                return String.valueOf(t.getId());
            }

        };
        List<PersonGroup> allDepartmentsPG = prService.getAllDepartmentsPG();
        final SelectDialog<PersonGroup> dialogPersonGroup = new SelectDialog<PersonGroup>("dialogParent", "Choose a department", allDepartmentsPG, iChoiceRenderer) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                PersonGroup selectedPersonGroup = getSelected();
                if (selectedPersonGroup != null) {
                    personGroup.setIdPersonGroupParent(selectedPersonGroup.getId());
                    parentName.setName(selectedPersonGroup.getName());
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

    public List<IEntity> getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(List<IEntity> selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public List<IEntity> getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(List<IEntity> selectedProject) {
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
