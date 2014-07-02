/**
 *
 */

package org.komea.product.database.dto;



/**
 * @author sleroy
 */
public class ProjectDto
{


    private Integer id;

    private String  name;
    
    
    
    public Integer getId() {
    
    
        return id;
    }
    
    
    public String getName() {


        return name;
    }
    
    
    public void setId(final Integer _id) {
    
    
        id = _id;
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
