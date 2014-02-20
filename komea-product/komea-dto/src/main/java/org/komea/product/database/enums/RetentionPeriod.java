
package org.komea.product.database.enums;



public enum RetentionPeriod {
    
    NEVER(""),
    ONE_DAY(".win:time(1 day)"),
    ONE_HOUR(".win:time(1 hour)"),
    ONE_MONTH(".win:time(1 month)"),
    ONE_WEEK(".win:time(1 week)"),
    ONE_YEAR(".win:time(1 year)"),
    SIX_HOURS(".win:time(6 hour)"),
    THREE_DAYS(".win:time(3 day)"),
    THREE_MONTHS(".win:time(3 month)"),
    TWO_WEEKS(".win:time(2 week)")
    
    ;
    
    
    private final String valueName;
    
    
    
    private RetentionPeriod(final String _valueName) {
    
    
        valueName = _valueName;
        
    }
    
    
    public String getWindow() {
    
    
        return valueName;
    }
    
    
}
