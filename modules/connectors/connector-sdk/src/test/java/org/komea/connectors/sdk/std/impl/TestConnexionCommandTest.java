package org.komea.connectors.sdk.std.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.Application;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=9991")
public class TestConnexionCommandTest {

    @Test
    public void testRun() throws Exception {
        final TestConnexionCommand testConnexion = new TestConnexionCommand();
        testConnexion.setServerURL("http://localhost:9991");
        testConnexion.run();

    }

    @Test(expected = Exception.class)
    public void testRunFail() throws Exception {
        final TestConnexionCommand testConnexion = new TestConnexionCommand();
        testConnexion.setServerURL("http://localhost:9992");
        testConnexion.run();

    }

}
