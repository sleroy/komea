
package org.komea.product.service.dto;


import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class ManyHistoricalMeasureRequest {
    
    private HistoryStringKeyList historyKeyList;
    private LimitCriteria        limit;
    
    public ManyHistoricalMeasureRequest() {
    
        historyKeyList = new HistoryStringKeyList();
        limit = LimitCriteria.createDefaultLimitCriteria();
    }
    
    public ManyHistoricalMeasureRequest(final HistoryStringKeyList _historyKeyList, final LimitCriteria _limit) {
    
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
