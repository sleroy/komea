
package org.kormea.product.rest.api.controllers;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.dto.EventDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/alerts")
public class AlertController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);
    
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    public void pushEvent(@RequestBody final EventDto _event) {
    
        LOGGER.debug("call rest method /alerts/push to push event {}", _event.getMessage());
        // TODO
    }
    
    /**
     * This method find alerts which have been stored into komea
     * 
     * @param _searchAlert
     *            looking for event of some entities and of dome type
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    public List<KpiAlertDto> findAlerts(@RequestBody final SearchKpiAlertsDto _searchAlert) {
    
        LOGGER.debug("call rest method /alerts/find to find alerts {}", _searchAlert.getEntityKeys());
        // TODO
        return new ArrayList<KpiAlertDto>();
    }
    
    /**
     * This method check if alert of type 'kpiAlertTypeKey' exist for the entity 'entityKey'
     * 
     * @param _kpiAlertTypeKey
     *            the alert type
     * @param entityKey
     *            the entity to check id an alert exist
     * @return true is an alert exist, false otherwise
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{kpiAlertTypeKey}/{entityKey}")
    public boolean getAlerts(@RequestParam(value = "kpiAlertTypeKey") final String _kpiAlertTypeKey,
            @RequestParam(value = "number") final String _entityKey) {
    
        LOGGER.debug("call rest method /alerts/get/{kpiAlertTypeKey}/{number} to check if an alert exist ");
        // TODO
        return false;
    }
}
