/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.api;



import org.komea.product.database.dto.EventSimpleDto;



/**
 * @author rgalerme
 */
public interface ITestLinkAlertFactory
{
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newBlockedTests(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newFailedTests(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newRequirements(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newSuccessfultest(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newTested(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newTotalTests(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newUnassociedTest(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newUnexecutedTest(long _alert, String _project);
    
    
    /**
     * @param _alert
     * @param _project
     * @return
     */
    public EventSimpleDto newUntested(long _alert, String _project);
    
}
