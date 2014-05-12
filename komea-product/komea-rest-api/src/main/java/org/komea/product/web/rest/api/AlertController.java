package org.komea.product.web.rest.api;

import java.util.List;
import javax.validation.Valid;
import org.komea.product.backend.service.alert.IAlertFinderService;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/alerts")
public class AlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    private IAlertFinderService alertService;

    /**
     * This method find alerts which have been stored into komea
     *
     * @param _searchAlert looking for event of some entities and of dome type
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public List<KpiAlertDto> findAlerts(@Valid @RequestBody final SearchKpiAlertsDto _searchAlert) {
        List<KpiAlertDto> findAlerts = alertService.findAlerts(_searchAlert);
        return findAlerts;
    }

}
