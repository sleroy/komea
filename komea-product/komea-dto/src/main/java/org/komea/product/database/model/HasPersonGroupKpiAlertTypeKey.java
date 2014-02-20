package org.komea.product.database.model;

import java.io.Serializable;

public class HasPersonGroupKpiAlertTypeKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_kpia_pegr.idKpiAlertType
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer idKpiAlertType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_kpia_pegr.idPersonGroup
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer idPersonGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public HasPersonGroupKpiAlertTypeKey(Integer idKpiAlertType, Integer idPersonGroup) {
        this.idKpiAlertType = idKpiAlertType;
        this.idPersonGroup = idPersonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public HasPersonGroupKpiAlertTypeKey() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_kpia_pegr.idKpiAlertType
     *
     * @return the value of kom_has_kpia_pegr.idKpiAlertType
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getIdKpiAlertType() {
        return idKpiAlertType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_kpia_pegr.idKpiAlertType
     *
     * @param idKpiAlertType the value for kom_has_kpia_pegr.idKpiAlertType
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setIdKpiAlertType(Integer idKpiAlertType) {
        this.idKpiAlertType = idKpiAlertType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_kpia_pegr.idPersonGroup
     *
     * @return the value of kom_has_kpia_pegr.idPersonGroup
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getIdPersonGroup() {
        return idPersonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_kpia_pegr.idPersonGroup
     *
     * @param idPersonGroup the value for kom_has_kpia_pegr.idPersonGroup
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setIdPersonGroup(Integer idPersonGroup) {
        this.idPersonGroup = idPersonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idKpiAlertType=").append(idKpiAlertType);
        sb.append(", idPersonGroup=").append(idPersonGroup);
        sb.append("]");
        return sb.toString();
    }
}