/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.core;



import java.util.Date;

import org.komea.product.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.product.database.dto.EventSimpleDto;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 */
@Service
public class TestLinkAlertFactory implements ITestLinkAlertFactory
{
    
    
    @Override
    public EventSimpleDto newBlockedTests(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of blocked test in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    @Override
    public EventSimpleDto newFailedTests(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of failed test in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    @Override
    public EventSimpleDto newRequirements(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of requirements in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    @Override
    public EventSimpleDto newSuccessfultest(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of successful test in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    @Override
    public EventSimpleDto newTested(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of tested in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    @Override
    public EventSimpleDto newTotalTests(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of tests in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        
        event.setDate(new Date());
        
        return event;
    }
    
    
    @Override
    public EventSimpleDto newUnassociedTest(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of unassocied test in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setDate(new Date());
        return event;
    }
    
    
    @Override
    public EventSimpleDto newUnexecutedTest(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of unexecuted test in testlink");
        
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setDate(new Date());
        
        return event;
    }
    
    
    @Override
    public EventSimpleDto newUntested(final long _alert, final String _project) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of untested in testlink");
        event.setValue(_alert);
        event.setMessage(event.getMessage());
        event.setProject(_project);
        event.setDate(new Date());
        
        return event;
    }
    
}
