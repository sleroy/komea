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
     *
     * @param Project
     * @return
     */
    public List<BugzillaBug> getListBugs(String Project);

    /**
     *
     * @param Project
     * @param status
     * @return
     */
    public List<BugzillaBug> getListBugs(String Project, BugZillaStatus... status);
}
