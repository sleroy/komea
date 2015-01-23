/**
 *
 */
package org.komea.event.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.Validate;

/**
 * This class defines an interval between two dates.
 *
 * @author sleroy
 */
public class DateInterval implements Serializable {

    private static final long serialVersionUID = 1L;

    public static DateInterval since(final Date _time) {
        return new DateInterval(_time, null);
    }

    public static DateInterval until(final Date _time) {
        return new DateInterval(null, _time);
    }

    private Date from;

    private Date to;

    public DateInterval() {
        super();
    }

    /**
     * @param _from
     * @param _to
     */
    public DateInterval(final Date _from, final Date _to) {
        super();
        from = _from;
        to = _to;
        Validate.isTrue(!(_from == null && _to == null));
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
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

    public void setFrom(final Date _from) {
        from = _from;
    }

    public void setTo(final Date _to) {
        to = _to;
    }

    @Override
    public String toString() {
        return "DateInterval [from=" + from + ", to=" + to + "]";
    }
}
