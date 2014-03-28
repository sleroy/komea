/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.kpis;

import java.util.List;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 * @author rgalerme
 */
public class KpiDeleteAction implements IDeleteAction<Kpi> {

    private final IKPIService kpiDao;
    private final List<Kpi> kpiAffichage;

    public KpiDeleteAction(final IKPIService _kpi, List<Kpi> _kpiAffichage) {

        kpiDao = _kpi;
        this.kpiAffichage = _kpiAffichage;
    }

    @Override
    public void delete(final Kpi _kpi) {

        kpiDao.deleteKpi(_kpi);
        this.kpiAffichage.remove(_kpi);
    }

}
