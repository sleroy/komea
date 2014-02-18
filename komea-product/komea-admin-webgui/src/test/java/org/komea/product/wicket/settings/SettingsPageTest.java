package org.komea.product.wicket.settings;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.*;
import static org.junit.Assert.*;
import org.komea.product.backend.service.ISettingService;

/**
 * The class <code>SettingsPageTest</code> contains tests for the class <code>{@link SettingsPage}</code>.
 *
 * @generatedBy CodePro at 17/02/14 22:41
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class SettingsPageTest
{
    /**
     * Run the SettingsPage(PageParameters) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testSettingsPage_1()
        throws Exception {
        PageParameters _parameters = new PageParameters();

        SettingsPage result = new SettingsPage(_parameters);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:157)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:106)
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:30)
        //       at org.komea.product.wicket.settings.SettingsPage.<init>(SettingsPage.java:35)
        assertNotNull(result);
        // unverified
    }

    /**
     * Run the ISettingService getService() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testGetService_1()
        throws Exception {
        SettingsPage fixture = new SettingsPage(new PageParameters());
        fixture.setService(EasyMock.createNiceMock(ISettingService.class));
        fixture.getAutoIndex();
        fixture.componentRendered(new AccessDeniedPage());
        fixture.renderPage();

        ISettingService result = fixture.getService();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:157)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:106)
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:30)
        //       at org.komea.product.wicket.settings.SettingsPage.<init>(SettingsPage.java:35)
        assertNotNull(result);
        // unverified
    }

    /**
     * Run the String getTitle() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testGetTitle_1()
        throws Exception {
        SettingsPage fixture = new SettingsPage(new PageParameters());
        fixture.setService(EasyMock.createNiceMock(ISettingService.class));
        fixture.getAutoIndex();
        fixture.componentRendered(new AccessDeniedPage());
        fixture.renderPage();

        String result = fixture.getTitle();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:157)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:106)
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:30)
        //       at org.komea.product.wicket.settings.SettingsPage.<init>(SettingsPage.java:35)
        assertNotNull(result);
        // unverified
    }

    /**
     * Run the void setService(ISettingService) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testSetService_1()
        throws Exception {
        SettingsPage fixture = new SettingsPage(new PageParameters());
        fixture.setService(EasyMock.createNiceMock(ISettingService.class));
        fixture.getAutoIndex();
        fixture.componentRendered(new AccessDeniedPage());
        fixture.renderPage();
        ISettingService _service = EasyMock.createMock(ISettingService.class);
        // add mock object expectations here

        EasyMock.replay(_service);

        fixture.setService(_service);

        // add additional test code here
        EasyMock.verify(_service);
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:157)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:106)
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:30)
        //       at org.komea.product.wicket.settings.SettingsPage.<init>(SettingsPage.java:35)
        // unverified
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Before
    public void setUp()
        throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }
}