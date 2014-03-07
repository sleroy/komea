/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.kpis;

import java.util.ArrayList;
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
        List<Kpi> listAllKpis = this.kpiService.listAllKpis();
        List<Kpi> listKpisResult = new ArrayList<Kpi>();
        for (Kpi kpi : listAllKpis) {
           if(!kpi.isGlobal()) 
           {
           listKpisResult.add(kpi);
           }
        }
        return listKpisResult.subList((int) l, (int) l1).iterator();
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
