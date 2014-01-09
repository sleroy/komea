package org.komea.product.database.model;

import java.io.Serializable;

public class Provider implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.idProvider
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private Integer idProvider;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.key
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String key;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.name
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.icon
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.url
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_pvd
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Provider(Integer idProvider, String key, String name, String icon, String url) {
        this.idProvider = idProvider;
        this.key = key;
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Provider() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.idProvider
     *
     * @return the value of kom_pvd.idProvider
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Integer getIdProvider() {
        return idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.idProvider
     *
     * @param idProvider the value for kom_pvd.idProvider
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.key
     *
     * @return the value of kom_pvd.key
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.key
     *
     * @param key the value for kom_pvd.key
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.name
     *
     * @return the value of kom_pvd.name
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.name
     *
     * @param name the value for kom_pvd.name
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.icon
     *
     * @return the value of kom_pvd.icon
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.icon
     *
     * @param icon the value for kom_pvd.icon
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.url
     *
     * @return the value of kom_pvd.url
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.url
     *
     * @param url the value for kom_pvd.url
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setUrl(String url) {
        this.url = url;
    }
}