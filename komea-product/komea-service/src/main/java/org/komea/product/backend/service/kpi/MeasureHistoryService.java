
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
 * @version $Revision: 1.0 $
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
    
     * @return the history purge action. * @see org.komea.product.backend.service.kpi.IMeasureHistoryService#buildHistoryPurgeAction(Kpi)
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
    
    
    /**
     * Method getEsperEngine.
     * @return IEsperEngine
     */
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
    
     * @param _criteria MeasureCriteria
     * @return the measures list * @see org.komea.product.backend.service.history.IHistoryService#getFilteredHistory(HistoryKey, int, MeasureCriteria)
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
    
    
    /**
     * Method getFilteredHistory.
     * @param _kpiKey HistoryKey
     * @param measureCriteria MeasureCriteria
     * @return List<Measure>
     * @see org.komea.product.backend.service.history.IHistoryService#getFilteredHistory(HistoryKey, MeasureCriteria)
     */
    @Override
    public List<Measure> getFilteredHistory(
            final HistoryKey _kpiKey,
            final MeasureCriteria measureCriteria) {
    
    
        initMeasureCriteria(_kpiKey, measureCriteria);
        
        final List<Measure> selectByCriteria = measureDAO.selectByCriteria(measureCriteria);
        return selectByCriteria;
    }
    
    
    /**
     * Returns the measures.
     * @param _kpiKey HistoryKey
     * @return List<Measure>
     * @see org.komea.product.backend.service.history.IHistoryService#getHistory(HistoryKey)
     */
    @Override
    public List<Measure> getHistory(final HistoryKey _kpiKey) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        return getFilteredHistory(_kpiKey, measureCriteria);
    }
    
    
    /**
     * Method getMeasureDAO.
     * @return MeasureDao
     */
    public MeasureDao getMeasureDAO() {
    
    
        return measureDAO;
    }
    
    
    /**
     * Method setEsperEngine.
     * @param _esperEngine IEsperEngine
     */
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /**
     * Method setMeasureDAO.
     * @param _measureDAO MeasureDao
     */
    public void setMeasureDAO(final MeasureDao _measureDAO) {
    
    
        measureDAO = _measureDAO;
    }
    
    
    /**
     * Method storeMeasure.
     * @param _measure Measure
     * @see org.komea.product.backend.service.history.IHistoryService#storeMeasure(Measure)
     */
    @Override
    public void storeMeasure(final Measure _measure) {
    
    
        LOGGER.debug("Storing new measure : {}", _measure);
        measureDAO.insert(_measure);
        
    }
    
    
    /**
     * Method initMeasureCriteria.
     * @param _kpiKey HistoryKey
     * @param measureCriteria MeasureCriteria
     */
    private void initMeasureCriteria(final HistoryKey _kpiKey, final MeasureCriteria measureCriteria) {
    
    
        final Criteria createCriteria = measureCriteria.createCriteria();
        createCriteria.andIdKpiEqualTo(_kpiKey.getKpiID());
        
    }
}
