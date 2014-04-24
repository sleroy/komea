/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.department;

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
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;

/**
 * @author rgalerme
 */
public final class DepartmentPage extends LayoutPage {

    @SpringBean
    private IPersonGroupService personGroupService;

    @SpringBean
    private IProjectService projectService;

    @SpringBean
    private IPersonService membersService;

    public DepartmentPage(final PageParameters _parameters) {

        super(_parameters);
        List<PersonGroup> listAffichage = personGroupService.getAllDepartmentsPG();
        final IDeleteAction<PersonGroup> personGroupDeleteAction
                = new PersonGroupDeleteAction(personGroupService, listAffichage, this);
        final IEditAction<PersonGroup> personGroupEditAction = new DepartmentEditAction(this);

        final ISortableDataProvider<PersonGroup, String> dataProvider
                = new ListDataModel<PersonGroup>(listAffichage);
        //specific column
        CountColumn<PersonGroup> countTeams = new CountColumn<PersonGroup>() {

            @Override
            public Integer getNumberdisplay(PersonGroup type) {
                List<PersonGroup> children = personGroupService.getChildren(type.getId());
                return Integer.valueOf(children.size());
            }
        };
        CountColumn<PersonGroup> countUsers = new CountColumn<PersonGroup>() {

            @Override
            public Integer getNumberdisplay(PersonGroup type) {
                List<Person> personsOfPersonGroup = membersService.getPersonsOfPersonGroupRecursively(type.getId());
                return Integer.valueOf(personsOfPersonGroup.size());
            }
        };
        CountColumn<PersonGroup> countProjects = new CountColumn<PersonGroup>() {

            @Override
            public Integer getNumberdisplay(PersonGroup type) {
                List<Project> projectsOfPersonGroup = projectService.getProjectsOfPersonGroupRecursively(type.getId());
                return Integer.valueOf(projectsOfPersonGroup.size());
            }
        };
        // table
        final DataTable<PersonGroup, String> build
                = DataTableBuilder.<PersonGroup, String>newTable("table")
                .addColumn(getString("global.field.key"), "PersonGroupKey")
                .addColumn(getString("global.field.name"), "Name")
                .addColumn(countTeams.bluid(getString("departmentpage.main.table.teamnumber")))
                .addColumn(countUsers.bluid(getString("departmentpage.main.table.membersnumber")))
                .addColumn(countProjects.bluid(getString("departmentpage.main.table.projectnumber")))
                .withEditDeleteColumn(personGroupDeleteAction, personGroupEditAction)
                .displayRows(listAffichage.size() + 10).withData(dataProvider).build();

        add(build);

    }

    @Override
    public String getTitle() {
        return getString("departmentpage.main.title");
    }

}
