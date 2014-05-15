package org.komea.product.database.dto;

/**
 * This class defines the DTO for property.
 *
 * @author sleroy
 */
public class PropertyDTO {

    private String key;
    private String value;
    private String type;
    private String description;

    public PropertyDTO() {

        super();
    }

    /**
     * @param _key
     * @param _value
     * @param _type
     * @param _description
     */
    public PropertyDTO(
            final String _key,
            final String _value,
            final String _type,
            final String _description) {

        super();
        key = _key;
        value = _value;
        type = _type;
        description = _description;
    }

    public String getDescription() {

        return description;
    }

    public String getKey() {

        return key;
    }

    public String getType() {

        return type;
    }

    public String getValue() {

        return value;
    }

    public void setDescription(final String _description) {

        description = _description;
    }

    public void setKey(final String _key) {

        key = _key;
    }

    public void setType(final String _type) {

        type = _type;
    }

    public void setValue(final String _value) {

        value = _value;
    }

    @Override
    public String toString() {
        return "PropertyDTO{" + "key=" + key + ", value=" + value + ", type=" + type + ", description=" + description + '}';
    }

}
