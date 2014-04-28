package org.komea.product.web.rest.api;

import java.util.Collections;
import java.util.List;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/kpis")
public class KpisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KpisController.class);

    @Autowired
    private IKPIService kpiService;

    /**
     * This method return the kpi list
     *
     * @return the kpi list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<Kpi> allKpis() {

        LOGGER.debug("call rest method /kpis/all/");
        return kpiService.selectAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get", produces = "application/json")
    @ResponseBody
    public List<Kpi> getKpis(@RequestBody ExtendedEntityType extendedEntityType) {

        LOGGER.debug("call rest method /kpis/get/");
        final List<Kpi> kpis = kpiService.getKpis(extendedEntityType.getKpiType(), Collections.<String>emptyList());
        if (extendedEntityType.isForGroups()) {
            return kpiService.getKpisForGroups(kpis);
        } else {
            return kpis;
        }
    }

}
