
package org.komea.product.plugins.rss.admin;



import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IFormularService;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.KomeaEntry;
import org.komea.product.wicket.LayoutPage;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class RssRepositoryAddPage extends LayoutPage
{
    
    
    @SpringBean
    private IFormularService   formularService;
    
    @SpringBean
    private IPersonService     personDAO;
    
    
    @SpringBean
    private IPersonRoleService personRoleDAO;
    
    
    @SpringBean
    private IProjectService    projectDAO;
    
    
    
    public RssRepositoryAddPage(final PageParameters _parameters) {
    
    
        this(_parameters, new Person());
        
    }
    
    
    public RssRepositoryAddPage(final PageParameters _parameters, final Person _person) {
    
    
        super(_parameters);
        
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        final PersonFormData newPersonForm = formularService.newPersonForm();
        final RepositoryForm personForm =
                new RepositoryForm(personDAO, newPersonForm, "form", feedbackPanel,
                        new CompoundPropertyModel<Person>(_person));
        add(personForm);
        
    }
    
    
    /**
     * @return the formularService
     */
    public IFormularService getFormularService() {
    
    
        return formularService;
    }
    
    
    @Override
    public List<? extends Entry<String, Class>> getMiddleLevelPages() {
    
    
        return Collections.singletonList(new KomeaEntry<String, Class>(
                getString("RssRepositoryPage.title"), RssRepositoryPage.class));
        
    }
    
    
    /**
     * @return the personDAO
     */
    public IPersonService getPersonDAO() {
    
    
        return personDAO;
    }
    
    
    /**
     * @return the personRoleDAO
     */
    public IPersonRoleService getPersonRoleDAO() {
    
    
        return personRoleDAO;
    }
    
    
    /**
     * @return the projectDAO
     */
    public IProjectService getProjectDAO() {
    
    
        return projectDAO;
    }
    
    
    /**
     * @param _formularService
     *            the formularService to set
     */
    public void setFormularService(final IFormularService _formularService) {
    
    
        formularService = _formularService;
    }
    
    
    /**
     * @param _personDAO
     *            the personDAO to set
     */
    public void setPersonDAO(final IPersonService _personDAO) {
    
    
        personDAO = _personDAO;
    }
    
    
    /**
     * @param _personRoleDAO
     *            the personRoleDAO to set
     */
    public void setPersonRoleDAO(final IPersonRoleService _personRoleDAO) {
    
    
        personRoleDAO = _personRoleDAO;
    }
    
    
    /**
     * @param _projectDAO
     *            the projectDAO to set
     */
    public void setProjectDAO(final IProjectService _projectDAO) {
    
    
        projectDAO = _projectDAO;
    }
    
    
}
