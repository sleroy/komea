/**
 *
 */

package org.komea.product.backend.randomizer;


import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.quartz.JobDataMap;
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
    
    static final Logger LOGGER = LoggerFactory.getLogger(RandomizerDataJob.class);
    
    public static Double randomValue(final Kpi _kpi, final Double lastValue) {
    
        Double min = _kpi.getValueMin();
        Double max = _kpi.getValueMax();
        if (min == null) {
            min = 0d;
        }
        if (max == null || max > 1000000) {
            max = 100d;
        }
        final double range = max - min;
        Double value;
        if (lastValue == null) {
            value = Math.random() * range + min;
        } else {
            value = lastValue + (Math.random() * 0.3 - 0.15) * range;
        }
        value = Math.max(min, Math.min(max, value));
        if (ValueType.INT.equals(_kpi.getValueType())) {
            value = (double) Math.round(value);
        }
        return value;
    }
    
    @Autowired
    private ICronRegistryService cronRegistryService;
    
    @PostConstruct
    public void execute() {
    
        cronRegistryService.registerCronTask("RANDOMIZER_JOB", BackupDelay.HOUR, RandomizerCron.class, new JobDataMap());
        
    }
    
}
