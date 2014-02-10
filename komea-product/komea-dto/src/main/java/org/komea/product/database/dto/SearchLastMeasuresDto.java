
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.List;

import org.komea.product.service.dto.KpiKey;

import com.google.common.collect.Lists;

public class SearchLastMeasuresDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<KpiKey>      kpiKeys;
    
    public SearchLastMeasuresDto(final List<KpiKey> kpiKeys) {
    
        this.kpiKeys = kpiKeys;
    }
    
    public SearchLastMeasuresDto() {
    
        kpiKeys = Lists.newArrayList();
    }
    
    public List<KpiKey> getKpiKeys() {
    
        return kpiKeys;
    }
    
    public void setKpiKeys(final List<KpiKey> kpiKeys) {
    
        this.kpiKeys = kpiKeys;
    }
    
}
