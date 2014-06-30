import org.komea.product.database.enums.EntityType
import org.komea.product.backend.service.kpi.StubQuery;
import org.komea.product.database.enums.GroupFormula
import org.komea.product.database.enums.ProviderType
import org.komea.product.database.enums.ValueDirection
import org.komea.product.database.enums.ValueType
import org.komea.product.database.model.Kpi
import org.komea.eventory.api.engine.IQuery


/** 
 * This method accepts a closure which is essentially the DSL. Delegate the
 * closure methods to
 * the DSL class so the calls can be processed
 */

// any method called in closure will be delegated to the memoDsl class

def kpibuilder = new ObjectGraphBuilder()
kpibuilder.classNameResolver = "org.komea.product.database.model"


define "kpi", kpibuilder.kpi (
        description: "Number of closed bugs per project",
        entityType: EntityType.PROJECT,
        groupFormula: GroupFormula.SUM_VALUE,
        kpiKey: "bugs_status_closed",
        name: "Closed bugs",
        providerType: ProviderType.BUGTRACKER,
        valueDirection: ValueDirection.BETTER,
        valueMax: 50000d,
        valueMin: 0d,
        valueType: ValueType.INT
        )
new StubQuery()


