/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.service.queries.IQueryWithAnnotations;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;



@Service
public class KpiImportationService implements IKpiImportationService
{


    /**
     * @author sleroy
     */
    public final class SaveDefaultImportation implements KpiImportator
    {
        
        
        @Override
        public void iterate(
                final String _file,
                final IQueryWithAnnotations<IQuery> _kpiDefinition,
                final Throwable _throwable) {


            if (_kpiDefinition != null) {
                final Kpi kpi = _kpiDefinition.getAnnotations().getAnnotation("kpi");

                try {

                    if (kpiService.exists(kpi.getKey())) {
                        return;
                    }
                    LOGGER.info("Saving the KPI into komea " + _kpiDefinition);
                    kpiService.saveOrUpdate(kpi);
                } catch (final Exception e) {
                    LOGGER.error("Exception during importation {}: ", e);
                }
            } else {
                LOGGER.error("Could not import the file " + _file + "  for the reason {} ",
                        _throwable.getMessage(), _throwable);
            }


        }
    }



    private static final Logger  LOGGER = LoggerFactory.getLogger(KpiImportationService.class);

    @Autowired
    private IGroovyEngineService groovyEngineService;


    @Autowired
    private IKPIService          kpiService;

    @Autowired
    private ISpringService       springService;



    public void addKpiToCatalog(
            final String _fileName,
            final InputStream _inputStream,
            final KpiImportator _kpiImportator) {


        final String fileName = _fileName;
        Exception eres = null;
        IQueryWithAnnotations kpiDefinition = null;
        try {
            kpiDefinition = parseGroovyScript(_fileName, _inputStream);


        } catch (final Exception e) {
            LOGGER.error("Could not import the groovy script {}", fileName, e);
            eres = e;
        } finally {
            _kpiImportator.iterate(fileName, kpiDefinition, eres);
        }
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiImportationService#importCatalog(java.io.File,
     * org.komea.product.backend.service.kpi.IKpiImportationService.KpiImportator)
     */
    @Override
    public void importCatalog(final File _zipFile, final KpiImportator _kpiImportator) {


        try {
            final ZipFile zipFile = new ZipFile(_zipFile);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry zipEntry = entries.nextElement();
                if (isGroovyScript(zipEntry)) {
                    addKpiToCatalog(zipEntry.getName(), zipFile.getInputStream(zipEntry),
                            _kpiImportator);

                }
            }
        } catch (final Exception e) {
            LOGGER.error("Could not import the catalog {}", _zipFile, e);
        }

    }


    @Override
    public void importCatalogFromClassLoader(final KpiImportator _kpiImportator) {


        LOGGER.info("Importing KPI from Classloader");

        try {
            for (final Resource resource : springService.getApplicationContext().getResources(
                    "scripts/**/*.groovy")) {
                LOGGER.info("Found script {}", resource);
                addKpiToCatalog(resource.getFilename(), resource.getInputStream(), _kpiImportator);
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("Importation finished");
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiImportationService#importFolder(java.io.File,
     * org.komea.product.backend.service.kpi.IKpiImportationService.KpiImportator)
     */
    @Override
    public void importFolder(final File _file) {


        final Iterator<File> iterateFiles = FileUtils.iterateFiles(_file, new String[] {
        "groovy" }, true);
        while (iterateFiles.hasNext()) {
            final File next = iterateFiles.next();
            try {
                addKpiToCatalog(next.getName(), FileUtils.openInputStream(next),
                        new SaveDefaultImportation());
            } catch (final IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }


    @PostConstruct
    public void initCatalog() {


        importCatalogFromClassLoader(new SaveDefaultImportation());
    }


    private boolean isGroovyScript(final ZipEntry nextElement) {


        return nextElement.getName().endsWith(".groovy") && !nextElement.isDirectory();
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiImportationService#importCatalogFromClassLoader(java.lang.ClassLoader,
     * org.komea.product.backend.service.kpi.IKpiImportationService.KpiImportator)
     */
    private IQueryWithAnnotations parseGroovyScript(
            final String _fileName,
            final InputStream _inputStream) throws Exception {


        final Kpi temporaryKpi = new Kpi();
        temporaryKpi.setName(_fileName);
        temporaryKpi.setEsperRequest(readContentOfFile(_inputStream));
        final IQueryWithAnnotations queryWithANnotations =
                groovyEngineService.parseQueryAndAnnotations(temporaryKpi);
        final Kpi kpi = queryWithANnotations.getAnnotations().getAnnotationOrFail("kpi");
        kpi.setEsperRequest(temporaryKpi.getEsperRequest());
        LOGGER.debug("Extracting embedded kpi '{}'", kpi.getName());
        return queryWithANnotations;
    }


    private String readContentOfFile(final InputStream _inputStream) throws IOException {


        return IOUtils.toString(_inputStream);
    }
}
