package org.komea.product.backend.service.entities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.HasProjectTagDao;
import org.komea.product.database.dao.LinkDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dao.TagDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.HasProjectTagCriteria;
import org.komea.product.database.model.HasProjectTagKey;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.LinkCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.komea.product.database.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
@Transactional
public final class ProjectService extends AbstractService<Project, Integer, ProjectCriteria>
        implements IProjectService {

    @Autowired
    private CustomerDao customerDAO;

    @Autowired
    private LinkDao linkDAO;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IPersonGroupService personGroupService;

    @Autowired
    private HasProjectPersonDao projectPersonDAO;

    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;

    @Autowired
    private HasProjectTagDao projectTagsDAO;

    @Autowired
    private ProjectDao requiredDAO;

    @Autowired
    private TagDao tagDAO;

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IProjectService#getAllProjects()
     */
    @Override
    public List<ProjectDto> getAllProjects() {

        final ProjectCriteria request = new ProjectCriteria();
        final List<Project> projects = requiredDAO.selectByCriteria(request);
        final List<ProjectDto> projectDTOs = Lists.newArrayList();
        for (final Project project : projects) {
            final ProjectDto projectDto = new ProjectDto();
            projectDto.setDescription(project.getDescription());
            projectDto.setName(project.getName());
            projectDto.setProjectKey(project.getProjectKey());
            projectDto.associatePersonList(personService.getPersonsOfProject(project.getId()));
            projectDto.associateTeamList(personGroupService.getTeamsOfProject(project.getId()));
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

    @Override
    public List<Project> getAllProjectsEntities() {

        final ProjectCriteria projectCriteria = new ProjectCriteria();
        return requiredDAO.selectByCriteria(projectCriteria);

    }

    /**
     * @return the customerDAO
     */
    public CustomerDao getCustomerDAO() {

        return customerDAO;
    }

    /**
     * @return the linkDAO
     */
    public LinkDao getLinkDAO() {

        return linkDAO;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IProjectService#getOrCreate(java.lang.String)
     */
    @Override
    public Project getOrCreate(final String _projectKey) {

        Project project = selectByKey(_projectKey);
        if (project == null) {
            project = new Project();
            project.setDescription("Project automatically generated   " + _projectKey);
            project.setName(_projectKey);
            project.setProjectKey(_projectKey);
            requiredDAO.insert(project);
            project = selectByKey(_projectKey);
        }
        return project;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IProjectService#getProjectLinks(java.lang.Integer)
     */
    @Override
    public List<Link> getProjectLinks(final Integer _projectId) {

        final LinkCriteria criteria = new LinkCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectId);

        return linkDAO.selectByCriteria(criteria);
    }

    /**
     * @return the projectPersonDAO
     */
    public HasProjectPersonDao getProjectPersonDAO() {

        return projectPersonDAO;
    }

    /**
     * @return the projectPersonGroupDAO
     */
    public HasProjectPersonGroupDao getProjectPersonGroupDAO() {

        return projectPersonGroupDAO;
    }

    /**
     * Method getProjects.
     *
     * @param projectKeys List<String>
     * @return List<Project>
     * @see
     * org.komea.product.backend.service.entities.IProjectService#getProjects(List<String>)
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
        return requiredDAO.selectByCriteria(projectCriteria);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IProjectService#getProjectTags(java.lang.Integer)
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
     * @return the projectTagsDAO
     */
    public HasProjectTagDao getProjectTagsDAO() {

        return projectTagsDAO;
    }

    @Override
    public ProjectDao getRequiredDAO() {

        return requiredDAO;
    }

    /**
     * @return the tagDAO
     */
    public TagDao getTagDAO() {

        return tagDAO;
    }

    /**
     * Method projectsToBaseEntities.
     *
     * @param projects List<Project>
     * @return List<BaseEntity>
     * @see
     * org.komea.product.backend.service.entities.IProjectService#projectsToBaseEntities(List<Project>)
     */
    @Override
    public List<BaseEntity> projectsToBaseEntities(final List<Project> projects) {

        final List<BaseEntity> entities = new ArrayList<BaseEntity>(projects.size());
        for (final Project project : projects) {
            final BaseEntity entity
                    = new BaseEntity(EntityType.PROJECT, project.getId(), project.getProjectKey(),
                            project.getName(), project.getDescription());
            entities.add(entity);
        }
        return entities;
    }

    /**
     * @param _customerDAO the customerDAO to set
     */
    public void setCustomerDAO(final CustomerDao _customerDAO) {

        customerDAO = _customerDAO;
    }

    /**
     * @param _linkDAO the linkDAO to set
     */
    public void setLinkDAO(final LinkDao _linkDAO) {

        linkDAO = _linkDAO;
    }

    /**
     * @param _projectPersonDAO the projectPersonDAO to set
     */
    public void setProjectPersonDAO(final HasProjectPersonDao _projectPersonDAO) {

        projectPersonDAO = _projectPersonDAO;
    }

    /**
     * @param _projectPersonGroupDAO the projectPersonGroupDAO to set
     */
    public void setProjectPersonGroupDAO(final HasProjectPersonGroupDao _projectPersonGroupDAO) {

        projectPersonGroupDAO = _projectPersonGroupDAO;
    }

    /**
     * @param _projectTagsDAO the projectTagsDAO to set
     */
    public void setProjectTagsDAO(final HasProjectTagDao _projectTagsDAO) {

        projectTagsDAO = _projectTagsDAO;
    }

    public void setRequiredDAO(final ProjectDao _requiredDAO) {

        requiredDAO = _requiredDAO;
    }

    /**
     * @param _tagDAO the tagDAO to set
     */
    public void setTagDAO(final TagDao _tagDAO) {

        tagDAO = _tagDAO;
    }

    @Override
    protected ProjectCriteria createPersonCriteriaOnLogin(String key) {
        final ProjectCriteria criteria = new ProjectCriteria();
        criteria.createCriteria().andProjectKeyEqualTo(key);
        return criteria;
    }

    @Override
    public List<Project> getProjectsOfPersonGroup(final Integer _personGroupId) {
        final HasProjectPersonGroupCriteria criteria = new HasProjectPersonGroupCriteria();
        criteria.createCriteria().andIdPersonGroupEqualTo(_personGroupId);
        final List<HasProjectPersonGroupKey> selectByCriteria
                = projectPersonGroupDAO.selectByCriteria(criteria);
        final List<Integer> projectIds = new ArrayList<Integer>(selectByCriteria.size());
        for (final HasProjectPersonGroupKey projectPersonGroup : selectByCriteria) {
            projectIds.add(projectPersonGroup.getIdProject());
        }
        return selectByPrimaryKeyList(projectIds);
    }

    @Override
    public List<Project> getProjectsOfPerson(final Integer _personId) {
        final HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdPersonEqualTo(_personId);
        final List<HasProjectPersonKey> result = projectPersonDAO.selectByCriteria(criteria);
        final List<Project> projects = Lists.newArrayList();
        for (final HasProjectPersonKey hasProjectPersonKey : result) {
            final Project project = selectByPrimaryKey(hasProjectPersonKey.getIdProject());
            if (project != null) {
                projects.add(project);
            }
        }
        return projects;
    }
}
