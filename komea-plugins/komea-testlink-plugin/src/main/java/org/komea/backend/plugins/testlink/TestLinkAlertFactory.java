/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

import java.util.Date;
import org.komea.backend.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.alert.enums.Criticity;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 */
@Service
public class TestLinkAlertFactory implements ITestLinkAlertFactory {

    @Override
    public IEvent newTotalTests(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of tests in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newSuccessfultest(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of successful test in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newFailedTests(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of failed test in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newUnexecutedTest(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of unexecuted test in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newBlockedTests(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of blocked test in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newRequirements(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of requirements in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newTested(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of tested in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newUntested(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of untested in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());

        return event;
    }

    @Override
    public IEvent newUnassociedTest(long _alert, String _project) {
        IEvent event = new Event();
        event.setProvider("TESTLINK");
        event.setMessage("Total number of unassocied test in testlink");
        event.setCategory("TEST");
        event.setValue(_alert);
        event.setFullMessage(event.getMessage());
        event.setProject(_project);
        event.setCriticity(Criticity.INFO);
        event.setDate(new Date());
        return event;
    }

}
