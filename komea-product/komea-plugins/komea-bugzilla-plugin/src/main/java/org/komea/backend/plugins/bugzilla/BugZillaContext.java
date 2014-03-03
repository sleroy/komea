/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BugZillaContext {

    private final Map<String, List<BugzillaBug>> listTotalBug;
    private final Map<String, List<BugzillaBug>> listOfNewBug;
    private final Map<String, List<BugzillaBug>> listUpdateBug;
    private final Map<String, Map<String, List<BugzillaBug>>> status;

//    private List<String> listOfProduct;
    public BugZillaContext() {

        this.status = new HashMap<String, Map<String, List<BugzillaBug>>>();
        this.listOfNewBug = new HashMap<String, List<BugzillaBug>>();
        this.listTotalBug = new HashMap<String, List<BugzillaBug>>();
        this.listUpdateBug = new HashMap<String, List<BugzillaBug>>();
    }

    /**
     * Method getStatus.
     * @return Set<String>
     */
    public Set<String> getStatus() {
        return status.keySet();
    }

    /**
     * Method getFilterBugsByStatus.
     * @param _status String
     * @param _project String
     * @return List<BugzillaBug>
     */
    public List<BugzillaBug> getFilterBugsByStatus(String _status, String _project) {
        Map<String, List<BugzillaBug>> temps_list = this.status.get(_status);
        if (temps_list != null) {
            return temps_list.get(_project);
        }
        return null;
    }

    /**
     * Method updateBugs.
     * @param project String
     * @param listOfBug List<BugzillaBug>
     */
    public void updateBugs(String project, List<BugzillaBug> listOfBug) {
        List<BugzillaBug> get = this.listTotalBug.get(project);
        List<BugzillaBug> newbug = new ArrayList<BugzillaBug>();
        List<BugzillaBug> newupdate = new ArrayList<BugzillaBug>();
        this.listOfNewBug.put(project, newbug);
        this.listUpdateBug.put(project, newupdate);

        for (BugzillaBug presentbug : listOfBug) {
            boolean newId = true;
            boolean newStatus = false;
//            status.add(presentbug.getStatus()); update a faire
            Map<String, List<BugzillaBug>> temp_map = this.status.get(presentbug.getStatus());
            List<BugzillaBug> temp_bug = null;
            if (temp_map == null) {
                temp_map = new HashMap<String, List<BugzillaBug>>();
                this.status.put(presentbug.getStatus(), temp_map);
                temp_bug = new ArrayList<BugzillaBug>();
                temp_map.put(project, temp_bug);
            } else {
                temp_bug = temp_map.get(project);
                if (temp_bug == null) {
                    temp_bug = new ArrayList<BugzillaBug>();
                    temp_map.put(project, temp_bug);
                }
            }
            temp_bug.add(presentbug);
            if (get != null) {
                for (BugzillaBug oldbug : get) {
                    if (presentbug.getId() == oldbug.getId()) {
                        newId = false;
                        if (!presentbug.getStatus().equals(oldbug.getStatus())) {
                            newStatus = true;
                        }
                    }
                }
                if (newId) {
                    newbug.add(presentbug);
                }
                if (newStatus) {
                    newupdate.add(presentbug);
                }
            }
        }
        this.listTotalBug.put(project, listOfBug);

    }

    /**
     * Method getFilterBugs.
     * @param project String
     * @param status BugZillaStatus
     * @return List<BugzillaBug>
     */
    public List<BugzillaBug> getFilterBugs(String project, BugZillaStatus status) {
        List<BugzillaBug> filterBugs = null;
        if (status.equals(BugZillaStatus.ADD)) {
            filterBugs = listOfNewBug.get(project);
        } else if (status.equals(BugZillaStatus.UPDATED)) {
            filterBugs = listUpdateBug.get(project);
        } else if (status.equals(BugZillaStatus.ASSIGNED)) {
            List<BugzillaBug> get = listTotalBug.get(project);
            filterBugs = new ArrayList<BugzillaBug>();
            for (BugzillaBug bugzillaBug : get) {
                if (bugzillaBug.isIs_assigned() && bugzillaBug.isIs_open()) {
                    filterBugs.add(bugzillaBug);
                }
            }
        }

        return filterBugs;
    }

}
