/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla.api;

import java.util.List;
import org.komea.backend.plugins.bugzilla.BugZillaStatus;
import org.komea.backend.plugins.bugzilla.BugzillaBug;

/**
 *
 * @author rgalerme
 */
public interface IBugZillaServerProxy {
    
    /**
     *
     * @return
     */
    public List<String> getListProjects();

    /**
     * get list of bugs in  bugzilla server
     * @param Project
     * @return
     */
    public List<BugzillaBug> getListBugs(String Project);

    /**
     * get spécific bug in list préviously get in bugzilla server
     * @param Project
     * @param status
     * @return
     */
    public List<BugzillaBug> getFilterBugs(String Project, BugZillaStatus... status);
}
