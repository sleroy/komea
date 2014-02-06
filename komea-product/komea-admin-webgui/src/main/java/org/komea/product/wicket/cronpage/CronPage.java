
package org.komea.product.wicket.cronpage;



import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.DataTableBuilder;
import org.ocpsoft.prettytime.PrettyTime;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class CronPage extends LayoutPage
{
    
    
    @SpringBean
    private ICronRegistryService cronService;
    
    
    
    public CronPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        
        final AbstractColumn<CronDetails, String> abstractColumn =
                new AbstractColumn<CronDetails, String>(Model.of("Next execution time"))
                {
                    
                    
                    @Override
                    public void populateItem(
                            final Item<ICellPopulator<CronDetails>> _cellItem,
                            final String _componentId,
                            final IModel<CronDetails> _rowModel) {
                    
                    
                        _cellItem.add(new Label(_componentId, Model.of(new PrettyTime()
                                .format(_rowModel.getObject().getNextTime()))));
                        
                    }
                    
                    
                };
        final DataTable dataTable =
                DataTableBuilder.<CronDetails, String> newTable("table")
                        .addColumn("Cron task", "cronName")
                        .addColumn("CRON Expression", "cronExpression").addColumn(abstractColumn)
                        .displayRows(40).withListData(cronService.getCronTasks()).build();
        add(dataTable);
    }
}
