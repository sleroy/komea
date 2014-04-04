/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;
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
public final class BugZillaPage extends LayoutPage {

    @SpringBean
    private IBugZillaConfigurationService bugZillaService;

    public BugZillaPage(final PageParameters _parameters) {
        super(_parameters);

        List<BugZillaServer> listAffichage = bugZillaService.selectAll();
        final IDeleteAction<BugZillaServer> deleteAction = new BugZillaDeleteAction(listAffichage, bugZillaService);
        final IEditAction<BugZillaServer> editAction = new BugZillaEditAction(this);
        final ISortableDataProvider<BugZillaServer, String> dataProvider = new ListDataModel(listAffichage);
        final DataTable<BugZillaServer, String> build
                = DataTableBuilder.<BugZillaServer, String>newTable("table")
                .addColumn("Address", "Address")
                .addColumn("Login", "Login")
                .withEditDeleteColumn(deleteAction, editAction)
                .displayRows(listAffichage.size() + 10).withData(dataProvider).build();
        add(build);

        add(new AjaxLinkLayout<LayoutPage>("addServer", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new BugZillaEditPage(page.getPageParameters()));
            }
        });
    }

    @Override
    public String getTitle() {

        return getString("BugZillaPage.title");
    }
}
