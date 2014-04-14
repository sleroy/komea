/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.kpis;

import static com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog.LBL_OK;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 * @author rgalerme
 */
public class KpiDeleteAction implements IDeleteAction<Kpi> {

    private final IKPIService kpiDao;
    private final List<Kpi> kpiAffichage;
    private final LayoutPage page;
    private MessageDialog dialog;
    private Kpi obKpi;

    public KpiDeleteAction(final IKPIService _kpi, List<Kpi> _kpiAffichage, LayoutPage _page) {
        this.page = _page;
        kpiDao = _kpi;
        this.kpiAffichage = _kpiAffichage;

        dialog = new MessageDialog("dialogdelete", "Warning", "are you sure to remove this element ?",
                DialogButtons.YES_NO, DialogIcon.WARN) {

                    @Override
                    public void onClose(AjaxRequestTarget art, DialogButton button) {
                        if (button != null && button.toString().equals(LBL_YES)) {
                            delete();
                            art.add(page);
                        }
                    }
                };
        page.add(dialog);

    }

    @Override
    public void delete(Kpi _object, AjaxRequestTarget _target) {
        this.obKpi = _object;
        dialog.open(_target);
    }

    private void delete() {
        kpiDao.deleteKpi(obKpi);
        kpiAffichage.remove(obKpi);
    }

}
