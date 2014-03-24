package org.komea.product.wicket.person;

import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.api.IDeleteAction;

public class PersonDeleteAction implements IDeleteAction<Person> {

    private final IPersonService personService;

    public PersonDeleteAction(final IPersonService _personDAO) {

        personService = _personDAO;

    }

    @Override
    public void delete(final Person _object) {

        personService.deletePerson(_object);

    }

}
