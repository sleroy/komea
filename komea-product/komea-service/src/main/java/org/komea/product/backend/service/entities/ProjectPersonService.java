
package org.komea.product.backend.service.entities;



import java.util.List;

import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 */
@Service
@Transactional
public class ProjectPersonService implements IProjectPersonService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectPersonService.class);
    
    @Autowired
    private HasProjectPersonDao projectPersonDAO;
    
    
    
    @Override
    public List<HasProjectPersonKey> getPersonIdsOfProject(final Integer _projectId) {
    
    
        final HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectId);
        return projectPersonDAO.selectByCriteria(criteria);
    }
    
    
    @Override
    public List<HasProjectPersonKey> getProjectIdsOfPerson(final Integer _personId) {
    
    
        final HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdPersonEqualTo(_personId);
        return projectPersonDAO.selectByCriteria(criteria);
    }
    
    
    public HasProjectPersonDao getProjectPersonDAO() {
    
    
        return projectPersonDAO;
    }
    
    
    public void setProjectPersonDAO(final HasProjectPersonDao _projectPersonDAO) {
    
    
        projectPersonDAO = _projectPersonDAO;
    }
    
    
    @Override
    @Transactional
    public void updatePersonsOfProject(final List<Person> _persons, final Project _project) {
    
    
        final HasProjectPersonCriteria hasProjectPersonCriteria = new HasProjectPersonCriteria();
        hasProjectPersonCriteria.createCriteria().andIdProjectEqualTo(_project.getId());
        projectPersonDAO.deleteByCriteria(hasProjectPersonCriteria);
        if (_persons != null) {
            for (final Person person : _persons) {
                projectPersonDAO.insert(new HasProjectPersonKey(_project.getId(), person.getId()));
            }
        }
    }
    
    
    @Override
    @Transactional
    public void updateProjectsOfPerson(final List<Project> _projects, final Person _person) {
    
    
        final HasProjectPersonCriteria hasProjectPersonCriteria = new HasProjectPersonCriteria();
        hasProjectPersonCriteria.createCriteria().andIdPersonEqualTo(_person.getId());
        projectPersonDAO.deleteByCriteria(hasProjectPersonCriteria);
        if (_projects != null) {
            for (final Project project : _projects) {
                projectPersonDAO.insert(new HasProjectPersonKey(project.getId(), _person.getId()));
            }
        }
    }
}
