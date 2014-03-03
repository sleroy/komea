
package org.komea.product.database.model;



import java.io.Serializable;



public class HasProjectKpiAlertTypeKey implements Serializable
{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_has_proj_kpia
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_proj_kpia.idKpiAlertType
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer           idKpiAlertType;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_has_proj_kpia.idProject
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer           idProject;
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public HasProjectKpiAlertTypeKey() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public HasProjectKpiAlertTypeKey(final Integer idProject, final Integer idKpiAlertType) {
    
    
        this.idProject = idProject;
        this.idKpiAlertType = idKpiAlertType;
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
        final HasProjectKpiAlertTypeKey other = (HasProjectKpiAlertTypeKey) obj;
        if (idKpiAlertType == null) {
            if (other.idKpiAlertType != null) { return false; }
        } else if (!idKpiAlertType.equals(other.idKpiAlertType)) { return false; }
        if (idProject == null) {
            if (other.idProject != null) { return false; }
        } else if (!idProject.equals(other.idProject)) { return false; }
        return true;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_proj_kpia.idKpiAlertType
     * 
     * @return the value of kom_has_proj_kpia.idKpiAlertType
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getIdKpiAlertType() {
    
    
        return idKpiAlertType;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_has_proj_kpia.idProject
     * 
     * @return the value of kom_has_proj_kpia.idProject
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getIdProject() {
    
    
        return idProject;
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
        result = prime * result + (idProject == null ? 0 : idProject.hashCode());
        return result;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_proj_kpia.idKpiAlertType
     * 
     * @param idKpiAlertType
     *            the value for kom_has_proj_kpia.idKpiAlertType
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setIdKpiAlertType(final Integer idKpiAlertType) {
    
    
        this.idKpiAlertType = idKpiAlertType;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_has_proj_kpia.idProject
     * 
     * @param idProject
     *            the value for kom_has_proj_kpia.idProject
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setIdProject(final Integer idProject) {
    
    
        this.idProject = idProject;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @Override
    public String toString() {
    
    
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idProject=").append(idProject);
        sb.append(", idKpiAlertType=").append(idKpiAlertType);
        sb.append("]");
        return sb.toString();
    }
}
