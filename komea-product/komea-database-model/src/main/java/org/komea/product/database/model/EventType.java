package org.komea.product.database.model;

import java.io.Serializable;

public class EventType implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.id
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.idProvider
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private Integer idProvider;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.eventKey
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private String eventKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.name
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.severity
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private Integer severity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.enabled
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private Boolean enabled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.description
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.category
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private String category;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_evt.entityType
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private Integer entityType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_evt
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_evt
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public EventType(Integer id, Integer idProvider, String eventKey, String name, Integer severity, Boolean enabled, String description, String category, Integer entityType) {
        this.id = id;
        this.idProvider = idProvider;
        this.eventKey = eventKey;
        this.name = name;
        this.severity = severity;
        this.enabled = enabled;
        this.description = description;
        this.category = category;
        this.entityType = entityType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_evt
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public EventType() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.id
     *
     * @return the value of kom_evt.id
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.id
     *
     * @param id the value for kom_evt.id
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.idProvider
     *
     * @return the value of kom_evt.idProvider
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.eventKey
     *
     * @return the value of kom_evt.eventKey
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public String getEventKey() {
        return eventKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_evt.eventKey
     *
     * @param eventKey the value for kom_evt.eventKey
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.name
     *
     * @return the value of kom_evt.name
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.description
     *
     * @return the value of kom_evt.description
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_evt.category
     *
     * @return the value of kom_evt.category
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
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
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    /**
     * This method was generated.
     * This method returns the value of the database column kom_evt.severity
     *
     * @return the value of kom_evt.severity
     */
    public org.komea.product.database.enums.Severity getSeverityEnum() {
        return org.komea.product.database.enums.Severity.values()[severity];
    }

    /**
     * This method was generated.
     * This method sets the value of the database column kom_evt.severity
     *
     * @param severity the value for kom_evt.severity
     */
    public void setSeverity(org.komea.product.database.enums.Severity severity) {
        this.severity = severity.ordinal();
    }

    /**
     * This method was generated.
     * This method returns the value of the database column kom_evt.entityType
     *
     * @return the value of kom_evt.entityType
     */
    public org.komea.product.database.enums.EntityType getEntityTypeEnum() {
        return org.komea.product.database.enums.EntityType.values()[entityType];
    }

    /**
     * This method was generated.
     * This method sets the value of the database column kom_evt.entityType
     *
     * @param entityType the value for kom_evt.entityType
     */
    public void setEntityType(org.komea.product.database.enums.EntityType entityType) {
        this.entityType = entityType.ordinal();
    }
}