/**
 *
 */

package org.komea.product.web.rest.api;



import java.util.List;

import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;



/**
 * @author sleroy
 */
public class PersonsControllerTest extends AbstractSpringWebIntegrationTestCase
{
    
    
    private static final String PERSON_XYA = "PERSON_XYA";
    private static final String PERSON_XYZ = "PERSON_XYZ";
    @Autowired
    private PersonsController   personsController;
    
    @Autowired
    private IPersonService      personService;
    
    
    
    /**
     * Test method for {@link org.komea.product.web.rest.api.PersonsController#allPersons()}.
     */
    @Test
    public final void testAllPersons() throws Exception {


        personService.saveOrUpdate(fakePerson(PERSON_XYA));
        personService.saveOrUpdate(fakePerson(PERSON_XYZ));
        
        final List<PersonDto> persons = personsController.allPersons();
        assertNotNull(CollectionUtil.filter(persons, new IFilter<PersonDto>()
        {
            
            
            @Override
            public boolean matches(final PersonDto _task) {
            
            
                return PERSON_XYA.equals(_task.getLogin());
            }

                }));
        assertNotNull(CollectionUtil.filter(persons, new IFilter<PersonDto>()
        {
            
            
            @Override
            public boolean matches(final PersonDto _task) {
            
            
                return PERSON_XYZ.equals(_task.getLogin());
            }

                }));
    }
    
    
    private Person fakePerson(final String _string) {


        final Person person = new Person();
        person.setEmail(_string);
        person.setLogin(_string);
        person.setUserBdd(UserBdd.KOMEA);
        person.setLastName("");
        person.setFirstName("");
        return person;
    }
}
