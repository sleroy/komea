/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.komea.product.backend.api.IGroovyEngineService;
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



    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiImportationService#importCatalog(java.io.File)
     */
    @Override
    public Map<String, KpiDefinition> importCatalog(final File _inputFile) {


        final Map<String, KpiDefinition> kpiDefinitions = new HashMap<String, KpiDefinition>();
        try {
            final ZipFile zipFile = new ZipFile(_inputFile);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry nextElement = entries.nextElement();
                if (isGroovyScript(nextElement)) {
                    kpiDefinitions.put(nextElement.getName(), null);
                    addKpiToCatalog(kpiDefinitions, zipFile, nextElement);

                }
            }
        } catch (final Exception e) {
            LOGGER.error("Could not import the catalog {}", _inputFile, e);
        }
        return kpiDefinitions;
    }


    private void addKpiToCatalog(
            final Map<String, KpiDefinition> kpiDefinitions,
            final ZipFile zipFile,
            final ZipEntry nextElement) {
    
    
        try {
            final KpiDefinition kpiDefinition = parseGroovyScript(zipFile, nextElement);
            kpiDefinitions.put(nextElement.getName(), kpiDefinition);
            groovyEngineService.injectSpringIntoScript(kpiDefinition.getQuery());
        } catch (final Exception e) {
            LOGGER.error("Could not import the groovy script {}", nextElement.getName(), e);
        }
    }


    private boolean isGroovyScript(final ZipEntry nextElement) {


        return nextElement.getName().endsWith(".groovy") && !nextElement.isDirectory();
    }


    private KpiDefinition parseGroovyScript(final ZipFile zipFile, final ZipEntry nextElement)
            throws IOException {


        final Kpi temporaryKpi = new Kpi();
        temporaryKpi.setName(nextElement.getName());
        temporaryKpi.setEsperRequest(readContentOfFile(zipFile, nextElement));
        final KpiDefinition kpiDefinition =
                (KpiDefinition) groovyEngineService.parseScript(temporaryKpi).run();
        kpiDefinition.getKpi().setEsperRequest(temporaryKpi.getEsperRequest());
        LOGGER.debug("Extracting embedded kpi '{}'", kpiDefinition.getKpi().getName());
        return kpiDefinition;
    }


    private String readContentOfFile(final ZipFile zipFile, final ZipEntry nextElement)
            throws IOException {


        return IOUtils.toString(zipFile.getInputStream(nextElement));
    }
}
