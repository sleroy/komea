
package org.komea.product.web.rest.api;


import java.util.List;

import javax.validation.Valid;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/alerts")
public class AlertController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertController.class);
    
    @Autowired
    private IEventPushService   pushService;
    
    // private IAlertService alertService;
    
    /**
     * This method find alerts which have been stored into komea
     * 
     * @param _searchAlert
     *            looking for event of some entities and of dome type
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public List<KpiAlertDto> findAlerts(@Valid @RequestBody final SearchKpiAlertsDto _searchAlert) {
    
        LOGGER.debug("call rest method /alerts/find to find alerts {}", _searchAlert.getEntityKeys());
        // TODO STUB
        
        final List<KpiAlertDto> alerts = Lists.newArrayList();
        final KpiAlertDto kpiAlert = new KpiAlertDto();
        kpiAlert.setEntityName("anEntity");
        kpiAlert.setEnabled(true);
        kpiAlert.setValue(12D);
        final Kpi kpi = new Kpi();
        kpi.setKpiKey("kpiKey");
        kpi.setDescription("aDescription");
        kpi.setEntityType(EntityType.PERSON);
        kpi.setName("aName");
        kpiAlert.setKpi(kpi);
        alerts.add(kpiAlert);
        return alerts;
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
    @ResponseBody
    public boolean getAlerts(@RequestParam(value = "kpiAlertTypeKey") final String _kpiAlertTypeKey,
            @RequestParam(value = "entityKey") final String _entityKey) {
    
        LOGGER.debug("call rest method /alerts/get/{kpiAlertTypeKey}/{entityKey} to check if an alert exist ");
        // TODO
        return false;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushEvent(@Valid @RequestBody final EventSimpleDto _event) {
    
        LOGGER.debug("call rest method /alerts/push to push event {}", _event.getMessage());
        pushService.sendEventDto(_event);
    }
    
}
