/**
 *
 */
package org.komea.microservices.events.configuration;

import org.hibernate.validator.constraints.NotBlank;
import org.komea.event.model.SerializerType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sleroy
 *
 */
@ConfigurationProperties(prefix = "komea.storage")
public class EventStorageSettings {

    @NotBlank
    private String serializer;

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public SerializerType getSerializerType() {
        return SerializerType.valueOf(serializer);
    }
}
