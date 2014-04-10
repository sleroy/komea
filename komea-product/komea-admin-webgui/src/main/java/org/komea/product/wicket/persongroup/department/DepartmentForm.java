/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.department;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.DialogFactory;
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
    private List<IHasKey> currentEntityList;
    private List<IHasKey> selectedEntity;

    DepartmentForm(String form, IPersonGroupService personGroupService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, DepartmentEditPage aThis) {
        super(form, compoundPropertyModel);
        this.prService = personGroupService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
        selectedEntity = new ArrayList<IHasKey>();
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
        this.currentEntityList = new ArrayList<IHasKey>();
        for (PersonGroup personGroup1 : allTeamsPG) {
            if (personGroup.getId() != null && personGroup.getId().equals(personGroup1.getIdPersonGroupParent())) {
                currentEntityList.add(personGroup1);
            }
        }

        DialogFactory.addListWithSelectDialog(this,
                "table",
                "dialogAddPerson",
                "btnAddPerson",
                "btnDelPerson",
                "selectedEntity",
                "Choose team",
                currentEntityList,
                selectedEntity,
                (List<IHasKey>) (List<?>) allTeamsPG,
                prService);

        //button
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
                info("Submitted information");
                target.add(feedBack);
                personGroup.setType(PersonGroupType.DEPARTMENT);
                prService.saveOrUpdatePersonGroup(personGroup, (List<PersonGroup>) (List) currentEntityList, null, null);
                page.setResponsePage(new DepartmentPage(page.getPageParameters()));

            }
        });
    }

    public List<IHasKey> getCurrentEntityList() {
        return currentEntityList;
    }

    public void setCurrentEntityList(List<IHasKey> currentEntityList) {
        this.currentEntityList = currentEntityList;
    }

    public List<IHasKey> getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(List<IHasKey> selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

}
