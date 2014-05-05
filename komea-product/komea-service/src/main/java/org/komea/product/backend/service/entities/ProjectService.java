package org.komea.product.backend.service.entities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.HasProjectTagDao;
import org.komea.product.database.dao.LinkDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dao.TagDao;
import org.komea.product.database.dto.ProjectDto;
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
    private IMeasureHistoryService measureService;

    @Autowired
    private IPersonGroupService personGroupService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;

    @Autowired
    private IProjectPersonService projectPersonService;

    @Autowired
    private HasProjectTagDao projectTagsDAO;

    @Autowired
    private ProjectDao requiredDAO;

    @Autowired
    private TagDao tagDAO;

    @Override
    public void deleteProject(final Project _project) {

        final Integer idProject = _project.getId();

        deleteMesuresAssociatedToAProject(idProject);

        removeAssociatedPersonsOutAProject(_project);

        removeTagsAssociatedToAProject(idProject);

        removeTeamsAndDepartmentsAssociatedToAProject(idProject);

        removeLinksAssociatedToAProject(idProject);

        delete(_project);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IProjectService#getAllProjectsAsDtos()
     */
    @Override
    public List<ProjectDto> getAllProjectsAsDtos() {

        final List<Project> allProjectsEntities = selectAll();
        final List<ProjectDto> projectDTOs = new ArrayList<ProjectDto>(allProjectsEntities.size());
        for (final Project project : allProjectsEntities) {
            projectDTOs.add(convertProjectToProjectDTO(project));
        }
        return projectDTOs;
    }

    @Override
    public List<Project> selectAll() {
        final ProjectCriteria criteria = new ProjectCriteria();
        criteria.createCriteria().andProjectKeyNotEqualTo("SYSTEM");
        return selectByCriteria(criteria);
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
            project = createNewProjectFromKey(_projectKey);
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
    public List<Project> getProjectsOfAMember(final Integer _personId) {

        final List<HasProjectPersonKey> result
                = projectPersonService.getProjectIdsOfPerson(_personId);
        final List<Integer> projectIds = new ArrayList<Integer>(result.size());
        for (final HasProjectPersonKey projectPersonGroup : result) {
            projectIds.add(projectPersonGroup.getIdProject());
        }

        return selectByPrimaryKeyList(projectIds);
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
    public List<Project> getProjectsOfPersonGroupRecursively(final Integer _personGroupId) {

        final PersonGroup personGroup = personGroupService.selectByPrimaryKey(_personGroupId);
        if (personGroup == null) {
            throw new IllegalArgumentException(
                    "Person group with id not found" + _personGroupId);
        }

        final List<PersonGroup> groups = personGroupService.getChildrenRecursively(_personGroupId);
        groups.add(personGroup);

        final List<Project> projects = getProjectsOfPersonGroup(_personGroupId);
        for (final PersonGroup group : groups) {
            projects.addAll(getProjectsOfPersonGroup(group.getId()));
        }
        final Map<Integer, Project> projectsMap = new HashMap<Integer, Project>(projects.size());
        for (final Project project : projects) {
            projectsMap.put(project.getId(), project);
        }
        return new ArrayList<Project>(projectsMap.values());
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

            final Tag tagByKey = tagDAO.selectByPrimaryKey(projectTag.getIdTag());
            if (tagByKey != null) {
                tags.add(tagByKey.getName());
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

    @Override
    public void saveOrUpdateProject(
            final Project _project,
            final List<Tag> _tags,
            final List<Person> _persons,
            final List<Link> _links,
            final List<PersonGroup> _teams) {

        Validate.notNull(_tags);
        Validate.notNull(_persons);
        Validate.notNull(_links);
        Validate.notNull(_links);
        Validate.notNull(_teams);
        saveOrUpdate(_project);
        final Integer idProject = _project.getId();

        projectPersonService.updatePersonsOfProject(_persons, _project);

        removeTagsAssociatedToAProject(idProject);
        for (final Tag tag : _tags) {
            projectTagsDAO.insert(new HasProjectTagKey(idProject, tag.getId()));
        }

        removeTeamsAndDepartmentsAssociatedToAProject(idProject);
        for (final PersonGroup team : _teams) {
            projectPersonGroupDAO.insert(new HasProjectPersonGroupKey(idProject, team.getId()));
        }

        removeLinksAssociatedToAProject(idProject);
        for (final Link link : _links) {
            linkDAO.insert(link);
        }
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

    private ProjectDto convertProjectToProjectDTO(final Project project) {

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
        return projectDto;
    }

    /**
     * Initializes a new empty project from the key. It provides a default
     * description and name.
     *
     * @param _projectKey the project key
     * @return the new project.
     */
    private Project createNewProjectFromKey(final String _projectKey) {

        Project project;
        project = new Project();
        project.setDescription("Project automatically generated   " + _projectKey);
        project.setName(_projectKey);
        project.setProjectKey(_projectKey);
        saveOrUpdate(project);
        return project;
    }

    private void deleteMesuresAssociatedToAProject(final Integer idProject) {

        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andIdProjectEqualTo(idProject);
        measureService.deleteByCriteria(measureCriteria);
    }

    private void removeAssociatedPersonsOutAProject(final Project _project) {

        projectPersonService.updatePersonsOfProject(Collections.<Person>emptyList(), _project);
    }

    private void removeLinksAssociatedToAProject(final Integer idProject) {

        final LinkCriteria linkCriteria = new LinkCriteria();
        linkCriteria.createCriteria().andIdProjectEqualTo(idProject);
        linkDAO.deleteByCriteria(linkCriteria);
    }

    private void removeTagsAssociatedToAProject(final Integer idProject) {

        final HasProjectTagCriteria hasProjectTagCriteria = new HasProjectTagCriteria();
        hasProjectTagCriteria.createCriteria().andIdProjectEqualTo(idProject);
        projectTagsDAO.deleteByCriteria(hasProjectTagCriteria);
    }

    private void removeTeamsAndDepartmentsAssociatedToAProject(final Integer idProject) {

        final HasProjectPersonGroupCriteria hasProjectPersonGroupCriteria
                = new HasProjectPersonGroupCriteria();
        hasProjectPersonGroupCriteria.createCriteria().andIdProjectEqualTo(idProject);
        projectPersonGroupDAO.deleteByCriteria(hasProjectPersonGroupCriteria);
    }

    @Override
    protected ProjectCriteria createKeyCriteria(final String key) {

        final ProjectCriteria criteria = new ProjectCriteria();
        criteria.createCriteria().andProjectKeyEqualTo(key);
        return criteria;
    }

}
