/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.department;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.project.ProjectPage;
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

    DepartmentForm(String form, IPersonGroupService personGroupService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, DepartmentEditPage aThis) {
        super(form, compoundPropertyModel);
        this.prService = personGroupService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
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

    @Override
    protected void onSubmit() {

        final PersonGroup insertPr = new PersonGroup();
        insertPr.setId(this.personGroup.getId());
        insertPr.setDescription(this.personGroup.getDescription());
//                insertPr.setIdPersonGroupParent(this.personGroup.getIdPersonGroupParent());
        insertPr.setName(this.personGroup.getName());
        insertPr.setPersonGroupKey(this.personGroup.getPersonGroupKey());
        insertPr.setType(PersonGroupType.DEPARTMENT);

        if (insertPr.getId() != null) {
            this.prService.updateByPrimaryKey(insertPr);

        } else {
            this.prService.insert(insertPr);
        }
        page.setResponsePage(new DepartmentPage(page.getPageParameters()));

    }
}
