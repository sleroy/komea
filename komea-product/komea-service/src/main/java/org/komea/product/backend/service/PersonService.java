
package org.komea.product.backend.service;


import java.util.List;

import org.komea.product.database.dao.PersonMapper;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Komea implementation og the person service
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 4 févr. 2014
 */
@Service
public final class PersonService implements IPersonService
{
    
    @Autowired
    private PersonMapper personRepository;
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.IPersonService#getPersonList()
     */
    @Override
    public List<Person> getPersonList() {
    
        PersonCriteria request = new PersonCriteria();
        List<Person> persons = personRepository.selectByExample(request);
        return persons;
    }
    //
}
