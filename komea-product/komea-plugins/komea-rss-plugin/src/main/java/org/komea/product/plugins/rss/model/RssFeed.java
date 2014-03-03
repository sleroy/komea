/**
 * 
 */

package org.komea.product.plugins.rss.model;



import org.komea.product.database.alert.enums.Criticity;



/**
 * @author sleroy
 */
public class RssFeed
{
    
    
    private Criticity defaultCriticity  = Criticity.MINOR;
    
    private String    feedName          = "";
    
    private Long      id;
    
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
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof RssFeed)) { return false; }
        final RssFeed other = (RssFeed) obj;
        if (defaultCriticity != other.defaultCriticity) { return false; }
        if (feedName == null) {
            if (other.feedName != null) { return false; }
        } else if (!feedName.equals(other.feedName)) { return false; }
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        if (url == null) {
            if (other.url != null) { return false; }
        } else if (!url.equals(other.url)) { return false; }
        return true;
    }
    
    
    public Criticity getDefaultCriticity() {
    
    
        return defaultCriticity;
    }
    
    
    public String getFeedName() {
    
    
        return feedName;
    }
    
    
    public Long getId() {
    
    
        return id;
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
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (defaultCriticity == null ? 0 : defaultCriticity.hashCode());
        result = prime * result + (feedName == null ? 0 : feedName.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (url == null ? 0 : url.hashCode());
        return result;
    }
    
    
    public void setDefaultCriticity(final Criticity _defaultCriticity) {
    
    
        defaultCriticity = _defaultCriticity;
    }
    
    
    public void setFeedName(final String _feedName) {
    
    
        feedName = _feedName;
    }
    
    
    public void setId(final Long _id) {
    
    
        id = _id;
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
    
    
        return "RssFeed [id="
                + id + ", feedName=" + feedName + ", url=" + url + ", defaultCriticity="
                + defaultCriticity + "]";
    }
    
}
