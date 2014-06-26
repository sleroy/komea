/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.userinterface;



import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBugZillaRebuildHistory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



/**
 * @author rgalerme
 */
public final class BugZillaPage extends StatelessLayoutPage
{


    @SpringBean
    private IBugZillaRebuildHistory bugzillaBuildHistory;
    @SpringBean
    private IBZConfigurationDAO     bugZillaService;



    public BugZillaPage(final PageParameters _parameters) {


        super(_parameters);

        final List<BZServerConfiguration> listAffichage = bugZillaService.selectAll();
        final IDeleteAction<BZServerConfiguration> deleteAction =
                new BugZillaDeleteAction(listAffichage, bugZillaService, this);
        final IEditAction<BZServerConfiguration> editAction = new BugZillaEditAction(this);
        final ISortableDataProvider<BZServerConfiguration, String> dataProvider =
                new ListDataModel(listAffichage);
        final DataTable<BZServerConfiguration, String> build =
                DataTableBuilder.<BZServerConfiguration, String> newTable("table")
                .addColumn(getString("bugzillapage.main.table.column.address"), "Address")
                .addColumn(getString("global.save.form.field.label.login"), "Login")
                .withEditDeleteColumn(deleteAction, editAction)
                .displayRows(listAffichage.size() + 10).withData(dataProvider).build();
        add(build);

        add(new AjaxLinkLayout<StatelessLayoutPage>("addServer", this)
                {


            @Override
            public void onClick(final AjaxRequestTarget art) {


                final StatelessLayoutPage page = getCustom();
                page.setResponsePage(new BugZillaEditPage(page.getPageParameters()));
            }
                });

        add(new AjaxLink("rebuildHistory")
        {


            /*
             * (non-Javadoc)
             * @see org.apache.wicket.Component#isEnabled()
             */
            @Override
            public boolean isEnabled() {


                return !bugzillaBuildHistory.isRunning();
            }


            @Override
            public void onClick(final AjaxRequestTarget art) {


                bugzillaBuildHistory.rebuildHistory();

            }
        });
    }


    @Override
    public String getTitle() {


        return getString("bugzillapage.main.title");
    }
}
