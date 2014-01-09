package org.komea.product.database.model;

import java.io.Serializable;

public class EventType implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.idEventType
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private Integer idEventType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.idProvider
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private Integer idProvider;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.key
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private String key;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.name
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.severity
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private Integer severity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.enabled
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private Boolean enabled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.category
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private String category;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.entityType
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private Integer entityType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.description
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_evt
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_evt
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public EventType(Integer idEventType, Integer idProvider, String key, String name, Integer severity, Boolean enabled, String category, Integer entityType, String description) {
        this.idEventType = idEventType;
        this.idProvider = idProvider;
        this.key = key;
        this.name = name;
        this.severity = severity;
        this.enabled = enabled;
        this.category = category;
        this.entityType = entityType;
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_evt
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public EventType() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.idEventType
     *
     * @return the value of kom_evt.idEventType
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public Integer getIdEventType() {
        return idEventType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.idEventType
     *
     * @param idEventType the value for kom_evt.idEventType
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setIdEventType(Integer idEventType) {
        this.idEventType = idEventType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.idProvider
     *
     * @return the value of kom_evt.idProvider
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public Integer getIdProvider() {
        return idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.idProvider
     *
     * @param idProvider the value for kom_evt.idProvider
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.key
     *
     * @return the value of kom_evt.key
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public String getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.key
     *
     * @param key the value for kom_evt.key
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.name
     *
     * @return the value of kom_evt.name
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.name
     *
     * @param name the value for kom_evt.name
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.severity
     *
     * @return the value of kom_evt.severity
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public Integer getSeverity() {
        return severity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.severity
     *
     * @param severity the value for kom_evt.severity
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.enabled
     *
     * @return the value of kom_evt.enabled
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.enabled
     *
     * @param enabled the value for kom_evt.enabled
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.category
     *
     * @return the value of kom_evt.category
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public String getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.category
     *
     * @param category the value for kom_evt.category
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.entityType
     *
     * @return the value of kom_evt.entityType
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public Integer getEntityType() {
        return entityType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.entityType
     *
     * @param entityType the value for kom_evt.entityType
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.description
     *
     * @return the value of kom_evt.description
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.description
     *
     * @param description the value for kom_evt.description
     *
     * @mbggenerated Thu Jan 09 10:55:40 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }
}