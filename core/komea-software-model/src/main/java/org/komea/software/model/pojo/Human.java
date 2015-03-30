package org.komea.software.model.pojo;

import java.util.Date;

public class Human {
	public String getLogin() {
		return login;
	}
	public void setLogin(String _login) {
		login = _login;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String _firstName) {
		firstName = _firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String _lastName) {
		lastName = _lastName;
	}
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date _creation_date) {
		creation_date = _creation_date;
	}
	private String login;
	private String firstName;
	private String lastName;
	private Date creation_date;
}