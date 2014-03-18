package org.komea.product.backend.service.alert;

import java.util.Collections;
import java.util.List;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dao.KpiAlertTypeDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiAlertTypeCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public final class AlertTypeService extends AbstractService<KpiAlertType, Integer, KpiAlertTypeCriteria>
        implements IAlertTypeService {

    @Autowired
    private KpiAlertTypeDao requiredDAO;

    @Autowired
    private IKPIService kpiService;

    @Override
    public KpiAlertTypeDao getRequiredDAO() {

        return requiredDAO;
    }

    @Override
    protected KpiAlertTypeCriteria createPersonCriteriaOnLogin(String key) {
        final KpiAlertTypeCriteria criteria = new KpiAlertTypeCriteria();
        criteria.createCriteria().andKpiAlertKeyEqualTo(key);
        return criteria;
    }

    @Override
    public List<KpiAlertType> getAlertTypes(EntityType entityType) {
        final List<Kpi> kpis = kpiService.getKpis(entityType, null);
        if (kpis.isEmpty()) {
            return Collections.emptyList();
        }
        final KpiAlertTypeCriteria alertTypeCriteria = new KpiAlertTypeCriteria();
        for (final Kpi kpi : kpis) {
            alertTypeCriteria.or().andIdKpiEqualTo(kpi.getId());
        }
        return requiredDAO.selectByCriteria(alertTypeCriteria);
    }

}
