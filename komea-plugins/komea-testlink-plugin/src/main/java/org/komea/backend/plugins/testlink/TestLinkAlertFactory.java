/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

import java.util.Date;
import org.komea.backend.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.product.database.alert.Alert;
import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 */
@Service
public class TestLinkAlertFactory implements ITestLinkAlertFactory {

    @Override
    public IAlert newTotalTests(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of tests in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newSuccessfultest(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of successful test in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newFailedTests(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of failed test in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newUnexecutedTest(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of unexecuted test in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newBlockedTests(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of blocked test in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newRequirements(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of requirements in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newTested(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of tested in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

    @Override
    public IAlert newUntested(long _alert, String _project) {
        Alert alert = new Alert();
        alert.setProvider("TESTLINK");
        alert.setMessage("Total number of untested in testlink");
        alert.setCategory("TEST");
        alert.setValue(_alert);
        alert.setFullMessage(alert.getMessage());
        alert.setProject(_project);
        alert.setCriticity(Criticity.INFO);
        alert.setDate(new Date());

        return alert;
    }

}
