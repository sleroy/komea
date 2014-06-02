/**
 * 
 */

package org.komea.product.service.dto;



import java.util.Date;



/**
 * This class defines a period in a project identified with a name, a begin date and an end date.
 * 
 * @author sleroy
 */
public class Sprint
{
    
    
    private Date   begin;
    private String description;
    private Date   end;
    private String name;
    
    
    
    /**
     * Returns the value of the field begin.
     * 
     * @return the begin
     */
    public Date getBegin() {
    
    
        return begin;
    }
    
    
    /**
     * Returns the value of the field description.
     * 
     * @return the description
     */
    public String getDescription() {
    
    
        return description;
    }
    
    
    /**
     * Returns the value of the field end.
     * 
     * @return the end
     */
    public Date getEnd() {
    
    
        return end;
    }
    
    
    /**
     * Returns the value of the field name.
     * 
     * @return the name
     */
    public String getName() {
    
    
        return name;
    }
    
    
    /**
     * Sets the field begin with the value of _begin.
     * 
     * @param _begin
     *            the begin to set
     */
    public void setBegin(final Date _begin) {
    
    
        begin = _begin;
    }
    
    
    /**
     * Sets the field description with the value of _description.
     * 
     * @param _description
     *            the description to set
     */
    public void setDescription(final String _description) {
    
    
        description = _description;
    }
    
    
    /**
     * Sets the field end with the value of _end.
     * 
     * @param _end
     *            the end to set
     */
    public void setEnd(final Date _end) {
    
    
        end = _end;
    }
    
    
    /**
     * Sets the field name with the value of _name.
     * 
     * @param _name
     *            the name to set
     */
    public void setName(final String _name) {
    
    
        name = _name;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "Sprint [\\n\\tbegin="
                + begin + ", \\n\\tdescription=" + description + ", \\n\\tend=" + end
                + ", \\n\\tname=" + name + "]";
    }
    
}
