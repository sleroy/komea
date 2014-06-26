package org.komea.product.wicket.person;

import java.util.List;

import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

public class PersonDeleteAction extends AbstractDeleteAction<Person> {

    private final IPersonService personService;
    private final List<Person> listModel;

    public PersonDeleteAction(final IPersonService _personDAO, List<Person> _listModel, StatelessLayoutPage page) {
        super(page, "dialogdelete");
        personService = _personDAO;
        listModel = _listModel;
    }

    @Override
    public void deleteAction() {
        personService.deletePerson(getObject());
        listModel.remove(getObject());
    }

}
