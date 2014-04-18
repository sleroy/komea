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
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.persongroup.PersonGroupDeleteAction;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



/**
 * @author rgalerme
 */
public class TeamPage extends LayoutPage
{
    
    
    @SpringBean
    private IPersonGroupService personGroupService;
    
    
    
    public TeamPage(final PageParameters _parameters) {
    
    
        super(_parameters);
          List<PersonGroup> listAffichage = personGroupService.getAllTeamsPG();
        final IDeleteAction<PersonGroup> personGroupDeleteAction =
                new PersonGroupDeleteAction(personGroupService,listAffichage,this);
        final IEditAction<PersonGroup> personGroupEditAction = new TeamEditAction(this);
        
        final ISortableDataProvider<PersonGroup, String> dataProvider =
                new ListDataModel(listAffichage);
        final DataTable<PersonGroup, String> build =
                DataTableBuilder.<PersonGroup, String> newTable("table")
                        .addColumn(getString("global.field.key"), "PersonGroupKey").addColumn(getString("global.field.placeholder.name"), "Name")
                        .addColumn(getString("global.field.label.description"), "Description")
                        .withEditDeleteColumn(personGroupDeleteAction, personGroupEditAction)
                        .displayRows(listAffichage.size()+10).withData(dataProvider).build();
        add(build);
    }
    
     @Override
    public String getTitle() {
        return getString("teampage.main.title");
    }
}
