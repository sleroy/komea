
package org.komea.product.database.model;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.RetentionPeriod;



/**
 * The class <code>ActivityFilterTest</code> contains tests for the class <code>{@link ActivityFilter}</code>.
 * 
 * @generatedBy CodePro at 19/02/14 16:07
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class ActivityFilterTest
{
    
    
    /**
     * Run the ActivityFilter(Integer,EntityType,Integer,RetentionPeriod,RetentionPeriod,RetentionPeriod,RetentionPeriod,RetentionPeriod)
     * constructor test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testActivityFilter_2() throws Exception {
    
    
        final Integer id = new Integer(1);
        final EntityType entityType = EntityType.DEPARTMENT;
        final Integer idEntity = new Integer(1);
        final RetentionPeriod infoRetention = RetentionPeriod.NEVER;
        final RetentionPeriod minorRetention = RetentionPeriod.NEVER;
        final RetentionPeriod majorRetention = RetentionPeriod.NEVER;
        final RetentionPeriod criticalRetention = RetentionPeriod.NEVER;
        final RetentionPeriod blockerRetention = RetentionPeriod.NEVER;
        
        final ActivityFilter result =
                new ActivityFilter(id, entityType, idEntity, infoRetention, minorRetention,
                        majorRetention, criticalRetention, blockerRetention);
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("DEPARTMENT__1__ACTIVITY", result.generateActivityQueryName());
        assertEquals(new Integer(1), result.getIdEntity());
        
        assertEquals(new Integer(1), result.getId());
    }
    
    
    /**
     * Run the String generateActivityQueryName() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGenerateActivityQueryName_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), (EntityType) null, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final String result = fixture.generateActivityQueryName();
        
        // add additional test code here
        assertEquals("SYSTEM__1__ACTIVITY", result);
    }
    
    
    /**
     * Run the String generateActivityQueryName() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGenerateActivityQueryName_2() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, (Integer) null,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final String result = fixture.generateActivityQueryName();
        
        // add additional test code here
        assertEquals("DEPARTMENT__ACTIVITY", result);
    }
    
    
    /**
     * Run the RetentionPeriod getBlockerRetention() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetBlockerRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final RetentionPeriod result = fixture.getBlockerRetention();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("NEVER", result.name());
        assertEquals("NEVER", result.toString());
        assertEquals(0, result.ordinal());
    }
    
    
    /**
     * Run the RetentionPeriod getCriticalRetention() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetCriticalRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final RetentionPeriod result = fixture.getCriticalRetention();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("NEVER", result.name());
        assertEquals("NEVER", result.toString());
        assertEquals(0, result.ordinal());
    }
    
    
    /**
     * Run the EntityType getEntityType() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetEntityType_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final EntityType result = fixture.getEntityType();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("DEPARTMENT", result.name());
        assertEquals("DEPARTMENT", result.toString());
        assertEquals(3, result.ordinal());
    }
    
    
    /**
     * Run the Integer getId() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetId_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final Integer result = fixture.getId();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("1", result.toString());
        assertEquals((byte) 1, result.byteValue());
        assertEquals((short) 1, result.shortValue());
        assertEquals(1, result.intValue());
        assertEquals(1L, result.longValue());
        assertEquals(1.0f, result.floatValue(), 1.0f);
        assertEquals(1.0, result.doubleValue(), 1.0);
    }
    
    
    /**
     * Run the Integer getIdEntity() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetIdEntity_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final Integer result = fixture.getIdEntity();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("1", result.toString());
        assertEquals((byte) 1, result.byteValue());
        assertEquals((short) 1, result.shortValue());
        assertEquals(1, result.intValue());
        assertEquals(1L, result.longValue());
        assertEquals(1.0f, result.floatValue(), 1.0f);
        assertEquals(1.0, result.doubleValue(), 1.0);
    }
    
    
    /**
     * Run the RetentionPeriod getInfoRetention() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetInfoRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final RetentionPeriod result = fixture.getInfoRetention();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("NEVER", result.name());
        assertEquals("NEVER", result.toString());
        assertEquals(0, result.ordinal());
    }
    
    
    /**
     * Run the RetentionPeriod getMajorRetention() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetMajorRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final RetentionPeriod result = fixture.getMajorRetention();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("NEVER", result.name());
        assertEquals("NEVER", result.toString());
        assertEquals(0, result.ordinal());
    }
    
    
    /**
     * Run the RetentionPeriod getMinorRetention() method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testGetMinorRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        
        final RetentionPeriod result = fixture.getMinorRetention();
        
        // add additional test code here
        assertNotNull(result);
        assertEquals("NEVER", result.name());
        assertEquals("NEVER", result.toString());
        assertEquals(0, result.ordinal());
    }
    
    
    /**
     * Run the void setBlockerRetention(RetentionPeriod) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetBlockerRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final RetentionPeriod blockerRetention = RetentionPeriod.NEVER;
        
        fixture.setBlockerRetention(blockerRetention);
        
        // add additional test code here
    }
    
    
    /**
     * Run the void setCriticalRetention(RetentionPeriod) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetCriticalRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final RetentionPeriod criticalRetention = RetentionPeriod.NEVER;
        
        fixture.setCriticalRetention(criticalRetention);
        
        // add additional test code here
    }
    
    
    /**
     * Run the void setEntityType(EntityType) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetEntityType_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final EntityType entityType = EntityType.DEPARTMENT;
        
        fixture.setEntityType(entityType);
        
        // add additional test code here
    }
    
    
    /**
     * Run the void setId(Integer) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetId_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final Integer id = new Integer(1);
        
        fixture.setId(id);
        
        // add additional test code here
    }
    
    
    /**
     * Run the void setIdEntity(Integer) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetIdEntity_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final Integer idEntity = new Integer(1);
        
        fixture.setIdEntity(idEntity);
        
        // add additional test code here
    }
    
    
    /**
     * Run the void setInfoRetention(RetentionPeriod) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetInfoRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final RetentionPeriod infoRetention = RetentionPeriod.NEVER;
        
        fixture.setInfoRetention(infoRetention);
        
        // add additional test code here
    }
    
    
    /**
     * Run the void setMajorRetention(RetentionPeriod) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetMajorRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final RetentionPeriod majorRetention = RetentionPeriod.NEVER;
        
        fixture.setMajorRetention(majorRetention);
        
        // add additional test code here
    }
    
    
    /**
     * Run the void setMinorRetention(RetentionPeriod) method test.
     * 
     * @throws Exception
     * @generatedBy CodePro at 19/02/14 16:07
     */
    @Test @Ignore
    public void testSetMinorRetention_1() throws Exception {
    
    
        final ActivityFilter fixture =
                new ActivityFilter(new Integer(1), EntityType.DEPARTMENT, new Integer(1),
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER, RetentionPeriod.NEVER,
                        RetentionPeriod.NEVER, RetentionPeriod.NEVER);
        final RetentionPeriod minorRetention = RetentionPeriod.NEVER;
        
        fixture.setMinorRetention(minorRetention);
        
        // add additional test code here
    }
    
}
