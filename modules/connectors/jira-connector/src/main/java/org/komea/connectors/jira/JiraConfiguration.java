
package org.komea.connectors.jira;


public class JiraConfiguration
{
    
    private final String url;
    private final String login;
    private final String password;
    
    public JiraConfiguration(final String url, final String login, final String pass) {
    
        this.url = url;
        this.login = login;
        this.password = pass;
    }
    
    public JiraConfiguration(final String url) {
    
        this(url, null, null);
    }
    
    public String getUrl() {
    
        return this.url;
    }
    
    public String getLogin() {
    
        return this.login;
    }
    
    public String getPassword() {
    
        return this.password;
    }
    
}
