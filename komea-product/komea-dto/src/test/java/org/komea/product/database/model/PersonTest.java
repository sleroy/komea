/**
 * 
 */

package org.komea.product.database.model;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.enums.UserBdd;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonTest
{
    
    
    /**
     * Test method for {@link org.komea.product.database.model.Person#newPersonWithOnlyEmailAndLogin(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testNewPersonWithOnlyEmailAndLogin() throws Exception {
    
    
        final Person newPersonWithOnlyEmailAndLogin =
                Person.newPersonWithOnlyEmailAndLogin("email@email.org", "email");
        assertEquals("email@email.org", newPersonWithOnlyEmailAndLogin.getEmail());
        assertEquals("email", newPersonWithOnlyEmailAndLogin.getLogin());
        assertEquals(UserBdd.KOMEA, newPersonWithOnlyEmailAndLogin.getUserBdd());
    }
    
    
    @Test
    public void testSetIdPersonGroupOrNull() throws Exception {
    
    
        final Person person2 = new Person();
        final PersonGroup personGroup = new PersonGroup();
        personGroup.setId(14);
        person2.setIdPersonGroupOrNull(personGroup);
        assertEquals("Person id group should be 14", Integer.valueOf(14),
                person2.getIdPersonGroup());
        person2.setIdPersonGroupOrNull(null);
        assertNull("Person id group should be null", person2.getIdPersonGroup());
    }
    
    
    /**
     * Test method for {@link org.komea.product.database.model.Person#setIdPersonRoleOrNull(org.komea.product.database.model.PersonRole)}.
     */
    @Test
    public void testSetIdPersonRoleOrNull() throws Exception {
    
    
        final Person person2 = new Person();
        final PersonRole personRole = new PersonRole();
        personRole.setId(14);
        person2.setIdPersonRoleOrNull(personRole);
        assertEquals("Person id role should be 14", Integer.valueOf(14), person2.getIdPersonRole());
        person2.setIdPersonRoleOrNull(null);
        assertNull("Person id role should be null", person2.getIdPersonRole());
    }
    
}
