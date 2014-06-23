/**
 *
 */

package org.komea.product.plugins.bugzilla.datasource;



import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import jodd.io.StringOutputStream;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class BZIssueWrapperTest
{
    
    
    @Test
    public void testSerialization() throws IOException {


        final StringOutputStream stringOutputStream = new StringOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(stringOutputStream);
        objectOutputStream.writeObject(new BZIssueWrapper());
        assertFalse(stringOutputStream.toString().isEmpty());
        assertTrue(new BZIssueWrapper() instanceof Serializable);

    }
}
