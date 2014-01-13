package org.komea.product.database.model;

import java.io.Serializable;

public class PersonGroup implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.id
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.name
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.personGroupKey
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private String personGroupKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.description
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.idPersonGroupParent
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private Integer idPersonGroupParent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.idGroupKind
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private Integer idGroupKind;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pegr.depth
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private Integer depth;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_pegr
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pegr
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public PersonGroup(Integer id, String name, String personGroupKey, String description, Integer idPersonGroupParent, Integer idGroupKind, Integer depth) {
        this.id = id;
        this.name = name;
        this.personGroupKey = personGroupKey;
        this.description = description;
        this.idPersonGroupParent = idPersonGroupParent;
        this.idGroupKind = idGroupKind;
        this.depth = depth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pegr
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public PersonGroup() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.id
     *
     * @return the value of kom_pegr.id
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.id
     *
     * @param id the value for kom_pegr.id
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.name
     *
     * @return the value of kom_pegr.name
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
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
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.personGroupKey
     *
     * @return the value of kom_pegr.personGroupKey
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public String getPersonGroupKey() {
        return personGroupKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.personGroupKey
     *
     * @param personGroupKey the value for kom_pegr.personGroupKey
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public void setPersonGroupKey(String personGroupKey) {
        this.personGroupKey = personGroupKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.description
     *
     * @return the value of kom_pegr.description
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
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
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.idPersonGroupParent
     *
     * @return the value of kom_pegr.idPersonGroupParent
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
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
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
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
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
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
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public void setIdGroupKind(Integer idGroupKind) {
        this.idGroupKind = idGroupKind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pegr.depth
     *
     * @return the value of kom_pegr.depth
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pegr.depth
     *
     * @param depth the value for kom_pegr.depth
     *
     * @mbggenerated Mon Jan 13 16:42:59 CET 2014
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}