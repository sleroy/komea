/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.utils.ThreadUtils;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;



/**
 * This service collects all the kpi groovy script entries and stores them into a catalog , ready to import.
 *
 * @author sleroy
 */
@Component
public class EmbeddedKpiCatalog implements IEmbeddedKpiCatalog
{


    private static final Logger  LOGGER = LoggerFactory.getLogger(EmbeddedKpiCatalog.class);


    @Autowired
    private IGroovyEngineService groovyEngineService;

    @Autowired
    private IKPIService          kpiService;
    
    
    @Autowired
    private ISpringService       springService;



    @PostConstruct
    public void init() throws IOException {


        final Runnable runnable = new Runnable()
        {


            @Override
            public void run() {


                try {
                    for (final Resource resource : springService.getApplicationContext()
                            .getResources("scripts/*.groovy")) {
                        try {
                            final Kpi kpi = new Kpi();
                            kpi.setName(resource.getFilename());
                            kpi.setEsperRequest(IOUtils.toString(resource.getInputStream()));
                            final KpiDefinition kpiDefinition =
                                    (KpiDefinition) groovyEngineService.parseScript(kpi).run();
                            LOGGER.info("Registering Embedded kpi '{}'", kpiDefinition.getKpi()
                                    .getName());
                            kpiService.saveOrUpdate(kpi);
                        } catch (final Exception e) {
                            LOGGER.error("Cannot load a script with reference '{}'", resource);
                        }
                    }
                } catch (final Exception e) {
                    LOGGER.error("Cannot initialize embedded kpi scripts");
                }


            }
        };
        ThreadUtils.execThreadAsync("loading-embedded-kpis", runnable);
    }
}
