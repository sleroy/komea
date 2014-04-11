
package org.komea.product.database.dao.it;



import org.junit.Test;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



public class PersonGroupDAOIT extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private PersonGroupDao groupDAO;
    
    
    
    /**
     * This tests performs the validation of enum.
     */
    @Test @Ignore
    @Transactional
    public void test() {
    
    
        final PersonGroupCriteria personGroupCriteria = new PersonGroupCriteria();
        personGroupCriteria.createCriteria().andTypeEqualTo(PersonGroupType.DEPARTMENT);
        groupDAO.selectByCriteria(personGroupCriteria);
        
    }
    
    
}
