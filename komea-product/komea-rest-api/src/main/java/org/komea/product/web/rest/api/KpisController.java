package org.komea.product.web.rest.api;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.komea.product.backend.service.IKpiGoalService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.KpiStringKey;
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

    @Autowired
    private IKpiGoalService goalService;

    @Autowired
    private IEntityService entityService;

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
    public List<Kpi> getKpis(@RequestBody final ExtendedEntityType extendedEntityType) {

        return kpiService.getAllKpisOfEntityType(extendedEntityType.getKpiType());
    }

    /**
     * Returns a list of kpi goals for a given entity and a kpi.
     *
     * @return the list of kpi goals.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/goals", produces = "application/json")
    @ResponseBody
    List<KpiGoal> findKpiGoals(@Valid @RequestBody final KpiStringKey _kpiKey) {

        final IEntity entity = entityService.findEntityByEntityStringKey(_kpiKey.getEntityKey());
        if (entity == null) {
            return Collections.emptyList();
        }
        final Kpi kpi = kpiService.selectByKey(_kpiKey.getKpiName());
        if (kpi == null) {
            return Collections.emptyList();
        }
        KpiKey kpiKey = KpiKey.ofKpiAndEntity(kpi, entity);
        return goalService.findKpiGoals(kpiKey);
    }
}
