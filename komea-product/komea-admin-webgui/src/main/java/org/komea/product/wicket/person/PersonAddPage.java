
package org.komea.product.wicket.person;



import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.wicket.LayoutPage;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class PersonAddPage extends LayoutPage
{
    
    
    @SpringBean
    private PersonDao     personDAO;
    @SpringBean
    private PersonRoleDao personRoleDAO;
    
    
    
    public PersonAddPage(final PageParameters _parameters) {
    
    
        this(_parameters, new PersonDto());
        
        
    }
    
    
    public PersonAddPage(final PageParameters _parameters, final PersonDto _personDTO) {
    
    
        super(_parameters);
        replaceFormular(_personDTO);
        
        
    }
    
    
    public PersonAddPage(final PersonDto _personDTO) {
    
    
        this(new PageParameters(), _personDTO);
        
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "Add/Edit a new User";
    }
    
    
    /**
     * Edit/Refresh formular with new informations.
     * 
     * @param _editedPerson
     *            the new person object.
     * @return the new formular
     */
    public void replaceFormular(final PersonDto _editedPerson) {
    
    
        final PersonForm personForm =
                new PersonForm(personRoleDAO, personDAO, "form",
                        new CompoundPropertyModel<PersonDto>(_editedPerson));
        add(personForm);
    }
}
