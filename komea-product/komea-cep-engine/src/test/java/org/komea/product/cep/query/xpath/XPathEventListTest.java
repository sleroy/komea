/**
 * 
 */

package org.komea.product.cep.query.xpath;



import java.util.Collections;

import org.junit.Test;

import com.google.common.base.Strings;

import static org.junit.Assert.assertFalse;



/**
 * @author sleroy
 */
public class XPathEventListTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.query.xpath.XPathEventList#toString()}.
     */
    @Test
    public void testToString() throws Exception {
    
    
        assertFalse(Strings.isNullOrEmpty(new XPathEventList("valu", Collections.EMPTY_LIST)
                .toString()));
    }
    
}
