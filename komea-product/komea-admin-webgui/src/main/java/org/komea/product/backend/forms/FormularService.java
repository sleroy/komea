/**
 * 
 */

package org.komea.product.backend.forms;



import org.komea.product.backend.api.IFormularService;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IProjectService;
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
    private IPersonRoleService  personRoleDAO;
    
    
    @Autowired
    private IProjectService     projectDAO;
    
    
    @Autowired
    private IPersonGroupService userGroupDAO;
    
    
    
    /**
     * 
     */
    public FormularService() {
    
    
        super();
    }
    
    
    /**
     * @return the personRoleDAO
     */
    public IPersonRoleService getPersonRoleDAO() {
    
    
        return personRoleDAO;
    }
    
    
    /**
     * @return the projectDAO
     */
    public IProjectService getProjectDAO() {
    
    
        return projectDAO;
    }
    
    
    public IPersonGroupService getUserGroupDAO() {
    
    
        return userGroupDAO;
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
    public void setPersonRoleDAO(final IPersonRoleService _personRoleDAO) {
    
    
        personRoleDAO = _personRoleDAO;
    }
    
    
    /**
     * @param _projectDAO
     *            the projectDAO to set
     */
    public void setProjectDAO(final IProjectService _projectDAO) {
    
    
        projectDAO = _projectDAO;
    }
    
    
    public void setUserGroupDAO(final IPersonGroupService _userGroupDAO) {
    
    
        userGroupDAO = _userGroupDAO;
    }
}
