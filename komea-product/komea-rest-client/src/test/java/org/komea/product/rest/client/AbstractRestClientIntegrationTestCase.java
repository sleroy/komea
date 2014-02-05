
package org.komea.product.rest.client;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.komea.product.rest.client.util.KomeaServerLauncher;

public class AbstractRestClientIntegrationTestCase
{
    
    private static KomeaServerLauncher komeaServerLauncher;
    
    @BeforeClass
    public static void setBeforeClass() throws Exception {
    
        komeaServerLauncher = new KomeaServerLauncher();
        komeaServerLauncher.startServer();
        Thread.sleep(5000);
        
    }
    
    @AfterClass
    public static void setAfterClass() throws Exception {
    
        komeaServerLauncher.stopServer();
    }
}
