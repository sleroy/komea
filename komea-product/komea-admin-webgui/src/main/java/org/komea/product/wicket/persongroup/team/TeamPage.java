/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.team;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.persongroup.PersonGroupDeleteAction;
import org.komea.product.wicket.utils.CountColumn;
import org.komea.product.wicket.utils.CustomColumn;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;

/**
 * @author rgalerme
 */
public class TeamPage extends LayoutPage {

    @SpringBean
    private IPersonGroupService personGroupService;
    @SpringBean
    private IPersonService membersService;
    @SpringBean
    private IProjectService projectService;

    public TeamPage(final PageParameters _parameters) {

        super(_parameters);
        List<PersonGroup> listAffichage = personGroupService.getAllTeamsPG();
        final IDeleteAction<PersonGroup> personGroupDeleteAction
                = new PersonGroupDeleteAction(personGroupService, listAffichage, this);
        final IEditAction<PersonGroup> personGroupEditAction = new TeamEditAction(this);

        CustomColumn<PersonGroup> departmentcol = new CustomColumn<PersonGroup>() {
            @Override
            public String getValueDisplay(PersonGroup personGroup) {
                String name = "";
                if (personGroup.getIdPersonGroupParent() != null) {
                    PersonGroup selectByPrimaryKey = personGroupService.selectByPrimaryKey(personGroup.getIdPersonGroupParent());
                    if (selectByPrimaryKey != null) {
                        name = selectByPrimaryKey.getDisplayName();
                    }
                }
                return name;
            }
        };
        CountColumn<PersonGroup> countUsers = new CountColumn<PersonGroup>() {

            @Override
            public Integer getNumberdisplay(PersonGroup type) {
                List<Person> personsOfPersonGroup = membersService.getPersonsOfPersonGroupRecursively(type.getId());
                int result = 0;
                if (personsOfPersonGroup != null) {
                    result = personsOfPersonGroup.size();
                }
                return Integer.valueOf(result);
            }
        };

        CountColumn<PersonGroup> countProjects = new CountColumn<PersonGroup>() {

            @Override
            public Integer getNumberdisplay(PersonGroup type) {
                List<Project> listResult = projectService.getProjectsOfPersonGroup(type.getId());
                List<Person> personsOfPersonGroup = membersService.getPersonsOfPersonGroup(type.getId());
                for (Person person : personsOfPersonGroup) {
                    DialogFactory.addDistictList((List)listResult, (List)projectService.getProjectsOfAMember(person.getId()));
                }
                int result = 0;
                if (listResult != null) {
                    result = listResult.size();
                }
                return Integer.valueOf(result);
            }
        };

        final ISortableDataProvider<PersonGroup, String> dataProvider
                = new ListDataModel(listAffichage);
        final DataTable<PersonGroup, String> build
                = DataTableBuilder.<PersonGroup, String>newTable("table")
                .addColumn(getString("global.field.key"), "PersonGroupKey").addColumn(getString("global.field.placeholder.name"), "Name")
                .addColumn(departmentcol.build(getString("teampage.save.form.field.label.department")))
                .addColumn(countUsers.build(getString("teampage.save.form.field.label.members")))
                .addColumn(countProjects.build(getString("teampage.save.form.field.label.projects")))
                .withEditDeleteColumn(personGroupDeleteAction, personGroupEditAction)
                .displayRows(listAffichage.size() + 10).withData(dataProvider).build();
        add(build);
    }

    @Override
    public String getTitle() {
        return getString("teampage.main.title");
    }
}
