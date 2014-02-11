package org.komea.product.database.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;

public class Project implements IEntity, Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.id
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.projectKey
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String projectKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.name
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.description
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    @Size(min = 0, max = 2048)
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_proj.idCustomer
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private Integer idCustomer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_proj
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Project(Integer id, String projectKey, String name, String description, Integer idCustomer) {
        this.id = id;
        this.projectKey = projectKey;
        this.name = name;
        this.description = description;
        this.idCustomer = idCustomer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Project() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.id
     *
     * @return the value of kom_proj.id
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.id
     *
     * @param id the value for kom_proj.id
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.projectKey
     *
     * @return the value of kom_proj.projectKey
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public String getProjectKey() {
        return projectKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.projectKey
     *
     * @param projectKey the value for kom_proj.projectKey
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.name
     *
     * @return the value of kom_proj.name
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.name
     *
     * @param name the value for kom_proj.name
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.description
     *
     * @return the value of kom_proj.description
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.description
     *
     * @param description the value for kom_proj.description
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_proj.idCustomer
     *
     * @return the value of kom_proj.idCustomer
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public Integer getIdCustomer() {
        return idCustomer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_proj.idCustomer
     *
     * @param idCustomer the value for kom_proj.idCustomer
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
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
        sb.append(", projectKey=").append(projectKey);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", idCustomer=").append(idCustomer);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public EntityType entityType() {
        return EntityType.PROJECT;
    }
}