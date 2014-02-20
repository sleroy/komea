/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.kpis;



import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.widget.api.IDeleteAction;



/**
 * @author rgalerme
 */
public class KpiDeleteAction implements IDeleteAction<Kpi>
{
    
    
    private final IKPIService kpiDao;
    
    
    
    public KpiDeleteAction(final IKPIService _kpi) {
    
    
        kpiDao = _kpi;
    }
    
    
    @Override
    public void delete(final Kpi _kpi) {
    
    
        kpiDao.deleteByPrimaryKey(_kpi.getId());
    }
    
}
