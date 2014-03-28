package org.komea.product.wicket.person;

import java.util.List;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.api.IDeleteAction;

public class PersonDeleteAction implements IDeleteAction<Person> {

    private final IPersonService personService;
    private final List<Person> listModel;

    public PersonDeleteAction(final IPersonService _personDAO,List<Person> _listModel) {

        personService = _personDAO;
        listModel=_listModel;

    }

    @Override
    public void delete(final Person _object) {

        personService.deletePerson(_object);
        listModel.remove(_object);

    }

}
