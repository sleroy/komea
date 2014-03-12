/**
 * 
 */

package org.komea.product.cep.formula;



import org.apache.commons.lang.Validate;



/**
 * @author sleroy
 */
public class ELOperator
{
    
    
    /**
     * Test if a value is inside a collection.
     * 
     * @param _value
     *            the value
     * @param _objects
     *            the objects.
     * @return true if inside a collection.
     */
    public static boolean in(final Object _value, final Object... _objects) {
    
    
        Validate.notNull(_value);
        Validate.notNull(_objects);
        for (final Object refObject : _objects) {
            Validate.notNull(refObject);
            if (_value.equals(refObject)) { return true; }
        }
        return false;
        
    }
    
}
