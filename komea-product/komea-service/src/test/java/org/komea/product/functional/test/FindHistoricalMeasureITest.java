/**
 *
 */

package org.komea.product.functional.test;



import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.api.IMeasureStorageService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.backend.utils.ObjectValidation;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.EntityStringKey;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * This tests validates that we can collect real time values from a kpi.
 *
 * @author sleroy
 */
public class FindHistoricalMeasureITest extends AbstractSpringIntegrationTestCase
{


    public static class DemoDynamicQuery implements IDynamicDataQuery<KpiResult>
    {


        public static final double VALUE = 2.0;
        
        @Autowired
        private IProjectService    projectService;



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
            kpiResult.put(projectService.selectByKey(PROJECT_NAME).getEntityKey(), VALUE);
            return kpiResult;
        }
    }



    private static final String    PROJECT_NAME = "XYZXYZXYZ";
    
    @Autowired
    private IKPIService            kpiAPI;
    
    @Autowired
    private IQueryService          kpiQueryService;
    
    @Autowired
    private IKPIService            kpiService;
    
    @Autowired
    private MeasureDao             measureDao;
    
    @Autowired
    private IMeasureService        measureService;
    
    @Autowired
    private IMeasureStorageService measureStorageService;
    
    @Autowired
    private IProjectService        projectService;

    @Autowired
    private IStatisticsAPI         statisticsAPI;
    
    
    
    @Test
    public void testGetRealTimeValueFromKPI() {
    
    
        final Kpi build =
                KpiBuilder.create().nameAndKey("FindHistoricalMeasureITest")
                        .description("example of kpi").forProject()
                        .groupFormula(GroupFormula.AVG_VALUE).interval(0d, 100d)
                        .providerType(ProviderType.BUGTRACKER).dynamicQuery(DemoDynamicQuery.class)
                        .produceValue(ValueType.INT, ValueDirection.BETTER).build();
        
        // AND I REGISTER THIS KPI
        kpiService.saveOrUpdate(build);
        final Project orCreate = projectService.getOrCreate(PROJECT_NAME);
        
        // AND KPI SHOULD EXISTS
        assertTrue(kpiService.exists(build.getKpiKey()));
        // AND QUERY SHOULD HAVE BEEN REGISTERED
        assertTrue(kpiQueryService.isQueryOfKpiRegistered(build));
        // FORCE BACKUP
        measureStorageService.storeActualValueInHistory(build.getId(), BackupDelay.DAY);
        // WE SHOULD HAVE ONE VALUE FOR THIS KPI IN MEASURE
        final MeasureCriteria example = new MeasureCriteria();
        example.createCriteria().andIdKpiEqualTo(FormulaID.of(build).getId());
        assertEquals(1, measureDao.countByCriteria(example));
        final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
        timeSerieOptions.untilNow();
        timeSerieOptions.lastYears(2);
        timeSerieOptions.pickBestGranularity();
        timeSerieOptions.setKpi(build);
        new ObjectValidation().validateObject(timeSerieOptions);
        // WE SHOULD HAVE ONE TIME COORDINATE
        final TimeSerie timeSeries =
                statisticsAPI.buildPeriodTimeSeries(timeSerieOptions, orCreate.getEntityKey());
        assertNotNull(timeSerieOptions);
        assertEquals("Should have one coordinate", 1, timeSeries.getCoordinates().size());
        // MEASURE SERVICE : should produce analog results
        final TimeSerieDTO findHistoricalMeasure =
                measureService.findHistoricalMeasure(
                        KpiStringKey.ofKpiNameAndEntityKey(build.getKey(),
                                EntityStringKey.of(EntityType.PROJECT, PROJECT_NAME)),
                        timeSerieOptions);
        assertEquals(1, findHistoricalMeasure.getCoordinates().size());
        
    }
}
