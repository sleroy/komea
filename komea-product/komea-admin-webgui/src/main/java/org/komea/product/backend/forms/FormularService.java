/**
 * 
 */

package org.komea.product.backend.forms;



import org.komea.product.backend.api.IFormularService;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.PersonRoleCriteria;
import org.komea.product.database.model.ProjectCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class FormularService implements IFormularService
{
    
    
    @Autowired
    private PersonRoleDao  personRoleDAO;
    
    
    @Autowired
    private ProjectDao     projectDAO;
    
    
    @Autowired
    private PersonGroupDao userGroupDAO;
    
    
    
    /**
     * 
     */
    public FormularService() {
    
    
        super();
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
    public ProjectDao getProjectDAO() {
    
    
        return projectDAO;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.forms.IFormularService#newPersonForm()
     */
    @Override
    public PersonFormData newPersonForm() {
    
    
        final PersonFormData personFormData = new PersonFormData();
        personFormData.setPersonRoles(personRoleDAO.selectByCriteria(new PersonRoleCriteria()));
        personFormData.setProjects(projectDAO.selectByCriteria(new ProjectCriteria()));
        final PersonGroupCriteria teams = new PersonGroupCriteria();
        teams.createCriteria().andTypeEqualTo(PersonGroupType.TEAM);
        personFormData.setTeams(getUserGroupDAO().selectByCriteria(teams));
        final PersonGroupCriteria deps = new PersonGroupCriteria();
        deps.createCriteria().andTypeEqualTo(PersonGroupType.DEPARTMENT);
        personFormData.setDepartments(getUserGroupDAO().selectByCriteria(deps));
        return personFormData;
    }
    
    
    /**
     * @param _personRoleDAO
     *            the personRoleDAO to set
     */
    public void setPersonRoleDAO(final PersonRoleDao _personRoleDAO) {
    
    
        personRoleDAO = _personRoleDAO;
    }
    
    
    /**
     * @param _projectDAO
     *            the projectDAO to set
     */
    public void setProjectDAO(final ProjectDao _projectDAO) {
    
    
        projectDAO = _projectDAO;
    }


    public PersonGroupDao getUserGroupDAO() {
    
    
        return userGroupDAO;
    }


    public void setUserGroupDAO(PersonGroupDao _userGroupDAO) {
    
    
        userGroupDAO = _userGroupDAO;
    }
}
