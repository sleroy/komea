
package org.komea.product.database.model;



import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;



public class PersonRole implements Serializable, IHasKey
{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_pero
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pero.id
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer           id;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pero.name
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            name;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pero.roleKey
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            roleKey;
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public PersonRole() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public PersonRole(final Integer id, final String roleKey, final String name) {
    
    
        this.id = id;
        this.roleKey = roleKey;
        this.name = name;
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
        final PersonRole other = (PersonRole) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        return true;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pero.id
     * 
     * @return the value of kom_pero.id
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pero.name
     * 
     * @return the value of kom_pero.name
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public String getName() {
    
    
        return name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pero.roleKey
     * 
     * @return the value of kom_pero.roleKey
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public String getRoleKey() {
    
    
        return roleKey;
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
     * This method sets the value of the database column kom_pero.id
     * 
     * @param id
     *            the value for kom_pero.id
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pero.name
     * 
     * @param name
     *            the value for kom_pero.name
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setName(final String name) {
    
    
        this.name = name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pero.roleKey
     * 
     * @param roleKey
     *            the value for kom_pero.roleKey
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setRoleKey(final String roleKey) {
    
    
        this.roleKey = roleKey;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     * 
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @Override
    public String toString() {
    
    
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleKey=").append(roleKey);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}
