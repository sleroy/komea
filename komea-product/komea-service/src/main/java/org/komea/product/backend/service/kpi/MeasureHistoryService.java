
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.criterias.MeasureDateComparator;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.MeasureCriteria.Criteria;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This interface provides the functions needed to manipulate the history
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
@Transactional
public final class MeasureHistoryService implements IMeasureHistoryService
{
    
    
    /**
     *
     */
    private static final String DATE_ORDER_DESC = "date DESC";
    
    private static final Logger LOGGER          = LoggerFactory
                                                        .getLogger(MeasureHistoryService.class);
    
    
    
    /**
     * Build measure criteria from search filter.
     * 
     * @param searchMeasuresDto
     *            the search filter
     * @param _historyK
     *            the history key
     * @return the history key
     */
    public static MeasureCriteria buildMeasureCriteriaFromSearchFilter(
            final SearchMeasuresDto searchMeasuresDto,
            final HistoryKey _historyK) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.setOrderByClause(DATE_ORDER_DESC);
        
        final Criteria criteria = initMeasureCriteria(_historyK, measureCriteria);
        // Add ored criterias
        if (searchMeasuresDto.hasFromDate()) {
            criteria.andDateGreaterThanOrEqualTo(searchMeasuresDto.getFromDate());
        }
        if (searchMeasuresDto.hasToDate()) {
            criteria.andDateLessThanOrEqualTo(searchMeasuresDto.getToDate());
        }
        
        return measureCriteria;
    }
    
    
    private static void createEntityCriteriaForMeasure(
            final EntityKey _entityKey,
            final MeasureCriteria.Criteria criteria) {
    
    
        criteria.andEntityIDEqualTo(_entityKey.getId());
        
    }
    
    
    /**
     * Method initMeasureCriteria.
     * 
     * @param _kpiKey
     *            HistoryKey
     * @param measureCriteria
     *            MeasureCriteria
     * @return
     */
    private static Criteria initMeasureCriteria(
            final HistoryKey _kpiKey,
            final MeasureCriteria measureCriteria) {
    
    
        final Criteria createCriteria = measureCriteria.createCriteria();
        createCriteria.andIdKpiEqualTo(_kpiKey.getKpiID());
        if (_kpiKey.hasEntityReference()) {
            createEntityCriteriaForMeasure(_kpiKey.getEntityKey(), createCriteria);
        }
        return createCriteria;
        
    }
    
    
    
    @Autowired
    private IEventEngineService esperEngine;
    
    @Autowired
    private KpiDao              kpiDao;
    
    @Autowired
    private MeasureDao          measureDAO;
    
    
    
    @Override
    public void deleteByCriteria(final MeasureCriteria _measureCriteria) {
    
    
        measureDAO.deleteByCriteria(_measureCriteria);
    }
    
    
    /**
     * Method getEsperEngine.
     * 
     * @return IEventEngineService
     */
    public final IEventEngineService getEsperEngine() {
    
    
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
     *         org.komea.product.backend.service.history
     *         .IHistoryService#getFilteredHistory(HistoryKey, int,
     *         MeasureCriteria)
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
        
        return getListOfMeasures(rowBounds, measureCriteria);
    }
    
    
    /**
     * Method getFilteredHistory.
     * 
     * @param _kpiKey
     *            HistoryKey
     * @param measureCriteria
     *            MeasureCriteria
     * @return List<Measure>
     * @see org.komea.product.backend.api.IHistoryService#getFilteredHistory(HistoryKey, MeasureCriteria)
     */
    @Override
    public List<Measure> getFilteredHistory(
            final HistoryKey _kpiKey,
            final MeasureCriteria measureCriteria) {
    
    
        initMeasureCriteria(_kpiKey, measureCriteria);
        
        return measureDAO.selectByCriteria(measureCriteria);
    }
    
    
    /**
     * Returns the measures.
     * 
     * @param _kpiKey
     *            HistoryKey
     * @return List<Measure>
     * @see org.komea.product.backend.api.IHistoryService#getHistory(HistoryKey)
     */
    @Override
    public List<Measure> getHistory(final HistoryKey _kpiKey) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        return getFilteredHistory(_kpiKey, measureCriteria);
    }
    
    
    public KpiDao getKpiDao() {
    
    
        return kpiDao;
    }
    
    
    /**
     * Method getMeasureDAO.
     * 
     * @return MeasureDao
     */
    public final MeasureDao getMeasureDAO() {
    
    
        return measureDAO;
    }
    
    
    /**
     * Method getMeasures : obtain a list of measures for a subset of KPI and a
     * subset of entities and filters provided by a DTO.
     * 
     * @param kpis
     *            List<Kpi>
     * @param entities
     *            List<BaseEntityDto>
     * @param searchMeasuresDto
     *            SearchMeasuresDto
     * @return List<Measure>
     * @see
     *      org.komea.product.backend.service.measure.IMeasureService#getMeasures
     *      (List<Kpi>, List<BaseEntityDto>, SearchMeasuresDto)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<MeasureDto> getMeasures(
            final Collection<Kpi> _kpis,
            final Collection<? extends IEntity> _entities,
            final SearchMeasuresDto _searchMeasuresDto) {
    
    
        if (_kpis.isEmpty() || _entities.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        final Integer limit = _searchMeasuresDto.getNbMeasures();
        final RowBounds rowBounds = buildRowBounds(limit);
        final List<MeasureDto> measures = new ArrayList<MeasureDto>(1000);
        for (final IEntity entity : _entities) {
            for (final Kpi kpi : _kpis) {
                final MeasureCriteria measureCriteria =
                        buildMeasureCriteriaFromSearchFilter(_searchMeasuresDto,
                                HistoryKey.of(kpi.getId(), entity.getEntityKey()));
                final List<MeasureDto> listOfMeasures =
                        MeasureDto.convert(getListOfMeasures(rowBounds, measureCriteria),
                                kpi.getKpiKey());
                measures.addAll(listOfMeasures);
            }
        }
        Collections.sort(measures, new MeasureDateComparator());
        return measures;
    }
    
    
    /**
     * Method setEsperEngine.
     * 
     * @param _esperEngine
     *            IEventEngineService
     */
    public void setEsperEngine(final IEventEngineService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setKpiDao(final KpiDao kpiDao) {
    
    
        this.kpiDao = kpiDao;
    }
    
    
    public void setMeasureDAO(final MeasureDao _measureDAO) {
    
    
        measureDAO = _measureDAO;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.api.IMeasureHistoryService#storeMeasure(org
     * .komea.product.service.dto.KpiKey, double,
     * org.komea.product.service.dto.EntityKey, org.joda.time.DateTime)
     */
    @SuppressWarnings("boxing")
    @Override
    public void storeMeasure(
            final HistoryKey _ofKpi,
            final Double _value,
            final DateTime _analysisDate) {
    
    
        final Measure measure =
                Measure.initializeMeasure(_ofKpi.getKpiID(), _ofKpi.getEntityKey().getId());
        measure.setValue(_value);
        measure.setDateTime(_analysisDate);
        measureDAO.insert(measure);
        
    }
    
    
    /**
     * Method storeMeasure.
     * 
     * @param _measure
     *            Measure
     * @see org.komea.product.backend.api.IHistoryService#storeMeasure(Measure)
     */
    @Override
    public void storeMeasure(final Measure _measure) {
    
    
        LOGGER.debug("Storing new measure : {}", _measure);
        
        measureDAO.insert(_measure);
        
    }
    
    
    private RowBounds buildRowBounds(final Integer limit) {
    
    
        return new RowBounds(0, limit == null ? Integer.MAX_VALUE : limit);
    }
    
    
    private List<Measure> getListOfMeasures(
            final RowBounds rowBounds,
            final MeasureCriteria measureCriteria) {
    
    
        final List<Measure> list =
                measureDAO.selectByCriteriaWithRowbounds(measureCriteria, rowBounds);
        LOGGER.trace("Get list of measures {} {} returns {}", rowBounds, measureCriteria, list);
        return list;
    }
    
    
    private void saveOrUpdate(final Measure _measure) {
    
    
        measureDAO.insert(_measure);
        
    }
    
    
}
