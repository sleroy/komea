
package org.komea.product.database.model;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.service.dto.EntityKey;



public class Person implements IEntity
{
    
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table kom_pe
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private static final long serialVersionUID = 1L;
    
    
    
    /**
     * BUilds a new person with only a login and an email information.
     * 
     * @param _email
     * @param login
     * @return
     */
    public static Person newPersonWithOnlyEmailAndLogin(final String _email, final String login) {
    
    
        final Person personRequested = new Person();
        personRequested.setLogin(login);
        personRequested.setFirstName(personRequested.getLogin());
        personRequested.setLastName("");
        personRequested.setPassword("");
        personRequested.setUserBdd(UserBdd.KOMEA);
        personRequested.setEmail(_email);
        return personRequested;
    }
    
    
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.email
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String  email;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.firstName
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String  firstName;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.id
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer id;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.idPersonGroup
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer idPersonGroup;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.idPersonRole
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer idPersonRole;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.lastName
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String  lastName;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.login
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String  login;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.password
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String  password;
    
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_pe.userBdd
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    private UserBdd userBdd;
    
    
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_pe
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Person() {
    
    
        super();
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_pe
     * 
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Person(
            final Integer id,
            final Integer idPersonGroup,
            final Integer idPersonRole,
            final String firstName,
            final String lastName,
            final String email,
            final String login,
            final String password,
            final UserBdd userBdd) {
    
    
        this.id = id;
        this.idPersonGroup = idPersonGroup;
        this.idPersonRole = idPersonRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = password;
        this.userBdd = userBdd;
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
    
    
        return EntityType.PERSON;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public String getDisplayName() {
    
    
        return getFullName();
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.email
     * 
     * @return the value of kom_pe.email
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getEmail() {
    
    
        return email;
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
     * value of the database column kom_pe.firstName
     * 
     * @return the value of kom_pe.firstName
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getFirstName() {
    
    
        return firstName;
    }
    
    
    public String getFullName() {
    
    
        return firstName + " " + lastName;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.id
     * 
     * @return the value of kom_pe.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.idPersonGroup
     * 
     * @return the value of kom_pe.idPersonGroup
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Integer getIdPersonGroup() {
    
    
        return idPersonGroup;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.idPersonRole
     * 
     * @return the value of kom_pe.idPersonRole
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Integer getIdPersonRole() {
    
    
        return idPersonRole;
    }
    
    
    @Override
    public String getKey() {
    
    
        return login;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.lastName
     * 
     * @return the value of kom_pe.lastName
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getLastName() {
    
    
        return lastName;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.login
     * 
     * @return the value of kom_pe.login
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getLogin() {
    
    
        return login;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.password
     * 
     * @return the value of kom_pe.password
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getPassword() {
    
    
        return password;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_pe.userBdd
     * 
     * @return the value of kom_pe.userBdd
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public UserBdd getUserBdd() {
    
    
        return userBdd;
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
     * Returns if a person has a role associated.
     * 
     * @return true if the person does not have an associated.
     */
    public boolean isAssociatedToRole() {
    
    
        return idPersonRole != null;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.email
     * 
     * @param email
     *            the value for kom_pe.email
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setEmail(final String email) {
    
    
        this.email = email;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.firstName
     * 
     * @param firstName
     *            the value for kom_pe.firstName
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setFirstName(final String firstName) {
    
    
        this.firstName = firstName;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.id
     * 
     * @param id
     *            the value for kom_pe.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public void setId(final Integer id) {
    
    
        this.id = id;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.idPersonGroup
     * 
     * @param idPersonGroup
     *            the value for kom_pe.idPersonGroup
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setIdPersonGroup(final Integer idPersonGroup) {
    
    
        this.idPersonGroup = idPersonGroup;
    }
    
    
    /**
     * @param _personGroup
     */
    public void setIdPersonGroupOrNull(final PersonGroup _personGroup) {
    
    
        if (_personGroup == null) {
            setIdPersonGroup(null);
        } else {
            setIdPersonGroup(_personGroup.getId());
            ;
        }
        
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.idPersonRole
     * 
     * @param idPersonRole
     *            the value for kom_pe.idPersonRole
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setIdPersonRole(final Integer idPersonRole) {
    
    
        this.idPersonRole = idPersonRole;
    }
    
    
    /**
     * Sets the id person or null.
     * 
     * @param _personRole
     *            the person role
     */
    public void setIdPersonRoleOrNull(final PersonRole _personRole) {
    
    
        if (_personRole == null) {
            setIdPersonRole(null);
        } else {
            setIdPersonRole(_personRole.getId());
        }
        
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.lastName
     * 
     * @param lastName
     *            the value for kom_pe.lastName
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setLastName(final String lastName) {
    
    
        this.lastName = lastName;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.login
     * 
     * @param login
     *            the value for kom_pe.login
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setLogin(final String login) {
    
    
        this.login = login;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.password
     * 
     * @param password
     *            the value for kom_pe.password
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setPassword(final String password) {
    
    
        this.password = password;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_pe.userBdd
     * 
     * @param userBdd
     *            the value for kom_pe.userBdd
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setUserBdd(final UserBdd userBdd) {
    
    
        this.userBdd = userBdd;
    }
    
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_pe
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
        sb.append(", idPersonGroup=").append(idPersonGroup);
        sb.append(", idPersonRole=").append(idPersonRole);
        sb.append(", firstName=").append(firstName);
        sb.append(", lastName=").append(lastName);
        sb.append(", email=").append(email);
        sb.append(", login=").append(login);
        sb.append(", password=").append(password);
        sb.append(", userBdd=").append(userBdd);
        sb.append("]");
        return sb.toString();
    }
}
