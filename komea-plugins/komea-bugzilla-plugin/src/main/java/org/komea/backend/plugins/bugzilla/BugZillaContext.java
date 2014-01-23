/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rgalerme
 */
public class BugZillaContext {

    private List<BugzillaBug> listOfBug;
//    private List<String> listOfProduct;

    public BugZillaContext() {
        this.listOfBug = new ArrayList<BugzillaBug>();
    }

    public List<BugzillaBug> getListOfBug() {
        return listOfBug;
    }

    
    
}
