
package org.komea.experimental.metrics;


import org.joda.time.DateTime;
import org.komea.experimental.IssuesDao;
import org.komea.experimental.prediction.Release;

public class NbIssuesPerRelease
{
    
    private final IssuesDao dao;
    private  boolean         strict = false;

    
    public NbIssuesPerRelease(final IssuesDao dao) {
    
        super();
        this.dao = dao;
    }
    
    
    public void setStrict(final boolean strict) {
    
        this.strict = strict;
    }
    
    public int countNbIssues(final Release release) {
    
        int count = this.dao.countIssues(release, "Bug");
        
        if (!this.strict) {
            Release nextRelease = this.dao.findNextRelease(release);
            if (nextRelease != null) {
                count += this.dao.countUnknownVersionIssues("Bug", release.getDate(), nextRelease.getDate());
            } else {
                count += this.dao.countUnknownVersionIssues("Bug", release.getDate(), new DateTime().toDate());
            }
        }
        return count;
    }
    
}
