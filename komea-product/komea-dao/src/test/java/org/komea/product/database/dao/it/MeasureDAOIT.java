
package org.komea.product.database.dao.it;



import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



public class MeasureDAOIT extends AbstractSpringIntegrationTestCase
{


    @Autowired
    private MeasureDao measureDAO;



    @Test
    @Transactional
    public void test() {


        final MeasureCriteria request = new MeasureCriteria();
        request.createCriteria().andIdKpiEqualTo("id123");

        Assert.assertTrue(measureDAO.selectByCriteria(request).isEmpty());

        final Measure measure = new Measure();

        measure.setValue(12d);
        measure.setDateTime(new DateTime());
        measure.setIdKpi("id123");
        measureDAO.insert(measure);

        Assert.assertEquals(1, measureDAO.selectByCriteria(request).size());
        Assert.assertEquals("id123", measureDAO.selectByCriteria(request).get(0).getIdKpi());

    }
}
