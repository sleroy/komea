
package org.komea.experimental.kpis;


import java.util.Date;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.komea.event.query.impl.EventQueryManager;
import org.komea.experimental.model.AnalyzedApplication;
import org.komea.experimental.prediction.Release;
import org.komea.experimental.prediction.ReleaseCodeChunk;
import org.komea.orientdb.session.document.IODocument;

public class CodeChunkPerRelease
{
    
    private final EventQueryManager   queries;
    private final AnalyzedApplication application;
    
    public CodeChunkPerRelease(final EventQueryManager queries, final AnalyzedApplication application) {
    
        super();
        this.queries = queries;
        this.application = application;
    }
    
    public ReleaseCodeChunk chunk(final String release) {
    
        Date releaseDate = findReleaseDate(release);
        if (releaseDate != null) {
            Release previous = findPreviousRelease(releaseDate);
            int chunk = chunk(previous.getDate(), releaseDate);
            return new ReleaseCodeChunk(new Release(release, releaseDate), previous, chunk);
        }
        return null;
    }
    private int chunk(final Date previous, final Date releaseDate) {
    
        String query = "SELECT SUM(total_updated_lines) FROM file_update WHERE " + clause(this.application) + " AND date < '"
                + format(releaseDate) + "' AND  date > '" + format(previous) + "'" + pathClause(this.application);
        Iterator<IODocument> tags = this.queries.query(query);
        if (tags.hasNext()) {
            IODocument tag = tags.next();
            return tag.getField("SUM", Integer.class);
        }
        return 0;
    }
    
    public String pathClause(final AnalyzedApplication app) {
    
        StringBuilder sb = new StringBuilder();
        for (String path : app.getSourcePaths()) {
            sb.append(" AND file LIKE ").append("'").append(path).append("%' ");
        }
        return sb.toString();
    }
    public String clause(final AnalyzedApplication app) {
    
        StringBuilder sb = new StringBuilder();
        sb.append("project=").append("'").append(app.getName()).append("'");
        sb.append(" AND ").append("branch=").append("'").append(app.getBranch()).append("' ");
        return sb.toString();
    }
    
    private Release findPreviousRelease(final Date date) {
    
        String query = "SELECT * FROM tag WHERE " + clause(this.application) + "AND date < '" + format(date)
                + "' ORDER BY date DESC LIMIT 1";
        Iterator<IODocument> tags = this.queries.query(query);
        if (tags.hasNext()) {
            IODocument tag = tags.next();
            Date rdate = tag.getField("date", Date.class);
            return new Release(tag.getField("name", String.class), rdate);
        }
        return null;
    }
    
    private String format(final Date date) {
    
        DateTime dtime = new DateTime(date);
        
        return dtime.toString("yyyy-MM-dd");
    }
    
    private Date findReleaseDate(final String release) {
    
        Iterator<IODocument> tags = this.queries
                .query("SELECT * FROM tag WHERE " + clause(this.application) + "AND name='" + release + "'");
        if (tags.hasNext()) {
            IODocument tag = tags.next();
            Date date = tag.getField("date", Date.class);
            return date;
        }
        return null;
    }
}
