package org.komea.product.database.alert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.komea.product.database.alert.enums.Criticity;

public interface IAlert extends Serializable {

	void addParam(String _string, Object _value);

	void addUser(String _string);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	boolean equals(Object obj);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getDate()
	 */
	String getCategory();

	Criticity getCriticity();

	Date getDate();

	Map<String, Double> getDoubleParams();

	String getFullMessage();

	String getIcon();

	Map<String, Long> getIntParams();

	String getMessage();

	Map<String, Object> getParams();

	String getPrettyTime();

	String getProject();

	String getProvider();

	String getTeam();

	String getType();

	String getURL();

	List<String> getUsers();

	double getValue();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	int hashCode();

	void setCategory(String _category);

	void setCriticity(Criticity _criticity);

	void setDate(Date _date);

	void setFullMessage(String _fulMessage);

	void setIcon(String _icon);

	void setMessage(String _message);

	void setParams(Map<String, Object> _params);

	/**
	 * @param _project
	 *            the project to set
	 */
	void setProject(String _project);

	void setProvider(String _provider);

	/**
	 * @param _team
	 *            the team to set
	 */
	void setTeam(String _team);

	void setType(String _type);

	void setURL(String _uRL);

	void setUsers(List<String> _users);

	void setValue(double _value);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();

}