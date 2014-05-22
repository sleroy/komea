/**
 * 
 */

package org.komea.product.backend.service;



import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
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
public class RandomizerDataJob
{
    
    
    public static class LastDateStorage
    {
        
        
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
    
    
    
    private static final Logger   LOGGER = LoggerFactory.getLogger(RandomizerDataJob.class);
    
    @Autowired
    private IEntityService        entityService;
    
    @Autowired
    private IKpiAPI               kpiAPI;
    
    @Autowired
    private MeasureDao            measureService;
    
    @Autowired
    private IPluginStorageService pluginStorage;
    
    private final Random          random = new Random();
    
    
    @Autowired
    private IStatisticsAPI        statisticsAPI;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @PostConstruct
    public void execute() {
    
    
        LOGGER.info("Generating random values...");
        
        generateKpiValues(EntityType.PERSON);
        generateKpiValues(EntityType.PROJECT);
        LOGGER.info("Random values generated...");
        
    }
    
    
    @Transactional
    public void generateKpiValues(final EntityType _entityType) {
    
    
        final List<Kpi> allKpisOfEntityType = kpiAPI.getAllKpisOfEntityType(_entityType);
        for (final IEntity entity : entityService.getEntitiesByEntityType(_entityType)) {
            for (final Kpi kpi : allKpisOfEntityType) {
                
                generateKpiValues(kpi, entity);
            }
        }
        
    }
    
    
    /**
     * Generate Kpi value for a given
     * 
     * @param _kpi
     * @param _entity
     */
    private void generateKpiValues(final Kpi _kpi, final IEntity _entity) {
    
    
        if (measureService.countByCriteria(new MeasureCriteria()) > 0) {
            return;
        }
        DateTime previousTime = new DateTime(2012, 1, 1, 0, 0);
        
        final DateTime dateTime = new DateTime();
        while (previousTime.isBefore(dateTime)) {
            Double lastStoredValueInHistory =
                    statisticsAPI.getLastStoredValueInHistory(HistoryKey.of(_kpi, _entity));
            
            if (lastStoredValueInHistory == null || lastStoredValueInHistory == 0d) {
                lastStoredValueInHistory =
                        _kpi.getValueMin() + random.nextDouble() * interval(_kpi);
            }
            
            lastStoredValueInHistory =
                    lastStoredValueInHistory * (1.0 + (10.0 - random.nextInt(20)) / 10.0);
            
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
