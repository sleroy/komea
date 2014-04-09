
package org.komea.product.rest.client;



import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.komea.product.rest.client.util.KomeaServerLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ServerMethodRule implements TestRule
{
    
    
    private static KomeaServerLauncher komeaServerLauncher;
    
    
    private static final Logger        LOGGER = LoggerFactory.getLogger(ServerMethodRule.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement, org.junit.runner.Description)
     */
    @Override
    public Statement apply(final Statement _base, final Description _description) {
    
    
        return serverStatement(_base);
    }
    
    
    public String getAddress() {
    
    
        return "http://localhost:" + getPort() + "/komea";
    }
    
    
    /**
     * @return
     */
    public Integer getPort() {
    
    
        return komeaServerLauncher.getLocalPort();
    }
    
    
    private Statement serverStatement(final Statement base) {
    
    
        return new Statement()
        {
            
            
            @Override
            public void evaluate() throws Throwable {
            
            
                before();
                base.evaluate();
                after();
            }
            
            
            private void after() {
            
            
                try {
                    komeaServerLauncher.stopServer();
                } catch (final Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    Assert.fail(e.getMessage());
                }
            }
            
            
            private void before() {
            
            
                try {
                    
                    komeaServerLauncher = new KomeaServerLauncher();
                    komeaServerLauncher.startServer();
                } catch (final Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    Assert.fail(e.getMessage());
                }
            }
        };
    }
}
