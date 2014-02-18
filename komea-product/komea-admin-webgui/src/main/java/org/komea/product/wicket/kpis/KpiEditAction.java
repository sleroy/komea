/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.kpis;

import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class KpiEditAction implements IEditAction<Kpi> {

    private KpiPage pagekpi;

    public KpiEditAction(final KpiPage _pagekpi) {
        this.pagekpi = _pagekpi;
    }

    @Override
    public void selected(Kpi _kpi) {
        final Kpi kpi = new Kpi();
        kpi.setId(_kpi.getId());
        kpi.setName(_kpi.getName());
        kpi.setDescription(_kpi.getDescription());
        kpi.setCronExpression(_kpi.getCronExpression());
        this.pagekpi.setResponsePage(new KpiEditPage(this.pagekpi.getPageParameters(),_kpi));
    }

}
