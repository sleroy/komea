
package org.komea.product.database.dao.it;


import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class KpiDAOIT extends AbstractSpringIntegrationTestCase {
    
    @Autowired
    private KpiDao kpiDAO;
    
    @Test
    @Transactional
    public void test() {
    
        final KpiCriteria request = new KpiCriteria();
        request.createCriteria().andEntityTypeEqualTo(EntityType.PROJECT);
        
        Assert.assertTrue(kpiDAO.selectByCriteria(request).isEmpty());
        
        final Kpi record = new Kpi();
        record.setDescription("");
        record.setEsperRequest("salut");
        record.setKpiKey("");
        record.setName("");
        record.setValueDirection(ValueDirection.BETTER);
        record.setValueType(ValueType.BOOL);
        record.setEntityType(EntityType.PROJECT);
        record.setEntityID(0);
        record.setMaxValue(0d);
        record.setMinValue(0d);
        
        kpiDAO.insert(record);
        
        Assert.assertEquals(1, kpiDAO.selectByCriteria(request).size());
        Assert.assertEquals("salut", kpiDAO.selectByExampleWithBLOBs(request).get(0).getEsperRequest());
        
    }
    
}
