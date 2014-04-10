/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.model;



/**
 * This class defines a testlink project.
 * 
 * @author rgalerme
 */
public class TestLinkProject
{
    
    
    private Integer id;
    private String  name;
    
    
    
    public TestLinkProject() {
    
    
    }
    
    
    public TestLinkProject(final Integer id, final String name) {
    
    
        this.id = id;
        this.name = name;
    }
    
    
    public Integer getId() {
    
    
        return id;
    }
    
    
    public String getName() {
    
    
        return name;
    }
    
    
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    public void setName(final String name) {
    
    
        this.name = name;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "TestLinkProject [id=" + id + ", name=" + name + "]";
    }
    
}
