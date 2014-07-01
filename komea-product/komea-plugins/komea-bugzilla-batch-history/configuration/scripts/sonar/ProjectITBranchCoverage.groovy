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



def sonarmetric = "it_branch_coverage"

define "kpi", kpibuilder.kpi (
        nameAndDescription: "IT Branch coverage",
        kpiKey: sonarmetric,
        entityType: EntityType.PROJECT,
        groupFormula: GroupFormula.AVG_VALUE,                
        providerType: ProviderType.QUALITY,
        valueDirection: ValueDirection.BETTER,
        valueMax: 100d,
        valueMin: 0d,
        valueType: ValueType.PERCENT
        )
query = new CEPQuery(new SonarMetricKpi(sonarmetric))
query



