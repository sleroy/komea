/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.alert;



import com.googlecode.wicket.jquery.core.Options;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



/**
 * @author rgalerme
 */
public final class AlertPage extends StatelessLayoutPage
{
    
    
    private static final long serialVersionUID = 64816760503383491L;
    @SpringBean
    private IAlertTypeService alertService;
    
    
    
    public AlertPage(final PageParameters params) {
    
    
        super(params);
        
        final List<KpiAlertType> listAffichage = alertService.selectAll();
        final IDeleteAction<KpiAlertType> deleteAction =
                new AlertDeleteAction(listAffichage, alertService, this);
        final IEditAction<KpiAlertType> editAction = new AlertEditAction(this);
        final ISortableDataProvider<KpiAlertType, String> dataProvider =
                new ListDataModel(listAffichage);
        final DataTable<KpiAlertType, String> build =
                DataTableBuilder.<KpiAlertType, String> newTable("table")
                        .addColumn(getString("alertpage.globale.table.column.key"), "KpiAlertKey")
                        .addColumn(getString("alertpage.globale.table.column.name"), "Name")
                        .addColumn(getString("alertpage.save.form.field.label.enabled"), "Enabled")
                        .withEditDeleteColumn(deleteAction, editAction)
                        .displayRows(listAffichage.size() + 10).withData(dataProvider).build();
        add(build);
        setStatelessHint(true);
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("alertpage.globale.title");
    }
}
