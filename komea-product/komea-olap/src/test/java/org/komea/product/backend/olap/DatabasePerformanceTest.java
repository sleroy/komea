
package org.komea.product.backend.olap;



import java.util.Random;

import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



public class DatabasePerformanceTest extends AbstractSpringIntegrationTestCase
{
    
    
    private static final Logger LOGGER    = LoggerFactory.getLogger(DatabasePerformanceTest.class);
    
    private final int           KPI_BUILD = 1;
    
    @Autowired
    private MeasureDao          measureDao;
    
    
    
    @Test
    @Transactional
    public void test() {
    
    
        generateTwoYearsOfBuildForJenkins(2);
        final int countNumberOfValues = measureDao.countByCriteria(new MeasureCriteria());
        LOGGER.info("Number of values produced {}", countNumberOfValues);
        
    }
    
    
    private Measure fakeMeasure(
            final DateTime _from,
            final int _idKpi,
            final int _idProject,
            final double _d) {
    
    
        final Measure measure = new Measure();
        measure.setDateTime(_from);
        measure.setValue(_d);
        measure.setIdProject(_idProject);
        measure.setIdKpi(_idKpi);
        return measure;
        
    }
    
    
    @Transactional
    private void generateTwoYearsOfBuildForJenkins(final int _numberOfProjects) {
    
    
        final Random random = new Random();
        DateTime from = new DateTime().minusYears(2);
        while (from.isBeforeNow()) {
            for (int idProject = 0; idProject < _numberOfProjects; ++idProject) {
                if (random.nextBoolean()) { // Generate a build
                    measureDao.insert(fakeMeasure(from, KPI_BUILD, idProject, 1.0));
                }
            }
            from = from.plusDays(1);
        }
        
    }
}
