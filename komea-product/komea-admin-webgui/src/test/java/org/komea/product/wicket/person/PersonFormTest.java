package org.komea.product.wicket.person;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.model.IModel;
import org.easymock.EasyMock;
import org.junit.*;
import static org.junit.Assert.*;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dto.PersonDto;

/**
 * The class <code>PersonFormTest</code> contains tests for the class <code>{@link PersonForm}</code>.
 *
 * @generatedBy CodePro at 17/02/14 23:08
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class PersonFormTest
{
    /**
     * Run the PersonForm(PersonRoleDao,PersonDao,String,Component,IModel<PersonDto>) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 23:08
     */
    @Test
    public void testPersonForm_1()
        throws Exception {
        PersonRoleDao _personRoleDAO = EasyMock.createMock(PersonRoleDao.class);
        PersonDao _personDAO = EasyMock.createMock(PersonDao.class);
        String _id = "";
        Component _feedBack = new AccessDeniedPage();
        IModel<PersonDto> _dto = EasyMock.createMock(IModel.class);
        // add mock object expectations here

        EasyMock.replay(_personRoleDAO);
        EasyMock.replay(_personDAO);
        EasyMock.replay(_dto);

        PersonForm result = new PersonForm(_personRoleDAO, _personDAO, _id, _feedBack, _dto);

        // add additional test code here
        EasyMock.verify(_personRoleDAO);
        EasyMock.verify(_personDAO);
        EasyMock.verify(_dto);
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:132)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:76)
        //       at org.apache.wicket.markup.html.pages.AbstractErrorPage.<init>(AbstractErrorPage.java:33)
        //       at org.apache.wicket.markup.html.pages.AccessDeniedPage.<init>(AccessDeniedPage.java:37)
        assertNotNull(result);
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 23:08
     */
    @Test
    public void testOnSubmit_1()
        throws Exception {
        PersonForm fixture = new PersonForm(EasyMock.createNiceMock(PersonRoleDao.class), EasyMock.createNiceMock(PersonDao.class), "", new AccessDeniedPage(), EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:132)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:76)
        //       at org.apache.wicket.markup.html.pages.AbstractErrorPage.<init>(AbstractErrorPage.java:33)
        //       at org.apache.wicket.markup.html.pages.AccessDeniedPage.<init>(AccessDeniedPage.java:37)
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 23:08
     */
    @Test
    public void testOnSubmit_2()
        throws Exception {
        PersonForm fixture = new PersonForm(EasyMock.createNiceMock(PersonRoleDao.class), EasyMock.createNiceMock(PersonDao.class), "", new AccessDeniedPage(), EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:132)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:76)
        //       at org.apache.wicket.markup.html.pages.AbstractErrorPage.<init>(AbstractErrorPage.java:33)
        //       at org.apache.wicket.markup.html.pages.AccessDeniedPage.<init>(AccessDeniedPage.java:37)
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 23:08
     */
    @Test
    public void testOnSubmit_3()
        throws Exception {
        PersonForm fixture = new PersonForm(EasyMock.createNiceMock(PersonRoleDao.class), EasyMock.createNiceMock(PersonDao.class), "", new AccessDeniedPage(), EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:132)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:76)
        //       at org.apache.wicket.markup.html.pages.AbstractErrorPage.<init>(AbstractErrorPage.java:33)
        //       at org.apache.wicket.markup.html.pages.AccessDeniedPage.<init>(AccessDeniedPage.java:37)
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 23:08
     */
    @Test
    public void testOnSubmit_4()
        throws Exception {
        PersonForm fixture = new PersonForm(EasyMock.createNiceMock(PersonRoleDao.class), EasyMock.createNiceMock(PersonDao.class), "", new AccessDeniedPage(), EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: There is no application attached to current thread Request Execution Thread
        //       at org.apache.wicket.Application.get(Application.java:233)
        //       at org.apache.wicket.Component.getApplication(Component.java:1301)
        //       at org.apache.wicket.Component.<init>(Component.java:683)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.Page.<init>(Page.java:168)
        //       at org.apache.wicket.Page.<init>(Page.java:132)
        //       at org.apache.wicket.markup.html.WebPage.<init>(WebPage.java:76)
        //       at org.apache.wicket.markup.html.pages.AbstractErrorPage.<init>(AbstractErrorPage.java:33)
        //       at org.apache.wicket.markup.html.pages.AccessDeniedPage.<init>(AccessDeniedPage.java:37)
        // unverified
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 23:08
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
     * @generatedBy CodePro at 17/02/14 23:08
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }
}