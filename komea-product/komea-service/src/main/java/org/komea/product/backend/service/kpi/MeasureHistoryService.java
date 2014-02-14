
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.service.history.HistoryKey;
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
public final class MeasureHistoryService implements IMeasureHistoryService
{
    
    
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
    
    
    /**
     * This method get the last n measure for a history key
     * 
     * @param _kpiKey
     *            the kpi key
     * @param _nbRow
     *            the number of result asked
     * @return the measures list
     */
    @Override
    public List<Measure> getFilteredHistory(
            final HistoryKey _kpiKey,
            final int _nbRow,
            final MeasureCriteria _criteria) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.setOrderByClause("date DESC");
        initMeasureCriteria(_kpiKey, measureCriteria);
        
        final RowBounds rowBounds = new RowBounds(0, _nbRow);
        final List<Measure> selectByCriteria =
                measureDAO.selectByCriteriaWithRowbounds(measureCriteria, rowBounds);
        return selectByCriteria;
    }
    
    
    public MeasureDao getMeasureDAO() {
    
    
        return measureDAO;
    }
    
    
    /**
     * Returns the measures.
     */
    @Override
    public List<Measure> getHistory(final HistoryKey _kpiKey) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        return getFilteredHistory(_kpiKey, measureCriteria);
    }
    
    
    @Override
    public List<Measure> getFilteredHistory(final HistoryKey _kpiKey, final MeasureCriteria measureCriteria) {
    
    
        initMeasureCriteria(_kpiKey, measureCriteria);
        
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
    
    
    private void initMeasureCriteria(final HistoryKey _kpiKey, final MeasureCriteria measureCriteria) {
    
    
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
    }
}
