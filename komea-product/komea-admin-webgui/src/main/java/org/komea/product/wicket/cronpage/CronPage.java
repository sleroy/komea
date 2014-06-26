package org.komea.product.wicket.cronpage;

import java.util.List;
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
import org.komea.product.wicket.StatelessLayoutPage;
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
public class CronPage extends StatelessLayoutPage {

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

        final AbstractColumn<CronDetails, String> lastExecutionTime
                = new AbstractColumn<CronDetails, String>(Model.of("Last execution time")) {

                    @Override
                    public void populateItem(
                            final Item<ICellPopulator<CronDetails>> _cellItem,
                            final String _componentId,
                            final IModel<CronDetails> _rowModel) {
                         Model<String> of;
                                if (_rowModel.getObject().getLastTime() != null) {
                                    of = Model.of(new PrettyTime().format(_rowModel.getObject().getLastTime()));

                                } else {
                                    of = Model.of("No last execution");
                                }

                                _cellItem.add(new Label(_componentId, of));

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
                                        _cellItem.add(SexyLabel.newDangerLabel(_componentId, getString("cronpage.status.blocker")));
                                        break;

                                    case ERROR:
                                        _cellItem.add(SexyLabel.newDangerLabel(_componentId, getString("cronpage.status.error")));
                                        break;
                                    case PAUSED:
                                        _cellItem.add(SexyLabel.newWarningLabel(_componentId, getString("cronpage.status.paused")));
                                        break;
                                    case NORMAL:
                                        _cellItem.add(SexyLabel.newSuccessLabel(_componentId, getString("cronpage.status.complete")));
                                        break;
                                    case COMPLETE:
                                        _cellItem.add(SexyLabel.newSuccessLabel(_componentId, getString("cronpage.status.normal")));
                                        break;
                                    case NONE:
                                        _cellItem.add(SexyLabel.newInfoLabel(_componentId, getString("cronpage.status.none")));
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
        List<CronDetails> cronTasks = cronService.getCronTasks();
        final IAjaxEditAction<CronDetails> cronLaunchAction = new CronLaunchAction(cronService);
        final IAjaxEditAction<CronDetails> cronActiveAction = new CronEnableAction(cronService, cronTasks);
        final IAjaxEditAction<CronDetails> cronDisalbeAction = new CronDisableAction(cronService, cronTasks);
        CustomColumnLaunch cLaunch = new CustomColumnLaunch(Model.of(getString("cronpage.main.table.cronlaunch")), cronLaunchAction, cronActiveAction, cronDisalbeAction);
        final IAjaxEditAction<CronDetails> cronEditAction = new CronEditAction(cd);
        CustomColumnExpression cexp = new CustomColumnExpression(Model.of(getString("cronpage.main.table.cronexp")), cronEditAction);

        dataTable = DataTableBuilder.<CronDetails, String>newTable("table").addColumn("Cron task", "cronName")
                .addColumn(cexp)
                .addColumn(lastExecutionTime)
                .addColumn(nextExecutionTime)
                .addColumn(statusCol).displayRows(40)
                .addColumn(cLaunch)
                .withListData(cronTasks).build();

        dataTable.setOutputMarkupId(true);
        dataTable.setOutputMarkupPlaceholderTag(true);
        ((CronLaunchAction) cronLaunchAction).setComposant(dataTable);
        ((CronEnableAction) cronActiveAction).setComposant(dataTable);
        ((CronDisableAction) cronDisalbeAction).setComposant(dataTable);

        add(dataTable);

    }

    @Override
    public String getTitle() {
        return getString("administration.title.crons");
    }

}
