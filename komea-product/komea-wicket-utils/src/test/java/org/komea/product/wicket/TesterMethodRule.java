
package org.komea.product.wicket;



import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.komea.product.backend.WicketAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class initializes the required objects to produce unit tests with wicket. You will have to provide mock to enable it in your tests.
 *
 * @author sleroy
 */
class TesterMethodRule implements TestRule
{
    
    
    private static final Logger    LOGGER = LoggerFactory.getLogger(TesterMethodRule.class);
    private ApplicationContextMock applicationContextMock;
    
    private WicketAdminService     wicketAdminService;
    
    
    private WicketApplication      wicketApplicationTest;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement, org.junit.runner.Description)
     */
    @Override
    public Statement apply(final Statement _base, final Description _description) {
    
    
        return testerStatement(_base);
    }
    
    
    /**
     * @return the applicationContextMock
     */
    public ApplicationContextMock getApplicationContextMock() {
    
    
        return applicationContextMock;
    }
    
    
    /**
     * @return the wicketAdminService
     */
    public WicketAdminService getWicketAdminService() {
    
    
        return wicketAdminService;
    }
    
    
    /**
     * @return the wicketApplicationTest
     */
    public WicketApplication getWicketApplicationTest() {
    
    
        return wicketApplicationTest;
    }
    
    
    public WicketTester newWicketTester() {
    
    
        return new WicketTester(wicketApplicationTest);
    }
    
    
    /**
     * @param _applicationContextMock
     *            the applicationContextMock to set
     */
    public void setApplicationContextMock(final ApplicationContextMock _applicationContextMock) {
    
    
        applicationContextMock = _applicationContextMock;
    }
    
    
    /**
     * @param _wicketAdminService
     *            the wicketAdminService to set
     */
    public void setWicketAdminService(final WicketAdminService _wicketAdminService) {
    
    
        wicketAdminService = _wicketAdminService;
    }
    
    
    /**
     * @param _wicketApplicationTest
     *            the wicketApplicationTest to set
     */
    public void setWicketApplicationTest(final WicketApplication _wicketApplicationTest) {
    
    
        wicketApplicationTest = _wicketApplicationTest;
    }
    
    
    /**
     * @param _class
     */
    public void testStart(final Class _class) {
    
    
        final WicketTester newWicketTester = newWicketTester();
        try {
            newWicketTester.startPage(_class);
            newWicketTester.assertNoErrorMessage();
        } finally {
            newWicketTester.destroy();
        }
        
        
    }
    
    
    private Statement testerStatement(final Statement base) {
    
    
        return new Statement()
        {
            
            
            @Override
            public void evaluate() throws Throwable {
            
            
                before();
                base.evaluate();
                after();
            }
            
            
            private void after() {
            
            
                //
            }
            
            
            private void before() {
            
            
                try {
                    
                    wicketApplicationTest = new WicketApplication(true);
                    applicationContextMock = wicketApplicationTest.getContextMock();
                    wicketAdminService = new WicketAdminService();
                    applicationContextMock.putBean(wicketAdminService);
                    
                } catch (final Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    Assert.fail(e.getMessage());
                }
            }
        };
    }
}
