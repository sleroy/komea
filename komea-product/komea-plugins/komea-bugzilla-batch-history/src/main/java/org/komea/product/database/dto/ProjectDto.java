/**
 *
 */

package org.komea.product.database.dto;



/**
 * @author sleroy
 */
public class ProjectDto
{


    private String name;
    
    
    
    public String getName() {


        return name;
    }
    
    
    public void setName(final String _name) {


        name = _name;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "ProjectDto [\\n\\tname=" + name + "]";
    }
}
