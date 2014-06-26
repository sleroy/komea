/**
 *
 */

package org.komea.product.plugins.bugzilla.core;



/**
 * @author sleroy
 */
public class BugChange
{
    
    
    private String added;
    private String field_name;
    private String removed;
    
    
    
    /**
     * Returns the value of the field added.
     * 
     * @return the added
     */
    public String getAdded() {


        return added;
    }
    
    
    /**
     * Returns the value of the field field_name.
     * 
     * @return the field_name
     */
    public String getField_name() {


        return field_name;
    }
    
    
    /**
     * Returns the value of the field removed.
     * 
     * @return the removed
     */
    public String getRemoved() {


        return removed;
    }
    
    
    /**
     * Sets the field added with the value of _added.
     * 
     * @param _added
     *            the added to set
     */
    public void setAdded(final String _added) {


        added = _added;
    }
    
    
    /**
     * Sets the field field_name with the value of _field_name.
     * 
     * @param _field_name
     *            the field_name to set
     */
    public void setField_name(final String _field_name) {


        field_name = _field_name;
    }
    
    
    /**
     * Sets the field removed with the value of _removed.
     * 
     * @param _removed
     *            the removed to set
     */
    public void setRemoved(final String _removed) {


        removed = _removed;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "BugChange [\\n\\tadded="
                + added + ", \\n\\tfield_name=" + field_name + ", \\n\\tremoved=" + removed + "]";
    }
}
