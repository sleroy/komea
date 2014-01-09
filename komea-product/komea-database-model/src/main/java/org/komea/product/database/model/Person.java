package org.komea.product.database.model;

import java.io.Serializable;

public class Person implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pe.idPerson
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private Integer idPerson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pe.idPersonGroup
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private Integer idPersonGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pe.idPersonRole
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private Integer idPersonRole;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pe.firstName
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private String firstName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pe.lastName
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private String lastName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pe.email
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pe.login
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private String login;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_pe
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pe
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Person(Integer idPerson, Integer idPersonGroup, Integer idPersonRole, String firstName, String lastName, String email, String login) {
        this.idPerson = idPerson;
        this.idPersonGroup = idPersonGroup;
        this.idPersonRole = idPersonRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pe
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Person() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pe.idPerson
     *
     * @return the value of kom_pe.idPerson
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Integer getIdPerson() {
        return idPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pe.idPerson
     *
     * @param idPerson the value for kom_pe.idPerson
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pe.idPersonGroup
     *
     * @return the value of kom_pe.idPersonGroup
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Integer getIdPersonGroup() {
        return idPersonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pe.idPersonGroup
     *
     * @param idPersonGroup the value for kom_pe.idPersonGroup
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setIdPersonGroup(Integer idPersonGroup) {
        this.idPersonGroup = idPersonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pe.idPersonRole
     *
     * @return the value of kom_pe.idPersonRole
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Integer getIdPersonRole() {
        return idPersonRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pe.idPersonRole
     *
     * @param idPersonRole the value for kom_pe.idPersonRole
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setIdPersonRole(Integer idPersonRole) {
        this.idPersonRole = idPersonRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pe.firstName
     *
     * @return the value of kom_pe.firstName
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pe.firstName
     *
     * @param firstName the value for kom_pe.firstName
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pe.lastName
     *
     * @return the value of kom_pe.lastName
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pe.lastName
     *
     * @param lastName the value for kom_pe.lastName
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pe.email
     *
     * @return the value of kom_pe.email
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pe.email
     *
     * @param email the value for kom_pe.email
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pe.login
     *
     * @return the value of kom_pe.login
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public String getLogin() {
        return login;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pe.login
     *
     * @param login the value for kom_pe.login
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setLogin(String login) {
        this.login = login;
    }
}