/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.scm.userinterface;

import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;

/**
 *
 * @author rgalerme
 */
public final class ScmPage extends LayoutPage {

    @SpringBean
    private IScmRepositoryService scmService;

    public ScmPage(PageParameters _parameters) {
        super(_parameters);
        List<ScmRepositoryDefinition> allRepositories = scmService.getAllRepositories();

        final IDeleteAction<ScmRepositoryDefinition> deleteAction = new ScmDeleteAction(scmService, allRepositories, this);
        final IEditAction<ScmRepositoryDefinition> editAction = new ScmEditAction(this);
        final ISortableDataProvider<ScmRepositoryDefinition, String> dataProvider = new ListDataModel(allRepositories);

        final DataTable<ScmRepositoryDefinition, String> build
                = DataTableBuilder.<ScmRepositoryDefinition, String>newTable("table")
                .addColumn(getString("scm.main.key"), "Key")
//                .addColumn(getString("scm.main.type"), "Login")
                .addColumn(getString("scm.main.reponame"), "RepoName")
                .addColumn(getString("scm.main.url"), "url")
                .withEditDeleteColumn(deleteAction, editAction)
                .displayRows(allRepositories.size() + 10).withData(dataProvider).build();
        add(build);
                add(new AjaxLinkLayout<LayoutPage>("addPlugin", this)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                final LayoutPage page = getCustom();
                page.setResponsePage(new ScmEditPage(page.getPageParameters()));
            }
        });

    }
    
        @Override
    public String getTitle() {
    
    
        return getString("scm.main.title");
    }

}
