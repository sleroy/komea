
package org.komea.product.service.dto;


import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class HistoricalMeasureRequest {
    
    private HistoryStringKeyList historyKeyList;
    private LimitCriteria        limit;
    
    public HistoricalMeasureRequest() {
    
        historyKeyList = new HistoryStringKeyList();
        limit = LimitCriteria.createDefaultLimitCriteria();
    }
    
    public HistoricalMeasureRequest(final HistoryStringKeyList _historyKeyList, final LimitCriteria _limit) {
    
        super();
        historyKeyList = _historyKeyList;
        limit = _limit;
    }
    
    public HistoryStringKeyList getHistoryKeyList() {
    
        return historyKeyList;
    }
    
    public LimitCriteria getLimit() {
    
        return limit;
    }
    
    public void setHistoryKeyList(final HistoryStringKeyList _historyKeyList) {
    
        historyKeyList = _historyKeyList;
    }
    
    public void setLimit(final LimitCriteria _limit) {
    
        limit = _limit;
    }
    
}
