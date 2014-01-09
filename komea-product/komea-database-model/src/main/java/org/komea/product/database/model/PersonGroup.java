package org.komea.product.database.model;

import java.io.Serializable;

public class PersonGroup implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.idPersonGroup
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private Integer idPersonGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.name
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.key
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private String key;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.idPersonGroupParent
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private Integer idPersonGroupParent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.idGroupKind
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private Integer idGroupKind;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.description
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_pegr
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pegr
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public PersonGroup(Integer idPersonGroup, String name, String key, Integer idPersonGroupParent, Integer idGroupKind, String description) {
        this.idPersonGroup = idPersonGroup;
        this.name = name;
        this.key = key;
        this.idPersonGroupParent = idPersonGroupParent;
        this.idGroupKind = idGroupKind;
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pegr
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public PersonGroup() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.idPersonGroup
     *
     * @return the value of kom_pegr.idPersonGroup
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Integer getIdPersonGroup() {
        return idPersonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.idPersonGroup
     *
     * @param idPersonGroup the value for kom_pegr.idPersonGroup
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setIdPersonGroup(Integer idPersonGroup) {
        this.idPersonGroup = idPersonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.name
     *
     * @return the value of kom_pegr.name
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.name
     *
     * @param name the value for kom_pegr.name
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.key
     *
     * @return the value of kom_pegr.key
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public String getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.key
     *
     * @param key the value for kom_pegr.key
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.idPersonGroupParent
     *
     * @return the value of kom_pegr.idPersonGroupParent
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Integer getIdPersonGroupParent() {
        return idPersonGroupParent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.idPersonGroupParent
     *
     * @param idPersonGroupParent the value for kom_pegr.idPersonGroupParent
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setIdPersonGroupParent(Integer idPersonGroupParent) {
        this.idPersonGroupParent = idPersonGroupParent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.idGroupKind
     *
     * @return the value of kom_pegr.idGroupKind
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public Integer getIdGroupKind() {
        return idGroupKind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.idGroupKind
     *
     * @param idGroupKind the value for kom_pegr.idGroupKind
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setIdGroupKind(Integer idGroupKind) {
        this.idGroupKind = idGroupKind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.description
     *
     * @return the value of kom_pegr.description
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.description
     *
     * @param description the value for kom_pegr.description
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }
}