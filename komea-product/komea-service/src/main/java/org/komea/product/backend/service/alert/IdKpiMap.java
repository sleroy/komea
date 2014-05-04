/**
 * 
 */

package org.komea.product.backend.service.alert;



import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;

import com.google.common.collect.Sets;



/**
 * @author sleroy
 */
public class IdKpiMap
{
    
    
    private final Map<Integer, Kpi> idKpiMap = new HashMap<Integer, Kpi>();
    
    
    
    /**
     * Fill the map and returns the kpi keys.
     * 
     * @param alertTypes
     * @param _kpiService
     * @return
     */
    public Set<String> fillIdKpi(final List<KpiAlertType> alertTypes, final IKPIService _kpiService) {
    
    
        final Set<String> kpiKeys = Sets.newHashSet();
        for (final KpiAlertType alertType : alertTypes) {
            final Kpi kpi = _kpiService.selectByPrimaryKey(alertType.getIdKpi());
            idKpiMap.put(kpi.getId(), kpi);
            kpiKeys.add(kpi.getKpiKey());
        }
        return kpiKeys;
    }
    
    
    /**
     * Returns the kpi from an id.
     * 
     * @param _idKpi
     *            the id of the kpi
     * @return the kpi.
     */
    public Kpi get(final Integer _idKpi) {
    
    
        return idKpiMap.get(_idKpi);
    }
    
    
    /**
     * Returns the list of values.
     */
    public Collection<Kpi> values() {
    
    
        return Collections.unmodifiableCollection(idKpiMap.values());
    }
    
}
