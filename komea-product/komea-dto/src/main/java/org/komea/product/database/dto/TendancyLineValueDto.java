/**
 * 
 */

package org.komea.product.database.dto;



import org.komea.product.service.dto.EntityKey;



/**
 * @author sleroy
 */
public class TendancyLineValueDto
{
    
    
    private EntityKey entity;
    
    
    private Number    previousValue;
    
    
    private Number    realValue;
    
    
    
    public TendancyLineValueDto(final EntityKey _entity) {
    
    
        super();
        
        entity = _entity;
    }
    
    
    public TendancyLineValueDto(
            final EntityKey _entity,
            final Number _realValue,
            final Number _previousValue) {
    
    
        super();
        entity = _entity;
        previousValue = _previousValue;
        realValue = _realValue;
    }
    
    
    public EntityKey getEntity() {
    
    
        return entity;
    }
    
    
    public Number getPastValue() {
    
    
        return previousValue;
    }
    
    
    public Number getRealValue() {
    
    
        return realValue;
    }
    
    
    public void setEntity(final EntityKey _entity) {
    
    
        entity = _entity;
    }
    
    
    public void setPastValue(final Number _pastValue) {
    
    
        previousValue = _pastValue;
    }
    
    
    public void setRealValue(final Number _realValue) {
    
    
        realValue = _realValue;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "TendancyLineValueDto [entity="
                + entity + ", previousValue=" + previousValue + ", realValue=" + realValue + "]";
    }
    
}
