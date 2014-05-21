package org.komea.product.model.timeserie.dto;

import java.util.Date;

public class TimeCoordinateDTO {

    //
    private Date date;
    private Double value;

    public TimeCoordinateDTO() {

        // TODO Auto-generated TimeCoordinateDTO stub
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

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public String toString() {
        return "TimeCoordinateDTO{" + "date=" + date + ", value=" + value + '}';
    }

}
