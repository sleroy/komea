/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink.api;

import org.komea.product.database.alert.IAlert;

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
    public IAlert newTotalTests(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newSuccessfultest(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newFailedTests(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newUnexecutedTest(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newBlockedTests(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newRequirements(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newTested(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newUntested(long _alert, String _project);

    /**
     *
     * @param _alert
     * @param _project
     * @return
     */
    public IAlert newUnassociedTest(long _alert, String _project);

}
