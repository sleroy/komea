/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.api;



import java.io.Closeable;
import java.util.List;

import biz.futureware.mantis.rpc.soap.client.IssueData;



/**
 * @author rgalerme
 */
public interface IMantisServerProxy extends Closeable
{
    
    
    /**
     * get list of bugs in bugzilla server
     * 
     * @param Project
     * @return
     */
    public List<IssueData> getBugs(String Project);
    
    
    public List<String> getPriorities();
    
    
    /**
     * Returns the list of product names.
     */
    public List<String> getProductNames();
    
    
    public List<String> getSeverities();
    
    
    public boolean testConnexion();
    
}
