
package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class SystemProjectBean implements ISystemProjectBean
{
    
    
    @Autowired
    private ProjectDao          projectDAO;
    
    
    private Project             systemProject;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemProjectBean.class);
    
    
    
    public SystemProjectBean() {
    
    
        super();
    }
    
    
    public ProjectDao getProjectDAO() {
    
    
        return projectDAO;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISystemProject#getSystemProject()
     */
    @Override
    public Project getSystemProject() {
    
    
        return systemProject;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("--SYSTEM PROJECT--");
        final ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.createCriteria().andNameEqualTo("SYSTEM");
        systemProject = CollectionUtil.singleOrNull(projectDAO.selectByCriteria(projectCriteria));
        if (systemProject == null) {
            
            LOGGER.info("Creating system project for Komea");
            systemProject = new Project();
            systemProject.setDescription("System project for Komea Statistics");
            systemProject.setName("Komea System");
            systemProject.setProjectKey("SYSTEM");
            
            projectDAO.insert(systemProject);
            
        }
        
        LOGGER.info("--SYSTEM PROJECT--");
        
    }
    
    
    public void setProjectDAO(final ProjectDao _projectDAO) {
    
    
        projectDAO = _projectDAO;
    }
    
    
    public void setSystemProject(final Project _systemProject) {
    
    
        systemProject = _systemProject;
    }
}
