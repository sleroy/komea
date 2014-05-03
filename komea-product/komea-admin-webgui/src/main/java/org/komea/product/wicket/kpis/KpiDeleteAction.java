/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.kpis;

import java.util.List;

import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

/**
 * @author rgalerme
 */
public class KpiDeleteAction extends AbstractDeleteAction<Kpi> {

    private final IKPIService kpiDao;
    private final List<Kpi> kpiAffichage;

    private MessageDialog dialog;

    public KpiDeleteAction(IKPIService kpiDao, List<Kpi> kpiAffichage, LayoutPage _page) {
        super(_page, "dialogdelete");
        this.kpiDao = kpiDao;
        this.kpiAffichage = kpiAffichage;

    }


    @Override
    public void deleteAction() {
        kpiDao.deleteKpi(getObject());
        kpiAffichage.remove(getObject());
    }

}
