package org.komea.product.wicket.person;

import org.easymock.EasyMock;
import org.junit.*;
import static org.junit.Assert.*;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.model.Person;

/**
 * The class <code>PersonDeleteActionTest</code> contains tests for the class <code>{@link PersonDeleteAction}</code>.
 *
 * @generatedBy CodePro at 17/02/14 21:29
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class PersonDeleteActionTest
{
    /**
     * Run the PersonDeleteAction(PersonDao) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testPersonDeleteAction_1()
        throws Exception {
        PersonDao _personDAO = EasyMock.createMock(PersonDao.class);
        // add mock object expectations here

        EasyMock.replay(_personDAO);

        PersonDeleteAction result = new PersonDeleteAction(_personDAO);

        // add additional test code here
        EasyMock.verify(_personDAO);
        assertNotNull(result);
    }

    /**
     * Run the void delete(Person) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:29
     */
    @Test
    public void testDelete_1()
        throws Exception {
        PersonDeleteAction fixture = new PersonDeleteAction(EasyMock.createNiceMock(PersonDao.class));
        Person _object = new Person(new Integer(1), new Integer(1), new Integer(1), "", "", "", "", "");

        fixture.delete(_object);

        // add additional test code here
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
        new org.junit.runner.JUnitCore().run(PersonDeleteActionTest.class);
    }
}