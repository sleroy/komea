/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.userinterface;

import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.testlink.api.ITestLinkServerService;
import org.komea.product.plugins.testlink.core.TestLinkServer;
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
public final class TestLinkPage extends LayoutPage {

     @SpringBean
    private ITestLinkServerService TestLinkService;
    
    public TestLinkPage(PageParameters params) {
        super(params);
             List<TestLinkServer> listAffichage = TestLinkService.selectAll();
        final IDeleteAction<TestLinkServer> deleteAction = new TestLinkDeleteAction(listAffichage, TestLinkService);
        final IEditAction<TestLinkServer> editAction = new TestLinkEditAction(this);
        final ISortableDataProvider<TestLinkServer, String> dataProvider = new ListDataModel(listAffichage);
        final DataTable<TestLinkServer, String> build
                = DataTableBuilder.<TestLinkServer, String>newTable("table")
                .addColumn("Address", "Address")
                .withEditDeleteColumn(deleteAction, editAction)
                .displayRows(listAffichage.size() + 10).withData(dataProvider).build();
        add(build);

        add(new AjaxLinkLayout<LayoutPage>("addServer", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new TestLinkEditPage(page.getPageParameters()));
            }
        });
    }
        @Override
    public String getTitle() {

        return getString("TestLinkPage.title");
    }
}
