package org.komea.product.wicket.person;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.*;
import static org.junit.Assert.*;
import org.komea.product.database.dto.PersonDto;

/**
 * The class <code>PersonPageTest</code> contains tests for the class <code>{@link PersonPage}</code>.
 *
 * @generatedBy CodePro at 17/02/14 21:29
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class PersonPageTest
{
    /**
     * Run the PersonPage(PageParameters) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testPersonPage_1()
        throws Exception {
        PageParameters _parameters = new PageParameters();

        PersonPage result = new PersonPage(_parameters);

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
        //       at org.komea.product.wicket.person.PersonPage.<init>(PersonPage.java:49)
        //       at org.komea.product.wicket.person.PersonPage.<init>(PersonPage.java:40)
        assertNotNull(result);
    }

    /**
     * Run the PersonPage(PageParameters,PersonDto) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testPersonPage_2()
        throws Exception {
        PageParameters _parameters = new PageParameters();
        PersonDto _personDTO = new PersonDto();

        PersonPage result = new PersonPage(_parameters, _personDTO);

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
        //       at org.komea.product.wicket.person.PersonPage.<init>(PersonPage.java:49)
        assertNotNull(result);
    }

    /**
     * Run the String getTitle() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testGetTitle_1()
        throws Exception {
        PersonPage fixture = new PersonPage(new PageParameters());
        fixture.getAutoIndex();
        fixture.renderPage();
        fixture.componentRendered(new AccessDeniedPage());

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
        //       at org.komea.product.wicket.person.PersonPage.<init>(PersonPage.java:49)
        //       at org.komea.product.wicket.person.PersonPage.<init>(PersonPage.java:40)
        assertNotNull(result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 21:29
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
     * @generatedBy CodePro at 17/02/14 21:29
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
     * @generatedBy CodePro at 17/02/14 21:29
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(PersonPageTest.class);
    }
}