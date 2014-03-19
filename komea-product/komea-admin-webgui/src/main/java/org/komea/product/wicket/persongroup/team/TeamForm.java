/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.team;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.utils.SelectMultipleDialog;
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
    private List<Person> selectedPerson;
    private List<Person> personsOfGroup;
    private List<Person> personNeedUpdate;

    TeamForm(String form, IPersonService _personService, IPersonGroupService _prService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, TeamEditPage aThis) {

        super(form, compoundPropertyModel);
        this.prService = _prService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
        feedBack.setVisible(false);
        parentName = new NameGeneric("");
        personService = _personService;
        personNeedUpdate = new ArrayList<Person>();
        selectedPerson = new ArrayList<Person>();
        personsOfGroup = new ArrayList<Person>();

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
        this.parentField = TextFieldBuilder.<String>create("parent", this.parentName, "name").withTooltip("Parent can be affected").build();
        add(this.parentField);

        if (this.personGroup.getId() != null) {
            personsOfGroup = personService.getPersonsOfPersonGroup(this.personGroup.getId());
        }
        IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();

        final ListMultipleChoice<IEntity> listEntite = new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this, "selectedPerson"), personsOfGroup);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);

        add(listEntite);
        //button 
        IChoiceRenderer<IEntity> iChoiceRenderer = DialogFactory.getChoiceRendenerEntity();

        final SelectMultipleDialog<IEntity> dialogPersonGroup = new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose a person", this.personService, iChoiceRenderer) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                List<IEntity> selected = getSelected();
                for (IEntity iEntity : selected) {
                    if (iEntity != null) {
                        Person selectByPrimaryKey1 = personService.selectByPrimaryKey(iEntity.getId());
                        if (!personsOfGroup.contains(selectByPrimaryKey1)) {
                            selectByPrimaryKey1.setIdPersonGroup(personGroup.getId());
                            personNeedUpdate.add(selectByPrimaryKey1);
                            personsOfGroup.add(selectByPrimaryKey1);
                        }
                        target.add(listEntite);
                    }
                }

            }

        };
        add(dialogPersonGroup);
        dialogPersonGroup.setFilter((List<IEntity>) (List<?>) personsOfGroup);
        add(new AjaxLinkLayout<Object>("btnAddPerson", null) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogPersonGroup.open(art);
            }
        });

        add(new AjaxButton("btnDelPerson") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                for (Person person : selectedPerson) {
                    person.setIdPersonGroup(null);
                    personsOfGroup.remove(person);
                    personNeedUpdate.add(person);
                }

                target.add(listEntite);
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
                final PersonGroup insertPr = new PersonGroup();
                insertPr.setId(personGroup.getId());
                insertPr.setDescription(personGroup.getDescription());
                insertPr.setIdPersonGroupParent(personGroup.getIdPersonGroupParent());
                insertPr.setName(personGroup.getName());
                insertPr.setPersonGroupKey(personGroup.getPersonGroupKey());
                insertPr.setType(PersonGroupType.TEAM);
                if (insertPr.getId() != null) {
                    prService.updateByPrimaryKey(insertPr);

                } else {
                    prService.insert(insertPr);
                }
                for (Person peson : personNeedUpdate) {
                    personService.saveOrUpdate(peson);
                }
                page.setResponsePage(new TeamPage(page.getPageParameters()));

            }
        });
    }

    public List<Person> getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(List<Person> selectedPerson) {
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

}
