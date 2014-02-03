/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.EPStatementResult;
import org.komea.product.service.dto.AlertTypeStatistic;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.EPStatement;



/**
 * This service provides informations about the informations received by esper (number of alerts received per days, and alert type
 * breakdown);
 * 
 * @author sleroy
 */
@Service
public class AlertStatisticsService implements IAlertStatisticsService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertStatisticsService.class);
    
    
    @Autowired
    private IEsperQueryService  queryService;
    @Autowired
    private IEsperEngine        esperEngine;
    
    
    
    /**
     * 
     */
    public AlertStatisticsService() {
    
    
        super();
    }
    
    
    public IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public IEsperQueryService getQueryService() {
    
    
        return queryService;
    }
    
    
    public long getReceivedAlertsIn24Hours() {
    
    
        final EPStatement statsNumberStatement = esperEngine.getStatement("STATS_NUMBER_24H");
        final Long alert_number =
                EPStatementResult.build(statsNumberStatement).singleResult("alert_number");
        LOGGER.debug("Alert number at {} is {}", new PrettyTime(), alert_number);
        return alert_number;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IAlertStatisticsService#getReceivedAlertTypesIn24Hours()
     */
    @Override
    public List<AlertTypeStatistic> getReceivedAlertTypesIn24Hours() {
    
    
        final EPStatement statsBreakdownStatement = esperEngine.getStatement("STATS_BREAKDOWN_24H");
        return EPStatementResult.build(statsBreakdownStatement).listMapResult(
                AlertTypeStatistic.class);
    }
    
    
    @Autowired
    public void init() {
    
    
        queryService.registerQuery("SELECT COUNT(*) as alert_number FROM Alert.win:time(24 hour)",
                "STATS_NUMBER_24H");
        
        queryService.registerQuery(
                "SELECT DISTINCT provider, type, count(*) as number FROM Alert.win:time(24 hour)",
                "STATS_BREAKDOWN_24H");
        
        
    }
    
    
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setQueryService(final IEsperQueryService _queryService) {
    
    
        queryService = _queryService;
    }
}
