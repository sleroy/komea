/**
 * 
 */

package org.komea.product.backend.service;



import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
@DisallowConcurrentExecution
public class RandomizerDataJob implements Job
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
    
    
    
    private static final Logger             LOGGER = LoggerFactory
                                                           .getLogger(RandomizerDataJob.class);
    
    
    @Autowired
    private IEntityService                  entityService;
    
    @Autowired
    private IKpiAPI                         kpiAPI;
    
    @Autowired
    private IPluginStorageService           pluginStorage;
    
    private final Random                    random = new Random();
    
    
    private IObjectStorage<LastDateStorage> registerStorage;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        LOGGER.info("Generating random values...");
        registerStorage = pluginStorage.registerStorage("randomizer", LastDateStorage.class);
        
        generateKpiValues(EntityType.PERSON);
        generateKpiValues(EntityType.PROJECT);
        
        
    }
    
    
    public void generateKpiValues(final EntityType _entityType) {
    
    
        final List<Kpi> allKpisOfEntityType = kpiAPI.getAllKpisOfEntityType(_entityType);
        for (final IEntity entity : entityService.getEntitiesByEntityType(_entityType)) {
            for (final Kpi kpi : allKpisOfEntityType) {
                LOGGER.debug("Generating random value for {} and {}...", kpi, entity);
                generateKpiValues(kpi, entity);
            }
        }
        registerStorage.get().setDateTime(new DateTime());
    }
    
    
    /**
     * Generate Kpi value for a given
     * 
     * @param _kpi
     * @param _entity
     */
    private void generateKpiValues(final Kpi _kpi, final IEntity _entity) {
    
    
        DateTime previousTime = registerStorage.get().getDateTime();
        if (registerStorage.get().hasNoDateTime()) {
            previousTime = new DateTime(2012, 1, 1, 0, 0);
        }
        final DateTime dateTime = new DateTime();
        while (previousTime.isBefore(dateTime)) {
            Double lastStoredValueInHistory =
                    kpiAPI.getLastStoredValueInHistory(HistoryKey.of(_kpi, _entity));
            
            if (lastStoredValueInHistory == null || lastStoredValueInHistory == 0d) {
                lastStoredValueInHistory =
                        _kpi.getValueMin()
                                + random.nextInt((int) (_kpi.getValueMax() - _kpi.getValueMin()));
            }
            
            
            lastStoredValueInHistory =
                    lastStoredValueInHistory * (1.0 + (10.0 - random.nextInt(20)) / 10.0);
            
            
            kpiAPI.storeValueInHistory(KpiKey.ofKpiAndEntity(_kpi, _entity),
                    lastStoredValueInHistory, previousTime);
            previousTime = previousTime.plusDays(1);
        }
    }
}
