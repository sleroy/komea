
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
import org.komea.product.wicket.widget.bootstrap.SexyLabel;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.ocpsoft.prettytime.PrettyTime;
import org.quartz.Trigger.TriggerState;



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
        
        accordion.setActiveTab(ADMIN_INDEX);
        final AbstractColumn<CronDetails, String> nextExecutionTime =
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
        final AbstractColumn<CronDetails, String> statusCol =
                new AbstractColumn<CronDetails, String>(Model.of("Cron status"))
                {
                    
                    
                    @Override
                    public void populateItem(
                            final Item<ICellPopulator<CronDetails>> _cellItem,
                            final String _componentId,
                            final IModel<CronDetails> _rowModel) {
                    
                    
                        final TriggerState triggerState = _rowModel.getObject().getTriggerState();
                        switch (triggerState) {
                            case BLOCKED:
                                _cellItem.add(SexyLabel.newDangerLabel(_componentId, "Blocked"));
                                break;
                            
                            case ERROR:
                                _cellItem.add(SexyLabel.newDangerLabel(_componentId, "Danger"));
                                break;
                            case PAUSED:
                                _cellItem.add(SexyLabel.newWarningLabel(_componentId, "Warning"));
                                break;
                            case NORMAL:
                            case COMPLETE:
                                _cellItem.add(SexyLabel.newSuccessLabel(_componentId, "Normal"));
                                break;
                            case NONE:
                                _cellItem.add(SexyLabel.newInfoLabel(_componentId, "Unknown"));
                                break;
                        }
                        
                    }
                    
                    
                };
        final DataTable dataTable =
                DataTableBuilder.<CronDetails, String> newTable("table")
                        .addColumn("Cron task", "cronName")
                        .addColumn("CRON Expression", "cronExpression")
                        .addColumn(nextExecutionTime).addColumn(statusCol).displayRows(40)
                        .withListData(cronService.getCronTasks()).build();
        add(dataTable);
    }
       @Override
    public String getTitle() {
        return getString("administration.title.crons");
    }
    
}
