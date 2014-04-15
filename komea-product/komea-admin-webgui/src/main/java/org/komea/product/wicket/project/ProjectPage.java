/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.project;



import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



/**
 * @author rgalerme
 */
public class ProjectPage extends LayoutPage
{
    
    
    @SpringBean
    private IProjectService projectService;
    
    
    
    public ProjectPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        List<Project> listAffichage = projectService.selectAll();
        final IDeleteAction<Project> projectDeleteAction = new ProjectDeleteAction(projectService,listAffichage,this);
        
        final IEditAction<Project> projectEditAction = new ProjectEditAction(this);
        
        final ISortableDataProvider<Project, String> dataProvider =
                new ListDataModel(listAffichage);
        final DataTable<Project, String> build
                = DataTableBuilder.<Project, String>newTable("table")
                .addColumn("Project key", "ProjectKey").addColumn("Name", "Name")
                .addColumn("Description", "Description")
                .withEditDeleteColumn(projectDeleteAction, projectEditAction)
                .displayRows(listAffichage.size()+10).withData(dataProvider).build();
        add(build);
        
    }
    
}
