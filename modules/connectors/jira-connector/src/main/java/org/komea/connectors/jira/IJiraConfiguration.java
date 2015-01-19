/**
 * 
 */
package org.komea.connectors.jira;

/**
 * @author sleroy
 *
 */
public interface IJiraConfiguration {

	public abstract String getLogin();

	public abstract String getPassword();

	public abstract String getSelectedFields();

	public abstract String getUrl();

}