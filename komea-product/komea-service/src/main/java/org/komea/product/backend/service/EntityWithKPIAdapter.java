
package org.komea.product.backend.service;



import java.util.List;

import org.komea.product.backend.kpi.EntityWithKPI;
import org.komea.product.backend.service.business.IEntityWithKPI;
import org.komea.product.backend.service.kpi.IEntityWithKPIAdapter;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class EntityWithKPIAdapter implements IEntityWithKPIAdapter
{
    
    
    @Autowired
    private KpiDao kpiDAO;
    
    
    
    public EntityWithKPIAdapter() {
    
    
        super();
    }
    
    
    @Override
    public IEntityWithKPI<Person> adapt(final Person _person) {
    
    
        return buildEntity(_person.getId(), _person, EntityType.PERSON);
    }
    
    
    @Override
    public IEntityWithKPI<PersonGroup> adapt(final PersonGroup _personGroup) {
    
    
        return buildEntity(_personGroup.getId(), _personGroup, EntityType.PERSONG_GROUP);
    }
    
    
    @Override
    public IEntityWithKPI<Project> adapt(final Project _project) {
    
    
        return buildEntity(_project.getId(), _project, EntityType.PROJECT);
    }
    
    
    public KpiDao getKpiDAO() {
    
    
        return kpiDAO;
    }
    
    
    public KpiDao getKpIDAO() {
    
    
        return kpiDAO;
    }
    
    
    public void setKpiDAO(final KpiDao _kpiDAO) {
    
    
        kpiDAO = _kpiDAO;
    }
    
    
    public void setKpIDAO(final KpiDao _kpIDAO) {
    
    
        kpiDAO = _kpIDAO;
    }
    
    
    private IEntityWithKPI buildEntity(
            final int _entityID,
            final Object _person,
            final EntityType _entityType) {
    
    
        final KpiCriteria criteria = new KpiCriteria();
        criteria.createCriteria().andEntityTypeEqualTo(_entityType);
        final List<Kpi> kpiCriterias = kpiDAO.selectByCriteria(criteria);
        final EntityWithKPI entityWithKPI =
                new EntityWithKPI(_entityID, _person, kpiCriterias, _entityType);
        return entityWithKPI;
    }
    
}
