
package org.komea.product.backend.service.entities;


import java.util.List;

import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.HasProjectTagDao;
import org.komea.product.database.dao.LinkDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dao.TagDao;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.HasProjectTagCriteria;
import org.komea.product.database.model.HasProjectTagKey;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.LinkCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public final class ProjectService implements IProjectService {
    
    @Autowired
    private PersonDao                personDAO;
    
    @Autowired
    private ProjectDao               projectDao;
    
    @Autowired
    private HasProjectPersonDao      projectPersonDAO;
    
    @Autowired
    private HasProjectTagDao         projectTagsDAO;
    
    @Autowired
    private TagDao                   tagDAO;
    
    @Autowired
    private LinkDao                  linkDAO;
    
    @Autowired
    private CustomerDao              customerDAO;
    
    @Autowired
    private IPersonGroupService      groupService;
    
    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;
    
    @Autowired
    private PersonGroupDao           personGroupDao;
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getAllProjects()
     */
    @Override
    @Autowired
    public List<ProjectDto> getAllProjects() {
    
        ProjectCriteria request = new ProjectCriteria();
        List<Project> projects = projectDao.selectByCriteria(request);
        List<ProjectDto> projectDTOs = Lists.newArrayList();
        for (Project project : projects) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setDescription(project.getDescription());
            projectDto.setName(project.getName());
            projectDto.setProjectKey(project.getProjectKey());
            projectDto.associatePersonList(getPersonsAssociateToProject(project.getId()));
            projectDto.associateTeamList(getTeamsAssociateToProject(project.getId()));
            Customer customer = customerDAO.selectByPrimaryKey(project.getIdCustomer());
            if (customer != null) {
                projectDto.setCustomer(customer.getName());
            }
            projectDto.setTags(getProjectTags(project.getId()));
            projectDto.addLinkList(getprojectList(project.getId()));
            projectDTOs.add(projectDto);
        }
        
        return projectDTOs;
    }
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getPersonsAssociateToProject(int)
     */
    @Override
    public List<Person> getPersonsAssociateToProject(final int _projectID) {
    
        List<Person> personList = Lists.newArrayList();
        HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectID);
        List<HasProjectPersonKey> selection = projectPersonDAO.selectByCriteria(criteria);
        for (HasProjectPersonKey hasProjectPersonKey : selection) {
            personList.add(personDAO.selectByPrimaryKey(hasProjectPersonKey.getIdPerson()));
        }
        return personList;
    }
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getprojectList(java.lang.Integer)
     */
    @Override
    public List<Link> getprojectList(final Integer _projectId) {
    
        LinkCriteria criteria = new LinkCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectId);
        
        return linkDAO.selectByCriteria(criteria);
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getProjectTags(java.lang.Integer)
     */
    @Override
    public List<String> getProjectTags(final Integer _projectId) {
    
        List<String> tags = Lists.newArrayList();
        HasProjectTagCriteria criteria = new HasProjectTagCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectId);
        List<HasProjectTagKey> projectTags = projectTagsDAO.selectByCriteria(criteria);
        for (HasProjectTagKey projectTag : projectTags) {
            tags.add(tagDAO.selectByPrimaryKey(projectTag.getIdTag()).getName());
            
        }
        return tags;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getTeamsAssociateToProject(java.lang.Integer)
     */
    @Override
    public List<PersonGroup> getTeamsAssociateToProject(final Integer _projectID) {
    
        List<PersonGroup> groupList = Lists.newArrayList();
        HasProjectPersonGroupCriteria criteria = new HasProjectPersonGroupCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectID);
        List<HasProjectPersonGroupKey> selection = projectPersonGroupDAO.selectByCriteria(criteria);
        for (HasProjectPersonGroupKey hasProjectPersonGroupKey : selection) {
            PersonGroupCriteria departmentCriteria = new PersonGroupCriteria();
            departmentCriteria.createCriteria().andIdEqualTo(hasProjectPersonGroupKey.getIdPersonGroup())
                    .andTypeEqualTo(PersonGroupType.DEPARTMENT);
            groupList.addAll(personGroupDao.selectByCriteria(departmentCriteria));
        }
        return groupList;
    }
    
}
