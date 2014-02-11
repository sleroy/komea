package org.komea.product.database.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;

public class Kpi implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.id
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.kpiKey
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String kpiKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.name
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.description
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 2048)
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.idProvider
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer idProvider;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.valueMin
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Double valueMin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.valueMax
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Double valueMax;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.valueDirection
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    private ValueDirection valueDirection;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.valueType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    private ValueType valueType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.entityType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    private EntityType entityType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.entityID
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer entityID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.cronExpression
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @Size(min = 0, max = 60)
    private String cronExpression;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.evictionRate
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    private Integer evictionRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.evictionType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    private EvictionType evictionType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_kpi.esperRequest
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 16777215)
    private String esperRequest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpi
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Kpi(Integer id, String kpiKey, String name, String description, Integer idProvider, Double valueMin, Double valueMax, ValueDirection valueDirection, ValueType valueType, EntityType entityType, Integer entityID, String cronExpression, Integer evictionRate, EvictionType evictionType, String esperRequest) {
        this.id = id;
        this.kpiKey = kpiKey;
        this.name = name;
        this.description = description;
        this.idProvider = idProvider;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.valueDirection = valueDirection;
        this.valueType = valueType;
        this.entityType = entityType;
        this.entityID = entityID;
        this.cronExpression = cronExpression;
        this.evictionRate = evictionRate;
        this.evictionType = evictionType;
        this.esperRequest = esperRequest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.kpiKey
     *
     * @return the value of kom_kpi.kpiKey
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public String getKpiKey() {
        return kpiKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.kpiKey
     *
     * @param kpiKey the value for kom_kpi.kpiKey
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setKpiKey(String kpiKey) {
        this.kpiKey = kpiKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.name
     *
     * @return the value of kom_kpi.name
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.description
     *
     * @return the value of kom_kpi.description
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.description
     *
     * @param description the value for kom_kpi.description
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.idProvider
     *
     * @return the value of kom_kpi.idProvider
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.valueMin
     *
     * @return the value of kom_kpi.valueMin
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Double getValueMin() {
        return valueMin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.valueMin
     *
     * @param valueMin the value for kom_kpi.valueMin
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setValueMin(Double valueMin) {
        this.valueMin = valueMin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.valueMax
     *
     * @return the value of kom_kpi.valueMax
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Double getValueMax() {
        return valueMax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.valueMax
     *
     * @param valueMax the value for kom_kpi.valueMax
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setValueMax(Double valueMax) {
        this.valueMax = valueMax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.valueDirection
     *
     * @return the value of kom_kpi.valueDirection
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public ValueDirection getValueDirection() {
        return valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.valueDirection
     *
     * @param valueDirection the value for kom_kpi.valueDirection
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setValueDirection(ValueDirection valueDirection) {
        this.valueDirection = valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.valueType
     *
     * @return the value of kom_kpi.valueType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public ValueType getValueType() {
        return valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.valueType
     *
     * @param valueType the value for kom_kpi.valueType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.entityType
     *
     * @return the value of kom_kpi.entityType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.entityType
     *
     * @param entityType the value for kom_kpi.entityType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.entityID
     *
     * @return the value of kom_kpi.entityID
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getEntityID() {
        return entityID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.entityID
     *
     * @param entityID the value for kom_kpi.entityID
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setEntityID(Integer entityID) {
        this.entityID = entityID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.cronExpression
     *
     * @return the value of kom_kpi.cronExpression
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.cronExpression
     *
     * @param cronExpression the value for kom_kpi.cronExpression
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.evictionRate
     *
     * @return the value of kom_kpi.evictionRate
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getEvictionRate() {
        return evictionRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.evictionRate
     *
     * @param evictionRate the value for kom_kpi.evictionRate
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setEvictionRate(Integer evictionRate) {
        this.evictionRate = evictionRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.evictionType
     *
     * @return the value of kom_kpi.evictionType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public EvictionType getEvictionType() {
        return evictionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.evictionType
     *
     * @param evictionType the value for kom_kpi.evictionType
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setEvictionType(EvictionType evictionType) {
        this.evictionType = evictionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_kpi.esperRequest
     *
     * @return the value of kom_kpi.esperRequest
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public String getEsperRequest() {
        return esperRequest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_kpi.esperRequest
     *
     * @param esperRequest the value for kom_kpi.esperRequest
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setEsperRequest(String esperRequest) {
        this.esperRequest = esperRequest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", kpiKey=").append(kpiKey);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", idProvider=").append(idProvider);
        sb.append(", valueMin=").append(valueMin);
        sb.append(", valueMax=").append(valueMax);
        sb.append(", valueDirection=").append(valueDirection);
        sb.append(", valueType=").append(valueType);
        sb.append(", entityType=").append(entityType);
        sb.append(", entityID=").append(entityID);
        sb.append(", cronExpression=").append(cronExpression);
        sb.append(", evictionRate=").append(evictionRate);
        sb.append(", evictionType=").append(evictionType);
        sb.append(", esperRequest=").append(esperRequest);
        sb.append("]");
        return sb.toString();
    }

    public String computeKPIEsperKey(IEntity _entity) {
        return "KPI_" + getKpiKey() + "_T_" + getEntityType().ordinal() + "_ENTITY_" + _entity.getId();
    }

    public String getCronHistoryJobName(IEntity _entity) {
        return "HISTORY_" + computeKPIEsperKey(_entity);
    }

    public Kpi(Integer _id, String _kpiKey, String _name, String _description, Integer _idProvider, Double _valueMin, Double _valueMax, ValueDirection _valueDirection, ValueType _valueType, EntityType _entityType, Integer _entityID, String _cronExpression, Integer _evictionRate, EvictionType _evictionType) {
        this.id = _id;
        this.kpiKey = _kpiKey;
        this.name = _name;
        this.description = _description;
        this.idProvider = _idProvider;
        this.valueMin = _valueMin;
        this.valueMax = _valueMax;
        this.valueDirection = _valueDirection;
        this.valueType = _valueType;
        this.entityType = _entityType;
        this.entityID = _entityID;
        this.cronExpression = _cronExpression;
        this.evictionRate = _evictionRate;
        this.evictionType = _evictionType;
    }
}