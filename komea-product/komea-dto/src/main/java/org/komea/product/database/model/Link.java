package org.komea.product.database.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Link implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.id
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.name
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.url
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_link.idProject
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer idProject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_link
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Link(Integer id, String name, String url, Integer idProject) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.idProject = idProject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Link() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.id
     *
     * @return the value of kom_link.id
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_link.id
     *
     * @param id the value for kom_link.id
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_link.name
     *
     * @return the value of kom_link.name
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
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
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_link
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
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", idProject=").append(idProject);
        sb.append("]");
        return sb.toString();
    }
}