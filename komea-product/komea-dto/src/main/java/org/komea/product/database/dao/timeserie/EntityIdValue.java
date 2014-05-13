/**
 * 
 */

package org.komea.product.database.dao.timeserie;



/**
 * @author sleroy
 */
public class EntityIdValue
{
    
    
    private Integer entityID;
    
    
    private Double  value;
    
    
    
    public EntityIdValue() {
    
    
        super();
    }
    
    
    /**
     * @param _entityID
     * @param _value
     */
    public EntityIdValue(final Integer _entityID, final Double _value) {
    
    
        super();
        entityID = _entityID;
        value = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityIdValue other = (EntityIdValue) obj;
        if (entityID == null) {
            if (other.entityID != null) {
                return false;
            }
        } else if (!entityID.equals(other.entityID)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Returns the value of the field entityID.
     * 
     * @return the entityID
     */
    public Integer getEntityID() {
    
    
        return entityID;
    }
    
    
    /**
     * Returns the value of the field value.
     * 
     * @return the value
     */
    public Double getValue() {
    
    
        return value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (entityID == null ? 0 : entityID.hashCode());
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }
    
    
    /**
     * Sets the field entityID with the value of _entityID.
     * 
     * @param _entityID
     *            the entityID to set
     */
    public void setEntityID(final Integer _entityID) {
    
    
        entityID = _entityID;
    }
    
    
    /**
     * Sets the field value with the value of _value.
     * 
     * @param _value
     *            the value to set
     */
    public void setValue(final Double _value) {
    
    
        value = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "EntityIdValue [entityID=" + entityID + ", value=" + value + "]";
    }
}
