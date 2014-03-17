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
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.widget.GridViewEntities;
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
    private Person selectedPerson;
    private List<Person> personsOfGroup;
    private List<Person> personNeedUpdate;

    TeamForm(String form, IPersonService _personService, IPersonGroupService prService,
            FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel,
            TeamEditPage aThis, IProjectService projectService) {

        super(form, compoundPropertyModel);
        this.prService = prService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
        feedBack.setVisible(false);
        parentName = new NameGeneric("");
        personService = _personService;
        personNeedUpdate = new ArrayList<Person>();
        add(TextFieldBuilder.<String>createRequired("name", this.personGroup, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Departement requires a name").build());

        add(TextFieldBuilder.<String>createRequired("personGroupKey", this.personGroup, "personGroupKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("Departement requires a Key").build());

        add(TextAreaBuilder.<String>create("description", this.personGroup, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description can be add").build());

        personsOfGroup = personService.getPersonsOfPersonGroup(this.personGroup.getId());
        IChoiceRenderer<Person> displayGroup = new IChoiceRenderer<Person>() {
            @Override
            public Object getDisplayValue(Person t) {
                return t.getFirstName() + " " + t.getLastName();
            }

            @Override
            public String getIdValue(Person t, int i) {
                return String.valueOf(t.getId());
            }
        };
        if (!personsOfGroup.isEmpty()) {
            this.selectedPerson = personsOfGroup.get(0);
        }
        ListChoice<Person> listEntite = new ListChoice<Person>("table",
                new PropertyModel<Person>(this, "selectedPerson"), personsOfGroup) {

                    @Override
                    protected String getNullKeyDisplayValue() {
                        return " ";//To change body of generated methods, choose Tools | Templates.
                    }
                };
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setNullValid(false);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);

        add(listEntite);

        final List<Project> associatedProjects = projectService.getProjectsOfPersonGroup(this.personGroup.getId());
        final GridViewEntities<Project> projectsView = new GridViewEntities<Project>(
                "projectsTable", associatedProjects);
        add(projectsView);

        PersonGroup selectByPrimaryKey = this.prService.selectByPrimaryKey(this.personGroup.getIdPersonGroupParent());
        if (selectByPrimaryKey != null) {
            this.parentName.setName(selectByPrimaryKey.getName());
        }
        this.parentField = TextFieldBuilder.<String>create("parent", this.parentName, "name").withTooltip("Parent can be affected").build();
        add(this.parentField);
        //button
        add(new AjaxLinkLayout<ListChoice>("btnAddPerson", listEntite) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                art.add(getCustom());
            }
        });

        add(new AjaxLinkLayout<ListChoice>("btnDelPerson", listEntite) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                art.add(getCustom());
                selectedPerson.setIdPersonGroup(null);
                personsOfGroup.remove(selectedPerson);
                personNeedUpdate.add(selectedPerson);
//               personService.saveOrUpdateProject(selectedPerson);
                art.add(getCustom());
            }
        });

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

            }
        });
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
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

    @Override
    protected void onSubmit() {

        final PersonGroup insertPr = new PersonGroup();
        insertPr.setId(this.personGroup.getId());
        insertPr.setDescription(this.personGroup.getDescription());
        insertPr.setIdPersonGroupParent(this.personGroup.getIdPersonGroupParent());
        insertPr.setName(this.personGroup.getName());
        insertPr.setPersonGroupKey(this.personGroup.getPersonGroupKey());
        insertPr.setType(PersonGroupType.TEAM);

        if (insertPr.getId() != null) {
            this.prService.updateByPrimaryKey(insertPr);

        } else {
            this.prService.insert(insertPr);
        }
        for (Person peson : this.personNeedUpdate) {
            personService.saveOrUpdate(peson);
        }

        page.setResponsePage(new TeamPage(page.getPageParameters()));

    }
}
