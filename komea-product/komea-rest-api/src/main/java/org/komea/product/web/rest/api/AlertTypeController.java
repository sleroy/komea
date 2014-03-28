package org.komea.product.web.rest.api;

import java.util.List;
import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.database.dto.AlertTypeDto;
import org.komea.product.database.enums.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/alerttypes")
public class AlertTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertTypeController.class);

    @Autowired
    private IAlertTypeService alertTypeService;

    /**
     * This method return the alert type list
     *
     * @param entityType
     * @return the alert type list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/all")
    @ResponseBody
    public List<AlertTypeDto> allAlertTypes(@RequestBody EntityType entityType) {
        return alertTypeService.getAlertTypes(entityType);
    }

}
