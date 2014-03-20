/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.project;



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
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
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
public class ProjectForm extends Form<Project>
{
    
    
    private List<PersonGroup>         currentEntityList;
    private final TextField           customerFiel;
    private final NameGeneric         customerName;
    private List<PersonGroup>         entityNeedUpdate;
    private final Component           feedBack;
    private final LayoutPage          page;
    private final IPersonGroupService personGroupService;
    private final Project             project;
    private final IProjectService     prService;
    private List<PersonGroup>         selectedEntity;
    
    
    
    public ProjectForm(
            final String form,
            final IPersonGroupService _personGroupService,
            final IProjectService projectService,
            final CustomerDao _customerService,
            final FeedbackPanel feedbackPanel,
            final CompoundPropertyModel<Project> compoundPropertyModel,
            final ProjectEditPage aThis) {
    
    
        super(form, compoundPropertyModel);
        prService = projectService;
        feedBack = feedbackPanel;
        page = aThis;
        project = compoundPropertyModel.getObject();
        personGroupService = _personGroupService;
        feedBack.setVisible(false);
        entityNeedUpdate = new ArrayList<PersonGroup>();
        selectedEntity = new ArrayList<PersonGroup>();
        currentEntityList = new ArrayList<PersonGroup>();
        customerName = new NameGeneric("");
        // field
        add(TextFieldBuilder.<String> createRequired("name", project, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Project requires a name").build());
        
        add(TextFieldBuilder.<String> createRequired("projectKey", project, "projectKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("Project requires a Key")
                .build());
        
        add(TextAreaBuilder.<String> create("description", project, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description can be add")
                .build());
        
        final Customer selectByPrimaryKey =
                _customerService.selectByPrimaryKey(project.getIdCustomer());
        if (selectByPrimaryKey != null) {
            customerName.setName(selectByPrimaryKey.getName());
        }
        customerFiel =
                TextFieldBuilder.<String> create("idCustomer", customerName, "name")
                        .withTooltip("customer can be affected").buildTextField();
        add(customerFiel);
        
        if (project.getId() != null) {
            currentEntityList = personGroupService.getTeamsOfProject(project.getId());
        }
        final IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();
        
        final ListMultipleChoice<IEntity> listEntite =
                new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this,
                        "selectedEntity"), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        add(listEntite);
        
        final List<IEntity> allTeamsPG =
                (List<IEntity>) (List<?>) personGroupService.getAllTeamsPG();
        final SelectMultipleDialog<IEntity> dialogPersonGroup =
                new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose team", allTeamsPG)
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
                                final PersonGroup selectByPrimaryKey1 =
                                        personGroupService.selectByPrimaryKey(iEntity.getId());
                                if (!currentEntityList.contains(selectByPrimaryKey1)) {
                                    // selectByPrimaryKey1.setIdPersonGroupParent(selectByPrimaryKey1.getId());
                                    // entityNeedUpdate.add(selectByPrimaryKey1);
                                    // currentEntityList.add(selectByPrimaryKey1);
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
            
            
                for (final PersonGroup person : selectedEntity) {
                    // person.set
                    // currentEntityList.remove(person);
                    // entityNeedUpdate.add(person);
                }
                
                target.add(listEntite);
            }
        });
        
        // button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                final LayoutPage page = getCustom();
                page.setResponsePage(new ProjectPage(page.getPageParameters()));
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
                final Project projectInsert = new Project();
                projectInsert.setId(project.getId());
                projectInsert.setIdCustomer(project.getIdCustomer());
                projectInsert.setDescription(project.getDescription());
                projectInsert.setName(project.getName());
                projectInsert.setProjectKey(project.getProjectKey());
                if (projectInsert.getId() != null) {
                    prService.updateByPrimaryKey(projectInsert);
                } else {
                    prService.insert(projectInsert);
                }
                for (final PersonGroup person : entityNeedUpdate) {
                    personGroupService.saveOrUpdate(person);
                }
                page.setResponsePage(new ProjectPage(page.getPageParameters()));
                
            }
        });
        
    }
    
    
    public TextField getCustomerFiel() {
    
    
        return customerFiel;
    }
    
    
    public NameGeneric getCustomerName() {
    
    
        return customerName;
    }
    
    
    public List<PersonGroup> getPersonGroupList() {
    
    
        return currentEntityList;
    }
    
    
    public List<PersonGroup> getPersonNeedUpdate() {
    
    
        return entityNeedUpdate;
    }
    
    
    public Project getProject() {
    
    
        return project;
    }
    
    
    public List<PersonGroup> getSelectedPerson() {
    
    
        return selectedEntity;
    }
    
    
    public void setPersonGroupList(final List<PersonGroup> personGroupList) {
    
    
        currentEntityList = personGroupList;
    }
    
    
    public void setPersonNeedUpdate(final List<PersonGroup> personNeedUpdate) {
    
    
        entityNeedUpdate = personNeedUpdate;
    }
    
    
    public void setSelectedPerson(final List<PersonGroup> selectedPerson) {
    
    
        selectedEntity = selectedPerson;
    }
}
