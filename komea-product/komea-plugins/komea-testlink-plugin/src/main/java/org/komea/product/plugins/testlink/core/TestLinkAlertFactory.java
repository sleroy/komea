/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.core;


import java.util.Date;

import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.testlink.api.ITestLinkAlertFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rgalerme
 */
@Transactional
@Service
public class TestLinkAlertFactory implements ITestLinkAlertFactory {
    
    public static final String TESTLINK_BLOCKED_TESTS      = "testlink_blocked_tests";
    
    public static final String TESTLINK_FAILED_TESTS       = "testlink_failed_tests";
    
    public static final String TESTLINK_REQUIREMENTS       = "testlink_requirements";
    
    public static final String TESTLINK_SUCCESS_TESTS      = "testlink_success_tests";
    
    public static final String TESTLINK_TESTED_CASES       = "testlink_tested_cases";
    
    public static final String TESTLINK_TOTAL_TESTS        = "testlink_total_tests";
    
    public static final String TESTLINK_UNASSOCIATED_TESTS = "testlink_unassociated_tests";
    
    public static final String TESTLINK_UNEXECUTED_TESTS   = "testlink_unexecuted_tests";
    
    public static final String TESTLINK_UNTESTED_TESTS     = "testlink_untested_tests";
    
    public static final String TESTLINK_URL                = "/testlink";
    
    @Override
    public EventSimpleDto newBlockedTests(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setEventType(TESTLINK_BLOCKED_TESTS);
        event.setDate(new Date());
        
        return event;
    }
    
    @Override
    public EventSimpleDto newFailedTests(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        event.setEventType(TESTLINK_FAILED_TESTS);
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    @Override
    public EventSimpleDto newRequirements(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("Total number of requirements for the project {0} in testlink", _project);
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setEventType(TESTLINK_REQUIREMENTS);
        event.setDate(new Date());
        
        return event;
    }
    
    @Override
    public EventSimpleDto newSuccessfultest(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setEventType(TESTLINK_SUCCESS_TESTS);
        event.setDate(new Date());
        
        return event;
    }
    
    @Override
    public EventSimpleDto newTested(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setEventType(TESTLINK_TESTED_CASES);
        event.setDate(new Date());
        
        return event;
    }
    
    @Override
    public EventSimpleDto newTotalTests(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setEventType(TESTLINK_TOTAL_TESTS);
        event.setDate(new Date());
        
        return event;
    }
    
    @Override
    public EventSimpleDto newUnassociedTest(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        event.setEventType(TESTLINK_UNASSOCIATED_TESTS);
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setDate(new Date());
        return event;
    }
    
    @Override
    public EventSimpleDto newUnexecutedTest(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        event.setEventType(TESTLINK_UNEXECUTED_TESTS);
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setDate(new Date());
        
        return event;
    }
    
    @Override
    public EventSimpleDto newUntested(final long _alert, final String _project) {
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(TESTLINK_URL);
        event.setMessage("{0} tests are unexecuted tests in testlink for project {1}", _alert, _project);
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setDate(new Date());
        event.setEventType(TESTLINK_UNTESTED_TESTS);
        
        return event;
    }
    
}
