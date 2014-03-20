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
import org.komea.product.wicket.utils.SelectMultipleDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;



/**
 * @author rgalerme
 */
public class TeamForm extends Form<PersonGroup>
{
    
    
    private final Component           feedBack;
    private final LayoutPage          page;
    private final TextField           parentField;
    private final NameGeneric         parentName;
    private final PersonGroup         personGroup;
    private final List<Person>        personNeedUpdate;
    private final IPersonService      personService;
    private List<Person>              personsOfGroup;
    private final IPersonGroupService prService;
    private List<Person>              selectedPerson;
    
    
    
    TeamForm(
            final String form,
            final IPersonService _personService,
            final IPersonGroupService _prService,
            final FeedbackPanel feedbackPanel,
            final CompoundPropertyModel<PersonGroup> compoundPropertyModel,
            final TeamEditPage aThis) {
    
    
        super(form, compoundPropertyModel);
        prService = _prService;
        feedBack = feedbackPanel;
        page = aThis;
        personGroup = compoundPropertyModel.getObject();
        feedBack.setVisible(false);
        parentName = new NameGeneric("");
        personService = _personService;
        personNeedUpdate = new ArrayList<Person>();
        selectedPerson = new ArrayList<Person>();
        personsOfGroup = new ArrayList<Person>();
        
        add(TextFieldBuilder.<String> createRequired("name", personGroup, "name")
                .highlightOnErrors().simpleValidator(0, 255)
                .withTooltip("Departement requires a name").build());
        
        add(TextFieldBuilder
                .<String> createRequired("personGroupKey", personGroup, "personGroupKey")
                .simpleValidator(0, 255).highlightOnErrors()
                .withTooltip("Departement requires a Key").build());
        
        add(TextAreaBuilder.<String> create("description", personGroup, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description can be add")
                .build());
        
        if (personGroup.getIdPersonGroupParent() != null) {
            final PersonGroup selectByPrimaryKey =
                    prService.selectByPrimaryKey(personGroup.getIdPersonGroupParent());
            if (selectByPrimaryKey != null) {
                parentName.setName(selectByPrimaryKey.getName());
            }
        }
        parentField =
                TextFieldBuilder.<String> create("parent", parentName, "name")
                        .withTooltip("Parent can be affected").buildTextField();
        add(parentField);
        
        if (personGroup.getId() != null) {
            personsOfGroup = personService.getPersonsOfPersonGroup(personGroup.getId());
        }
        final IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();
        
        final ListMultipleChoice<IEntity> listEntite =
                new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this,
                        "selectedPerson"), personsOfGroup);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        
        add(listEntite);
        // button
        final IChoiceRenderer<IEntity> iChoiceRenderer = DialogFactory.getChoiceRendenerEntity();
        
        final SelectMultipleDialog<IEntity> dialogPersonGroup =
                new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose a person",
                        personService, iChoiceRenderer)
                {
                    
                    
                    @Override
                    public void onClose(final AjaxRequestTarget target, final DialogButton button) {
                    
                    
                        // FIXME complete the method
                    }
                    
                    
                    @Override
                    protected void onSubmit(final AjaxRequestTarget target) {
                    
                    
                        final List<IEntity> selected = getSelected();
                        for (final IEntity iEntity : selected) {
                            if (iEntity != null) {
                                final Person selectByPrimaryKey1 =
                                        personService.selectByPrimaryKey(iEntity.getId());
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
        add(new AjaxLinkLayout<Object>("btnAddPerson", null)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                dialogPersonGroup.open(art);
            }
        });
        
        add(new AjaxButton("btnDelPerson")
        {
            
            
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
            
            
                for (final Person person : selectedPerson) {
                    person.setIdPersonGroup(null);
                    personsOfGroup.remove(person);
                    personNeedUpdate.add(person);
                }
                
                target.add(listEntite);
            }
        });
        
        add(new AjaxLinkLayout<LayoutPage>("cancel", page)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                final LayoutPage page = getCustom();
                page.setResponsePage(new TeamPage(page.getPageParameters()));
            }
        });
        
        add(new AjaxButton("submit", this)
        {
            
            
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
                for (final Person peson : personNeedUpdate) {
                    personService.saveOrUpdate(peson);
                }
                page.setResponsePage(new TeamPage(page.getPageParameters()));
                
            }
        });
    }
    
    
    public TextField getParentField() {
    
    
        return parentField;
    }
    
    
    public NameGeneric getParentName() {
    
    
        return parentName;
    }
    
    
    public PersonGroup getPersonGroup() {
    
    
        return personGroup;
    }
    
    
    public List<Person> getSelectedPerson() {
    
    
        return selectedPerson;
    }
    
    
    public void setSelectedPerson(final List<Person> selectedPerson) {
    
    
        this.selectedPerson = selectedPerson;
    }
    
}
