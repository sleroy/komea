/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.model;



import java.io.Serializable;



/**
 * Describes the configuration necessary for a testlink server
 * 
 * @author rgalerme
 */
public class TestLinkServer implements Serializable
{
    
    
    private String address = "";
    private String key     = "";
    
    
    private String name;
    
    
    
    public TestLinkServer() {
    
    
    }
    
    
    public TestLinkServer(final String address, final String key) {
    
    
        this.address = address;
        this.key = key;
    }
    
    
    public TestLinkServer(final String _address, final String _key, final String _name) {
    
    
        super();
        address = _address;
        key = _key;
        name = _name;
    }
    
    
    public String getAddress() {
    
    
        return address;
    }
    
    
    public String getKey() {
    
    
        return key;
    }
    
    
    public String getName() {
    
    
        return name;
    }
    
    
    public void setAddress(final String address) {
    
    
        this.address = address;
    }
    
    
    public void setKey(final String key) {
    
    
        this.key = key;
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
    
    
        return "TestLinkServer [address=" + address + ", key=" + key + "]";
    }
}
