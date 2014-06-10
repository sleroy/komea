/**
 *
 */

package org.komea.product.backend.service.standardkpi;



import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.api.exceptions.GroovyScriptException.GroovyValidationStatus;
import org.komea.product.backend.groovy.GroovyEngineService;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.kpi.standard.sonar.SonarMetricKpi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class SonarKpiServiceTest
{


    private final GroovyEngineService groovyEngineService       = new GroovyEngineService();

    private final SonarKPIService     standardKpiBuilderService = new SonarKPIService();

    final ICacheStorageFactory        iCacheStorageFactory      = new ICacheStorageFactory()
    {


        @Override
        public ICacheStorage newCacheStorage(
                final ICacheConfiguration _arg0) {


            return new GoogleCacheStorage(
                    _arg0);
        }

    };



    @Before
    public void before() {


        final ISpringService mock = mock(ISpringService.class);
        when(mock.getBean(ICacheStorageFactory.class)).thenReturn(iCacheStorageFactory);
        groovyEngineService.setSpringService(mock);
        groovyEngineService.init();

    }


    @After
    public void destroy() {


        groovyEngineService.destroy();

    }


    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#actualLineCoverage()} .
     */
     @Test
     public void testActualLineCoverage() throws Exception {


         testKpi(standardKpiBuilderService.actualLineCoverage());

     }


     /**
      * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#actualLineCoverage()} .
      */
      @Test
      public void testActualLineCoverageInvokcation() throws Exception {


          new CEPQuery(new SonarMetricKpi("ncloc"), iCacheStorageFactory);

      }


      @Test
      public void testBuildSonarMetricKpi() throws Exception {


          testKpi(standardKpiBuilderService.buildSonarMetricKpi("Lines of code", "ncloc", 0, 100,
                  ValueType.INT, ValueDirection.NONE));
      }


      private void testKpi(final Kpi kpiToTest) {


          final Set<ConstraintViolation<Kpi>> validate =
                  Validation.buildDefaultValidatorFactory().getValidator().validate(kpiToTest);
          Assert.assertEquals(GroovyValidationStatus.OK,
                groovyEngineService.isValidFormula(kpiToTest.getEsperRequest()));
          Assert.assertTrue(validate.isEmpty());
      }
}
