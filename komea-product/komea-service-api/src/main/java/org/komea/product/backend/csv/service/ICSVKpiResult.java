/**
 *
 */

package org.komea.product.backend.csv.service;



import java.io.Writer;

import org.komea.product.database.dto.KpiResult;



/**
 * @author sleroy
 */
public interface ICSVKpiResult
{


    /**
     * Export a KpiResult as a csv without special configuration requested. The entity references are converted into entityKeys.
     *
     * @param _kpiResult
     *            the kpi Result
     */
    public void exportCSV(KpiResult _kpiResult, Writer _writer);

}
