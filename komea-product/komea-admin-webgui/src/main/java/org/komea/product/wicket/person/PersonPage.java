
package org.komea.product.wicket.person;



import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.PersonDataModel;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class PersonPage extends LayoutPage
{
    
    
    @SpringBean
    private IPersonService     personDAO;
    @SpringBean
    private IPersonRoleService personRoleDAO;
    
    
    
    public PersonPage(final PageParameters _parameters) {
    
    
        this(_parameters, new PersonDto());
        
        
    }
    
    
    public PersonPage(final PageParameters _parameters, final PersonDto _personDTO) {
    
    
        super(_parameters);
        
        
        final IDeleteAction<Person> personDeleteAction = new PersonDeleteAction(personDAO);
        
        final IEditAction<Person> personEditAction = new PersonEditAction(this, personRoleDAO);
        final ISortableDataProvider<Person, String> dataProvider = new PersonDataModel(personDAO);
        add(DataTableBuilder.<Person, String> newTable("table").addColumn("Login", "login")
                .addColumn("First name", "firstName").addColumn("Last name", "lastName")
                .addColumn("Email", "email")
                .withEditDeleteColumn(personDeleteAction, personEditAction).displayRows(10)
                .withData(dataProvider).build());
        
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("PersonPage.title");
    }
    
}
