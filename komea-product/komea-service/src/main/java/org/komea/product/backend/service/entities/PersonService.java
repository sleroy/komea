
package org.komea.product.backend.service.entities;


import java.util.List;

import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public final class PersonService implements IPersonService {
    
    @Autowired
    private PersonDao           personDAO;
    
    @Autowired
    private IPersonGroupService groupService;
    
    @Autowired
    private PersonRoleDao       roleDao;
    
    @Autowired
    private HasProjectPersonDao projectPersonDao;
    
    @Autowired
    private ProjectDao          projectDAO;
    
    /**
     * (non-Javadoc)
     * 
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
            personDto.setId(person.getId());
            personDto.setEmail(person.getEmail());
            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setLogin(person.getLogin());
            personDto.modifyDepartment(groupService.getDepartment(person.getIdPersonGroup()));
            personDto.modifyTeam(groupService.getTeam(person.getIdPersonGroup()));
            PersonRole role = roleDao.selectByPrimaryKey(person.getIdPersonRole());
            if (role != null) {
                personDto.setRole(role.getName());
            }
            personDto.associateToProjectList(getProjectsAssociateToAPerson(person.getId()));
            personDtos.add(personDto);
            
        }
        return personDtos;
    }
    
    @Override
    public List<Project> getProjectsAssociateToAPerson(final Integer _personId) {
    
        HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdPersonEqualTo(_personId);
        List<HasProjectPersonKey> result = projectPersonDao.selectByCriteria(criteria);
        List<Project> projects = Lists.newArrayList();
        for (HasProjectPersonKey hasProjectPersonKey : result) {
            Project project = projectDAO.selectByPrimaryKey(hasProjectPersonKey.getIdProject());
            if (project != null) {
                projects.add(project);
            }
        }
        return projects;
    }
    
}
