
package org.komea.product.database.model;



import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;



public class Project implements IEntity, Serializable
{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_proj
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.description
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Size(min = 0, max = 2048)
    private String            description;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.id
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           id;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.idCustomer
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           idCustomer;
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.name
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            name;
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.projectKey
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            projectKey;
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Project() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Project(
            final Integer id,
            final String projectKey,
            final String name,
            final String description,
            final Integer idCustomer) {
    
    
        this.id = id;
        this.projectKey = projectKey;
        this.name = name;
        this.description = description;
        this.idCustomer = idCustomer;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasKey#accept(org.komea.product.database.api.IKeyVisitor)
     */
    @Override
    public void accept(final IKeyVisitor _visitor) {
    
    
        _visitor.visit(this);
        
        
    }
    
    
    @Override
    public EntityType entityType() {
    
    
        return EntityType.PROJECT;
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
        final Project other = (Project) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        return true;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.description
     * 
     * @return the value of kom_proj.description
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getDescription() {
    
    
        return description;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IEntity#getKey()
     */
    @Override
    public EntityKey getEntityKey() {
    
    
        return new EntityKey(entityType(), getId());
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.id
     * 
     * @return the value of kom_proj.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.idCustomer
     * 
     * @return the value of kom_proj.idCustomer
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Integer getIdCustomer() {
    
    
        return idCustomer;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.name
     * 
     * @return the value of kom_proj.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getName() {
    
    
        return name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.projectKey
     * 
     * @return the value of kom_proj.projectKey
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getProjectKey() {
    
    
        return projectKey;
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
     * This method sets the value of the database column kom_proj.description
     * 
     * @param description
     *            the value for kom_proj.description
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setDescription(final String description) {
    
    
        this.description = description;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.id
     * 
     * @param id
     *            the value for kom_proj.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.idCustomer
     * 
     * @param idCustomer
     *            the value for kom_proj.idCustomer
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setIdCustomer(final Integer idCustomer) {
    
    
        this.idCustomer = idCustomer;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.name
     * 
     * @param name
     *            the value for kom_proj.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setName(final String name) {
    
    
        this.name = name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.projectKey
     * 
     * @param projectKey
     *            the value for kom_proj.projectKey
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setProjectKey(final String projectKey) {
    
    
        this.projectKey = projectKey;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
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
        sb.append(", projectKey=").append(projectKey);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", idCustomer=").append(idCustomer);
        sb.append("]");
        return sb.toString();
    }
}
