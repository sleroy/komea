/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.File;
import java.util.Map;



/**
 * This interface defines the mechanism to import kpis from zip file.
 *
 * @author sleroy
 */
public interface IKpiImportationService
{


    /**
     * Import the catalog from the zip file
     *
     * @param _zipFile
     *            the zip file
     * @return the catalog.
     */
    public Map<String, KpiDefinition> importCatalog(File _zipFile);

}
