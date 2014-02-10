
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.MeasureCriteria.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This interface provides the functions needed to manipulate the history
 * 
 * @author sleroy
 */
@Service
public final class MeasureHistoryService implements IMeasureHistoryService {
    
    @Autowired
    private MeasureDao          measureDAO;
    
    @Autowired
    private IEsperEngine        esperEngine;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasureHistoryService.class);
    
    public MeasureHistoryService() {
    
        super();
    }
    
    /**
     * Builds the history purge action.
     * 
     * @param _kpi
     *            the kpi
     * @return the history purge action.
     */
    @Override
    public IHistoryPurgeAction buildHistoryPurgeAction(final Kpi _kpi) {
    
        switch (_kpi.getEvictionType()) {
            case DAYS:
                return new HistoryPurgePerDaysAction(measureDAO, _kpi);
            case MONTHS:
                return new HistoryPurgePerMonthsAction(measureDAO, _kpi);
            case VALUES:
                return new HistoryPurgePerValuesAction(measureDAO, _kpi);
                
        }
        return null;
        
    }
    
    public IEsperEngine getEsperEngine() {
    
        return esperEngine;
    }
    
    public MeasureDao getMeasureDAO() {
    
        return measureDAO;
    }
    
    /**
     * Returns the measures.
     */
    @Override
    public List<Measure> getMeasures(final HistoryKey _kpiKey) {
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        return getMeasures(_kpiKey, measureCriteria);
    }
    
    @Override
    public List<Measure> getMeasures(final HistoryKey _kpiKey, final MeasureCriteria measureCriteria) {
    
        final Criteria createCriteria = measureCriteria.createCriteria();
        createCriteria.andIdKpiEqualTo(_kpiKey.getKpiID());
        switch (_kpiKey.getEntityType()) {
            case PERSON:
                createCriteria.andIdPersonEqualTo(_kpiKey.getEntityID());
                break;
            case PERSON_GROUP:
                createCriteria.andIdPersonGroupEqualTo(_kpiKey.getEntityID());
                break;
            case PROJECT:
                createCriteria.andIdProjectEqualTo(_kpiKey.getEntityID());
                break;
            default:
                // TODO:: Add code for default statement
                throw new UnsupportedOperationException("Not implemented default statement");
                
        }
        
        final List<Measure> selectByCriteria = measureDAO.selectByCriteria(measureCriteria);
        return selectByCriteria;
    }
    
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
        esperEngine = _esperEngine;
    }
    
    public void setMeasureDAO(final MeasureDao _measureDAO) {
    
        measureDAO = _measureDAO;
    }
    
    @Override
    public void storeMeasure(final Measure _measure) {
    
        LOGGER.debug("Storing new measure : {}", _measure);
        measureDAO.insert(_measure);
        
    }
}
