/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.kpis;

import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;

/**
 *
 * @author rgalerme
 */
public class KpiDataModel extends SortableDataProvider<Kpi, String> {

    private final IKPIService kpiService;

    public KpiDataModel(IKPIService kpiService) {
        this.kpiService = kpiService;
    }

    @Override
    public Iterator<? extends Kpi> iterator(long l, long l1) {
        return this.kpiService.listAllKpis().subList((int) l, (int) l1).iterator();
    }

    @Override
    public long size() {
        return this.kpiService.listAllKpis().size();
    }

    @Override
    public IModel<Kpi> model(Kpi t) {
        return Model.of(t);
    }

}
