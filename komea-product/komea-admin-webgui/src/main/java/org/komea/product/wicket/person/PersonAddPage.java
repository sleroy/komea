
package org.komea.product.wicket.person;



import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IFormularService;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.PersonService;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.KomeaEntry;
import org.komea.product.wicket.LayoutPage;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class PersonAddPage extends LayoutPage
{
    
    
    @SpringBean
    private IFormularService formularService;
    
    @SpringBean
    private PersonService    personDAO;
    
    
    @SpringBean
    private PersonRoleDao    personRoleDAO;
    
    
    @SpringBean
    private ProjectDao       projectDAO;
    
    
    
    public PersonAddPage(final PageParameters _parameters) {
    
    
        this(_parameters, new Person());
        
    }
    
    
    public PersonAddPage(final PageParameters _parameters, final Person _person) {
    
    
        super(_parameters);
        
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        final PersonFormData newPersonForm = formularService.newPersonForm();
        final PersonForm personForm =
                new PersonForm(personDAO, newPersonForm, "form", feedbackPanel,
                        new CompoundPropertyModel<Person>(_person));
        add(personForm);
        
    }
    
    
    @Override
    public List<? extends Entry<String, Class>> getMiddleLevelPages() {
    
    
        return Collections.singletonList(new KomeaEntry<String, Class>(
                getString("PersonPage.title"), PersonPage.class));
        
    }
    
    
    /**
     * @return the personDAO
     */
    public PersonService getPersonDAO() {
    
    
        return personDAO;
    }
    
    
    /**
     * @return the personRoleDAO
     */
    public PersonRoleDao getPersonRoleDAO() {
    
    
        return personRoleDAO;
    }
    
    
    /**
     * @return the projectDAO
     */
    public ProjectDao getProjecDAO() {
    
    
        return projectDAO;
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("PersonAddPage.title");
    }
    
    
    /**
     * @param _personDAO
     *            the personDAO to set
     */
    public void setPersonDAO(final PersonService _personDAO) {
    
    
        personDAO = _personDAO;
    }
    
    
    /**
     * @param _personRoleDAO
     *            the personRoleDAO to set
     */
    public void setPersonRoleDAO(final PersonRoleDao _personRoleDAO) {
    
    
        personRoleDAO = _personRoleDAO;
    }
    
    
    /**
     * @param _projecDAO
     *            the projectDAO to set
     */
    public void setProjecDAO(final ProjectDao _projecDAO) {
    
    
        projectDAO = _projecDAO;
    }
    
}
