
package org.komea.product.backend.service.entities;


import java.util.List;

import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public final class PersonService implements IPersonService {
    
    @Autowired
    private PersonDao           personDAO;
    
    @Autowired
    private IPersonGroupService groupService;
    
    /**
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IPersonService#getPersonList()
     */
    @Override
    public List<PersonDto> getPersonList() {
    
        // TOTO STUB
        PersonCriteria request = new PersonCriteria();
        List<Person> persons = personDAO.selectByCriteria(request);
        
        List<PersonDto> personDtos = Lists.newArrayList();
        for (Person person : persons) {
            PersonDto personDto = new PersonDto();
            personDto.setEmail(person.getEmail());
            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setLogin(person.getLogin());
            PersonGroup department = groupService.getDepartment(person.getId());
            personDtos.add(personDto);
            
        }
        return personDtos;
    }
    
}
