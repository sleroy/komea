/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla.api;

import org.komea.product.database.alert.IAlert;

/**
 *
 * @author rgalerme
 */
public interface IBugZillaAlertFactory {

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newTotalBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newUnconfirmedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newNewBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newAssignedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newReopenedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newReadyBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newResolvedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newVerifiedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newNewBug(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newUpdatedBugs(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newReminterBugs(long _alert, String _project);

}
