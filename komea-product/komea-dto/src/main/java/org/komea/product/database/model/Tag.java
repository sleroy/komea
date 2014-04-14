package org.komea.product.database.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.api.IKeyVisitor;

public class Tag implements IHasKey {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table kom_tag
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_tag.id
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column kom_tag.name
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @NotNull
    @Size(min = 0, max = 255)
    private String name;

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_tag
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Tag() {

        super();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_tag
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public Tag(final Integer id, final String name) {

        this.id = id;
        this.name = name;
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
        final Tag other = (Tag) obj;
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
     * value of the database column kom_tag.id
     *
     * @return the value of kom_tag.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    @Override
    public Integer getId() {

        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column kom_tag.name
     *
     * @return the value of kom_tag.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public String getName() {

        return name;
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
     * value of the database column kom_tag.id
     *
     * @param id the value for kom_tag.id
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setId(final Integer id) {

        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column kom_tag.name
     *
     * @param name the value for kom_tag.name
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table kom_tag
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
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }

    @Override
    @JsonIgnore
    public String getKey() {
        return name;
    }

    @Override
    @JsonIgnore
    public String getDisplayName() {
        return name;
    }
}
