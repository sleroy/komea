import org.komea.eventory.query.*
import org.komea.product.plugins.kpi.standard.sonar.*
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



def sonarmetric = "file_complexity"

define "kpi", kpibuilder.kpi (
        entityType: EntityType.PROJECT,
        groupFormula: GroupFormula.AVG_VALUE,
        kpiKey: sonarmetric,
        nameAndDescription: "Complexity /file",
        providerType: ProviderType.QUALITY,
        valueDirection: ValueDirection.WORST,
        valueMax: 100,
        valueMin: 0d,
        valueType: ValueType.FLOAT
        )
query = new CEPQuery(new SonarMetricKpi(sonarmetric))
query



