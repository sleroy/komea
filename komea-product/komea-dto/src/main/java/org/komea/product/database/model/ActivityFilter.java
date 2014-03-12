
package org.komea.product.database.model;



import javax.validation.constraints.NotNull;

import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.RetentionPeriod;



public class ActivityFilter implements IHasKey
{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_acfi
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.blockerRetention
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private RetentionPeriod   blockerRetention;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.criticalRetention
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private RetentionPeriod   criticalRetention;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.entityType
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private EntityType        entityType;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.id
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           id;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.idEntity
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           idEntity;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.infoRetention
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private RetentionPeriod   infoRetention;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.majorRetention
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private RetentionPeriod   majorRetention;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_acfi.minorRetention
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private RetentionPeriod   minorRetention;
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_acfi
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public ActivityFilter() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_acfi
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public ActivityFilter(
            final Integer id,
            final EntityType entityType,
            final Integer idEntity,
            final RetentionPeriod infoRetention,
            final RetentionPeriod minorRetention,
            final RetentionPeriod majorRetention,
            final RetentionPeriod criticalRetention,
            final RetentionPeriod blockerRetention) {
    
    
        this.id = id;
        this.entityType = entityType;
        this.idEntity = idEntity;
        this.infoRetention = infoRetention;
        this.minorRetention = minorRetention;
        this.majorRetention = majorRetention;
        this.criticalRetention = criticalRetention;
        this.blockerRetention = blockerRetention;
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
        final ActivityFilter other = (ActivityFilter) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        return true;
    }
    
    
    /**
     * Returns the activity query
     * 
     * @return the activity query.
     */
    public String generateActivityQueryName() {
    
    
        final StringBuilder sb = new StringBuilder();
        if (entityType != null) {
            sb.append(entityType.name());
        } else {
            sb.append("SYSTEM");
        }
        sb.append("_");
        if (idEntity != null) {
            sb.append("_").append(idEntity).append("_");
        }
        sb.append("_ACTIVITY");
        
        return sb.toString();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.blockerRetention
     * 
     * @return the value of kom_acfi.blockerRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public RetentionPeriod getBlockerRetention() {
    
    
        return blockerRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.criticalRetention
     * 
     * @return the value of kom_acfi.criticalRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public RetentionPeriod getCriticalRetention() {
    
    
        return criticalRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.entityType
     * 
     * @return the value of kom_acfi.entityType
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public EntityType getEntityType() {
    
    
        return entityType;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.id
     * 
     * @return the value of kom_acfi.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.idEntity
     * 
     * @return the value of kom_acfi.idEntity
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Integer getIdEntity() {
    
    
        return idEntity;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.infoRetention
     * 
     * @return the value of kom_acfi.infoRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public RetentionPeriod getInfoRetention() {
    
    
        return infoRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.majorRetention
     * 
     * @return the value of kom_acfi.majorRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public RetentionPeriod getMajorRetention() {
    
    
        return majorRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_acfi.minorRetention
     * 
     * @return the value of kom_acfi.minorRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public RetentionPeriod getMinorRetention() {
    
    
        return minorRetention;
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
     * This method sets the value of the database column kom_acfi.blockerRetention
     * 
     * @param blockerRetention
     *            the value for kom_acfi.blockerRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setBlockerRetention(final RetentionPeriod blockerRetention) {
    
    
        this.blockerRetention = blockerRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_acfi.criticalRetention
     * 
     * @param criticalRetention
     *            the value for kom_acfi.criticalRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setCriticalRetention(final RetentionPeriod criticalRetention) {
    
    
        this.criticalRetention = criticalRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_acfi.entityType
     * 
     * @param entityType
     *            the value for kom_acfi.entityType
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setEntityType(final EntityType entityType) {
    
    
        this.entityType = entityType;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_acfi.id
     * 
     * @param id
     *            the value for kom_acfi.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_acfi.idEntity
     * 
     * @param idEntity
     *            the value for kom_acfi.idEntity
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setIdEntity(final Integer idEntity) {
    
    
        this.idEntity = idEntity;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_acfi.infoRetention
     * 
     * @param infoRetention
     *            the value for kom_acfi.infoRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setInfoRetention(final RetentionPeriod infoRetention) {
    
    
        this.infoRetention = infoRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_acfi.majorRetention
     * 
     * @param majorRetention
     *            the value for kom_acfi.majorRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setMajorRetention(final RetentionPeriod majorRetention) {
    
    
        this.majorRetention = majorRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_acfi.minorRetention
     * 
     * @param minorRetention
     *            the value for kom_acfi.minorRetention
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setMinorRetention(final RetentionPeriod minorRetention) {
    
    
        this.minorRetention = minorRetention;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_acfi
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public String toString() {
    
    
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", entityType=").append(entityType);
        sb.append(", idEntity=").append(idEntity);
        sb.append(", infoRetention=").append(infoRetention);
        sb.append(", minorRetention=").append(minorRetention);
        sb.append(", majorRetention=").append(majorRetention);
        sb.append(", criticalRetention=").append(criticalRetention);
        sb.append(", blockerRetention=").append(blockerRetention);
        sb.append("]");
        return sb.toString();
    }
}
