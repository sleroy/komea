/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.department;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.project.ProjectPage;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.SelectMultipleDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class DepartmentForm extends Form<PersonGroup> {

    private final IPersonGroupService prService;
    private final Component feedBack;
    private final LayoutPage page;
    private final PersonGroup personGroup;
    private List<PersonGroup> currentEntityList;
    private List<PersonGroup> selectedEntity;
    private List<PersonGroup> entityNeedUpdate;

    DepartmentForm(String form, IPersonGroupService personGroupService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, DepartmentEditPage aThis) {
        super(form, compoundPropertyModel);
        this.prService = personGroupService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
        entityNeedUpdate = new ArrayList<PersonGroup>();
        selectedEntity = new ArrayList<PersonGroup>();
        feedBack.setVisible(false);
        //field
        add(TextFieldBuilder.<String>createRequired("name", this.personGroup, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Departement requires a name").build());

        add(TextFieldBuilder.<String>createRequired("personGroupKey", this.personGroup, "personGroupKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("Departement requires a Key").build());

        add(TextAreaBuilder.<String>create("description", this.personGroup, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description can be add").build());

//        add(TextFieldBuilder.<String>create("idCustomer", this.project, "idCustomer").withTooltip("customer can be affected")
//                .build());
        //button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new DepartmentPage(page.getPageParameters()));
            }
        });
        // team component
        List<PersonGroup> allTeamsPG = prService.getAllTeamsPG();
        this.currentEntityList = new ArrayList<PersonGroup>();
        for (PersonGroup personGroup1 : allTeamsPG) {
            if (personGroup.getId() != null && personGroup.getId().equals(personGroup1.getIdPersonGroupParent())) {
                currentEntityList.add(personGroup1);
            }
        }
        IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();

        final ListMultipleChoice<IEntity> listEntite = new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this, "selectedEntity"), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        add(listEntite);


        final SelectMultipleDialog<IEntity> dialogPersonGroup = new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose teams", (List<IEntity>) (List<?>)allTeamsPG) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                List<IEntity> selected = getSelected();
                for (IEntity iEntity : selected) {
                    if (iEntity != null) {
                        PersonGroup selectByPrimaryKey1 = prService.selectByPrimaryKey(iEntity.getId());
                        if (!currentEntityList.contains(selectByPrimaryKey1)) {
                            selectByPrimaryKey1.setIdPersonGroupParent(personGroup.getId());
                            entityNeedUpdate.add(selectByPrimaryKey1);
                            currentEntityList.add(selectByPrimaryKey1);
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
                    person.setIdPersonGroupParent(null);
                    currentEntityList.remove(person);
                    entityNeedUpdate.add(person);
                }

                target.add(listEntite);
            }
        });

        //button
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
                target.add(feedBack);
                final PersonGroup insertPr = new PersonGroup();
                insertPr.setId(personGroup.getId());
                insertPr.setDescription(personGroup.getDescription());
                insertPr.setName(personGroup.getName());
                insertPr.setPersonGroupKey(personGroup.getPersonGroupKey());
                insertPr.setType(PersonGroupType.DEPARTMENT);

                if (insertPr.getId() != null) {
                    prService.updateByPrimaryKey(insertPr);

                } else {
                    prService.insert(insertPr);
                }
                for (PersonGroup personGroup : entityNeedUpdate) {
                    prService.updateByPrimaryKey(personGroup);
                }
                page.setResponsePage(new DepartmentPage(page.getPageParameters()));

            }
        });
    }

    public List<PersonGroup> getCurrentEntityList() {
        return currentEntityList;
    }

    public void setCurrentEntityList(List<PersonGroup> currentEntityList) {
        this.currentEntityList = currentEntityList;
    }

    public List<PersonGroup> getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(List<PersonGroup> selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public List<PersonGroup> getEntityNeedUpdate() {
        return entityNeedUpdate;
    }

    public void setEntityNeedUpdate(List<PersonGroup> entityNeedUpdate) {
        this.entityNeedUpdate = entityNeedUpdate;
    }

}
