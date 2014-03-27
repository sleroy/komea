package org.komea.product.backend.service.entities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.kpi.IMeasureHistoryService;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.HasProjectTagDao;
import org.komea.product.database.dao.LinkDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dao.TagDao;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.HasProjectTagCriteria;
import org.komea.product.database.model.HasProjectTagKey;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.LinkCriteria;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
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
    private IPersonGroupService personGroupService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IMeasureHistoryService measureService;

    @Autowired
    private IProjectPersonService projectPersonService;

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
            projectDto.associatePersonList(personService.getPersonsOfPersonGroup(project.getId()));
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
     * @return the projectPersonGroupDAO
     */
    public HasProjectPersonGroupDao getProjectPersonGroupDAO() {

        return projectPersonGroupDAO;
    }

    @Override
    public List<Project> getProjectsOfPerson(final Integer _personId) {
        final List<HasProjectPersonKey> result = projectPersonService.getProjectIdsOfPerson(_personId);
        final List<Project> projects = Lists.newArrayList();
        for (final HasProjectPersonKey hasProjectPersonKey : result) {
            final Project project = selectByPrimaryKey(hasProjectPersonKey.getIdProject());
            if (project != null) {
                projects.add(project);
            }
        }
        return projects;
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
     * @return List<BaseEntityDto>
     * @see
     * org.komea.product.backend.service.entities.IProjectService#projectsToBaseEntities(List<Project>)
     */
    @Override
    public List<BaseEntityDto> projectsToBaseEntities(final List<Project> projects) {

        final List<BaseEntityDto> entities = new ArrayList<BaseEntityDto>(projects.size());
        for (final Project project : projects) {
            final BaseEntityDto entity
                    = new BaseEntityDto(EntityType.PROJECT, project.getId(), project.getProjectKey(),
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
    protected ProjectCriteria createPersonCriteriaOnLogin(final String key) {

        final ProjectCriteria criteria = new ProjectCriteria();
        criteria.createCriteria().andProjectKeyEqualTo(key);
        return criteria;
    }

    @Override
    public void saveOrUpdateProject(final Project _project, final List<Tag> _tags,
            final List<Person> _persons, final List<Link> _links, final List<PersonGroup> _teams) {
        saveOrUpdate(_project);
        final Integer idProject = _project.getId();

        projectPersonService.updatePersonsOfProject(_persons, _project);

        final HasProjectTagCriteria hasProjectTagCriteria = new HasProjectTagCriteria();
        hasProjectTagCriteria.createCriteria().andIdProjectEqualTo(idProject);
        projectTagsDAO.deleteByCriteria(hasProjectTagCriteria);
        if (_tags != null) {
            for (final Tag tag : _tags) {
                projectTagsDAO.insert(new HasProjectTagKey(idProject, tag.getId()));
            }
        }

        final HasProjectPersonGroupCriteria hasProjectPersonGroupCriteria = new HasProjectPersonGroupCriteria();
        hasProjectPersonGroupCriteria.createCriteria().andIdProjectEqualTo(idProject);
        projectPersonGroupDAO.deleteByCriteria(hasProjectPersonGroupCriteria);
        if (_teams != null) {
            for (final PersonGroup team : _teams) {
                projectPersonGroupDAO.insert(new HasProjectPersonGroupKey(idProject, team.getId()));
            }
        }

        final LinkCriteria linkCriteria = new LinkCriteria();
        linkCriteria.createCriteria().andIdProjectEqualTo(idProject);
        linkDAO.deleteByCriteria(linkCriteria);
        if (_links != null) {
            for (final Link link : _links) {
                linkDAO.insert(link);
            }
        }
    }

    @Override
    public void deleteProject(final Project _project) {
        final Integer idProject = _project.getId();

        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andIdProjectEqualTo(idProject);
        measureService.deleteByCriteria(measureCriteria);

        projectPersonService.updatePersonsOfProject(Collections.<Person>emptyList(), _project);

        final HasProjectTagCriteria hasProjectTagCriteria = new HasProjectTagCriteria();
        hasProjectTagCriteria.createCriteria().andIdProjectEqualTo(idProject);
        projectTagsDAO.deleteByCriteria(hasProjectTagCriteria);

        final HasProjectPersonGroupCriteria hasProjectPersonGroupCriteria = new HasProjectPersonGroupCriteria();
        hasProjectPersonGroupCriteria.createCriteria().andIdProjectEqualTo(idProject);
        projectPersonGroupDAO.deleteByCriteria(hasProjectPersonGroupCriteria);

        final LinkCriteria linkCriteria = new LinkCriteria();
        linkCriteria.createCriteria().andIdProjectEqualTo(idProject);
        linkDAO.deleteByCriteria(linkCriteria);

        delete(_project);
    }

}
