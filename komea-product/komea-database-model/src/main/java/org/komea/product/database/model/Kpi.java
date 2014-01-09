package org.komea.product.database.model;

import java.io.Serializable;

public class Kpi implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.id
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.key
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private String key;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.name
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.idProvider
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private Integer idProvider;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.minValue
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private Double minValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.maxValue
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private Double maxValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.valueDirection
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private Integer valueDirection;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.valueType
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private Integer valueType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.entityType
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private Integer entityType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Kpi(Integer id, String key, String name, Integer idProvider, Double minValue, Double maxValue, Integer valueDirection, Integer valueType, Integer entityType) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.idProvider = idProvider;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.valueDirection = valueDirection;
        this.valueType = valueType;
        this.entityType = entityType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Kpi() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.id
     *
     * @return the value of kom_kpi.id
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.id
     *
     * @param id the value for kom_kpi.id
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.key
     *
     * @return the value of kom_kpi.key
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public String getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.key
     *
     * @param key the value for kom_kpi.key
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.name
     *
     * @return the value of kom_kpi.name
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.name
     *
     * @param name the value for kom_kpi.name
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.idProvider
     *
     * @return the value of kom_kpi.idProvider
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Integer getIdProvider() {
        return idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.idProvider
     *
     * @param idProvider the value for kom_kpi.idProvider
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.minValue
     *
     * @return the value of kom_kpi.minValue
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Double getMinValue() {
        return minValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.minValue
     *
     * @param minValue the value for kom_kpi.minValue
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.maxValue
     *
     * @return the value of kom_kpi.maxValue
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Double getMaxValue() {
        return maxValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.maxValue
     *
     * @param maxValue the value for kom_kpi.maxValue
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.valueDirection
     *
     * @return the value of kom_kpi.valueDirection
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Integer getValueDirection() {
        return valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.valueDirection
     *
     * @param valueDirection the value for kom_kpi.valueDirection
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setValueDirection(Integer valueDirection) {
        this.valueDirection = valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.valueType
     *
     * @return the value of kom_kpi.valueType
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Integer getValueType() {
        return valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.valueType
     *
     * @param valueType the value for kom_kpi.valueType
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.entityType
     *
     * @return the value of kom_kpi.entityType
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public Integer getEntityType() {
        return entityType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.entityType
     *
     * @param entityType the value for kom_kpi.entityType
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    /**
     * This method was generated.
     * This method returns the value of the database column kom_kpi.entityType
     *
     * @return the value of kom_kpi.entityType
     */
    public org.komea.product.database.enums.EntityType getEntityTypeEnum() {
        return org.komea.product.database.enums.EntityType.values()[entityType];
    }

    /**
     * This method was generated.
     * This method sets the value of the database column kom_kpi.entityType
     *
     * @param entityType the value for kom_kpi.entityType
     */
    public void setEntityType(org.komea.product.database.enums.EntityType entityType) {
        this.entityType = entityType.ordinal();
    }

    /**
     * This method was generated.
     * This method returns the value of the database column kom_kpi.valueDirection
     *
     * @return the value of kom_kpi.valueDirection
     */
    public org.komea.product.database.enums.ValueDirection getValueDirectionEnum() {
        return org.komea.product.database.enums.ValueDirection.values()[valueDirection];
    }

    /**
     * This method was generated.
     * This method sets the value of the database column kom_kpi.valueDirection
     *
     * @param valueDirection the value for kom_kpi.valueDirection
     */
    public void setValueDirection(org.komea.product.database.enums.ValueDirection valueDirection) {
        this.valueDirection = valueDirection.ordinal();
    }

    /**
     * This method was generated.
     * This method returns the value of the database column kom_kpi.valueType
     *
     * @return the value of kom_kpi.valueType
     */
    public org.komea.product.database.enums.ValueType getValueTypeEnum() {
        return org.komea.product.database.enums.ValueType.values()[valueType];
    }

    /**
     * This method was generated.
     * This method sets the value of the database column kom_kpi.valueType
     *
     * @param valueType the value for kom_kpi.valueType
     */
    public void setValueType(org.komea.product.database.enums.ValueType valueType) {
        this.valueType = valueType.ordinal();
    }
}