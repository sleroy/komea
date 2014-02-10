
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.komea.product.service.dto.KpiKey;

import com.google.common.collect.Lists;

public class SearchHistoricalMeasuresDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<KpiKey>      kpiKeys;
    private Date              start;
    private Date              end;
    private Integer           number;
    
    public SearchHistoricalMeasuresDto(final List<KpiKey> kpiKeys, final Date start, final Date end, final Integer number) {
    
        this.kpiKeys = kpiKeys;
        this.start = start;
        this.end = end;
        this.number = number;
    }
    
    public SearchHistoricalMeasuresDto() {
    
        kpiKeys = Lists.newArrayList();
    }
    
    public List<KpiKey> getKpiKeys() {
    
        return kpiKeys;
    }
    
    public void setKpiKeys(final List<KpiKey> kpiKeys) {
    
        this.kpiKeys = kpiKeys;
    }
    
    public Date getStart() {
    
        return start;
    }
    
    public void setStart(final Date start) {
    
        this.start = start;
    }
    
    public Date getEnd() {
    
        return end;
    }
    
    public void setEnd(final Date end) {
    
        this.end = end;
    }
    
    public Integer getNumber() {
    
        return number;
    }
    
    public void setNumber(final Integer number) {
    
        this.number = number;
    }
    
}
