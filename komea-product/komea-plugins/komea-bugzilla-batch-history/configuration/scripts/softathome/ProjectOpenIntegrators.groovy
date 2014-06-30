import org.komea.eventory.api.cache.BackupDelay

import org.komea.product.database.enums.EntityType
import org.komea.product.database.enums.GroupFormula
import org.komea.product.database.enums.ProviderType
import org.komea.product.database.enums.ValueDirection
import org.komea.product.database.enums.ValueType
import org.komea.product.plugins.bugtracker.kpis.*
import org.komea.product.plugins.bugtracking.model.*

def kpibuilder = new ObjectGraphBuilder()
kpibuilder.classNameResolver = "org.komea.product.database.model"


define "kpi", kpibuilder.kpi (
        description: "Number of opened bugs handled by integrators",
        entityType: EntityType.PROJECT,
        groupFormula: GroupFormula.AVG_VALUE,
        kpiKey: "bugs_opened_integrators",
        name: "Open bugs handled by integrators",
        providerType: ProviderType.BUGTRACKER,
        valueDirection: ValueDirection.WORST,
        valueMax: 50000d,
        valueMin: 0d,
        valueType: ValueType.INT
        )
def enterStatus = ["CONFIG_UPDATED", "COMPONENT_RELEASED"];

query = new IssueFilterKPI(BackupDelay.DAY)
query.setClosure({ enterStatus.contains(it.customFields.getField("status")) })
query.setGroupFunction({ it.getProduct() })
query




