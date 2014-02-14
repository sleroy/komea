package org.komea.product.backend.service.entities;

import java.util.ArrayList;
import java.util.List;
import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class EntityService implements IEntityService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PersonGroupDao personGroupDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IPersonGroupService personGroupService;

    public EntityService() {

        super();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IEntityService#getEntity(org.komea.product.database.enums.EntityType,
     * int)
     */
    @Override
    public <TEntity extends IEntity> TEntity getEntity(final EntityType _entityType, final int _key) {

        switch (_entityType) {
            case PERSON:
                return (TEntity) personDao.selectByPrimaryKey(_key);
            case TEAM:
            case DEPARTMENT:
                return (TEntity) personGroupDao.selectByPrimaryKey(_key);
            case PROJECT:
                return (TEntity) projectDao.selectByPrimaryKey(_key);
        }
        return null;

    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IEntityService#getEntityAssociatedToKpi(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public IEntity getEntityAssociatedToKpi(final KpiKey _kpiKey) {

        return getEntity(_kpiKey.getEntityType(), _kpiKey.getEntityID());
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IEntityService#getEntityOrFail(org.komea.product.database.enums.EntityType,
     * int)
     */
    @Override
    public IEntity getEntityOrFail(final EntityType _entityType, final int _entityID) {

        final IEntity entity = getEntity(_entityType, _entityID);
        if (entity == null) {
            throw new EntityNotFoundException(_entityID, _entityType);
        }
        return entity;
    }

    @Override
    public <TEntity extends IEntity> List<TEntity> loadEntities(final EntityType _entityType, final List<Integer> _keys) {

        final List<TEntity> listOfEntities = new ArrayList<TEntity>(_keys.size());
        for (final Integer key : _keys) {
            IEntity entity = getEntity(_entityType, key);
            if (entity != null) {
                listOfEntities.add((TEntity) entity);
            }
        }
        return listOfEntities;
    }

    @Override
    public List<BaseEntity> getEntities(final EntityType entityType, final List<String> entityKeys) {
        final List<BaseEntity> entities = new ArrayList<BaseEntity>(entityKeys.size());
        switch (entityType) {
            case PERSON:
                final List<Person> persons = personService.getPersons(entityKeys);
                entities.addAll(personService.personsToBaseEntities(persons));
                break;
            case TEAM:
            case DEPARTMENT:
                final List<PersonGroup> personGroups = personGroupService.getPersonGroups(
                        entityKeys, entityType);
                entities.addAll(personGroupService.personGroupsToBaseEntities(personGroups, entityType));
                break;
            case PROJECT:
                final List<Project> projects = projectService.getProjects(entityKeys);
                entities.addAll(projectService.projectsToBaseEntities(projects));
                break;
        }
        return entities;
    }

}
