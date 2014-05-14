package org.komea.product.database.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Measure implements Serializable {

    public static Comparator<Measure> DATE_MEASURE = new Comparator<Measure>() {

        @Override
        public int compare(
                final Measure _arg0,
                final Measure _arg1) {

            return _arg0.getDate().compareTo(
                    _arg1.getDate());
        }

    };

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table kom_msr
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.week
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public static Measure initializeMeasure(final int _kpiID, final Integer _entityID) {

        final Measure measure = new Measure();
        measure.setDate(new Date());

        measure.setEntityID(_entityID);

        measure.setIdKpi(_kpiID);

        return measure;
    }

    public static Measure initializeMeasure(
            final Kpi _kpi,
            final Integer _entityID,
            final Double value) {

        final Measure measureKey = Measure.initializeMeasure(_kpi.getId(), _entityID);
        measureKey.setValue(value);
        return measureKey;
    }

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.date
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @NotNull
    private Date date;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.day
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @NotNull
    private Integer day;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.entityID
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @NotNull
    private Integer entityID;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.hour
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @NotNull
    private Integer hour;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.id
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.idKpi
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    private Integer idKpi;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.month
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @NotNull
    private Integer month;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.sprint
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @Size(min = 0, max = 45)
    private String sprint;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.value
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @NotNull
    private Double value;

    @NotNull
    private Integer week;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_msr.year
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @NotNull
    private Integer year;

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_msr
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Measure() {

        super();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_msr
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Measure(
            final Integer id,
            final Integer idKpi,
            final Integer year,
            final Integer month,
            final Integer week,
            final Integer day,
            final Integer hour,
            final Integer entityID,
            final Double value,
            final Date date,
            final String sprint) {

        this.id = id;
        this.idKpi = idKpi;
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
        this.hour = hour;
        this.entityID = entityID;
        setValue(value);
        this.date = date;
        this.sprint = sprint;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.date
     *
     * @return the value of kom_msr.date
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Date getDate() {

        return date;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.day
     *
     * @return the value of kom_msr.day
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getDay() {

        return day;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.entityID
     *
     * @return the value of kom_msr.entityID
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getEntityID() {

        return entityID;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.hour
     *
     * @return the value of kom_msr.hour
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getHour() {

        return hour;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.id
     *
     * @return the value of kom_msr.id
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getId() {

        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.idKpi
     *
     * @return the value of kom_msr.idKpi
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getIdKpi() {

        return idKpi;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.month
     *
     * @return the value of kom_msr.month
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getMonth() {

        return month;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.sprint
     *
     * @return the value of kom_msr.sprint
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public String getSprint() {

        return sprint;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.value
     *
     * @return the value of kom_msr.value
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Double getValue() {

        return value;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.week
     *
     * @return the value of kom_msr.week
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getWeek() {

        return week;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_msr.year
     *
     * @return the value of kom_msr.year
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public Integer getYear() {

        return year;
    }

    @JsonIgnore
    public boolean isValid() {

        return getEntityID() != null && getValue() != null && hasMinimalDateReference();
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.date
     *
     * @param date the value for kom_msr.date
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setDate(final Date date) {

        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.day
     *
     * @param day the value for kom_msr.day
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setDay(final Integer day) {

        this.day = day;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.entityID
     *
     * @param entityID the value for kom_msr.entityID
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setEntityID(final Integer entityID) {

        this.entityID = entityID;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.hour
     *
     * @param hour the value for kom_msr.hour
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setHour(final Integer hour) {

        this.hour = hour;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.id
     *
     * @param id the value for kom_msr.id
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setId(final Integer id) {

        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.idKpi
     *
     * @param idKpi the value for kom_msr.idKpi
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setIdKpi(final Integer idKpi) {

        this.idKpi = idKpi;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.month
     *
     * @param month the value for kom_msr.month
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setMonth(final Integer month) {

        this.month = month;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.sprint
     *
     * @param sprint the value for kom_msr.sprint
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setSprint(final String sprint) {

        this.sprint = sprint;
    }

    public void setValue(final Double _value) {

        value = _value;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.week
     *
     * @param week the value for kom_msr.week
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setWeek(final Integer week) {

        this.week = week;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_msr.year
     *
     * @param year the value for kom_msr.year
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    public void setYear(final Integer year) {

        this.year = year;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_msr
     *
     * @mbggenerated Sat May 10 14:47:35 CEST 2014
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", idKpi=").append(idKpi);
        sb.append(", year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", week=").append(week);
        sb.append(", day=").append(day);
        sb.append(", hour=").append(hour);
        sb.append(", entityID=").append(entityID);
        sb.append(", value=").append(getValue());
        sb.append(", date=").append(date);
        sb.append(", sprint=").append(sprint);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return
     */
    @JsonIgnore
    private boolean hasMinimalDateReference() {

        return year != null
                && month != null && week != null && day != null && hour != null && date != null;
    }
}
