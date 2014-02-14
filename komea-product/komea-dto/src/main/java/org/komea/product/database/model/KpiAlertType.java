package org.komea.product.database.model;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.Severity;

public class KpiAlertType implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.id
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.idKpi
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    private Integer idKpi;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.kpiAlertKey
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String kpiAlertKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.name
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.description
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @Size(min = 0, max = 2048)
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.severity
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @NotNull
    private Severity severity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.value
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @NotNull
    private Double value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.averageSince
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    private Date averageSince;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.operator
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @NotNull
    private Operator operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpia.enabled
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @NotNull
    private Boolean enabled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpia
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public KpiAlertType(Integer id, Integer idKpi, String kpiAlertKey, String name, String description, Severity severity, Double value, Date averageSince, Operator operator, Boolean enabled) {
        this.id = id;
        this.idKpi = idKpi;
        this.kpiAlertKey = kpiAlertKey;
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.value = value;
        this.averageSince = averageSince;
        this.operator = operator;
        this.enabled = enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public KpiAlertType() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.id
     *
     * @return the value of kom_kpia.id
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.id
     *
     * @param id the value for kom_kpia.id
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.idKpi
     *
     * @return the value of kom_kpia.idKpi
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Integer getIdKpi() {
        return idKpi;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.idKpi
     *
     * @param idKpi the value for kom_kpia.idKpi
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setIdKpi(Integer idKpi) {
        this.idKpi = idKpi;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.kpiAlertKey
     *
     * @return the value of kom_kpia.kpiAlertKey
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public String getKpiAlertKey() {
        return kpiAlertKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.kpiAlertKey
     *
     * @param kpiAlertKey the value for kom_kpia.kpiAlertKey
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setKpiAlertKey(String kpiAlertKey) {
        this.kpiAlertKey = kpiAlertKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.name
     *
     * @return the value of kom_kpia.name
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.name
     *
     * @param name the value for kom_kpia.name
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.description
     *
     * @return the value of kom_kpia.description
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.description
     *
     * @param description the value for kom_kpia.description
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.severity
     *
     * @return the value of kom_kpia.severity
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.severity
     *
     * @param severity the value for kom_kpia.severity
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.value
     *
     * @return the value of kom_kpia.value
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Double getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.value
     *
     * @param value the value for kom_kpia.value
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.averageSince
     *
     * @return the value of kom_kpia.averageSince
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Date getAverageSince() {
        return averageSince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.averageSince
     *
     * @param averageSince the value for kom_kpia.averageSince
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setAverageSince(Date averageSince) {
        this.averageSince = averageSince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.operator
     *
     * @return the value of kom_kpia.operator
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.operator
     *
     * @param operator the value for kom_kpia.operator
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpia.enabled
     *
     * @return the value of kom_kpia.enabled
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpia.enabled
     *
     * @param enabled the value for kom_kpia.enabled
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", idKpi=").append(idKpi);
        sb.append(", kpiAlertKey=").append(kpiAlertKey);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", severity=").append(severity);
        sb.append(", value=").append(value);
        sb.append(", averageSince=").append(averageSince);
        sb.append(", operator=").append(operator);
        sb.append(", enabled=").append(enabled);
        sb.append("]");
        return sb.toString();
    }
}