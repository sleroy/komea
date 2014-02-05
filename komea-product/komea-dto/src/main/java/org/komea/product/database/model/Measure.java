
package org.komea.product.database.model;



import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;



public class Measure implements Serializable
{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.id
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    private Integer           id;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idKpi
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    private Integer           idKpi;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.date
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    @NotNull
    private Date              date;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idPersonGroup
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    private Integer           idPersonGroup;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idPerson
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    private Integer           idPerson;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idProject
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    private Integer           idProject;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.value
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    @NotNull
    private Double            value;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_msr
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_msr
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Measure() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_msr
     * 
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Measure(
            final Integer id,
            final Integer idKpi,
            final Date date,
            final Integer idPersonGroup,
            final Integer idPerson,
            final Integer idProject,
            final Double value) {
    
    
        this.id = id;
        this.idKpi = idKpi;
        this.date = date;
        this.idPersonGroup = idPersonGroup;
        this.idPerson = idPerson;
        this.idProject = idProject;
        this.value = value;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.date
     * 
     * @return the value of kom_msr.date
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Date getDate() {
    
    
        return date;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.id
     * 
     * @return the value of kom_msr.id
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idKpi
     * 
     * @return the value of kom_msr.idKpi
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Integer getIdKpi() {
    
    
        return idKpi;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idPerson
     * 
     * @return the value of kom_msr.idPerson
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Integer getIdPerson() {
    
    
        return idPerson;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idPersonGroup
     * 
     * @return the value of kom_msr.idPersonGroup
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Integer getIdPersonGroup() {
    
    
        return idPersonGroup;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idProject
     * 
     * @return the value of kom_msr.idProject
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Integer getIdProject() {
    
    
        return idProject;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.value
     * 
     * @return the value of kom_msr.value
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Double getValue() {
    
    
        return value;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.date
     * 
     * @param date
     *            the value for kom_msr.date
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setDate(final Date date) {
    
    
        this.date = date;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.id
     * 
     * @param id
     *            the value for kom_msr.id
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.idKpi
     * 
     * @param idKpi
     *            the value for kom_msr.idKpi
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setIdKpi(final Integer idKpi) {
    
    
        this.idKpi = idKpi;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.idPerson
     * 
     * @param idPerson
     *            the value for kom_msr.idPerson
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setIdPerson(final Integer idPerson) {
    
    
        this.idPerson = idPerson;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.idPersonGroup
     * 
     * @param idPersonGroup
     *            the value for kom_msr.idPersonGroup
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setIdPersonGroup(final Integer idPersonGroup) {
    
    
        this.idPersonGroup = idPersonGroup;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.idProject
     * 
     * @param idProject
     *            the value for kom_msr.idProject
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setIdProject(final Integer idProject) {
    
    
        this.idProject = idProject;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.value
     * 
     * @param value
     *            the value for kom_msr.value
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setValue(final Double value) {
    
    
        this.value = value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "Measure [id="
                + id + ", idKpi=" + idKpi + ", date=" + date + ", idPersonGroup=" + idPersonGroup
                + ", idPerson=" + idPerson + ", idProject=" + idProject + ", value=" + value + "]";
    }
}
