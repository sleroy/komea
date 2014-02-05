/**
 * 
 */

package org.komea.product.backend.kpi;



import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;



/**
 * @author sleroy
 */
public class EntityWithKPITest
{
    
    
    private EntityWithKPI<Person> entityWithKPI;
    private Person                person;
    private ArrayList<Kpi>        kpiList;
    private Kpi                   kpi;
    
    
    
    @Before
    public void setupBefore() {
    
    
        person = new Person();
        person.setId(1);
        
        kpiList = new ArrayList<Kpi>();
        
        kpi = new Kpi();
        kpi.setId(1);
        kpi.setEntityType(EntityType.PERSON);
        kpi.setKpiKey("demo");
        
        kpiList.add(kpi);
        entityWithKPI = new EntityWithKPI<Person>(person, kpiList);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.kpi.EntityWithKPI#getEntity()}.
     */
    @Test
    public final void testGetEntity() {
    
    
        Assert.assertEquals(person, entityWithKPI.getEntity());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.kpi.EntityWithKPI#getId()}.
     */
    @Test
    public final void testGetId() {
    
    
        Assert.assertEquals(person.getId(), entityWithKPI.getEntity().getId());
        Assert.assertEquals(person.getId(), entityWithKPI.getEntity().getId());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.kpi.EntityWithKPI#getListofKpis()}.
     */
    @Test
    public final void testGetListofKpis() {
    
    
        Assert.assertEquals(kpi, entityWithKPI.getKpi("demo"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.kpi.EntityWithKPI#getKpi(java.lang.String)}.
     */
    @Test
    public final void testGetListofKpisString() {
    
    
        Assert.assertEquals(kpiList, entityWithKPI.getListofKpis());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.kpi.EntityWithKPI#getType()}.
     */
    @Test
    public final void testGetType() {
    
    
        Assert.assertEquals(EntityType.PERSON, entityWithKPI.getEntity().getType());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.kpi.EntityWithKPI#getUnderlyingObject()}.
     */
    @Test
    public final void testGetUnderlyingObject() {
    
    
        Assert.assertEquals(person, entityWithKPI.getEntity());
    }
    
    
}
