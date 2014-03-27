package org.komea.product.database.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;

public class Kpi implements IHasKey {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table kom_kpi
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.cronExpression
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @Size(min = 0, max = 60)
    private String cronExpression;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.description
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 2048)
    private String description;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.entityID
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private Integer entityID;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.entityType
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private EntityType entityType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.esperRequest
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 16777215)
    private String esperRequest;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.evictionRate
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    private Integer evictionRate;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.evictionType
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    private EvictionType evictionType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.id
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.kpiKey
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String kpiKey;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.name
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String name;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.objective
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private Double objective;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.valueDirection
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    private ValueDirection valueDirection;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.valueMax
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private Double valueMax;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.valueMin
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private Double valueMin;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.valueType
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    private ValueType valueType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_kpi.providerType
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    private ProviderType providerType;

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_kpi
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Kpi() {

        super();
    }

    public Kpi(
            final Integer id,
            final String kpiKey,
            final String name,
            final String description,
            final Double valueMin,
            final Double valueMax,
            final ValueDirection valueDirection,
            final ValueType valueType,
            final EntityType entityType,
            final Integer entityID,
            final String cronExpression,
            final Integer evictionRate,
            final EvictionType evictionType,
            final Double objective,
            final ProviderType providerType) {

        this.id = id;
        this.kpiKey = kpiKey;
        this.name = name;
        this.description = description;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.valueDirection = valueDirection;
        this.valueType = valueType;
        this.entityType = entityType;
        this.entityID = entityID;
        this.cronExpression = cronExpression;
        this.evictionRate = evictionRate;
        this.evictionType = evictionType;
        this.objective = objective;
        this.providerType = providerType;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_kpi
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Kpi(
            final Integer id,
            final String kpiKey,
            final String name,
            final String description,
            final Double valueMin,
            final Double valueMax,
            final ValueDirection valueDirection,
            final ValueType valueType,
            final EntityType entityType,
            final Integer entityID,
            final String cronExpression,
            final Integer evictionRate,
            final EvictionType evictionType,
            final Double objective,
            final ProviderType providerType,
            final String esperRequest) {

        this.id = id;
        this.kpiKey = kpiKey;
        this.name = name;
        this.description = description;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.valueDirection = valueDirection;
        this.valueType = valueType;
        this.entityType = entityType;
        this.entityID = entityID;
        this.cronExpression = cronExpression;
        this.evictionRate = evictionRate;
        this.evictionType = evictionType;
        this.objective = objective;
        this.esperRequest = esperRequest;
        this.providerType = providerType;
    }

    @JsonIgnore
    @Override
    public void accept(final IKeyVisitor _visitor) {

        _visitor.visit(this);

    }

    @JsonIgnore
    public String computeKPIEsperKey() {

        final StringBuilder sb = new StringBuilder();
        sb.append("KPI_");
        sb.append(kpiKey);
        if (getEntityType() != null) {
            sb.append("_T_").append(getEntityType().name());
        }
        if (getId() != null) {
            sb.append("_ENTITY_").append(getId());
        }
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.cronExpression
     *
     * @return the value of kom_kpi.cronExpression
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public String getCronExpression() {

        return cronExpression;
    }

    @JsonIgnore
    public String getCronHistoryJobName() {

        return "HISTORY_" + computeKPIEsperKey();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.description
     *
     * @return the value of kom_kpi.description
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public String getDescription() {

        return description;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.entityID
     *
     * @return the value of kom_kpi.entityID
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Integer getEntityID() {

        return entityID;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.entityType
     *
     * @return the value of kom_kpi.entityType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public EntityType getEntityType() {

        return entityType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.esperRequest
     *
     * @return the value of kom_kpi.esperRequest
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public String getEsperRequest() {

        return esperRequest;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.evictionRate
     *
     * @return the value of kom_kpi.evictionRate
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Integer getEvictionRate() {

        return evictionRate;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.evictionType
     *
     * @return the value of kom_kpi.evictionType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public EvictionType getEvictionType() {

        return evictionType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.id
     *
     * @return the value of kom_kpi.id
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @Override
    public Integer getId() {

        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.kpiKey
     *
     * @return the value of kom_kpi.kpiKey
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public String getKpiKey() {

        return kpiKey;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.name
     *
     * @return the value of kom_kpi.name
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public String getName() {

        return name;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.objective
     *
     * @return the value of kom_kpi.objective
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Double getObjective() {

        return objective;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.valueDirection
     *
     * @return the value of kom_kpi.valueDirection
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public ValueDirection getValueDirection() {

        return valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.valueMax
     *
     * @return the value of kom_kpi.valueMax
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Double getValueMax() {

        return valueMax;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.valueMin
     *
     * @return the value of kom_kpi.valueMin
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Double getValueMin() {

        return valueMin;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.valueType
     *
     * @return the value of kom_kpi.valueType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public ValueType getValueType() {

        return valueType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_kpi.providerType
     *
     * @return the value of kom_pvd.providerType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public ProviderType getProviderType() {

        return providerType;
    }

    @JsonIgnore
    public boolean isAssociatedToEntity() {

        return entityID != null && entityType != null;
    }

    @JsonIgnore
    public boolean isAssociatedToEntityType() {

        return entityID == null && entityType != null;
    }

    @JsonIgnore
    public boolean isGlobal() {

        return entityID == null && entityType == null;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.cronExpression
     *
     * @param cronExpression the value for kom_kpi.cronExpression
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setCronExpression(final String cronExpression) {

        this.cronExpression = cronExpression;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.description
     *
     * @param description the value for kom_kpi.description
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setDescription(final String description) {

        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.entityID
     *
     * @param entityID the value for kom_kpi.entityID
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setEntityID(final Integer entityID) {

        this.entityID = entityID;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.entityType
     *
     * @param entityType the value for kom_kpi.entityType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setEntityType(final EntityType entityType) {

        this.entityType = entityType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.esperRequest
     *
     * @param esperRequest the value for kom_kpi.esperRequest
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setEsperRequest(final String esperRequest) {

        this.esperRequest = esperRequest;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.evictionRate
     *
     * @param evictionRate the value for kom_kpi.evictionRate
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setEvictionRate(final Integer evictionRate) {

        this.evictionRate = evictionRate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.evictionType
     *
     * @param evictionType the value for kom_kpi.evictionType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setEvictionType(final EvictionType evictionType) {

        this.evictionType = evictionType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.id
     *
     * @param id the value for kom_kpi.id
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setId(final Integer id) {

        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.kpiKey
     *
     * @param kpiKey the value for kom_kpi.kpiKey
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setKpiKey(final String kpiKey) {

        this.kpiKey = kpiKey;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.name
     *
     * @param name the value for kom_kpi.name
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setName(final String name) {

        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasKey#accept(org.komea.product.database.api.IKeyVisitor)
     */
    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.objective
     *
     * @param objective the value for kom_kpi.objective
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setObjective(final Double objective) {

        this.objective = objective;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.valueDirection
     *
     * @param valueDirection the value for kom_kpi.valueDirection
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setValueDirection(final ValueDirection valueDirection) {

        this.valueDirection = valueDirection;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.valueMax
     *
     * @param valueMax the value for kom_kpi.valueMax
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setValueMax(final Double valueMax) {

        this.valueMax = valueMax;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.valueMin
     *
     * @param valueMin the value for kom_kpi.valueMin
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setValueMin(final Double valueMin) {

        this.valueMin = valueMin;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.valueType
     *
     * @param valueType the value for kom_kpi.valueType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setValueType(final ValueType valueType) {

        this.valueType = valueType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_kpi.providerType
     *
     * @param providerType the value for kom_kpi.providerType
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setProviderType(final ProviderType providerType) {

        this.providerType = providerType;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_kpi
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", kpiKey=").append(kpiKey);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", valueMin=").append(valueMin);
        sb.append(", valueMax=").append(valueMax);
        sb.append(", valueDirection=").append(valueDirection);
        sb.append(", valueType=").append(valueType);
        sb.append(", entityType=").append(entityType);
        sb.append(", entityID=").append(entityID);
        sb.append(", cronExpression=").append(cronExpression);
        sb.append(", evictionRate=").append(evictionRate);
        sb.append(", evictionType=").append(evictionType);
        sb.append(", objective=").append(objective);
        sb.append(", providerType=").append(providerType);
        sb.append(", esperRequest=").append(esperRequest);
        sb.append("]");
        return sb.toString();
    }

}
