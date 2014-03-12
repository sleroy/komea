
package org.komea.product.database.model;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.service.dto.EntityKey;



public class PersonGroup implements IEntity
{
    
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table kom_pegr
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pegr.description
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Size(min = 0, max = 2048)
    private String            description;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pegr.id
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           id;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pegr.idPersonGroupParent
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer           idPersonGroupParent;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pegr.name
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            name;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pegr.personGroupKey
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String            personGroupKey;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pegr.type
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private PersonGroupType   type;
    
    
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_pegr
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public PersonGroup() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_pegr
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public PersonGroup(
            final Integer id,
            final String name,
            final String personGroupKey,
            final String description,
            final Integer idPersonGroupParent,
            final PersonGroupType type) {
    
    
        this.id = id;
        this.name = name;
        this.personGroupKey = personGroupKey;
        this.description = description;
        this.idPersonGroupParent = idPersonGroupParent;
        this.type = type;
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
    
    
        return EntityType.valueOf(getType().name());
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
        final PersonGroup other = (PersonGroup) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        return true;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pegr.description
     * 
     * @return the value of kom_pegr.description
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getDescription() {
    
    
        return description;
    }
    
    
    @Override
    public String getDisplayName() {
    
    
        return name;
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
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pegr.id
     * 
     * @return the value of kom_pegr.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pegr.idPersonGroupParent
     * 
     * @return the value of kom_pegr.idPersonGroupParent
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Integer getIdPersonGroupParent() {
    
    
        return idPersonGroupParent;
    }
    
    
    @Override
    public String getKey() {
    
    
        return personGroupKey;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pegr.name
     * 
     * @return the value of kom_pegr.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getName() {
    
    
        return name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pegr.personGroupKey
     * 
     * @return the value of kom_pegr.personGroupKey
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getPersonGroupKey() {
    
    
        return personGroupKey;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pegr.type
     * 
     * @return the value of kom_pegr.type
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public PersonGroupType getType() {
    
    
        return type;
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
    
    
    @JsonIgnore
    public boolean hasParent() {
    
    
        return idPersonGroupParent != null;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pegr.description
     * 
     * @param description
     *            the value for kom_pegr.description
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setDescription(final String description) {
    
    
        this.description = description;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pegr.id
     * 
     * @param id
     *            the value for kom_pegr.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pegr.idPersonGroupParent
     * 
     * @param idPersonGroupParent
     *            the value for kom_pegr.idPersonGroupParent
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setIdPersonGroupParent(final Integer idPersonGroupParent) {
    
    
        this.idPersonGroupParent = idPersonGroupParent;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pegr.name
     * 
     * @param name
     *            the value for kom_pegr.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setName(final String name) {
    
    
        this.name = name;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pegr.personGroupKey
     * 
     * @param personGroupKey
     *            the value for kom_pegr.personGroupKey
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setPersonGroupKey(final String personGroupKey) {
    
    
        this.personGroupKey = personGroupKey;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pegr.type
     * 
     * @param type
     *            the value for kom_pegr.type
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setType(final PersonGroupType type) {
    
    
        this.type = type;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_pegr
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
        sb.append(", personGroupKey=").append(personGroupKey);
        sb.append(", description=").append(description);
        sb.append(", idPersonGroupParent=").append(idPersonGroupParent);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }
}
