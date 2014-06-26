/**
 *
 */

package org.komea.product.wicket.kpiimport;



import java.io.File;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiImportationService;
import org.komea.product.backend.service.kpi.IKpiImportationService.KpiImportator;
import org.komea.product.backend.service.queries.IQueryWithAnnotations;
import org.komea.product.backend.utils.StackTracePrint;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.kpiimport.KpiEntry.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
final class ImportCatalogTask implements Runnable
{


    /**
     * @author sleroy
     */
    private final class ImportationAction implements KpiImportator
    {


        private final List<KpiEntry> kpiEntries;



        public ImportationAction(final List<KpiEntry> _kpiEntries) {


            kpiEntries = _kpiEntries;
            Validate.notNull(kpiEntries);

        }


        @Override
        public void iterate(
                final String _file,
                final IQueryWithAnnotations<IQuery> _kpiDefinition,
                final Throwable _throwable) {


            LOGGER.info("Processing kpi from file " + _file);
            Status status = Status.NO_IMPORT;
            final KpiEntry kpiEntry = new KpiEntry();
            if (_kpiDefinition != null) {
                final Kpi kpi = _kpiDefinition.getAnnotations().getAnnotation("kpi");
                kpiEntry.setKpiName(kpi.getKey());
                try {

                    if (kpiService.exists(kpi.getKey())) {
                        status = Status.UPDATED;
                    } else {
                        status = Status.IMPORTED;
                    }
                    LOGGER.info("Saving the KPI into komea " + _kpiDefinition);
                    kpiService.saveOrUpdate(kpi);
                } catch (final Exception e) {
                    LOGGER.error("Exception : " + StackTracePrint.printTrace(e));
                    status = Status.ERROR;
                }
            } else {
                LOGGER.error("Could not import the file "
                        + _file + "  for the reason " + StackTracePrint.printTrace(_throwable));
                System.err.println("Could not import the file "
                        + _file + "  for the reason " + StackTracePrint.printTrace(_throwable));
            }

            kpiEntry.setEntry(_file);
            kpiEntry.setStatus(status);
            kpiEntries.add(kpiEntry);

        }
    }



    private static final Logger          LOGGER = LoggerFactory.getLogger(ImportCatalogTask.class);
    private final File                   file;
    private final List<KpiEntry>         kpiEntries;

    private final IKpiImportationService kpiImportationService;


    private final IKPIService            kpiService;



    public ImportCatalogTask(
            final File _file,
            final List<KpiEntry> _kpiEntries,
            final IKpiImportationService _kpiImportationService,
            final IKPIService _kpiService) {


        file = _file;
        kpiEntries = _kpiEntries;
        kpiImportationService = _kpiImportationService;
        kpiService = _kpiService;

    }


    @Override
    public void run() {


        kpiImportationService.importCatalog(file, new ImportationAction(kpiEntries));


    }
}
