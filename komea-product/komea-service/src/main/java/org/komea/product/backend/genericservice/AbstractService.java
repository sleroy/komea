/**
 *
 */
package org.komea.product.backend.genericservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.dao.IGenericDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
public abstract class AbstractService<TEntity extends IHasKey, PK extends Serializable, TCriteria>
        implements IGenericService<TEntity, PK, TCriteria> {

    @Autowired
    private DAOEventRegistry daoEventRegistry;

    /**
     * Abstract Service./
     */
    public AbstractService() {

        super();
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#countByCriteria(java.lang.Object)
     */
    @Override
    public int countByCriteria(final TCriteria _example) {

        return getRequiredDAO().countByCriteria(_example);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#delete(java.lang.Object)
     */
    @Override
    public void delete(final TEntity _entity) {

        deleteByPrimaryKey((PK) _entity.getId());
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#deleteByCriteria(java.lang.Object)
     */
    @Override
    public int deleteByCriteria(final TCriteria _example) {

        for (final TEntity entity : getRequiredDAO().selectByCriteria(_example)) {
            daoEventRegistry.notifyDeleted(entity);
        }
        return getRequiredDAO().deleteByCriteria(_example);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#deleteByPrimaryKey(java.io.Serializable)
     */
    @Override
    public int deleteByPrimaryKey(final PK _id) {

        daoEventRegistry.notifyDeleted(selectByPrimaryKey(_id));
        return getRequiredDAO().deleteByPrimaryKey(_id);
    }

    @Override
    public boolean exists(final String key) {
        return getRequiredDAO().countByCriteria(createKeyCriteria(key)) > 0;
    }

    public DAOEventRegistry getDaoEventRegistry() {

        return daoEventRegistry;
    }

    /**
     * Returns the required DAO.
     *
     * @return the required DAO.
     */
    public abstract IGenericDAO<TEntity, PK, TCriteria> getRequiredDAO();

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#insert(java.lang.Object)
     */
    @Override
    public int insert(final TEntity _record) {

        daoEventRegistry.notifyUpdated(_record);
        return getRequiredDAO().insert(_record);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#insertSelective(java.lang.Object)
     */
    @Override
    public int insertSelective(final TEntity _record) {

        daoEventRegistry.notifyUpdated(_record);
        return getRequiredDAO().insertSelective(_record);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(final TEntity _tEntity) {

        if (_tEntity.getId() == null) {
            insert(_tEntity);
        } else {
            updateByPrimaryKey(_tEntity);
        }

    }

    @Override
    public List<TEntity> selectAll() {

        return selectByCriteria(null);
    }

    protected abstract TCriteria createKeyCriteria(String key);

    @Override
    public TEntity selectByKey(String key) {

        return CollectionUtil.singleOrNull(selectByCriteria(createKeyCriteria(key)));
    }

    @Override
    public List<TEntity> selectByKeys(List<String> keys) {
        final List<TEntity> elements = new ArrayList<TEntity>(keys.size());
        for (final String key : keys) {
            final TEntity element = selectByKey(key);
            if (element != null) {
                elements.add(element);
            }
        }
        return elements;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#selectByCriteria(java.lang.Object)
     */
    @Override
    public List<TEntity> selectByCriteria(final TCriteria _example) {

        return getRequiredDAO().selectByCriteria(_example);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#selectByCriteriaWithRowbounds(java.lang.Object, int, int)
     */
    @Override
    public List<TEntity> selectByCriteriaWithRowbounds(
            final TCriteria _example,
            final int _offset,
            final int _limit) {

        return getRequiredDAO().selectByCriteriaWithRowbounds(_example,
                new RowBounds(_offset, _limit));
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#selectByPrimaryKey(java.io.Serializable)
     */
    @Override
    public TEntity selectByPrimaryKey(final PK _id) {

        return getRequiredDAO().selectByPrimaryKey(_id);
    }

    @Override
    public List<TEntity> selectByPrimaryKeyList(final List<PK> _ids) {
        final List<TEntity> elements = new ArrayList<TEntity>(_ids.size());
        for (final PK id : _ids) {
            final TEntity element = selectByPrimaryKey(id);
            if (element != null) {
                elements.add(element);
            }
        }
        return elements;
    }

    public void setDaoEventRegistry(final DAOEventRegistry _daoEventRegistry) {

        daoEventRegistry = _daoEventRegistry;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#updateByCriteria(java.lang.Object, java.lang.Object)
     */
    @Override
    public int updateByCriteria(final TEntity _record, final TCriteria _example) {

        for (final TEntity entity : getRequiredDAO().selectByCriteria(_example)) {
            daoEventRegistry.notifyUpdated(entity);
        }

        return getRequiredDAO().updateByCriteria(_record, _example);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#updateByCriteriaSelective(java.lang.Object, java.lang.Object)
     */
    @Override
    public int updateByCriteriaSelective(final TEntity _record, final TCriteria _example) {

        for (final TEntity entity : getRequiredDAO().selectByCriteria(_example)) {
            daoEventRegistry.notifyUpdated(entity);
        }

        return getRequiredDAO().updateByCriteriaSelective(_record, _example);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#updateByPrimaryKey(java.lang.Object)
     */
    @Override
    public int updateByPrimaryKey(final TEntity _record) {

        daoEventRegistry.notifyUpdated(_record);

        return getRequiredDAO().updateByPrimaryKey(_record);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IGenericService#updateByPrimaryKeySelective(java.lang.Object)
     */
    @Override
    public int updateByPrimaryKeySelective(final TEntity _record) {

        daoEventRegistry.notifyUpdated(_record);

        return getRequiredDAO().updateByPrimaryKey(_record);
    }

}
