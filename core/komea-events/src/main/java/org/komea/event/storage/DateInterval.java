/**
 *
 */
package org.komea.event.storage;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;

/**
 * This class defines an interval between two dates.
 *
 * @author sleroy
 */
public class DateInterval {
	public static DateInterval since(final DateTime _time) {
		return new DateInterval(_time, null);
	}
	
	public static DateInterval until(final DateTime _time) {
		return new DateInterval(null, _time);
	}
	
	private DateTime	from;
	
	private DateTime	to;
	
	public DateInterval() {
		super();
	}
	
	/**
	 * @param _from
	 * @param _to
	 */
	public DateInterval(final DateTime _from, final DateTime _to) {
		super();
		from = _from;
		to = _to;
		Validate.isTrue(!(_from == null && _to == null));
	}
	
	public DateTime getFrom() {
		return from;
	}
	
	public DateTime getTo() {
		return to;
	}
	
	public boolean hasFrom() {
		return from != null;
	}
	
	public boolean hasTo() {
		return to != null;
	}
	
	public boolean isCompleteInterval() {
		return from != null && to != null;
	}
	
	public void setFrom(final DateTime _from) {
		from = _from;
	}
	
	public void setTo(final DateTime _to) {
		to = _to;
	}
	
	@Override
	public String toString() {
		return "DateInterval [from=" + from + ", to=" + to + "]";
	}
}
