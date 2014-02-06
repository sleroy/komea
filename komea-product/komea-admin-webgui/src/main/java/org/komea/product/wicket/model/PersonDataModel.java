
package org.komea.product.wicket.model;



import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;



public final class PersonDataModel extends SortableDataProvider<Person, String>
{
    
    
    private final PersonDao personDAO;
    
    
    
    public PersonDataModel(final PersonDao _personDAO) {
    
    
        personDAO = _personDAO;
        
        
    }
    
    
    @Override
    public Iterator<? extends Person> iterator(final long _first, final long _count) {
    
    
        final PersonCriteria example = new PersonCriteria();
        final List<Person> selectByCriteria = personDAO.selectByCriteria(example);
        
        return selectByCriteria.subList((int) _first, (int) _count).iterator();
    }
    
    
    @Override
    public IModel<Person> model(final Person _object) {
    
    
        return Model.of(_object);
    }
    
    
    @Override
    public long size() {
    
    
        return getPersons().size();
    }
    
    
    private List<Person> getPersons() {
    
    
        final PersonCriteria example = new PersonCriteria();
        return personDAO.selectByCriteria(example);
    }
}
