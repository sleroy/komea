
package org.komea.product.wicket.person;



import org.apache.wicket.markup.html.panel.FeedbackPanel;
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
        
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        final PersonForm personForm =
                new PersonForm(personRoleDAO, personDAO, "form", feedbackPanel,
                        new CompoundPropertyModel<PersonDto>(_personDTO));
        add(personForm);
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "Add a developer";
    }
    
    
}
