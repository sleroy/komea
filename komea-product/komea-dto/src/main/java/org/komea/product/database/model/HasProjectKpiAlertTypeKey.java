package org.komea.product.database.model;

import java.io.Serializable;

public class HasProjectKpiAlertTypeKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_proj_kpia.idProject
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    private Integer idProject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_proj_kpia.idKpiAlertType
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    private Integer idKpiAlertType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    public HasProjectKpiAlertTypeKey(Integer idProject, Integer idKpiAlertType) {
        this.idProject = idProject;
        this.idKpiAlertType = idKpiAlertType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    public HasProjectKpiAlertTypeKey() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_proj_kpia.idProject
     *
     * @return the value of kom_has_proj_kpia.idProject
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    public Integer getIdProject() {
        return idProject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_proj_kpia.idProject
     *
     * @param idProject the value for kom_has_proj_kpia.idProject
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_proj_kpia.idKpiAlertType
     *
     * @return the value of kom_has_proj_kpia.idKpiAlertType
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    public Integer getIdKpiAlertType() {
        return idKpiAlertType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_proj_kpia.idKpiAlertType
     *
     * @param idKpiAlertType the value for kom_has_proj_kpia.idKpiAlertType
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    public void setIdKpiAlertType(Integer idKpiAlertType) {
        this.idKpiAlertType = idKpiAlertType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Tue Feb 11 15:35:54 CET 2014
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idProject=").append(idProject);
        sb.append(", idKpiAlertType=").append(idKpiAlertType);
        sb.append("]");
        return sb.toString();
    }
}