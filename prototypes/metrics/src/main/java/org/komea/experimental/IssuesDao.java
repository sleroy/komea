
package org.komea.experimental;


import static org.komea.experimental.QueriesUtils.format;

import java.util.Date;
import java.util.Iterator;

import org.komea.event.query.impl.EventQueryManager;
import org.komea.experimental.model.AnalyzedApplication;
import org.komea.experimental.prediction.Release;
import org.komea.orientdb.session.document.IODocument;

public class IssuesDao extends ApplicationDAO
{

 
    
    public IssuesDao(final IReleaseTagConvertor tagConvertor, final EventQueryManager queries, final AnalyzedApplication application) {
    
        super(tagConvertor, queries, application);
    }

    public int countIssues(final Release release, final String type) {
    
        String query = "SELECT COUNT(*) FROM issue_new WHERE issuetype.name='"+type+"' AND versions.name CONTAINS '" + release.geReleaseName() + "'";
        Iterator<IODocument> tags = this.queries.query(query);
        if (tags.hasNext()) {
            IODocument tag = tags.next();
            return tag.getField("COUNT", Integer.class);
        }
        return 0;
    }
    
    public int countUnknownVersionIssues(final String type, final Date since, final Date until) {
    
        String query = "SELECT COUNT(*) FROM issue_new WHERE issuetype.name='"+type+"' AND (status.name ='Open' OR resolution.name='Fixed') AND versions.size()=0 AND date BETWEEN '" + format(since) + "' AND '"
                + format(until) + "'";
        Iterator<IODocument> tags = this.queries.query(query);
        if (tags.hasNext()) {
            IODocument tag = tags.next();
            return tag.getField("COUNT", Integer.class);
        }
        return 0;
    }
}
