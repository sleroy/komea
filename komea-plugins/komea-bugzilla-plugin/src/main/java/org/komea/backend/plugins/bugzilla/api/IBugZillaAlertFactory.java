/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla.api;

import org.komea.product.database.alert.IEvent;

/**
 *
 * @author rgalerme
 */
public interface IBugZillaAlertFactory {

    
    public IEvent newStatusBug(long _alert, String _project, String _status);
    
    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newTotalBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newUnconfirmedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newNewBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newAssignedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newReopenedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newReadyBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newResolvedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newVerifiedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newNewBug(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newUpdatedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newReminterBugs(long _alert, String _project);

}
