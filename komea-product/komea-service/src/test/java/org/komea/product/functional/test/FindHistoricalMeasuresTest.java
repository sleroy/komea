/**
 * 
 */

package org.komea.product.functional.test;



import java.util.List;

import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.PeriodCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * This tests validates that we can collect real time values from a kpi.
 * 
 * @author sleroy
 */
public class FindHistoricalMeasuresTest extends AbstractSpringIntegrationTestCase
{
    
    
    public static class DemoDynamicQuery implements IDynamicDataQuery<KpiResult>
    {
        
        
        /**
         * 
         */
        public static final double VALUE = 2.0;
        
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
         */
        @Override
        public BackupDelay getBackupDelay() {
        
        
            return BackupDelay.DAY;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.engine.IQuery#getResult()
         */
        @Override
        public KpiResult getResult() {
        
        
            final KpiResult kpiResult = new KpiResult();
            kpiResult.put(entityKey, VALUE);
            return kpiResult;
        }
    }
    
    
    
    private static final EntityKey entityKey = EntityKey.of(EntityType.PROJECT, 1);
    
    
    @Autowired
    private IKpiAPI                kpiAPI;
    
    @Autowired
    private IQueryService          kpiQueryService;
    
    @Autowired
    private IKPIService            kpiService;
    
    @Autowired
    private MeasureDao             measureDao;
    
    @Autowired
    private IMeasureService        measureService;
    
    @Autowired
    private IProjectService        projectService;
    
    @Autowired
    private IStatisticsAPI         statisticsAPI;
    
    
    
    @Test
    public void testGetRealTimeValueFromKPI() {
    
    
        final Kpi build =
                KpiBuilder.create().nameAndKey("kpigetlaststored").dailyKPI()
                        .description("example of kpi").forProject()
                        .groupFormula(GroupFormula.AVG_VALUE).interval(0d, 100d)
                        .providerType(ProviderType.BUGTRACKER).dynamicQuery(DemoDynamicQuery.class)
                        .produceValue(ValueType.INT, ValueDirection.BETTER).build();
        
        // AND I REGISTER THIS KPI
        kpiService.saveOrUpdate(build);
        
        assertEquals("We work on primary key = 1 in entityKey", Integer.valueOf(1), projectService
                .getOrCreate("SCERTIFY").getId());
        
        
        // AND KPI SHOULD EXISTS
        assertTrue(kpiService.exists(build.getKpiKey()));
        // AND QUERY SHOULD HAVE BEEN REGISTERED
        assertTrue(kpiQueryService.isQueryOfKpiRegistered(build));
        // FORCE BACKUP
        statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.DAY);
        // WE SHOULD HAVE ONE VALUE FOR THIS KPI IN MEASURE
        final MeasureCriteria example = new MeasureCriteria();
        example.createCriteria().andIdKpiEqualTo(FormulaID.of(build).getId());
        assertEquals(1, measureDao.countByCriteria(example));
        final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
        timeSerieOptions.untilNow();
        timeSerieOptions.lastYears(2);
        timeSerieOptions.pickBestGranularity();
        timeSerieOptions.setKpi(build);
        assertTrue(timeSerieOptions.isValid());
        // MEASURE SERVICE : should produce a list of time serie with one item and the item is one data.
        final KpiStringKeyList ks = new KpiStringKeyList();
        ks.addKpiKey(build.getKey());
        ks.addEntityKey("SCERTIFY");
        final PeriodCriteria period = new PeriodCriteria();
        period.setStartDate(timeSerieOptions.getFromPeriod());
        
        period.setEndDate(timeSerieOptions.getToPeriod());
        final List<TimeSerieDTO> findMupltipleHistoricalMeasure =
                measureService.findMupltipleHistoricalMeasure(ks, period);
        assertEquals("At least one timeserie", 1, findMupltipleHistoricalMeasure.size());
        assertEquals("At least one data in the first time serie", 1, findMupltipleHistoricalMeasure
                .get(0).getCoordinates().size());
        
        
    }
}
