package org.komea.product.wicket.cronpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
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
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.persongroup.department.DepartmentEditAction;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.widget.api.IAjaxEditAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.bootstrap.SexyLabel;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.ocpsoft.prettytime.PrettyTime;
import org.quartz.Trigger.TriggerState;

/**
 * Person admin page
 *
 * @author sleroy
 */
public class CronPage extends LayoutPage {
    
    @SpringBean
    private ICronRegistryService cronService;
    private final DataTable dataTable;
    
    public CronPage(final PageParameters _parameters) {
        
        super(_parameters);
        
        final AbstractColumn<CronDetails, String> nextExecutionTime
                = new AbstractColumn<CronDetails, String>(Model.of("Next execution time")) {
                    
                    @Override
                    public void populateItem(
                            final Item<ICellPopulator<CronDetails>> _cellItem,
                            final String _componentId,
                            final IModel<CronDetails> _rowModel) {
                                
                                _cellItem.add(new Label(_componentId, Model.of(new PrettyTime()
                                                        .format(_rowModel.getObject().getNextTime()))));
                                
                            }
                            
                };
        final AbstractColumn<CronDetails, String> statusCol
                = new AbstractColumn<CronDetails, String>(Model.of("Cron status")) {
                    
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
        
        CronDialog cd = new CronDialog("dialogCron", getString("cronpage.form.dialog.cronfield.title")) {
            
            @Override
            protected void onSubmit(AjaxRequestTarget art) {
                CronDetails cronDetails = getCronDetails();
                NameGeneric cronValue = getCronValue();
                cronDetails.setCronExpression(cronValue.getName());
                art.add(dataTable);
                cronService.updateCronFrequency(cronDetails.getCronName(), cronValue.getName());
                
            }
        };
        add(cd);

        final IAjaxEditAction<CronDetails> cronLaunchAction = new CronLaunchAction(cronService);
        CustomColumnLaunch cLaunch = new CustomColumnLaunch(Model.of(getString("cronpage.main.table.cronlaunch")), cronLaunchAction);
        final IAjaxEditAction<CronDetails> cronEditAction = new CronEditAction(cd);
        CustomColumnExpression cexp = new CustomColumnExpression(Model.of(getString("cronpage.main.table.cronexp")), cronEditAction);
        
       dataTable= DataTableBuilder.<CronDetails, String>newTable("table").addColumn("Cron task", "cronName")
                .addColumn(cexp)
                .addColumn(nextExecutionTime)
                .addColumn(statusCol).displayRows(40)
                .addColumn(cLaunch)
                .withListData(cronService.getCronTasks()).build();
        
        dataTable.setOutputMarkupId(true);
        dataTable.setOutputMarkupPlaceholderTag(true);
        ((CronLaunchAction)cronLaunchAction).setComposant(dataTable);
        
        add(dataTable);
        
    }
    
    @Override
    public String getTitle() {
        return getString("administration.title.crons");
    }
    
}
