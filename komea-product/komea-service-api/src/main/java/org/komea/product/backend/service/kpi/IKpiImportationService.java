/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.File;

import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.queries.IQueryWithAnnotations;



/**
 * This interface defines the mechanism to import kpis from zip file.
 *
 * @author sleroy
 */
public interface IKpiImportationService
{


    public interface KpiImportator
    {


        void iterate(
                String _file,
                IQueryWithAnnotations<IQuery> _kpiDefinition,
                Throwable _throwable);
    }



    /**
     * Import the catalog from the zip file
     *
     * @param _zipFile
     *            the zip file
     * @return the catalog.
     */
    public void importCatalog(File _zipFile, KpiImportator _kpiImportator);

}
