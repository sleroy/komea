
package org.komea.product.database.model;



import java.io.Serializable;



public class HasPersonGroupKpiAlertTypeKey implements Serializable
{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_has_kpia_pegr
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_kpia_pegr.idKpiAlertType
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer           idKpiAlertType;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_kpia_pegr.idPersonGroup
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer           idPersonGroup;
    
    
    
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
     * This method corresponds to the database table kom_has_kpia_pegr
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public HasPersonGroupKpiAlertTypeKey(final Integer idKpiAlertType, final Integer idPersonGroup) {
    
    
        this.idKpiAlertType = idKpiAlertType;
        this.idPersonGroup = idPersonGroup;
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
        final HasPersonGroupKpiAlertTypeKey other = (HasPersonGroupKpiAlertTypeKey) obj;
        if (idKpiAlertType == null) {
            if (other.idKpiAlertType != null) { return false; }
        } else if (!idKpiAlertType.equals(other.idKpiAlertType)) { return false; }
        if (idPersonGroup == null) {
            if (other.idPersonGroup != null) { return false; }
        } else if (!idPersonGroup.equals(other.idPersonGroup)) { return false; }
        return true;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_kpia_pegr.idKpiAlertType
     * 
     * @return the value of kom_has_kpia_pegr.idKpiAlertType
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getIdKpiAlertType() {
    
    
        return idKpiAlertType;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_kpia_pegr.idPersonGroup
     * 
     * @return the value of kom_has_kpia_pegr.idPersonGroup
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getIdPersonGroup() {
    
    
        return idPersonGroup;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (idKpiAlertType == null ? 0 : idKpiAlertType.hashCode());
        result = prime * result + (idPersonGroup == null ? 0 : idPersonGroup.hashCode());
        return result;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_kpia_pegr.idKpiAlertType
     * 
     * @param idKpiAlertType
     *            the value for kom_has_kpia_pegr.idKpiAlertType
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setIdKpiAlertType(final Integer idKpiAlertType) {
    
    
        this.idKpiAlertType = idKpiAlertType;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_kpia_pegr.idPersonGroup
     * 
     * @param idPersonGroup
     *            the value for kom_has_kpia_pegr.idPersonGroup
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setIdPersonGroup(final Integer idPersonGroup) {
    
    
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
    
    
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idKpiAlertType=").append(idKpiAlertType);
        sb.append(", idPersonGroup=").append(idPersonGroup);
        sb.append("]");
        return sb.toString();
    }
}
