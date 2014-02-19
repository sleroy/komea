package org.komea.product.wicket.person;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.*;
import static org.junit.Assert.*;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;

/**
 * The class <code>PersonEditActionTest</code> contains tests for the class <code>{@link PersonEditAction}</code>.
 *
 * @generatedBy CodePro at 17/02/14 21:29
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class PersonEditActionTest
{
    /**
     * Run the PersonEditAction(PersonPage,PersonRoleDao) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testPersonEditAction_1()
        throws Exception {
        PersonPage _editedPersonPage = new PersonPage(new PageParameters());
        PersonRoleDao _personRoleDAO = EasyMock.createMock(PersonRoleDao.class);
        // add mock object expectations here

        EasyMock.replay(_personRoleDAO);

        PersonEditAction result = new PersonEditAction(_editedPersonPage, _personRoleDAO);

        // add additional test code here
        EasyMock.verify(_personRoleDAO);
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
     * Run the void selected(Person) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testSelected_1()
        throws Exception {
        PersonEditAction fixture = new PersonEditAction(new PersonPage(new PageParameters()), EasyMock.createNiceMock(PersonRoleDao.class));
        Person _object = new Person(new Integer(1), new Integer(1), new Integer(1), "", "", "", "", "", UserBdd.KOMEA);

        fixture.selected(_object);

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
    }

    /**
     * Run the void selected(Person) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testSelected_2()
        throws Exception {
        PersonEditAction fixture = new PersonEditAction(new PersonPage(new PageParameters()), EasyMock.createNiceMock(PersonRoleDao.class));
        Person _object = new Person(new Integer(1), new Integer(1), new Integer(1), "", "", "", "", "", UserBdd.KOMEA);

        fixture.selected(_object);

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
        new org.junit.runner.JUnitCore().run(PersonEditActionTest.class);
    }
}