package org.komea.product.backend.service.kpi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.BaseEntityDto;
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
public final class MeasureHistoryService extends AbstractService<Measure, Integer, MeasureCriteria>
        implements IMeasureHistoryService {

    /**
     *
     */
    private static final String DATE_ORDER_DESC = "date DESC";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MeasureHistoryService.class);

    /**
     * Build measure criteria from search filter.
     *
     * @param searchMeasuresDto the search filter
     * @param _historyK the history key
     * @return the history key
     */
    public static MeasureCriteria buildMeasureCriteriaFromSearchFilter(
            final SearchMeasuresDto searchMeasuresDto,
            final HistoryKey _historyK) {

        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.setOrderByClause(DATE_ORDER_DESC);
        LOGGER.info("setOrderByClause: " + DATE_ORDER_DESC);

        final Criteria criteria = initMeasureCriteria(_historyK, measureCriteria);
        // Add ored criterias
        if (searchMeasuresDto.hasFromDate()) {
            LOGGER.info("andDateGreaterThanOrEqualTo: " + searchMeasuresDto.getFromDate());
            criteria.andDateGreaterThanOrEqualTo(searchMeasuresDto.getFromDate());
        }
        if (searchMeasuresDto.hasToDate()) {
            LOGGER.info("andDateLessThanOrEqualTo: " + searchMeasuresDto.getToDate());
            criteria.andDateLessThanOrEqualTo(searchMeasuresDto.getToDate());
        }

        return measureCriteria;
    }

    private static void createEntityCriteriaForMeasure(
            final EntityKey _entityKey,
            final MeasureCriteria.Criteria criteria) {

        switch (_entityKey.getEntityType()) {
            case PERSON:
                LOGGER.info("andIdPersonEqualTo: " + _entityKey.getId());
                criteria.andIdPersonEqualTo(_entityKey.getId());
                break;
            case PROJECT:
                LOGGER.info("andIdProjectEqualTo: " + _entityKey.getId());
                criteria.andIdProjectEqualTo(_entityKey.getId());
                break;
            case TEAM:
            case DEPARTMENT:
                LOGGER.info("andIdPersonGroupEqualTo: " + _entityKey.getId());
                criteria.andIdPersonGroupEqualTo(_entityKey.getId());
                break;
            default:
                break;
        }
    }

    /**
     * Method initMeasureCriteria.
     *
     * @param _kpiKey HistoryKey
     * @param measureCriteria MeasureCriteria
     * @return
     */
    private static Criteria initMeasureCriteria(
            final HistoryKey _kpiKey,
            final MeasureCriteria measureCriteria) {

        final Criteria createCriteria = measureCriteria.createCriteria();
        LOGGER.info("andIdKpiEqualTo: " + _kpiKey.getKpiID());
        createCriteria.andIdKpiEqualTo(_kpiKey.getKpiID());
        if (_kpiKey.hasEntityReference()) {
            createEntityCriteriaForMeasure(_kpiKey.getEntityKey(), createCriteria);
        }
        return createCriteria;

    }

    @Autowired
    private IEventEngineService esperEngine;

    @Autowired
    private MeasureDao requiredDAO;

    /**
     * Builds the history purge action.
     *
     * @param _kpi the kpi
     * @return the history purge action. * @see
     * org.komea.product.backend.service.kpi.IMeasureHistoryService#buildHistoryPurgeAction(Kpi)
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
     * @return IEventEngineService
     */
    public final IEventEngineService getEsperEngine() {

        return esperEngine;
    }

    /**
     * This method get the last n measure for a history key
     *
     * @param _kpiKey the kpi key
     * @param _nbRow the number of result asked
     * @param _criteria MeasureCriteria
     * @return the measures list * @see
     * org.komea.product.backend.service.history.IHistoryService#getFilteredHistory(HistoryKey,
     * int, MeasureCriteria)
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
     * @param _kpiKey HistoryKey
     * @param measureCriteria MeasureCriteria
     * @return List<Measure>
     * @see
     * org.komea.product.backend.service.history.IHistoryService#getFilteredHistory(HistoryKey,
     * MeasureCriteria)
     */
    @Override
    public List<Measure> getFilteredHistory(
            final HistoryKey _kpiKey,
            final MeasureCriteria measureCriteria) {

        initMeasureCriteria(_kpiKey, measureCriteria);

        return requiredDAO.selectByCriteria(measureCriteria);
    }

    /**
     * Returns the measures.
     *
     * @param _kpiKey HistoryKey
     * @return List<Measure>
     * @see
     * org.komea.product.backend.service.history.IHistoryService#getHistory(HistoryKey)
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
     * Method getMeasures : obtain a list of measures for a subset of KPI and a
     * subset of entities and filters provided by a DTO.
     *
     * @param kpis List<Kpi>
     * @param entities List<BaseEntityDto>
     * @param searchMeasuresDto SearchMeasuresDto
     * @return List<Measure>
     * @see
     * org.komea.product.backend.service.measure.IMeasureService#getMeasures(List<Kpi>,
     * List<BaseEntityDto>, SearchMeasuresDto)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Measure> getMeasures(
            final Collection<Kpi> kpis,
            final Collection<BaseEntityDto> entities,
            final SearchMeasuresDto searchMeasuresDto) {

        if (kpis.isEmpty() || entities.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        final Integer limit = searchMeasuresDto.getNbMeasures();
        final RowBounds rowBounds = new RowBounds(0, limit == null ? Integer.MAX_VALUE : limit);
        final List<Measure> measures = new ArrayList<Measure>(1000);
        LOGGER.info("getMeasures");
        for (final IEntity entity : entities) {
            LOGGER.info("rntity: " + entity);
            for (final Kpi kpi : kpis) {
                LOGGER.info("kpi: " + kpi);
                final MeasureCriteria measureCriteria
                        = buildMeasureCriteriaFromSearchFilter(searchMeasuresDto,
                                HistoryKey.of(kpi.getId(), entity.getEntityKey()));
                LOGGER.info("rowBounds: " + rowBounds.getOffset() + " - " + rowBounds.getLimit());
                final List<Measure> listOfMeasures = getListOfMeasures(rowBounds, measureCriteria);
                LOGGER.info("criteria results: " + listOfMeasures);
                measures.addAll(listOfMeasures);
            }
        }
        LOGGER.info("result measures : " + measures);
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
     * @param _esperEngine IEventEngineService
     */
    public void setEsperEngine(final IEventEngineService _esperEngine) {

        esperEngine = _esperEngine;
    }

    /**
     * Method setMeasureDAO.
     *
     * @param _measureDAO MeasureDao
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
     * @param _measure Measure
     * @see
     * org.komea.product.backend.service.history.IHistoryService#storeMeasure(Measure)
     */
    @Override
    public void storeMeasure(final Measure _measure) {

        LOGGER.debug("Storing new measure : {}", _measure);
        requiredDAO.insert(_measure);

    }

    private List<Measure> getListOfMeasures(
            final RowBounds rowBounds,
            final MeasureCriteria measureCriteria) {

        final List<Measure> list
                = requiredDAO.selectByCriteriaWithRowbounds(measureCriteria, rowBounds);
        LOGGER.trace("Get list of measures {} {} returns {}", rowBounds, measureCriteria, list);
        return list;
    }

    @Override
    protected MeasureCriteria createKeyCriteria(String key) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
