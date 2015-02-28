package org.komea.demo.gitspy.repository.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * This class declares the repository (GIT, SVN).
 *
 * @author sleroy
 *
 */
@Entity
public class Repository implements Serializable {

	@Id
	@GeneratedValue
	private Integer	id;

	@Column(nullable = false)
	private String	name;

	@Column(nullable = false)
	private String	url;

	@Column(nullable = false)
	private String	login;

	@Column(nullable = false)
	private String	password;

	@Column(nullable = false)
	private String	type;
	@Column(nullable = true)
	private Date lastExecution;

	@Column(nullable = true)
	private String branches;

	/**
	 * @return the branches
	 */
	public String getBranches() {
		return this.branches;
	}

	/**
	 * Returns the list of branches as an array.
	 * @return the branch array
	 */
	public String[] getBranchesArray() {
		return this.branches.split(",");
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the lastExecution
	 */
	public Date getLastExecution() {
		return this.lastExecution;
	}


	/**
	 * @return Returns the last execution time.
	 *
	 */
	@JsonIgnore
	public DateTime getLastExecutionTime() {
		if (this.lastExecution == null) {
			return null;
		}
		return new DateTime(this.lastExecution);
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Tests if a repository is outdated.
	 * @param _minMinutesRefresh the minimal refresh rate.
	 * @return true if the repository is outdated.
	 */
	public boolean isOutdated(final Integer _minMinutesRefresh) {
		final DateTime lastExecutionTime = this.getLastExecutionTime();
		if (lastExecutionTime == null) {
			return true;
		}
		return lastExecutionTime.plusMinutes(_minMinutesRefresh).isBefore(new DateTime());
	}

	/**
	 * @param _branches the branches to set
	 */
	public void setBranches(final String _branches) {
		this.branches = _branches;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _lastExecution the lastExecution to set
	 */
	public void setLastExecution(final Date _lastExecution) {
		this.lastExecution = _lastExecution;
	}

	/**
	 * @param _login
	 *            the login to set
	 */
	public void setLogin(final String _login) {
		this.login = _login;
	}

	/**
	 * @param _name
	 *            the name to set
	 */
	public void setName(final String _name) {
		this.name = _name;
	}

	/**
	 * @param _password
	 *            the password to set
	 */
	public void setPassword(final String _password) {
		this.password = _password;
	}

	/**
	 * @param _type
	 *            the type to set
	 */
	public void setType(final String _type) {
		this.type = _type;
	}

	/**
	 * @param _url
	 *            the url to set
	 */
	public void setUrl(final String _url) {
		this.url = _url;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Repository [id=" + this.id + ", name=" + this.name + ", url=" + this.url
				+ ", login=" + this.login + ", password=" + this.password + ", type="
				+ this.type + ", lastExecution=" + this.lastExecution + ", branches="
				+ this.branches + "]";
	}

}