/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.userinterface;



import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



/**
 * @author rgalerme
 */
public final class MantisPage extends LayoutPage
{
    
    
    @SpringBean
    private IMantisConfigurationDAO bugZillaService;
    
    
    
    public MantisPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        final List<MantisServerConfiguration> listAffichage = bugZillaService.selectAll();
        final IDeleteAction<MantisServerConfiguration> deleteAction =
                new MantisDeleteAction(listAffichage, bugZillaService,this);
        final IEditAction<MantisServerConfiguration> editAction = new MantisEditAction(this);
        final ISortableDataProvider<MantisServerConfiguration, String> dataProvider =
                new ListDataModel(listAffichage);
        final DataTable<MantisServerConfiguration, String> build =
                DataTableBuilder.<MantisServerConfiguration, String> newTable("table")
                        .addColumn(getString("bugzillapage.main.table.column.address"), "Address").addColumn(getString("global.save.form.field.label.login"), "Login")
                        .withEditDeleteColumn(deleteAction, editAction)
                        .displayRows(listAffichage.size() + 10).withData(dataProvider).build();
        add(build);
        
        add(new AjaxLinkLayout<LayoutPage>("addServer", this)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                final LayoutPage page = getCustom();
                page.setResponsePage(new MantisEditPage(page.getPageParameters()));
            }
        });
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("bugzillapage.main.title");
    }
}
