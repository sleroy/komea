/**
 * 
 */

package org.komea.product.database.alert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.komea.product.database.alert.enums.Criticity;
import org.ocpsoft.prettytime.PrettyTime;

/**
 * This type defines the informations necessary to launch an alert/information.
 * 
 * @author sleroy
 */

@XmlRootElement(name = "alert")
public class Alert implements IAlert {

	private Date date = new Date();

	private String provider = "";

	private String category = "NONE";

	private String type = "";

	private String project = null;

	private List<String> users = new ArrayList<String>();

	private String team = null;

	private Criticity criticity = Criticity.NONE;

	private String message = "";

	private String fullMessage = "";

	private Map<String, Object> params = new HashMap<String, Object>();

	private double value = 0.0d;

	private String URL = "";

	private String icon = "/static/pics/pin.png";

	/**
	 * 
	 */
	public Alert() {

		super();
	}

	/**
	 * @param _date
	 * @param _provider
	 * @param _category
	 * @param _type
	 * @param _project
	 * @param _users
	 * @param _criticity
	 * @param _message
	 * @param _params
	 * @param _value
	 * @param _uRL
	 */
	public Alert(final Date _date, final String _provider,
			final String _category, final String _type, final String _project,
			final List<String> _users, final Criticity _criticity,
			final String _message, final Map<String, Object> _params,
			final double _value, final String _uRL) {

		super();
		date = _date;
		provider = _provider;
		category = _category;
		type = _type;
		project = _project;
		users = _users;
		criticity = _criticity;
		message = _message;
		params = _params;
		value = _value;
		URL = _uRL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#addParam(java.lang.String,
	 * java.lang.Object)
	 */

	@Override
	public void addParam(final String _string, final Object _value) {

		params.put(_string, _value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#addUser(java.lang.String)
	 */

	@Override
	public void addUser(final String _string) {

		users.add(_string);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#equals(java.lang.Object)
	 */

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Alert)) {
			return false;
		}
		final Alert other = (Alert) obj;
		if (URL == null) {
			if (other.URL != null) {
				return false;
			}
		} else if (!URL.equals(other.URL)) {
			return false;
		}
		if (category != other.category) {
			return false;
		}
		if (criticity != other.criticity) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (fullMessage == null) {
			if (other.fullMessage != null) {
				return false;
			}
		} else if (!fullMessage.equals(other.fullMessage)) {
			return false;
		}
		if (icon == null) {
			if (other.icon != null) {
				return false;
			}
		} else if (!icon.equals(other.icon)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (params == null) {
			if (other.params != null) {
				return false;
			}
		} else if (!params.equals(other.params)) {
			return false;
		}
		if (project == null) {
			if (other.project != null) {
				return false;
			}
		} else if (!project.equals(other.project)) {
			return false;
		}
		if (provider == null) {
			if (other.provider != null) {
				return false;
			}
		} else if (!provider.equals(other.provider)) {
			return false;
		}
		if (team == null) {
			if (other.team != null) {
				return false;
			}
		} else if (!team.equals(other.team)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (users == null) {
			if (other.users != null) {
				return false;
			}
		} else if (!users.equals(other.users)) {
			return false;
		}
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#equals(java.lang.Object)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getDate()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getCategory()
	 */

	@Override
	@XmlElement
	public String getCategory() {

		return category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getCategory()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getCriticity()
	 */

	@Override
	@XmlElement
	public Criticity getCriticity() {

		return criticity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getDate()
	 */

	@Override
	@XmlElement
	public Date getDate() {

		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getDoubleParams()
	 */

	@Override
	public Map<String, Double> getDoubleParams() {

		return (Map) params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getMessage()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getFullMessage()
	 */

	@Override
	@XmlElement
	public String getFullMessage() {

		return fullMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getParams()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getIcon()
	 */

	@Override
	public String getIcon() {

		return icon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getPrettyTime()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getIntParams()
	 */

	@Override
	public Map<String, Long> getIntParams() {

		return (Map) params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getPrettyTime()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getMessage()
	 */

	@Override
	@XmlElement
	public String getMessage() {

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getProject()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getParams()
	 */

	@Override
	public Map<String, Object> getParams() {

		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getProject()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getPrettyTime()
	 */

	@Override
	public String getPrettyTime() {

		final PrettyTime tm = new PrettyTime();
		return tm.format(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getProvider()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getProject()
	 */

	@Override
	@XmlElement
	public String getProject() {

		return project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getType()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getProvider()
	 */

	@Override
	@XmlElement
	public String getProvider() {

		return provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getURL()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getTeam()
	 */

	@Override
	public String getTeam() {

		return team;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getUsers()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getType()
	 */

	@Override
	@XmlElement
	public String getType() {

		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#getValue()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getURL()
	 */

	@Override
	@XmlElement
	public String getURL() {

		return URL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#hashCode()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getUsers()
	 */

	@Override
	public List<String> getUsers() {

		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.model.Alert#setCategory(com.tocea.scertify
	 * .ci.flow.app.model.Category)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#getValue()
	 */

	@Override
	@XmlElement
	public double getValue() {

		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.model.Alert#setCriticity(com.tocea.scertify
	 * .ci.flow.app.model.Criticity)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#hashCode()
	 */

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (URL == null ? 0 : URL.hashCode());
		result = prime * result + (category == null ? 0 : category.hashCode());
		result = prime * result
				+ (criticity == null ? 0 : criticity.hashCode());
		result = prime * result + (date == null ? 0 : date.hashCode());
		result = prime * result
				+ (fullMessage == null ? 0 : fullMessage.hashCode());
		result = prime * result + (icon == null ? 0 : icon.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (params == null ? 0 : params.hashCode());
		result = prime * result + (project == null ? 0 : project.hashCode());
		result = prime * result + (provider == null ? 0 : provider.hashCode());
		result = prime * result + (team == null ? 0 : team.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		result = prime * result + (users == null ? 0 : users.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ temp >>> 32);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#setDate(java.util.Date)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setCategory(java.lang.String)
	 */

	@Override
	public void setCategory(final String _category) {

		category = _category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setCriticity(com.tocea.scertify
	 * .ci.flow.app.model.enums.Criticity)
	 */

	@Override
	public void setCriticity(final Criticity _criticity) {

		criticity = _criticity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#setParams(java.util.Map)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#setDate(java.util.Date)
	 */

	@Override
	public void setDate(final Date _date) {

		date = _date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.model.Alert#setProject(java.lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setFullMessage(java.lang.
	 * String)
	 */

	@Override
	public void setFullMessage(final String _fulMessage) {

		fullMessage = _fulMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#setType(java.lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setIcon(java.lang.String)
	 */

	@Override
	public void setIcon(final String _icon) {

		icon = _icon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#setURL(java.lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setMessage(java.lang.String)
	 */

	@Override
	public void setMessage(final String _message) {

		message = _message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#setUsers(java.util.List)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#setParams(java.util.Map)
	 */

	@Override
	public void setParams(final Map<String, Object> _params) {

		params = _params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.model.Alert#setValue(double)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setProject(java.lang.String)
	 */

	@Override
	public void setProject(final String _project) {

		project = _project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setProvider(java.lang.String)
	 */

	@Override
	public void setProvider(final String _provider) {

		provider = _provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setTeam(java.lang.String)
	 */

	@Override
	public void setTeam(final String _team) {

		team = _team;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.scertify.ci.flow.app.alert.IAlert#setType(java.lang.String)
	 */

	@Override
	public void setType(final String _type) {

		type = _type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#setURL(java.lang.String)
	 */

	@Override
	public void setURL(final String _uRL) {

		URL = _uRL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#setUsers(java.util.List)
	 */

	@Override
	public void setUsers(final List<String> _users) {

		users = _users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#setValue(double)
	 */

	@Override
	public void setValue(final double _value) {

		value = _value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tocea.scertify.ci.flow.app.alert.IAlert#toString()
	 */

	@Override
	public String toString() {

		return "Alert [date=" + date + ", provider=" + provider + ", category="
				+ category + ", type=" + type + ", project=" + project
				+ ", users=" + users + ", team=" + team + ", criticity="
				+ criticity + ", message=" + message + ", fullMessage="
				+ fullMessage + ", params=" + params + ", value=" + value
				+ ", URL=" + URL + ", icon=" + icon + "]";
	}
}
