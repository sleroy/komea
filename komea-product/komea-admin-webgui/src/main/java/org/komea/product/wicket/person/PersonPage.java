package org.komea.product.wicket.person;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.CustomColumn;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;

/**
 * Person admin page
 *
 * @author sleroy
 */
public class PersonPage extends LayoutPage {

    @SpringBean
    private IPersonService personDAO;
    @SpringBean
    private IPersonRoleService personRoleDAO;
    @SpringBean
    private IPersonGroupService personGroupService;
    @SpringBean
    private IPersonRoleService personRole;

    public PersonPage(final PageParameters _parameters) {

        super(_parameters);

        List<Person> listAffichage = personDAO.selectAll();
        final IDeleteAction<Person> personDeleteAction = new PersonDeleteAction(personDAO, listAffichage, this);
        final IEditAction<Person> personEditAction = new PersonEditAction(this, personRoleDAO);
        final ISortableDataProvider<Person, String> dataProvider
                = //                new PersonDataModel(personDAO.selectAll());
                new ListDataModel(listAffichage);

        CustomColumn<Person> teamCol = new CustomColumn<Person>() {
            @Override
            public String getValueDisplay(Person person) {
                String name = "";
                if (person.getIdPersonGroup() != null) {
                    PersonGroup selectByPrimaryKey = personGroupService.selectByPrimaryKey(person.getIdPersonGroup());
                    if (selectByPrimaryKey != null) {
                        name = selectByPrimaryKey.getDisplayName();
                    }
                }
                return name;
            }
        };

        CustomColumn<Person> admin = new CustomColumn<Person>() {
            @Override
            public String getValueDisplay(Person person) {
                String name = "";
                if (personRole.getAdminRole().getId().equals(person.getIdPersonRole())) {
                    name = "admin";
                }

                return name;
            }
        };

        add(DataTableBuilder.<Person, String>newTable("table")
                .addColumn(getString("global.save.form.field.label.login"), "login")
                .addColumn(getString("memberpage.save.form.label.lastn"), "lastName")
                .addColumn(getString("memberpage.save.form.label.firstn"), "firstName").addColumn(getString("memberpage.save.form.label.email"), "email")
                .addColumn(teamCol.build(getString("memberpage.save.form.field.label.memberof")))
                .addColumn(admin.build(getString("memberpage.save.form.label.role")))
                .withEditDeleteColumn(personDeleteAction, personEditAction).displayRows(listAffichage.size() + 10)
                .withData(dataProvider).build());

    }

    @Override
    public String getTitle() {

        return getString("memberpage.main.title");
    }

}
