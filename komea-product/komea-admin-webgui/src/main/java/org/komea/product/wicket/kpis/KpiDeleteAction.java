/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.kpis;

import java.util.List;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
class KpiDeleteAction implements IDeleteAction<Kpi> {

    private KpiDao kpiDao;

    public KpiDeleteAction(KpiDao _kpiDao) {
        this.kpiDao = _kpiDao;
    }

    @Override
    public void delete(Kpi _kpi) {
        this.kpiDao.deleteByPrimaryKey(_kpi.getId());
    }
    
}
