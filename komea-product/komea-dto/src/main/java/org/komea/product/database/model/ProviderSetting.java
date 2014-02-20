package org.komea.product.database.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProviderSetting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvds.id
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvds.providerSettingKey
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String providerSettingKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvds.value
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvds.idProvider
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private Integer idProvider;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvds.type
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvds.description
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @Size(min = 0, max = 2048)
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public ProviderSetting(Integer id, String providerSettingKey, String value, Integer idProvider, String type, String description) {
        this.id = id;
        this.providerSettingKey = providerSettingKey;
        this.value = value;
        this.idProvider = idProvider;
        this.type = type;
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public ProviderSetting() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvds.id
     *
     * @return the value of kom_pvds.id
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvds.id
     *
     * @param id the value for kom_pvds.id
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvds.providerSettingKey
     *
     * @return the value of kom_pvds.providerSettingKey
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public String getProviderSettingKey() {
        return providerSettingKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvds.providerSettingKey
     *
     * @param providerSettingKey the value for kom_pvds.providerSettingKey
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setProviderSettingKey(String providerSettingKey) {
        this.providerSettingKey = providerSettingKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvds.value
     *
     * @return the value of kom_pvds.value
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvds.value
     *
     * @param value the value for kom_pvds.value
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvds.idProvider
     *
     * @return the value of kom_pvds.idProvider
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public Integer getIdProvider() {
        return idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvds.idProvider
     *
     * @param idProvider the value for kom_pvds.idProvider
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvds.type
     *
     * @return the value of kom_pvds.type
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvds.type
     *
     * @param type the value for kom_pvds.type
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvds.description
     *
     * @return the value of kom_pvds.description
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvds.description
     *
     * @param description the value for kom_pvds.description
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Feb 20 01:05:41 CET 2014
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", providerSettingKey=").append(providerSettingKey);
        sb.append(", value=").append(value);
        sb.append(", idProvider=").append(idProvider);
        sb.append(", type=").append(type);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }
}