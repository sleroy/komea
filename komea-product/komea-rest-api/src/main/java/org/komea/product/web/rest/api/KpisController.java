
package org.komea.product.web.rest.api;



import java.util.List;

import org.komea.product.backend.service.kpi.IKpiAPI;
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
@RequestMapping(
    value = "/kpis")
public class KpisController
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KpisController.class);
    
    @Autowired
    private IKpiAPI             kpiService;
    
    
    
    /**
     * This method return the kpi list
     * 
     * @return the kpi list
     */
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/all")
    @ResponseBody
    public List<Kpi> allKpis() {
    
    
        LOGGER.debug("call rest method /kpis/all/");
        return kpiService.selectAll();
    }
    
    
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/get",
        produces = "application/json")
    @ResponseBody
    public List<Kpi> getKpis(@RequestBody
    final ExtendedEntityType extendedEntityType) {
    
    
        final List<Kpi> kpis = kpiService.getAllKpisOfEntityType(extendedEntityType.getKpiType());
        if (extendedEntityType.isForGroups()) {
            final List<Kpi> kpisForGroups = kpiService.getKpisForGroups(kpis);
            return kpisForGroups;
        } else {
            return kpis;
        }
    }
    
}
