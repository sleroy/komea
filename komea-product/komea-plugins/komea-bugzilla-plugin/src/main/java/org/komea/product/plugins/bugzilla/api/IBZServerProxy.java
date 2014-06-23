/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.api;



import java.io.Closeable;
import java.util.List;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.Comment;
import com.j2bugzilla.rpc.GetLegalValues.Fields;



/**
 * @author rgalerme
 */
public interface IBZServerProxy extends Closeable
{
    
    
    /**
     * get list of bugs in bugzilla server
     *
     * @param _projectName
     * @return
     */
    public List<Bug> getBugs(String _projectName);
    
    
    /**
     * @param _bug
     * @return
     */
    public List<Comment> getComments(Bug _bug);
    
    
    /**
     * @return
     */
    public BugzillaConnector getConnector();
    
    
    public List<String> getPriorities();


    /**
     * @return
     */
    public List<String> getProductNames();
    
    
    public List<String> getSeverities();


    public boolean testConnexion();
    
    
    /**
     * @param _field
     * @return
     */
    List<String> GetLegalValues(Fields _field);
    
}
