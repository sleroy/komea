/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink.api;

import org.komea.product.database.alert.IEvent;

/**
 *
 * @author rgalerme
 */
public interface ITestLinkAlertFactory {

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newTotalTests(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newSuccessfultest(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newFailedTests(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newUnexecutedTest(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newBlockedTests(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newRequirements(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newTested(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newUntested(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IEvent newUnassociedTest(long _alert, String _project);

}
