
package org.komea.product.wicket.person;



import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



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
    
    
        super(_parameters);
        
        
       
        List<Person> listAffichage = personDAO.selectAll();
         final IDeleteAction<Person> personDeleteAction = new PersonDeleteAction(personDAO,listAffichage,this);
        final IEditAction<Person> personEditAction = new PersonEditAction(this, personRoleDAO);
        final ISortableDataProvider<Person, String> dataProvider =
//                new PersonDataModel(personDAO.selectAll());
                new ListDataModel(listAffichage);
        
        add(DataTableBuilder.<Person, String> newTable("table")
                .addColumn(new LoginColumn(Model.of(getString("global.save.form.field.label.login")))).addColumn(getString("memberpage.save.form.label.lastn"), "lastName")
                .addColumn(getString("memberpage.save.form.label.firstn"), "firstName").addColumn(getString("memberpage.save.form.label.email"), "email")
                .withEditDeleteColumn(personDeleteAction, personEditAction).displayRows(listAffichage.size()+10)
                .withData(dataProvider).build());
        
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("memberpage.main.title");
    }
    
}
