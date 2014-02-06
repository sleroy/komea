
package org.komea.product.backend.service;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.backend.service.kpi.KpiKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.enums.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class EntityService implements IEntityService
{
    
    @Autowired
    private PersonDao      personDAO;
    
    @Autowired
    private PersonGroupDao personGroupDao;
    @Autowired
    private ProjectDao     projectDao;
    
    public EntityService() {
    
        super();
    }
    
    @Override
    public <TEntity extends IEntity> TEntity getEntity(final EntityType _entityType, final int _key) {
    
        switch (_entityType) {
            case PERSON:
                return (TEntity) personDAO.selectByPrimaryKey(_key);
            case PERSONG_GROUP:
                return (TEntity) personGroupDao.selectByPrimaryKey(_key);
            case PROJECT:
                return (TEntity) projectDao.selectByPrimaryKey(_key);
        }
        return null;
        
    }
    
    
    @Override
    public IEntity getEntityAssociatedToKpi(final KpiKey _kpiKey) {
    
    
        return getEntity(_kpiKey.getEntityType(), _kpiKey.getEntityID());
    }
    
    
    @Override
    public IEntity getEntityOrFail(final EntityType _entityType, final int _entityID) {
    
    
        final IEntity entity = getEntity(_entityType, _entityID);
        if (entity == null) { throw new EntityNotFoundException(_entityID, _entityType); }
        return entity;
    }
    
    /**
     * @return the personDAO
     */
    public PersonDao getPersonDAO() {
    
        return personDAO;
    }
    
    /**
     * @return the personGroupDao
     */
    public PersonGroupDao getPersonGroupDao() {
    
        return personGroupDao;
    }
    
    /**
     * @return the projectDao
     */
    public ProjectDao getProjectDao() {
    
        return projectDao;
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
    
    /**
     * @param _personDAO
     *            the personDAO to set
     */
    public void setPersonDAO(final PersonDao _personDAO) {
    
        personDAO = _personDAO;
    }
    
    /**
     * @param _personGroupDao
     *            the personGroupDao to set
     */
    public void setPersonGroupDao(final PersonGroupDao _personGroupDao) {
    
        personGroupDao = _personGroupDao;
    }
    
    /**
     * @param _projectDao
     *            the projectDao to set
     */
    public void setProjectDao(final ProjectDao _projectDao) {
    
        projectDao = _projectDao;
    }
}
