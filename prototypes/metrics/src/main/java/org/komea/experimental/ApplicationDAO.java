
package org.komea.experimental;


import static org.komea.experimental.QueriesUtils.format;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.komea.event.query.impl.EventQueryManager;
import org.komea.experimental.model.AnalyzedApplication;
import org.komea.experimental.prediction.Release;
import org.komea.orientdb.session.document.IODocument;

import com.google.common.collect.Lists;

public class ApplicationDAO
{
    
    protected IReleaseTagConvertor      convertor;
    private IReleaseFilter              filter;
    
    protected final EventQueryManager   queries;
    protected final AnalyzedApplication application;
    
    
    public ApplicationDAO(final IReleaseTagConvertor tagConvertor, final EventQueryManager queries, final AnalyzedApplication application) {
    
        super();
        this.queries = queries;
        this.application = application;
        this.convertor = tagConvertor;
        this.filter = new IReleaseFilter()
        {
            
            @Override
            public String getReleaseExclusionClause() {
            
               return "";
            }
        };
    }
    
    
    public void setFilter(final IReleaseFilter filter) {
    
        this.filter = filter;
    }
    
    public EventQueryManager getQueries() {
    
        return this.queries;
    }
    
    public AnalyzedApplication getApplication() {
    
        return this.application;
    }
    
    public List<Release> findAllReleases() {
    
        List<Release> releases = Lists.newArrayList();
        String query = "SELECT * FROM tag WHERE " + applicationClause() +" "+this.filter.getReleaseExclusionClause() + " ORDER BY date DESC";
        
        Iterator<IODocument> tags = this.queries.query(query);
        while (tags.hasNext()) {
            
            IODocument tag = tags.next();
            Date rdate = tag.getField("date", Date.class);
            String tagName = tag.getField("name", String.class);
            Release release = new Release(tagName, this.convertor.toRelease(tagName), rdate);
            releases.add(release);
        }
        
        return releases;
    }
    
    public Release findRelease(final String name) {
    
        String tag = this.convertor.toTag(name);
        Date releaseDate = findReleaseDate(tag);
        if (releaseDate != null) {
            return new Release(tag, name, releaseDate);
        }
        return null;
    }
    
    public Release findPreviousRelease(final Release release) {
    
        return findPreviousRelease(release.getDate());
    }
    
    public Release findPreviousRelease(final Date date) {
        DateTime time = new DateTime(date);
        DateTime bound = time.minusDays(1);
        String query = "SELECT date,name FROM tag WHERE " + applicationClause()  +" "+this.filter.getReleaseExclusionClause() + "AND date < '" + format(bound.toDate())
                + "' ORDER BY date DESC LIMIT 1";
        return buildReleaseFormQuery(query);
    }
    
    private Release buildReleaseFormQuery(final String query) {
    
        Iterator<IODocument> tags = this.queries.query(query);
        if (tags.hasNext()) {
            IODocument tag = tags.next();
            Date rdate = tag.getField("date", Date.class);
            String tagName = tag.getField("name", String.class);
            return new Release(tagName, this.convertor.toRelease(tagName), rdate);
        }
        return null;
    }
    
    public Release findNextRelease(final Release release) {
    
        return findNextRelease(release.getDate());
    }
    
    public Release findNextRelease(final Date date) {
        DateTime time = new DateTime(date);
        DateTime bound = time.plusDays(1);
        String query = "SELECT date,name FROM tag WHERE " + applicationClause()  +" "+this.filter.getReleaseExclusionClause() + "AND date > '" + format(bound.toDate())
                + "' ORDER BY date ASC LIMIT 1";
        return buildReleaseFormQuery(query);
    }
    
    protected String applicationClause() {
    
        StringBuilder sb = new StringBuilder();
        sb.append("project=").append("'").append(this.application.getName()).append("'");
        sb.append(" AND ").append("branches CONTAINS ").append("'").append(this.application.getBranch())
                .append("' ");
        return sb.toString();
    }
    
    private Date findReleaseDate(final String tag) {
    
        Iterator<IODocument> tags = this.queries.query("SELECT date FROM tag WHERE " + applicationClause() + "AND name='" + tag + "'");
        if (tags.hasNext()) {
            IODocument dtag = tags.next();
            Date date = dtag.getField("date", Date.class);
            return date;
        }
        return null;
    }
    
}
