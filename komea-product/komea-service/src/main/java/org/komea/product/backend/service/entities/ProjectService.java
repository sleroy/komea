
package org.komea.product.backend.service.entities;



import java.util.ArrayList;
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
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.enums.EntityType;
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
import org.komea.product.database.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;



/**
 */
@Service
public final class ProjectService implements IProjectService
{
    
    
    @Autowired
    private CustomerDao              customerDAO;
    
    @Autowired
    private IPersonGroupService      groupService;
    
    @Autowired
    private LinkDao                  linkDAO;
    
    @Autowired
    private PersonDao                personDAO;
    
    @Autowired
    private PersonGroupDao           personGroupDao;
    
    @Autowired
    private ProjectDao               projectDao;
    
    @Autowired
    private HasProjectPersonDao      projectPersonDAO;
    
    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;
    
    @Autowired
    private HasProjectTagDao         projectTagsDAO;
    
    @Autowired
    private TagDao                   tagDAO;
    
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getAllProjects()
     */
    @Override
    @Autowired
    public List<ProjectDto> getAllProjects() {
    
    
        final ProjectCriteria request = new ProjectCriteria();
        final List<Project> projects = projectDao.selectByCriteria(request);
        final List<ProjectDto> projectDTOs = Lists.newArrayList();
        for (final Project project : projects) {
            final ProjectDto projectDto = new ProjectDto();
            projectDto.setDescription(project.getDescription());
            projectDto.setName(project.getName());
            projectDto.setProjectKey(project.getProjectKey());
            projectDto.associatePersonList(getPersonsAssociateToProject(project.getId()));
            projectDto.associateTeamList(getTeamsAssociateToProject(project.getId()));
            final Customer customer = customerDAO.selectByPrimaryKey(project.getIdCustomer());
            if (customer != null) {
                projectDto.setCustomer(customer.getName());
            }
            projectDto.setTags(getProjectTags(project.getId()));
            projectDto.addLinkList(getProjectLinks(project.getId()));
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
    
    
        final List<Person> personList = Lists.newArrayList();
        final HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectID);
        final List<HasProjectPersonKey> selection = projectPersonDAO.selectByCriteria(criteria);
        for (final HasProjectPersonKey hasProjectPersonKey : selection) {
            final Person person = personDAO.selectByPrimaryKey(hasProjectPersonKey.getIdPerson());
            if (person != null) {
                
                personList.add(person);
            }
        }
        return personList;
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getProjectLinks(java.lang.Integer)
     */
    @Override
    public List<Link> getProjectLinks(final Integer _projectId) {
    
    
        final LinkCriteria criteria = new LinkCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectId);
        
        return linkDAO.selectByCriteria(criteria);
    }
    
    
    /**
     * Method getProjects.
     * @param projectKeys List<String>
     * @return List<Project>
     * @see org.komea.product.backend.service.entities.IProjectService#getProjects(List<String>)
     */
    @Override
    public List<Project> getProjects(final List<String> projectKeys) {
    
    
        final ProjectCriteria projectCriteria = new ProjectCriteria();
        if (projectKeys.isEmpty()) {
            projectCriteria.createCriteria();
        } else {
            for (final String entityKey : projectKeys) {
                final ProjectCriteria.Criteria criteria = projectCriteria.or();
                criteria.andProjectKeyEqualTo(entityKey);
            }
        }
        return projectDao.selectByCriteria(projectCriteria);
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IProjectService#getProjectTags(java.lang.Integer)
     */
    @Override
    public List<String> getProjectTags(final Integer _projectId) {
    
    
        final List<String> tags = Lists.newArrayList();
        final HasProjectTagCriteria criteria = new HasProjectTagCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectId);
        final List<HasProjectTagKey> projectTags = projectTagsDAO.selectByCriteria(criteria);
        for (final HasProjectTagKey projectTag : projectTags) {
            final Tag tag = tagDAO.selectByPrimaryKey(projectTag.getIdTag());
            if (tag != null) {
                tags.add(tag.getName());
            }
            
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
    
    
        final List<PersonGroup> groupList = Lists.newArrayList();
        final HasProjectPersonGroupCriteria criteria = new HasProjectPersonGroupCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectID);
        final List<HasProjectPersonGroupKey> selection =
                projectPersonGroupDAO.selectByCriteria(criteria);
        for (final HasProjectPersonGroupKey hasProjectPersonGroupKey : selection) {
            final PersonGroupCriteria departmentCriteria = new PersonGroupCriteria();
            departmentCriteria.createCriteria()
                    .andIdEqualTo(hasProjectPersonGroupKey.getIdPersonGroup())
                    .andTypeEqualTo(PersonGroupType.DEPARTMENT);
            groupList.addAll(personGroupDao.selectByCriteria(departmentCriteria));
        }
        return groupList;
    }
    
    
    /**
     * Method projectsToBaseEntities.
     * @param projects List<Project>
     * @return List<BaseEntity>
     * @see org.komea.product.backend.service.entities.IProjectService#projectsToBaseEntities(List<Project>)
     */
    @Override
    public List<BaseEntity> projectsToBaseEntities(final List<Project> projects) {
    
    
        final List<BaseEntity> entities = new ArrayList<BaseEntity>(projects.size());
        for (final Project project : projects) {
            final BaseEntity entity =
                    new BaseEntity(EntityType.PROJECT, project.getId(), project.getProjectKey(),
                            project.getName(), project.getDescription());
            entities.add(entity);
        }
        return entities;
    }
    
    
}
