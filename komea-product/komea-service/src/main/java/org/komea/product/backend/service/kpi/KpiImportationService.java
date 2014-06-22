/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.service.queries.IQueryWithAnnotations;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class KpiImportationService implements IKpiImportationService
{
    
    
    private static final Logger  LOGGER = LoggerFactory.getLogger(KpiImportationService.class);
    
    @Autowired
    private IGroovyEngineService groovyEngineService;
    
    
    
    public void addKpiToCatalog(
            final ZipFile _zipFile,
            final ZipEntry _zipEntry,
            final KpiImportator _kpiImportator) {
    
    
        final String fileName = _zipEntry.getName();
        Exception eres = null;
        IQueryWithAnnotations kpiDefinition = null;
        try {
            kpiDefinition = parseGroovyScript(_zipFile, _zipEntry);
            
            
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
                final ZipEntry nextElement = entries.nextElement();
                if (isGroovyScript(nextElement)) {
                    addKpiToCatalog(zipFile, nextElement, _kpiImportator);
                    
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Could not import the catalog {}", _zipFile, e);
        }
        
    }
    
    
    private boolean isGroovyScript(final ZipEntry nextElement) {
    
    
        return nextElement.getName().endsWith(".groovy") && !nextElement.isDirectory();
    }
    
    
    private IQueryWithAnnotations parseGroovyScript(
            final ZipFile zipFile,
            final ZipEntry nextElement) throws Exception {
    
    
        final Kpi temporaryKpi = new Kpi();
        temporaryKpi.setName(nextElement.getName());
        temporaryKpi.setEsperRequest(readContentOfFile(zipFile, nextElement));
        final IQueryWithAnnotations queryWithANnotations =
                groovyEngineService.parseQueryAndAnnotations(temporaryKpi);
        final Kpi kpi = queryWithANnotations.getAnnotations().getAnnotationOrFail("kpi");
        kpi.setEsperRequest(temporaryKpi.getEsperRequest());
        LOGGER.debug("Extracting embedded kpi '{}'", kpi.getName());
        return queryWithANnotations;
    }
    
    
    private String readContentOfFile(final ZipFile zipFile, final ZipEntry nextElement)
            throws IOException {
    
    
        return IOUtils.toString(zipFile.getInputStream(nextElement));
    }
}
