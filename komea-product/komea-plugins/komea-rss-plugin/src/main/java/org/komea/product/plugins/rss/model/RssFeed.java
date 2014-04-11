/**
 * 
 */

package org.komea.product.plugins.rss.model;



import java.util.Date;

import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.api.IHasId;



/**
 * @author sleroy
 */
public class RssFeed implements IHasId
{
    
    
    private Criticity defaultCriticity  = Criticity.INFO;
    
    private String    feedName          = "";
    
    private int       id;
    
    private Date      lastFetchDate;
    
    private String    projectAssociated = "";
    
    private String    teamAssociated    = "";
    
    private String    url               = "";
    
    private String    userAssociated    = "";
    
    
    
    /**
	 * 
	 */
    public RssFeed() {
    
    
        super();
        
    }
    
    
    /**
     * @param _feedName
     * @param _url
     */
    public RssFeed(final String _feedName, final String _url) {
    
    
        super();
        feedName = _feedName;
        url = _url;
    }
    
    
    public Criticity getDefaultCriticity() {
    
    
        return defaultCriticity;
    }
    
    
    public String getFeedName() {
    
    
        return feedName;
    }
    
    
    @Override
    public int getId() {
    
    
        return id;
    }
    
    
    public Date getLastFetchDate() {
    
    
        return lastFetchDate;
    }
    
    
    public String getProjectAssociated() {
    
    
        return projectAssociated;
    }
    
    
    public String getTeamAssociated() {
    
    
        return teamAssociated;
    }
    
    
    public String getUrl() {
    
    
        return url;
    }
    
    
    public String getUserAssociated() {
    
    
        return userAssociated;
    }
    
    
    public void setDefaultCriticity(final Criticity _defaultCriticity) {
    
    
        defaultCriticity = _defaultCriticity;
    }
    
    
    public void setFeedName(final String _feedName) {
    
    
        feedName = _feedName;
    }
    
    
    @Override
    public void setId(final int _id) {
    
    
        id = _id;
    }
    
    
    public void setLastFetchDate(final Date _lastFetchDate) {
    
    
        lastFetchDate = _lastFetchDate;
    }
    
    
    public void setProjectAssociated(final String _projectAssociated) {
    
    
        projectAssociated = _projectAssociated;
    }
    
    
    public void setTeamAssociated(final String _teamAssociated) {
    
    
        teamAssociated = _teamAssociated;
    }
    
    
    public void setUrl(final String _url) {
    
    
        url = _url;
    }
    
    
    public void setUserAssociated(final String _userAssociated) {
    
    
        userAssociated = _userAssociated;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "RssFeed [defaultCriticity="
                + defaultCriticity + ", feedName=" + feedName + ", id=" + id + ", lastFetchDate="
                + lastFetchDate + ", projectAssociated=" + projectAssociated + ", teamAssociated="
                + teamAssociated + ", url=" + url + ", userAssociated=" + userAssociated + "]";
    }
    
}
