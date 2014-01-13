package org.komea.product.database.model;

import java.io.Serializable;

public class Tag implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_tag.id
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_tag.name
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_tag
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_tag
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_tag
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public Tag() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_tag.id
     *
     * @return the value of kom_tag.id
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_tag.id
     *
     * @param id the value for kom_tag.id
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_tag.name
     *
     * @return the value of kom_tag.name
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_tag.name
     *
     * @param name the value for kom_tag.name
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }
}