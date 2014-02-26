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
    
    
    private Double  pastValue;
    
    
    private Double  realValue;
    
    
    
    public TendancyLineValueDto(final IEntity _entity) {
    
    
        super();
        entity = _entity;
    }
    
    
    public TendancyLineValueDto(
            final IEntity _entity,
            final Double _pastValue,
            final Double _realValue) {
    
    
        super();
        entity = _entity;
        pastValue = _pastValue;
        realValue = _realValue;
    }
    
    
    public IEntity getEntity() {
    
    
        return entity;
    }
    
    
    public Double getPastValue() {
    
    
        return pastValue;
    }
    
    
    public Double getRealValue() {
    
    
        return realValue;
    }
    
    
    public void setEntity(final IEntity _entity) {
    
    
        entity = _entity;
    }
    
    
    public void setPastValue(final Double _pastValue) {
    
    
        pastValue = _pastValue;
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
                + entity + ", pastValue=" + pastValue + ", realValue=" + realValue + "]";
    }
    
}
