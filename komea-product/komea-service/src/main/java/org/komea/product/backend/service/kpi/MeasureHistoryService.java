
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.SearchMeasuresDto;
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
public final class MeasureHistoryService extends AbstractService<Measure, Integer, MeasureCriteria>
        implements IMeasureHistoryService
{
    
    
    /**
     * 
     */
    private static final String DATE_ORDER_DESC = "date DESC";
    
    private static final Logger LOGGER          = LoggerFactory
                                                        .getLogger(MeasureHistoryService.class);
    
    @Autowired
    private IEsperEngine        esperEngine;
    
    @Autowired
    private MeasureDao          requiredDAO;
    
    
    
    /**
     * Builds the history purge action.
     * 
     * @param _kpi
     *            the kpi
     * @return the history purge action. * @see
     *         org.komea.product.backend.service.kpi.IMeasureHistoryService#buildHistoryPurgeAction(Kpi)
     */
    @Override
    public IHistoryPurgeAction buildHistoryPurgeAction(final Kpi _kpi) {
    
    
        switch (_kpi.getEvictionType()) {
            case DAYS:
                return new HistoryPurgePerDaysAction(requiredDAO, _kpi);
            case MONTHS:
                return new HistoryPurgePerMonthsAction(requiredDAO, _kpi);
            case VALUES:
                return new HistoryPurgePerValuesAction(requiredDAO, _kpi);
                
        }
        return null;
        
    }
    
    
    /**
     * Method getEsperEngine.
     * 
     * @return IEsperEngine
     */
    public final IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    /**
     * This method get the last n measure for a history key
     * 
     * @param _kpiKey
     *            the kpi key
     * @param _nbRow
     *            the number of result asked
     * @param _criteria
     *            MeasureCriteria
     * @return the measures list * @see
     *         org.komea.product.backend.service.history.IHistoryService#getFilteredHistory(HistoryKey,
     *         int, MeasureCriteria)
     */
    @Override
    public List<Measure> getFilteredHistory(
            final HistoryKey _kpiKey,
            final int _nbRow,
            final MeasureCriteria _criteria) {
    
    
        final RowBounds rowBounds = new RowBounds(0, _nbRow);
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.setOrderByClause(DATE_ORDER_DESC);
        initMeasureCriteria(_kpiKey, measureCriteria);
        
        
        return requiredDAO.selectByCriteriaWithRowbounds(measureCriteria, rowBounds);
    }
    
    
    /**
     * Method getFilteredHistory.
     * 
     * @param _kpiKey
     *            HistoryKey
     * @param measureCriteria
     *            MeasureCriteria
     * @return List<Measure>
     * @see org.komea.product.backend.service.history.IHistoryService#getFilteredHistory(HistoryKey, MeasureCriteria)
     */
    @Override
    public List<Measure> getFilteredHistory(
            final HistoryKey _kpiKey,
            final MeasureCriteria measureCriteria) {
    
    
        initMeasureCriteria(_kpiKey, measureCriteria);
        
        final List<Measure> selectByCriteria = requiredDAO.selectByCriteria(measureCriteria);
        return selectByCriteria;
    }
    
    
    /**
     * Returns the measures.
     * 
     * @param _kpiKey
     *            HistoryKey
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
     * 
     * @return MeasureDao
     */
    public final MeasureDao getMeasureDAO() {
    
    
        return requiredDAO;
    }
    
    
    /**
     * Method getMeasures : obtain a list of measures for a subset of KPI and a subset of entities and filters provided by a DTO.
     * 
     * @param kpis
     *            List<Kpi>
     * @param entities
     *            List<BaseEntity>
     * @param searchMeasuresDto
     *            SearchMeasuresDto
     * @return List<Measure>
     * @see
     *      org.komea.product.backend.service.measure.IMeasureService#getMeasures(List<Kpi>,
     *      List<BaseEntity>, SearchMeasuresDto)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Measure> getMeasures(
            final List<Kpi> kpis,
            final List<BaseEntity> entities,
            final SearchMeasuresDto searchMeasuresDto) {
    
    
        if (kpis.isEmpty() || entities.isEmpty()) { return Collections.EMPTY_LIST; }
        final Integer limit = searchMeasuresDto.getNbMeasures();
        final RowBounds rowBounds = new RowBounds(0, limit == null ? Integer.MAX_VALUE : limit);
        final List<Measure> measures = new ArrayList<Measure>(1000);
        for (final IEntity entity : entities) {
            final Integer idEntity = entity.getId();
            for (final Kpi kpi : kpis) {
                final Integer idKpi = kpi.getId();
                final MeasureCriteria measureCriteria =
                        buildMeasureCriteriaFromSearchFilter(searchMeasuresDto, idEntity, idKpi);
                final List<Measure> foundMeasures =
                        requiredDAO.selectByCriteriaWithRowbounds(measureCriteria, rowBounds);
                measures.addAll(foundMeasures);
            }
        }
        Collections.sort(measures, new DateComparator());
        return measures;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<Measure, Integer, MeasureCriteria> getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    /**
     * Method setEsperEngine.
     * 
     * @param _esperEngine
     *            IEsperEngine
     */
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /**
     * Method setMeasureDAO.
     * 
     * @param _measureDAO
     *            MeasureDao
     */
    public void setMeasureDAO(final MeasureDao _measureDAO) {
    
    
        requiredDAO = _measureDAO;
    }
    
    
    public void setRequiredDAO(final MeasureDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
    
    
    /**
     * Method storeMeasure.
     * 
     * @param _measure
     *            Measure
     * @see org.komea.product.backend.service.history.IHistoryService#storeMeasure(Measure)
     */
    @Override
    public void storeMeasure(final Measure _measure) {
    
    
        LOGGER.debug("Storing new measure : {}", _measure);
        requiredDAO.insert(_measure);
        
    }
    
    
    private MeasureCriteria buildMeasureCriteriaFromSearchFilter(
            final SearchMeasuresDto searchMeasuresDto,
            final Integer idEntity,
            final Integer idKpi) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.setOrderByClause(DATE_ORDER_DESC);
        final MeasureCriteria.Criteria criteria = measureCriteria.createCriteria();
        criteria.andIdKpiEqualTo(idKpi);
        if (searchMeasuresDto.hasFromDate()) {
            criteria.andDateGreaterThanOrEqualTo(searchMeasuresDto.getFromDate());
        }
        if (searchMeasuresDto.hasToDate()) {
            criteria.andDateLessThanOrEqualTo(searchMeasuresDto.getToDate());
        }
        createMeasureCriteriaForEntity(searchMeasuresDto, idEntity, criteria);
        return measureCriteria;
    }
    
    
    private void createMeasureCriteriaForEntity(
            final SearchMeasuresDto searchMeasuresDto,
            final Integer idEntity,
            final MeasureCriteria.Criteria criteria) {
    
    
        switch (searchMeasuresDto.getEntityType()) {
            case PERSON:
                criteria.andIdPersonEqualTo(idEntity);
                break;
            case PROJECT:
                criteria.andIdProjectEqualTo(idEntity);
                break;
            case TEAM:
            case DEPARTMENT:
                criteria.andIdPersonGroupEqualTo(idEntity);
                break;
            default:
                break;
        }
    }
    
    
    /**
     * Method initMeasureCriteria.
     * 
     * @param _kpiKey
     *            HistoryKey
     * @param measureCriteria
     *            MeasureCriteria
     */
    private void initMeasureCriteria(final HistoryKey _kpiKey, final MeasureCriteria measureCriteria) {
    
    
        final Criteria createCriteria = measureCriteria.createCriteria();
        createCriteria.andIdKpiEqualTo(_kpiKey.getKpiID());
        
    }
}
