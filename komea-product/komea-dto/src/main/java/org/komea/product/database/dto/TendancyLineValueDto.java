/**
 * 
 */

package org.komea.product.database.dto;



import org.komea.product.database.api.IEntity;



/**
 * @author sleroy
 */
public class TendancyLineValueDto
{
    
    
    private IEntity entity;
    
    
    private Double  previousValue;
    
    
    private Double  realValue;
    
    
    
    public TendancyLineValueDto(final IEntity _entity) {
    
    
        super();
        entity = _entity;
    }
    
    
    public TendancyLineValueDto(
            final IEntity _entity,
            final Double _realValue,
            final Double _previousValue) {
    
    
        super();
        entity = _entity;
        previousValue = _previousValue;
        realValue = _realValue;
    }
    
    
    public IEntity getEntity() {
    
    
        return entity;
    }
    
    
    public Double getPastValue() {
    
    
        return previousValue;
    }
    
    
    public Double getRealValue() {
    
    
        return realValue;
    }
    
    
    public void setEntity(final IEntity _entity) {
    
    
        entity = _entity;
    }
    
    
    public void setPastValue(final Double _pastValue) {
    
    
        previousValue = _pastValue;
    }
    
    
    public void setRealValue(final Double _realValue) {
    
    
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
