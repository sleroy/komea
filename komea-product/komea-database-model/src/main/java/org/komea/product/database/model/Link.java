package org.komea.product.database.model;

import java.io.Serializable;

public class Link implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.idLink
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private Integer idLink;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.name
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.url
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.idProject
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private Integer idProject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_link
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Link(Integer idLink, String name, String url, Integer idProject) {
        this.idLink = idLink;
        this.name = name;
        this.url = url;
        this.idProject = idProject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Link() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.idLink
     *
     * @return the value of kom_link.idLink
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Integer getIdLink() {
        return idLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.idLink
     *
     * @param idLink the value for kom_link.idLink
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setIdLink(Integer idLink) {
        this.idLink = idLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.name
     *
     * @return the value of kom_link.name
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.name
     *
     * @param name the value for kom_link.name
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.url
     *
     * @return the value of kom_link.url
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.url
     *
     * @param url the value for kom_link.url
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.idProject
     *
     * @return the value of kom_link.idProject
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public Integer getIdProject() {
        return idProject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.idProject
     *
     * @param idProject the value for kom_link.idProject
     *
     * @mbggenerated Thu Jan 09 18:27:42 CET 2014
     */
    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }
}