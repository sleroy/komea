/**
 *
 */

package org.komea.product.model.timeserie.table.dto;



import java.io.Serializable;
import java.util.Date;



/**
 * @author sleroy
 */
public class PointDTO implements Serializable
{
    
    
    private Date   date;
    private Double value;
    
    
    
    public PointDTO() {
    
    
    }
    
    
    /**
     * @param _date
     * @param _value
     */
    public PointDTO(final Date _date, final Double _value) {
    
    
        date = _date;
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
        final PointDTO other = (PointDTO) obj;
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
     * Returns the value of the field date.
     *
     * @return the date
     */
    public Date getDate() {
    
    
        return date;
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
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }
    
    
    /**
     * Sets the field date with the value of _date.
     *
     * @param _date
     *            the date to set
     */
    public void setDate(final Date _date) {
    
    
        date = _date;
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
    
    
        return "PointDTO [\\n\\tvalue="
                + value + ", \\n\\thashCode()=" + hashCode() + ", \\n\\tgetValue()=" + getValue()
                + ", \\n\\tgetClass()=" + getClass() + ", \\n\\ttoString()=" + super.toString()
                + "]";
    }
}
