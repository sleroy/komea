/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.komea.eventory.api.engine.IQuery;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;



/**
 * This interface defines a kpi and its query.
 *
 * @author sleroy
 */
public class KpiDefinition
{


    Kpi               kpi;


    IQuery<KpiResult> query;
}
