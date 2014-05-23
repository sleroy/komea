/**
 *
 */
package org.komea.product.backend.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Service
@Transactional
public class RandomizerDataJob {

    public static class LastDateStorage {

        private DateTime dateTime;

        public DateTime getDateTime() {

            return dateTime;
        }

        public boolean hasNoDateTime() {

            return dateTime == null;
        }

        public void setDateTime(final DateTime _dateTime) {

            dateTime = _dateTime;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomizerDataJob.class);

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IKpiAPI kpiAPI;

    @Autowired
    private MeasureDao measureService;

    @Autowired
    private IPluginStorageService pluginStorage;

    private final Random random = new Random();

    @Autowired
    private IStatisticsAPI statisticsAPI;

    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @PostConstruct
    public void execute() {

        LOGGER.info("Generating random values...");
        int personMeasures = generateKpiValues(EntityType.PERSON);
        if (personMeasures > 0) {
            LOGGER.info(personMeasures + " measures added for persons");
        }
        int projectMeasures = generateKpiValues(EntityType.PROJECT);
        if (projectMeasures > 0) {
            LOGGER.info(projectMeasures + " measures added for projects");
        }
        LOGGER.info("Random values generated.");

    }

    public int generateKpiValues(final EntityType _entityType) {
        final DateTime twoYearsAgo = twoYearsAgo();
        final List<Kpi> allKpisOfEntityType = kpiAPI.getAllKpisOfEntityType(_entityType);
        int cpt = 0;
        for (final IEntity entity : entityService.getEntitiesByEntityType(_entityType)) {
            for (final Kpi kpi : allKpisOfEntityType) {
//                generateKpiValues(kpi, entity);
                int addedMeasures = updateMeasures(kpi, entity, twoYearsAgo);
                if (addedMeasures > 0) {
                    LOGGER.info(addedMeasures + " measures added for entity " + entity.getDisplayName() + " and kpi " + kpi.getDisplayName());
                }
                cpt += addedMeasures;
            }
        }
        return cpt;
    }

    private int updateMeasures(final Kpi _kpi, final IEntity _entity, final DateTime _since) {
        final FormulaID formulaID = FormulaID.of(_kpi);
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        final MeasureCriteria.Criteria criteria = measureCriteria.createCriteria();
        criteria.andIdKpiEqualTo(formulaID.getId()).andEntityIDEqualTo(_entity.getId());
        final List<Measure> measures = measureService.selectByCriteria(measureCriteria);
        LOGGER.info(measures.size() + " measures exist for entity " + _entity.getDisplayName() + " and kpi " + _kpi.getDisplayName());
        DateTime date = new DateTime(_since);
        Double lastValue = null;
        int cpt = 0;
        while (date.isBeforeNow()) {
            final Double value = getValue(date, measures);
            if (value == null) {
                lastValue = addValue(_kpi, _entity, date, lastValue);
                cpt++;
            } else {
                lastValue = value;
            }
            date = date.plusDays(1);
        }
        return cpt;
    }

    private Double getValue(final DateTime date, final List<Measure> measures) {
        final DateTime nextDate = date.plusDays(1);
        final Date from = new Date(date.toDate().getTime() - 1);
        final Date to = new Date(nextDate.toDate().getTime() + 1);
        final Iterator<Measure> iterator = measures.iterator();
        Measure measure = null;
        while (iterator.hasNext()) {
            final Measure next = iterator.next();
            final Date d = next.getDate();
            if (d.after(from) && d.before(to)) {
                measure = next;
                break;
            } else if (d.before(from)) {
                iterator.remove();
            } else {
                break;
            }
        }
        return measure == null ? null : measure.getValue();
    }

    private Double addValue(final Kpi _kpi, IEntity _entity, final DateTime date, final Double lastValue) {
        Double min = _kpi.getValueMin();
        Double max = _kpi.getValueMax();
        if (min == null) {
            min = 0d;
        }
        if (max == null || max > 1000000) {
            max = 100d;
        }
        Double value;
        if (lastValue == null) {
            value = Math.random() * (max - min) + min;
        } else {
            value = lastValue * (Math.random() * 2.5 - 0.5);
        }
        value = Math.max(min, Math.min(max, value));
        final FormulaID formulaID = FormulaID.of(_kpi);
        final Measure measure = new Measure();
        measure.setDate(date.toDate());
        measure.setHour(0);
        measure.setDay(date.getDayOfMonth());
        measure.setWeek(date.getWeekOfWeekyear());
        measure.setMonth(date.getMonthOfYear());
        measure.setYear(date.getYear());
        measure.setEntityID(_entity.getId());
        measure.setIdKpi(formulaID.getId());
        measure.setValue(value);
        measureService.insert(measure);
        return value;
    }

    private DateTime twoYearsAgo() {
        DateTime date = new DateTime();
        date = date.minusHours(date.getHourOfDay());
        date = date.minusMinutes(date.getMinuteOfHour());
        date = date.minusSeconds(date.getSecondOfMinute());
        date = date.minusMillis(date.getMillisOfSecond());
        date = date.minusYears(2);
        return date;
    }

    /**
     * Generate Kpi value for a given
     *
     * @param _kpi
     * @param _entity
     */
    @Transactional
    private void generateKpiValues(final Kpi _kpi, final IEntity _entity) {

        if (measureService.countByCriteria(new MeasureCriteria()) > 0) {
            return;
        }
        DateTime previousTime = new DateTime(2012, 1, 1, 0, 0);

        final DateTime dateTime = new DateTime();
        while (previousTime.isBefore(dateTime)) {
            Double lastStoredValueInHistory
                    = statisticsAPI.getLastStoredValueInHistory(HistoryKey.of(_kpi, _entity));

            if (lastStoredValueInHistory == null || lastStoredValueInHistory == 0d) {
                lastStoredValueInHistory
                        = _kpi.getValueMin() + random.nextDouble() * interval(_kpi);
            }

            lastStoredValueInHistory
                    = lastStoredValueInHistory * (1.0 + (10.0 - random.nextInt(20)) / 10.0);

            lastStoredValueInHistory = Math.max(_kpi.getValueMin(), lastStoredValueInHistory);
            lastStoredValueInHistory = Math.min(_kpi.getValueMax(), lastStoredValueInHistory);
            statisticsAPI.storeValueInHistory(HistoryKey.of(_kpi, _entity),
                    lastStoredValueInHistory, previousTime);
            previousTime = previousTime.plusDays(1);
        }
    }

    private double interval(final Kpi _kpi) {

        return _kpi.getValueMax() - _kpi.getValueMin() + 1;
    }
}
