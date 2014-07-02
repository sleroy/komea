/**
 *
 */

package org.komea.product.backend.randomizer;



import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
public final class RandomizerCron implements Job
{


    @Autowired
    private IEntityService        entityService;

    @Autowired
    private IKPIService           kpiAPI;

    @Autowired
    private MeasureDao            measureService;

    @Autowired
    private IPluginStorageService pluginStorage;
    
    
    @Autowired
    private IStatisticsAPI        statisticsAPI;



    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        RandomizerDataJob.LOGGER.info("Generating random values...");
        final int personMeasures = generateKpiValues(EntityType.PERSON);
        if (personMeasures > 0) {
            RandomizerDataJob.LOGGER.info(personMeasures + " measures added for persons");
        }
        final int projectMeasures = generateKpiValues(EntityType.PROJECT);
        if (projectMeasures > 0) {
            RandomizerDataJob.LOGGER.info(projectMeasures + " measures added for projects");
        }
        RandomizerDataJob.LOGGER.info("Random values generated.");

    }


    public int generateKpiValues(final EntityType _entityType) {


        final DateTime twoYearsAgo = twoYearsAgo();
        final List<Kpi> allKpisOfEntityType = kpiAPI.getAllKpisOfEntityType(_entityType);
        int cpt = 0;
        for (final IEntity entity : entityService.getEntitiesByEntityType(_entityType)) {
            for (final Kpi kpi : allKpisOfEntityType) {
                // generateKpiValues(kpi, entity);
                final int addedMeasures = updateMeasures(kpi, entity, twoYearsAgo);
                if (addedMeasures > 0) {
                    RandomizerDataJob.LOGGER.info(addedMeasures
                            + " measures added for entity " + entity.getDisplayName() + " and kpi "
                            + kpi.getDisplayName());
                }
                cpt += addedMeasures;
            }
        }
        return cpt;
    }


    private Double addValue(
            final Kpi _kpi,
            final IEntity _entity,
            final DateTime date,
            final Double lastValue) {


        final Double value = RandomizerDataJob.randomValue(_kpi, lastValue);
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


    /**
     * Generate Kpi value for a given
     *
     * @param _kpi
     * @param _entity
     */
    @Transactional
    private void generateKpiValues(final Kpi _kpi, final IEntity _entity) {
    
    
        final Random random = new Random();
        if (measureService.countByCriteria(new MeasureCriteria()) > 0) {
            return;
        }
        DateTime previousTime = new DateTime(2012, 1, 1, 0, 0);

        final DateTime dateTime = new DateTime();
        final PeriodTimeSerieOptions options =
                PeriodTimeSerieOptions.buildOptionsFromTimeScale(_kpi, TimeScale.PER_DAY);
        final HistoryKey historyKey = HistoryKey.of(_kpi, _entity);
        while (previousTime.isBefore(dateTime)) {
            Double lastStoredValueInHistory =
                    statisticsAPI.evaluateKpiValueOnPeriod(options, historyKey.getEntityKey());
            if (lastStoredValueInHistory == null || lastStoredValueInHistory == 0d) {
                lastStoredValueInHistory =
                        _kpi.getValueMin() + random.nextDouble() * interval(_kpi);
            }

            lastStoredValueInHistory =
                    lastStoredValueInHistory * (1.0 + (10.0 - random.nextInt(20)) / 10.0);

            lastStoredValueInHistory = Math.max(_kpi.getValueMin(), lastStoredValueInHistory);
            lastStoredValueInHistory = Math.min(_kpi.getValueMax(), lastStoredValueInHistory);
            statisticsAPI.storeValueInHistory(historyKey, lastStoredValueInHistory, previousTime);
            previousTime = previousTime.plusDays(1);
        }
    }


    private Double getValue(final DateTime date, final List<Measure> measures) {


        final DateTime nextDate = date.plusDays(1);
        final long from = date.toDate().getTime();
        final long to = nextDate.toDate().getTime();
        final Iterator<Measure> iterator = measures.iterator();
        Measure measure = null;
        while (iterator.hasNext()) {
            final Measure next = iterator.next();
            final long time = next.getDate().getTime();
            if (from <= time && time < to) {
                measure = next;
                break;
            } else if (from > time) {
                iterator.remove();
            } else {
                break;
            }
        }
        return measure == null ? null : measure.getValue();
    }


    private double interval(final Kpi _kpi) {


        return _kpi.getValueMax() - _kpi.getValueMin() + 1;
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


    private int updateMeasures(final Kpi _kpi, final IEntity _entity, final DateTime _since) {


        final FormulaID formulaID = FormulaID.of(_kpi);
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        final MeasureCriteria.Criteria criteria = measureCriteria.createCriteria();
        criteria.andIdKpiEqualTo(formulaID.getId()).andEntityIDEqualTo(_entity.getId());
        final List<Measure> measures = measureService.selectByCriteria(measureCriteria);
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
}
