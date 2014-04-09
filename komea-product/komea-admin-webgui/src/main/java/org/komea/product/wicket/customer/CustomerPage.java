/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.customer;

import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.CustomerCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;

/**
 *
 * @author rgalerme
 */
public final class CustomerPage extends LayoutPage {

    @SpringBean
    private CustomerDao customerService;
    
    public CustomerPage(PageParameters params) {
        super(params);
        
        List<Customer> listAffichage = customerService.selectByCriteria(new CustomerCriteria());
        final IDeleteAction<Customer> deleteAction = new CustomerDeleteAction(listAffichage, customerService);
        final IEditAction<Customer> editAction = new CustomerEditAction(this);
        final ISortableDataProvider<Customer, String> dataProvider = new ListDataModel(listAffichage);
          final DataTable<Customer, String> build =
                DataTableBuilder.<Customer, String> newTable("table").addColumn("Name", "Name")
                        .withEditDeleteColumn(deleteAction, editAction)
                        .displayRows(listAffichage.size()+10).withData(dataProvider).build();
        add(build);
 
        
        
    }
      @Override
    public String getTitle() {
    
    
        return getString("CustomerPage.title");
    }
}
