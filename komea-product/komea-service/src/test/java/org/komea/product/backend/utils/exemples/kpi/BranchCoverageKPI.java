package org.komea.product.backend.utils.exemples.kpi;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.eventory.api.engine.IQueryVisitor;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;

public class BranchCoverageKPI implements IDynamicDataQuery {

    @Override
    public BackupDelay getBackupDelay() {

        return BackupDelay.DAY;
    }

    @Override
    public KpiResult getResult() {

        final KpiResult kpiResult = new KpiResult();
        kpiResult.put(EntityKey.of(EntityType.PROJECT, 4), 62);
        return kpiResult;
    }

    @Override
    public void accept(IQueryVisitor iqv) {
        iqv.visit(this);
    }
}
