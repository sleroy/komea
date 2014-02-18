package org.komea.product.wicket.widget.model;

import java.util.Iterator;

import org.apache.wicket.model.IModel;
import org.easymock.EasyMock;
import org.junit.*;

import static org.junit.Assert.*;

import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.model.PersonDataModel;

/**
 * The class <code>PersonDataModelTest</code> contains tests for the class <code>{@link PersonDataModel}</code>.
 *
 * @generatedBy CodePro at 17/02/14 21:28
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class PersonDataModelTest
{
    /**
     * Run the PersonDataModel(PersonDao) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testPersonDataModel_1()
        throws Exception {
        PersonDao _personDAO = EasyMock.createMock(PersonDao.class);
        // add mock object expectations here

        EasyMock.replay(_personDAO);

        PersonDataModel result = new PersonDataModel(_personDAO);

        // add additional test code here
        EasyMock.verify(_personDAO);
        assertNotNull(result);
        assertEquals(null, result.getSort());
    }

    /**
     * Run the Iterator<? extends Person> iterator(long,long) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testIterator_1()
        throws Exception {
        PersonDataModel fixture = new PersonDataModel(EasyMock.createNiceMock(PersonDao.class));
        long _first = 1L;
        long _count = 1L;

        Iterator<? extends Person> result = fixture.iterator(_first, _count);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.NullPointerException
        //       at org.komea.product.wicket.widget.model.PersonDataModel.iterator(PersonDataModel.java:42)
        assertNotNull(result);
    }

    /**
     * Run the IModel<Person> model(Person) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testModel_1()
        throws Exception {
        PersonDataModel fixture = new PersonDataModel(EasyMock.createNiceMock(PersonDao.class));
        Person _object = new Person();

        IModel<Person> result = fixture.model(_object);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the long size() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:28
     */
    @Test
    public void testSize_1()
        throws Exception {
        PersonDataModel fixture = new PersonDataModel(EasyMock.createNiceMock(PersonDao.class));

        long result = fixture.size();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.NullPointerException
        //       at org.komea.product.wicket.widget.model.PersonDataModel.size(PersonDataModel.java:58)
        assertEquals(0L, result);
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
        new org.junit.runner.JUnitCore().run(PersonDataModelTest.class);
    }
}