import org.komea.eventory.api.cache.BackupDelay
import org.komea.product.backend.service.kpi.KpiDefinition
import org.komea.product.database.enums.EntityType
import org.komea.product.database.enums.GroupFormula
import org.komea.product.database.enums.ProviderType
import org.komea.product.database.enums.ValueDirection
import org.komea.product.database.enums.ValueType
import org.komea.product.plugins.bugtracker.kpis.*
import org.komea.product.plugins.bugtracking.model.*

def kpibuilder = new ObjectGraphBuilder()
kpibuilder.classNameResolver = "org.komea.product.database.model"

def kpiDsl = new KpiDefinition()
kpiDsl.kpi = kpibuilder.kpi (
        description: "Total number of bugs per project",
        entityType: EntityType.PROJECT,
        groupFormula: GroupFormula.AVG_VALUE,
        kpiKey: "bugs_total",
        name: "Total bugs",
        providerType: ProviderType.BUGTRACKER,
        valueDirection: ValueDirection.WORST,
        valueMax: 50000d,
        valueMin: 0d,
        valueType: ValueType.INT
        )
kpiDsl.query = new IssueFilterKPI(BackupDelay.DAY)
kpi.setClosure({ it.getStatus() == IssueStatus.OPENED && it.severity == Severity.BLOCKER})
kpiDsl.query.setGroupFunction({ it.getProduct() })
kpiDsl




