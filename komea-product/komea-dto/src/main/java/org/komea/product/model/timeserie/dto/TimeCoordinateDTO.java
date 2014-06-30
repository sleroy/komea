package org.komea.product.model.timeserie.dto;

import java.io.Serializable;
import java.util.Date;

public class TimeCoordinateDTO implements Serializable, Comparable<TimeCoordinateDTO> {

    private static final long serialVersionUID = 1L;

    private Date date;
    private Double value;

    public TimeCoordinateDTO() {
    }

    public TimeCoordinateDTO(final Date _date, final Double _value) {

        super();
        date = _date;
        value = _value;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(final Date _date) {

        date = _date;
    }

    public Double getValue() {

        return value;
    }

    public void setValue(final Double _value) {

        value = _value;
    }

    @Override
    public String toString() {
        return "TimeCoordinateDTO{" + "date=" + date + ", value=" + value + '}';
    }

    @Override
    public int compareTo(TimeCoordinateDTO o) {
        return getDate().compareTo(o.getDate());
    }

}
