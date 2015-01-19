/**
 *
 */
package org.komea.microservices.events.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sleroy
 *
 */
@ConfigurationProperties(prefix = "org.komea.microservices.events.storage")
public class EventStorageSettings {

}
