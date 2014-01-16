package org.komea.product.database.model;

import java.io.Serializable;

public class Kpi implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.id
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.kpiKey
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private String kpiKey;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.name
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.description
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.idProvider
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private Integer idProvider;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.minValue
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private Double minValue;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.maxValue
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private Double maxValue;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.valueDirection
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private Integer valueDirection;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.valueType
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private Integer valueType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.entityType
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private Integer entityType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.esperRequest
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private String esperRequest;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Kpi(Integer id, String kpiKey, String name, String description, Integer idProvider, Double minValue, Double maxValue, Integer valueDirection, Integer valueType, Integer entityType, String esperRequest) {
        this.id = id;
        this.kpiKey = kpiKey;
        this.name = name;
        this.description = description;
        this.idProvider = idProvider;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.valueDirection = valueDirection;
        this.valueType = valueType;
        this.entityType = entityType;
        this.esperRequest = esperRequest;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Kpi() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.id
     *
     * @return the value of kom_kpi.id
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.id
     *
     * @param id the value for kom_kpi.id
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.kpiKey
     *
     * @return the value of kom_kpi.kpiKey
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public String getKpiKey() {
        return kpiKey;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.kpiKey
     *
     * @param kpiKey the value for kom_kpi.kpiKey
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setKpiKey(String kpiKey) {
        this.kpiKey = kpiKey;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.name
     *
     * @return the value of kom_kpi.name
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.name
     *
     * @param name the value for kom_kpi.name
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.description
     *
     * @return the value of kom_kpi.description
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.description
     *
     * @param description the value for kom_kpi.description
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.idProvider
     *
     * @return the value of kom_kpi.idProvider
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Integer getIdProvider() {
        return idProvider;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.idProvider
     *
     * @param idProvider the value for kom_kpi.idProvider
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.minValue
     *
     * @return the value of kom_kpi.minValue
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Double getMinValue() {
        return minValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.minValue
     *
     * @param minValue the value for kom_kpi.minValue
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.maxValue
     *
     * @return the value of kom_kpi.maxValue
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Double getMaxValue() {
        return maxValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.maxValue
     *
     * @param maxValue the value for kom_kpi.maxValue
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.valueDirection
     *
     * @return the value of kom_kpi.valueDirection
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Integer getValueDirection() {
        return valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.valueDirection
     *
     * @param valueDirection the value for kom_kpi.valueDirection
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setValueDirection(Integer valueDirection) {
        this.valueDirection = valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.valueType
     *
     * @return the value of kom_kpi.valueType
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Integer getValueType() {
        return valueType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.valueType
     *
     * @param valueType the value for kom_kpi.valueType
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.entityType
     *
     * @return the value of kom_kpi.entityType
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public Integer getEntityType() {
        return entityType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.entityType
     *
     * @param entityType the value for kom_kpi.entityType
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.esperRequest
     *
     * @return the value of kom_kpi.esperRequest
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public String getEsperRequest() {
        return esperRequest;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.esperRequest
     *
     * @param esperRequest the value for kom_kpi.esperRequest
     *
     * @mbggenerated Mon Jan 13 16:47:17 CET 2014
     */
    public void setEsperRequest(String esperRequest) {
        this.esperRequest = esperRequest;
    }

    /**
     * This method was generated. This method returns the value of the database
     * column kom_kpi.entityType
     *
     * @return the value of kom_kpi.entityType
     */
    public org.komea.product.database.enums.EntityType getEntityTypeEnum() {
        return org.komea.product.database.enums.EntityType.values()[entityType];
    }

    /**
     * This method was generated. This method sets the value of the database
     * column kom_kpi.entityType
     *
     * @param entityType the value for kom_kpi.entityType
     */
    public void setEntityTypeEnum(org.komea.product.database.enums.EntityType entityType) {
        this.entityType = entityType.ordinal();
    }

    /**
     * This method was generated. This method returns the value of the database
     * column kom_kpi.valueDirection
     *
     * @return the value of kom_kpi.valueDirection
     */
    public org.komea.product.database.enums.ValueDirection getValueDirectionEnum() {
        return org.komea.product.database.enums.ValueDirection.values()[valueDirection];
    }

    /**
     * This method was generated. This method sets the value of the database
     * column kom_kpi.valueDirection
     *
     * @param valueDirection the value for kom_kpi.valueDirection
     */
    public void setValueDirectionEnum(org.komea.product.database.enums.ValueDirection valueDirection) {
        this.valueDirection = valueDirection.ordinal();
    }

    /**
     * This method was generated. This method returns the value of the database
     * column kom_kpi.valueType
     *
     * @return the value of kom_kpi.valueType
     */
    public org.komea.product.database.enums.ValueType getValueTypeEnum() {
        return org.komea.product.database.enums.ValueType.values()[valueType];
    }

    /**
     * This method was generated. This method sets the value of the database
     * column kom_kpi.valueType
     *
     * @param valueType the value for kom_kpi.valueType
     */
    public void setValueTypeEnum(org.komea.product.database.enums.ValueType valueType) {
        this.valueType = valueType.ordinal();
    }
}
