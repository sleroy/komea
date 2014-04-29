
package org.komea.eventory.cache.guava;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DjangoTest {
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void test() {
    
        Django django = new Django("Django");
       // django.toString();
        Assert.assertEquals("Django", django.getName());
    }
}
