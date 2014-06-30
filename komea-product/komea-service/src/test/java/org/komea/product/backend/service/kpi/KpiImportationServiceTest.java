/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.File;

import org.junit.Test;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.kpi.IKpiImportationService.KpiImportator;
import org.komea.product.backend.service.queries.IQueryWithAnnotations;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
public class KpiImportationServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IKpiImportationService kpiImportationService;



    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KpiImportationService#importCatalog(java.io.File, org.komea.product.backend.service.kpi.IKpiImportationService.KpiImportator)}
     * .
     */
    @Test
    public final void testImportCatalog() throws Exception {


        kpiImportationService.importCatalog(new File(
                "src/test/resources/komea-kpis-1.1.24-SNAPSHOT.jar"),
                new IKpiImportationService.KpiImportator()
        {


            @Override
            public void iterate(
                    final String _file,
                            final IQueryWithAnnotations<IQuery> _kpiDefinition,
                            final Throwable _throwable) {


                System.out.println(_file);


            }
        });
    }
    
    
    @Test
    public final void testImportCatalogFromClassLoader() {
    
    
        kpiImportationService.importCatalogFromClassLoader(mock(KpiImportator.class));
    }
}
