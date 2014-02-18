package org.komea.product.wicket.events;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.*;
import static org.junit.Assert.*;
import org.komea.product.backend.service.esper.IEventViewerService;

/**
 * The class <code>EventsPageTest</code> contains tests for the class <code>{@link EventsPage}</code>.
 *
 * @generatedBy CodePro at 17/02/14 21:28
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EventsPageTest
{
    /**
     * Run the EventsPage(PageParameters) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testEventsPage_1()
        throws Exception {
        PageParameters _parameters = new PageParameters();

        EventsPage result = new EventsPage(_parameters);

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
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:24)
        //       at org.komea.product.wicket.events.EventsPage.<init>(EventsPage.java:129)
        assertNotNull(result);
    }

    /**
     * Run the IEventViewerService getService() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testGetService_1()
        throws Exception {
        EventsPage fixture = new EventsPage(new PageParameters());
        fixture.setService(EasyMock.createNiceMock(IEventViewerService.class));
        fixture.componentRendered(new AccessDeniedPage());
        fixture.renderPage();
        fixture.getAutoIndex();

        IEventViewerService result = fixture.getService();

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
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:24)
        //       at org.komea.product.wicket.events.EventsPage.<init>(EventsPage.java:129)
        assertNotNull(result);
    }

    /**
     * Run the String getTitle() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testGetTitle_1()
        throws Exception {
        EventsPage fixture = new EventsPage(new PageParameters());
        fixture.setService(EasyMock.createNiceMock(IEventViewerService.class));
        fixture.componentRendered(new AccessDeniedPage());
        fixture.renderPage();
        fixture.getAutoIndex();

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
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:24)
        //       at org.komea.product.wicket.events.EventsPage.<init>(EventsPage.java:129)
        assertNotNull(result);
    }

    /**
     * Run the void setService(IEventViewerService) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testSetService_1()
        throws Exception {
        EventsPage fixture = new EventsPage(new PageParameters());
        fixture.setService(EasyMock.createNiceMock(IEventViewerService.class));
        fixture.componentRendered(new AccessDeniedPage());
        fixture.renderPage();
        fixture.getAutoIndex();
        IEventViewerService _service = EasyMock.createMock(IEventViewerService.class);
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
        //       at org.komea.product.wicket.LayoutPage.<init>(LayoutPage.java:24)
        //       at org.komea.product.wicket.events.EventsPage.<init>(EventsPage.java:129)
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 21:28
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
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(EventsPageTest.class);
    }
}