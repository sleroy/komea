
package org.komea.product.database.model;



import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;



public class Measure implements Serializable, IHasKey

{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_msr
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    
    
    /**
     * This factory method builds a kpi from
     * 
     * @param _kpiKey
     *            KpiKey
     * @return Measure
     */
    public static Measure initializeMeasureFromKPIKey(final int _kpiID, final EntityKey _entityKey) {
    
    
        final Measure measure = new Measure();
        measure.setDate(new Date());
        if (_entityKey != null) {
            measure.setEntity(_entityKey.getEntityType(), _entityKey.getId());
        }
        measure.setIdKpi(_kpiID);
        
        return measure;
    }
    
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.date
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    private Date    date;
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.id
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer id;
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idKpi
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer idKpi;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idPerson
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer idPerson;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idPersonGroup
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer idPersonGroup;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.idProject
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer idProject;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_msr.value
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    private Double  value;
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_msr
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Measure() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_msr
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasKey#accept(org.komea.product.database.api.IKeyVisitor)
     */
    @Override
    public void accept(final IKeyVisitor _visitor) {
    
    
        _visitor.visit(this);
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        final Measure other = (Measure) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        return true;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.date
     * 
     * @return the value of kom_msr.date
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Date getDate() {
    
    
        return date;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.id
     * 
     * @return the value of kom_msr.id
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idKpi
     * 
     * @return the value of kom_msr.idKpi
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getIdKpi() {
    
    
        return idKpi;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idPerson
     * 
     * @return the value of kom_msr.idPerson
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getIdPerson() {
    
    
        return idPerson;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idPersonGroup
     * 
     * @return the value of kom_msr.idPersonGroup
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getIdPersonGroup() {
    
    
        return idPersonGroup;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.idProject
     * 
     * @return the value of kom_msr.idProject
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getIdProject() {
    
    
        return idProject;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_msr.value
     * 
     * @return the value of kom_msr.value
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Double getValue() {
    
    
        return value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.date
     * 
     * @param date
     *            the value for kom_msr.date
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setDate(final Date date) {
    
    
        this.date = date;
    }
    
    
    public void setEntity(final EntityType _entityType, final Integer _entityID) {
    
    
        if (_entityType == null || _entityID == null) { return; }
        switch (_entityType) {
            case PERSON:
                setIdPerson(_entityID);
                break;
            case DEPARTMENT:
            case TEAM:
                setIdPersonGroup(_entityID);
                break;
            case PROJECT:
                setIdProject(_entityID);
                break;
            default:
                break;
        
        }
        
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_msr.id
     * 
     * @param id
     *            the value for kom_msr.id
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setValue(final Double value) {
    
    
        this.value = value;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_msr
     * 
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @Override
    public String toString() {
    
    
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", idKpi=").append(idKpi);
        sb.append(", date=").append(date);
        sb.append(", idPersonGroup=").append(idPersonGroup);
        sb.append(", idPerson=").append(idPerson);
        sb.append(", idProject=").append(idProject);
        sb.append(", value=").append(value);
        sb.append("]");
        return sb.toString();
    }
}
