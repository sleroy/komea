package org.komea.product.database.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;

public class Setting implements IHasKey {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table kom_setting
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_setting.description
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Size(min = 0, max = 2048)
    private String description;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_setting.id
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_setting.settingKey
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String settingKey;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_setting.type
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String type;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_setting.value
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String value;

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_setting
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Setting() {

        super();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_setting
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Setting(
            final Integer id,
            final String settingKey,
            final String value,
            final String type,
            final String description) {

        this.id = id;
        this.settingKey = settingKey;
        this.value = value;
        this.type = type;
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasKey#accept(org.komea.product.database.api.IKeyVisitor)
     */
    @Override
    public void accept(final IKeyVisitor _visitor) {

        _visitor.visit(this);

    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Setting other = (Setting) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_setting.description
     *
     * @return the value of kom_setting.description
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getDescription() {

        return description;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_setting.id
     *
     * @return the value of kom_setting.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public Integer getId() {

        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_setting.settingKey
     *
     * @return the value of kom_setting.settingKey
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getSettingKey() {

        return settingKey;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_setting.type
     *
     * @return the value of kom_setting.type
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getType() {

        return type;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_setting.value
     *
     * @return the value of kom_setting.value
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getValue() {

        return value;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_setting.description
     *
     * @param description the value for kom_setting.description
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setDescription(final String description) {

        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_setting.id
     *
     * @param id the value for kom_setting.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setId(final Integer id) {

        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_setting.settingKey
     *
     * @param settingKey the value for kom_setting.settingKey
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setSettingKey(final String settingKey) {

        this.settingKey = settingKey;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_setting.type
     *
     * @param type the value for kom_setting.type
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setType(final String type) {

        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_setting.value
     *
     * @param value the value for kom_setting.value
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setValue(final String value) {

        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_setting
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", settingKey=").append(settingKey);
        sb.append(", value=").append(value);
        sb.append(", type=").append(type);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String getKey() {
        return settingKey;
    }

    @Override
    public String getDisplayName() {
        return settingKey;
    }
}
