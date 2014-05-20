package org.komea.product.backend.service.kpi;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.database.dto.KpiResult;

public class StubQuery implements IQuery {

    @Override
    public BackupDelay getBackupDelay() {

        return BackupDelay.MONTH;
    }

    @Override
    public Object getResult() {

        return new KpiResult();
    }

}
