package org.komea.product.database.dao;

import org.junit.Test;
import org.komea.product.database.model.Measure;
import org.mockito.Mockito;

public class AssociationTest {

    public AssociationTest() {
        super();
    }

    @Test 
    public void testAssociation() {
        final MeasureDao measureDao = Mockito.mock(MeasureDao.class);

        final Measure measure = new Measure();

    }

}
