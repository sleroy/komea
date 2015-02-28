package org.komea.demo.gitspy.users.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * This class declares a member that contributes to a repository. It is
 * basically identified by the email.
 *
 * @author sleroy
 *
 */
@Entity
public class Member implements Serializable {

	@Id
	@GeneratedValue
	private Integer	id;

	@Column(nullable = false)
	private String	firstName;

	@Column(nullable = false)
	private String	lastName;

	@Column(nullable = false)
	private String	email;

	@Column(nullable = false)
	private String	login;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * @param _email
	 *            the email to set
	 */
	public void setEmail(final String _email) {
		this.email = _email;
	}

	/**
	 * @param _firstName
	 *            the firstName to set
	 */
	public void setFirstName(final String _firstName) {
		this.firstName = _firstName;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _lastName
	 *            the lastName to set
	 */
	public void setLastName(final String _lastName) {
		this.lastName = _lastName;
	}

	/**
	 * @param _login
	 *            the login to set
	 */
	public void setLogin(final String _login) {
		this.login = _login;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Member [id=" + this.id + ", firstName=" + this.firstName + ", lastName="
				+ this.lastName + ", email=" + this.email + ", login=" + this.login + "]";
	}

}