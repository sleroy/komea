/**
 * 
 */

package org.komea.product.backend.storage;



import org.komea.product.database.api.IHasId;



public class MockID implements IHasId
{
    
    
    private Integer      id;
    private final String str;
    
    
    
    public MockID(final String _value) {
    
    
        str = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasId#getId()
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    public String getStr() {
    
    
        return str;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasId#setId(int)
     */
    @Override
    public void setId(final Integer _value) {
    
    
        id = _value;
        
    }
}
