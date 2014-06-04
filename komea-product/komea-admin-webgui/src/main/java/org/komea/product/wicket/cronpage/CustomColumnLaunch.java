/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.cronpage;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.wicket.widget.api.IAjaxEditAction;

/**
 *
 * @author rgalerme
 */
public class CustomColumnLaunch<S> extends AbstractColumn<CronDetails, S> {

    private final IAjaxEditAction<CronDetails> editAction;

    public CustomColumnLaunch(IModel<String> displayModel, final IAjaxEditAction<CronDetails> _editAction) {
        super(displayModel);
        this.editAction = _editAction;
    }

    @Override
    public void populateItem(Item<ICellPopulator<CronDetails>> item, String string, IModel<CronDetails> imodel) {    
        item.add(new CustomPanelLaunch(string, imodel, editAction));
    }

}