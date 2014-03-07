/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.team;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.persongroup.department.DepartmentPage;
import org.komea.product.wicket.utils.NameGeneric;
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
    
    TeamForm(String form, IPersonGroupService prService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, TeamEditPage aThis) {
        
        super(form, compoundPropertyModel);
        this.prService = prService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.personGroup = compoundPropertyModel.getObject();
        feedBack.setVisible(false);
        parentName = new NameGeneric("");
        
        add(TextFieldBuilder.<String>createRequired("name", this.personGroup, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Departement requires a name").build());
        
        add(TextFieldBuilder.<String>createRequired("personGroupKey", this.personGroup, "personGroupKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("Departement requires a Key").build());
        
        add(TextAreaBuilder.<String>create("description", this.personGroup, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description can be add").build());
        
        PersonGroup selectByPrimaryKey = this.prService.selectByPrimaryKey(this.personGroup.getIdPersonGroupParent());
        if (selectByPrimaryKey != null) {
            this.parentName.setName(selectByPrimaryKey.getName());
        }
        this.parentField = TextFieldBuilder.<String>create("parent", this.parentName, "name").withTooltip("Parent can be affected").build();
        add(this.parentField);
        //button
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
        page.setResponsePage(new TeamPage(page.getPageParameters()));
        
    }
}
