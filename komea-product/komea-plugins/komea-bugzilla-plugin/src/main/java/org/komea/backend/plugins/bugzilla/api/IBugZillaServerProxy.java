/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla.api;

import java.io.Closeable;
import java.util.List;
import org.komea.backend.plugins.bugzilla.data.BugzillaBug;

/**
 *
 * @author rgalerme
 */
public interface IBugZillaServerProxy  extends Closeable {

    /**
     *
     * @return
     */
    public List<String> getListProjects();

    /**
     * get list of bugs in bugzilla server
     *
     * @param Project
     * @return
     */
    public List<BugzillaBug> getListBugs(String Project);

   
}
