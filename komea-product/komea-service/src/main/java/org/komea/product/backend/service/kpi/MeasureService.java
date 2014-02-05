
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.MeasureCriteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MeasureService implements IMeasureService
{
    
    
    @Autowired
    private MeasureDao measureDAO;
    
    
    
    public MeasureService() {
    
    
        super();
    }
    
    
    public MeasureDao getMeasureDAO() {
    
    
        return measureDAO;
    }
    
    
    /**
     * Returns the measures.
     */
    @Override
    public List<Measure> getMeasures(final IEntity _entity, final Kpi _kpi) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        final Criteria createCriteria = measureCriteria.createCriteria();
        createCriteria.andIdKpiEqualTo(_kpi.getId());
        switch (_entity.getType()) {
            case PERSON:
                createCriteria.andIdPersonEqualTo(_entity.getId());
                break;
            case PERSONG_GROUP:
                createCriteria.andIdPersonGroupEqualTo(_entity.getId());
                break;
            case PROJECT:
                createCriteria.andIdProjectEqualTo(_entity.getId());
                break;
        }
        final List<Measure> selectByCriteria = measureDAO.selectByCriteria(measureCriteria);
        return selectByCriteria;
    }
    
    
    public void setMeasureDAO(final MeasureDao _measureDAO) {
    
    
        measureDAO = _measureDAO;
    }
    
}
