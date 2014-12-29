
package org.komea.experimental.prediction;


import java.util.Date;

public class Release
{
    
    private final String tagName;
    private final String releaseName;
    
    private final Date   date;
    
    public Release(final String tag,final String release, final Date date) {
    
        super();
        this.tagName = tag;
        this.releaseName = release;
        this.date = date;
    }
    
    
    public String geReleaseName() {
    
        return this.releaseName;
    }
    
    public String getTagName() {
    
        return this.tagName;
    }
    
    public Date getDate() {
    
        return this.date;
    }
    @Override
    public String toString() {
    
        return this.releaseName;
    }
}
