
package org.komea.experimental;


import static org.komea.experimental.QueriesUtils.format;

import java.util.Date;
import java.util.Iterator;

import org.komea.event.query.impl.EventQueryManager;
import org.komea.experimental.model.AnalyzedApplication;
import org.komea.orientdb.session.document.IODocument;

public class CommitsDao extends ApplicationDAO
{
    private final String[] extensions={"cpp"};
    
    public CommitsDao(final IReleaseTagConvertor tagConvertor, final EventQueryManager queries, final AnalyzedApplication application) {
    
        super(tagConvertor, queries, application);
    }

    public int countNumberOfModifiedLines(final Date previous, final Date releaseDate) {
    
        String query = "SELECT SUM(total_updated_lines) AS chunk FROM file_update WHERE " + applicationClause() +" AND merge ='false'"+ " AND  date BETWEEN '" + format(previous) + "' AND '" + format(releaseDate) + "'" + pathClause(this.application);
        Iterator<IODocument> tags = this.queries.query(query);
        if (tags.hasNext()) {
            IODocument tag = tags.next();
            return tag.getField("chunk", Integer.class);
        }
        return 0;
    }
    
    private String pathClause(final AnalyzedApplication app) {
    
        StringBuilder sb = new StringBuilder();
        sb.append(" AND file = old_file " );
        for (int i = 0; i < this.extensions.length; i++) {
            sb.append(" AND file LIKE '%.").append(this.extensions[i]).append("'");
        }
       
        sb.append(" AND file NOT LIKE '/dev/null'");
        
        for (String path : app.getExcludedPaths()) {
            sb.append(" AND file NOT LIKE ").append("'").append(path).append("%' ");
        }
        return sb.toString();
    }
    
}
