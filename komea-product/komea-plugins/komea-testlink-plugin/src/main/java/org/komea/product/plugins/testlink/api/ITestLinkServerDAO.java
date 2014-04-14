/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.api;



import java.util.List;

import org.komea.product.plugins.testlink.model.TestLinkServer;



/**
 * This interface defines the DAO to handle the testlink server configuration.
 * 
 * @author rgalerme
 */
public interface ITestLinkServerDAO
{
    
    
    /**
     * Removes a testlink server
     * 
     * @param server
     *            the server
     */
    public boolean delete(TestLinkServer server);
    
    
    /**
     * Find the server with the given name
     * 
     * @param _configuration
     *            the name of the server
     * @return the server configuration or null
     */
    public TestLinkServer find(String _configuration);
    
    
    /**
     * Saves or updates an server
     * 
     * @param _server
     */
    public void saveOrUpdate(TestLinkServer _server);
    
    
    /**
     * Saves the modjification on a testlink server.
     * 
     * @param server
     *            the server
     */
    public void saveOrUpdate(TestLinkServer server, String oldAddress);
    
    
    /**
     * Returns the list
     * 
     * @return
     */
    public List<TestLinkServer> selectAll();
    
    
}
