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
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.SelectMultipleDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;



/**
 * @author rgalerme
 */
public class DepartmentForm extends Form<PersonGroup>
{
    
    
    private List<IEntity>             currentEntityList;
    private final Component           feedBack;
    private final LayoutPage          page;
    private final PersonGroup         personGroup;
    private final IPersonGroupService prService;
    private List<IEntity>             selectedEntity;
    
    
    
    DepartmentForm(
            final String form,
            final IPersonGroupService personGroupService,
            final FeedbackPanel feedbackPanel,
            final CompoundPropertyModel<PersonGroup> compoundPropertyModel,
            final DepartmentEditPage aThis) {
    
    
        super(form, compoundPropertyModel);
        prService = personGroupService;
        feedBack = feedbackPanel;
        page = aThis;
        personGroup = compoundPropertyModel.getObject();
        selectedEntity = new ArrayList<IEntity>();
        feedBack.setVisible(false);
        // field
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
        
        // add(TextFieldBuilder.<String>create("idCustomer", this.project, "idCustomer").withTooltip("customer can be affected")
        // .build());
        // button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                final LayoutPage page = getCustom();
                page.setResponsePage(new DepartmentPage(page.getPageParameters()));
            }
        });
        // team component
        final List<PersonGroup> allTeamsPG = prService.getAllTeamsPG();
        currentEntityList = new ArrayList<IEntity>();
        for (final PersonGroup personGroup1 : allTeamsPG) {
            if (personGroup.getId() != null
                    && personGroup.getId().equals(personGroup1.getIdPersonGroupParent())) {
                currentEntityList.add(personGroup1);
            }
        }
        final IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();
        
        final ListMultipleChoice<IEntity> listEntite =
                new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this,
                        "selectedEntity"), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        add(listEntite);
        
        final SelectMultipleDialog<IEntity> dialogPersonGroup =
                new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose teams",
                        (List<IEntity>) (List<?>) allTeamsPG)
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
                                final IEntity selectByPrimaryKey1 =
                                        prService.selectByPrimaryKey(iEntity.getId());
                                if (!currentEntityList.contains(selectByPrimaryKey1)) {
                                    currentEntityList.add(selectByPrimaryKey1);
                                }
                                target.add(listEntite);
                            }
                        }
                        
                    }
                    
                };
        add(dialogPersonGroup);
        dialogPersonGroup.setFilter((List<IEntity>) (List<?>) currentEntityList);
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
            
            
                for (final IEntity person : selectedEntity) {
                    currentEntityList.remove(person);
                }
                
                target.add(listEntite);
            }
        });
        
        // button
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
                target.add(feedBack);
                personGroup.setType(PersonGroupType.DEPARTMENT);
                
                prService
                        .saveOrUpdatePersonGroup(personGroup, (List) currentEntityList, null, null);
                page.setResponsePage(new DepartmentPage(page.getPageParameters()));
                
            }
        });
    }
    
    
    public List<IEntity> getCurrentEntityList() {
    
    
        return currentEntityList;
    }
    
    
    public List<IEntity> getSelectedEntity() {
    
    
        return selectedEntity;
    }
    
    
    public void setCurrentEntityList(final List<IEntity> currentEntityList) {
    
    
        this.currentEntityList = currentEntityList;
    }
    
    
    public void setSelectedEntity(final List<IEntity> selectedEntity) {
    
    
        this.selectedEntity = selectedEntity;
    }
    
}
