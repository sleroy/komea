package org.komea.product.database.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.validator.routines.UrlValidator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.ProviderType;

public class Provider implements Serializable, IHasKey  {

    private static final String REST_LOCAL_RESOURCES_PIC = "../rest/local_resources/pic/";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.id
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.providerType
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    private ProviderType providerType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.name
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.icon
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.url
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kom_pvd.description
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @Size(min = 0, max = 2048)
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_pvd
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Provider(Integer id, ProviderType providerType, String name, String icon, String url, String description) {
        this.id = id;
        this.providerType = providerType;
        this.name = name;
        this.icon = icon;
        this.url = url;
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Provider() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.id
     *
     * @return the value of kom_pvd.id
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.id
     *
     * @param id the value for kom_pvd.id
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.providerType
     *
     * @return the value of kom_pvd.providerType
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public ProviderType getProviderType() {
        return providerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.providerType
     *
     * @param providerType the value for kom_pvd.providerType
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.name
     *
     * @return the value of kom_pvd.name
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
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
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
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
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
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
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
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
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
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
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kom_pvd.description
     *
     * @return the value of kom_pvd.description
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kom_pvd.description
     *
     * @param description the value for kom_pvd.description
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Thu Mar 06 10:32:30 CET 2014
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", providerType=").append(providerType);
        sb.append(", name=").append(name);
        sb.append(", icon=").append(icon);
        sb.append(", url=").append(url);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }
	
	    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasKey#accept(org.komea.product.database.api.IKeyVisitor)
     */
    @Override
    public void accept(final IKeyVisitor _visitor) {

        _visitor.visit(this);

    }
	
	/**
     * Returns the ICON url
     *
     * @return the icon url
     */
    @JsonIgnore
    public String getIconURL() {

        if (url.startsWith("http://")) {
            return icon;
        }
        return REST_LOCAL_RESOURCES_PIC + icon;
    }
	
	/**
     * Tests if the url provider is absolute.
     *
     * @return true if the url is absolute.
     */
    @JsonIgnore
    public boolean isAbsoluteURL() {

        final String[] schemes = {
            "http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        final UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    /**
     * Tests if the url provider is absolute.
     *
     * @return true if the url is absolute.
     */
    @JsonIgnore
    public boolean isValidURL() {

        final String[] schemes = {
            "http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        final UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }


}