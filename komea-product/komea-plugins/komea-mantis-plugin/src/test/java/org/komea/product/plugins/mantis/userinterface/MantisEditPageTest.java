/**
 *
 */
package org.komea.product.plugins.mantis.userinterface;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.plugins.mantis.userinterface.MantisEditPage;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

/**
 * @author sleroy
 */
public class MantisEditPageTest {

    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();

    @Before
    public void before() {

        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IMantisConfigurationDAO.class));

    }

    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.userinterface.MantisEditPage#BugZillaEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test
    public final void testBugZillaEditPagePageParameters() throws Exception {

        wicketRule.testStart(MantisEditPage.class);
    }

    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.userinterface.MantisEditPage#BugZillaEditPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.plugins.mantis.model.MantisServerConfiguration)}
     * .
     */
    @Test
    public final void testBugZillaEditPagePageParametersBZServerConfiguration() throws Exception {

        final WicketTester wicketTester = wicketRule.newWicketTester();
        try {
            wicketTester.startPage(new MantisEditPage(new PageParameters(),
                    new MantisServerConfiguration()));
            wicketTester.assertNoErrorMessage();
        } finally {
            wicketTester.destroy();
        }
    }

}
