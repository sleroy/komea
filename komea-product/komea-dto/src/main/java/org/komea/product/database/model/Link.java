
package org.komea.product.database.model;



import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;



public class Link implements Serializable, IHasKey
{
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_link
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.id
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           id;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.idProject
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           idProject;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.name
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            name;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.url
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            url;
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Link() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Link(final Integer id, final String name, final String url, final Integer idProject) {
    
    
        this.id = id;
        this.name = name;
        this.url = url;
        this.idProject = idProject;
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
        final Link other = (Link) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        return true;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.id
     * 
     * @return the value of kom_link.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.idProject
     * 
     * @return the value of kom_link.idProject
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Integer getIdProject() {
    
    
        return idProject;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.name
     * 
     * @return the value of kom_link.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getName() {
    
    
        return name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.url
     * 
     * @return the value of kom_link.url
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getUrl() {
    
    
        return url;
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
     * This method sets the value of the database column kom_link.id
     * 
     * @param id
     *            the value for kom_link.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.idProject
     * 
     * @param idProject
     *            the value for kom_link.idProject
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setIdProject(final Integer idProject) {
    
    
        this.idProject = idProject;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.name
     * 
     * @param name
     *            the value for kom_link.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setName(final String name) {
    
    
        this.name = name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.url
     * 
     * @param url
     *            the value for kom_link.url
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setUrl(final String url) {
    
    
        this.url = url;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
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
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", idProject=").append(idProject);
        sb.append("]");
        return sb.toString();
    }
}
