/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.department;

import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IProjectService;
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

    public DepartmentPage(final PageParameters _parameters) {

        super(_parameters);
        List<PersonGroup> listAffichage = personGroupService.getAllDepartmentsPG();
        final IDeleteAction<PersonGroup> personGroupDeleteAction
                = new PersonGroupDeleteAction(personGroupService, listAffichage, this);
        final IEditAction<PersonGroup> personGroupEditAction = new DepartmentEditAction(this);

        final ISortableDataProvider<PersonGroup, String> dataProvider
                = new ListDataModel<PersonGroup>(listAffichage);
        //specific column
        CountColumn<PersonGroup> countColumn = new CountColumn<PersonGroup>() {

            @Override
            public Integer getNumberdisplay(PersonGroup type) {
                List<PersonGroup> children = personGroupService.getChildren(type.getId());
                return Integer.valueOf(children.size());
            }
        };
        // table
        final DataTable<PersonGroup, String> build
                = DataTableBuilder.<PersonGroup, String>newTable("table")
                .addColumn(getString("global.field.key"), "PersonGroupKey")
                .addColumn(getString("global.field.name"), "Name")
                .addColumn(countColumn.bluid(getString("departmentpage.main.table.teamnumber")))
                .withEditDeleteColumn(personGroupDeleteAction, personGroupEditAction)
                .displayRows(listAffichage.size() + 10).withData(dataProvider).build();

        add(build);

    }

    @Override
    public String getTitle() {
        return getString("departmentpage.main.title");
    }

}
