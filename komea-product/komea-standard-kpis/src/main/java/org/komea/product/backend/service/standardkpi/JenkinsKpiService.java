/**
 *
 */

package org.komea.product.backend.service.standardkpi;



import org.komea.product.backend.api.standardkpi.IStandardKpiService;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@ProviderPlugin(
    eventTypes = {},
    icon = "provider",
    name = "KPI Provider plugin",
    type = ProviderType.NEWS,
    url = "/provider")
@Transactional
public class JenkinsKpiService implements IStandardKpiService
{
    
    
    @Autowired
    private IKPIService kpiService;
    
    
    
    /**
     *
     */
    public JenkinsKpiService() {
    
    
        super();
        
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.standardkpi.IStandardKpiService#saveOrUpdate(org.komea.product.database.model.Kpi)
     */
    @Override
    public void saveOrUpdate(final Kpi _kpi) {
    
    
        if (kpiService.findKPI(KpiKey.ofKpi(_kpi)) != null) { return; }
        kpiService.saveOrUpdate(_kpi);
        
    }
    
    
}
