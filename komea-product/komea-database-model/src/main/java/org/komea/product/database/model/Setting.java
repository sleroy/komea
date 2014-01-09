package org.komea.product.database.model;

import java.io.Serializable;

public class Setting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_setting.idSetting
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private Integer idSetting;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_setting.key
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String key;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_setting.value
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_setting
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_setting
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Setting(Integer idSetting, String key, String value) {
        this.idSetting = idSetting;
        this.key = key;
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_setting
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Setting() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_setting.idSetting
     *
     * @return the value of kom_setting.idSetting
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Integer getIdSetting() {
        return idSetting;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_setting.idSetting
     *
     * @param idSetting the value for kom_setting.idSetting
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setIdSetting(Integer idSetting) {
        this.idSetting = idSetting;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_setting.key
     *
     * @return the value of kom_setting.key
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_setting.key
     *
     * @param key the value for kom_setting.key
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_setting.value
     *
     * @return the value of kom_setting.value
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_setting.value
     *
     * @param value the value for kom_setting.value
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setValue(String value) {
        this.value = value;
    }
}