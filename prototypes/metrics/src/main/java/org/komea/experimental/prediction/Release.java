
package org.komea.experimental.prediction;


import java.util.Date;

public class Release
{
    
    private final String name;
    private final Date   date;
    
    public Release(final String name, final Date date) {
    
        super();
        this.name = name;
        this.date = date;
    }
    
    public String getName() {
    
        return this.name;
    }
    
    public Date getDate() {
    
        return this.date;
    }
    @Override
    public String toString() {
    
        return this.name;
    }
}
