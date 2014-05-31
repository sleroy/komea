/**
 *
 */
package org.komea.product.plugins.mantis.userinterface;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.userinterface.MantisPage;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class MantisPageTest {

    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();

    @Before
    public void before() {

        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IMantisConfigurationDAO.class));

    }

    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.userinterface.MantisPage#BugZillaPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test
    public final void testBugZillaPage() throws Exception {

        wicketRule.testStart(MantisPage.class);
    }

}
