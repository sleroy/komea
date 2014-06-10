import org.komea.product.backend.service.kpi.KpiDSL
import org.komea.product.database.enums.EntityType
import org.komea.product.database.enums.GroupFormula
import org.komea.product.database.enums.ProviderType
import org.komea.product.database.enums.ValueDirection
import org.komea.product.database.enums.ValueType
import org.komea.product.database.model.Kpi
import org.komea.eventory.api.engine.IQuery;

/** 
 * This method accepts a closure which is essentially the DSL. Delegate the
 * closure methods to
 * the DSL class so the calls can be processed
 */

// any method called in closure will be delegated to the memoDsl class

def builder = new ObjectGraphBuilder()
builder.classNameResolver = "org.komea.product.database.model"

def kpi = builder.kpi (
        description: "",
        entityType: EntityType.PERSON,
        groupFormula: GroupFormula.SUM_VALUE,
        kpiKey: "",
        name: "",
        providerType: ProviderType.SCM,
        valueDirection: ValueDirection.NONE,
        valueMax: 100d,
        valueMin: 0d,
        valueType: ValueType.BOOL        
        )
// BEGIN FORMULA
def query = null;
// END FORMULA

