package org.komea.product.backend.plugin.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.springframework.stereotype.Component;

/**
 * This interface defines a spring component defining a plugin (internal
 * provider) inside Komea. Several fields are required to describe the provider.
 *
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {
    ElementType.TYPE, ElementType.FIELD})
public @interface EventTypeDef {

    /**
     * This field defines the providerType of event.
     *
     * @return ProviderType
     */
    ProviderType providerType() default ProviderType.OTHER;

    /**
     * This field provides a description of the event triggered by the plugin
     *
     * @return String
     */
    String description() default "";

    /**
     * This field enables /disables the alert.
     *
     * @return boolean
     */
    boolean enabled() default true;

    /**
     * This field describes the entity associated to this alert.
     *
     * @return EntityType
     */
    EntityType entityType() default EntityType.PROJECT;

    /**
     * This field describes the key of the alert.
     *
     * @return String
     */
    String key() default "";

    /**
     * This field describes the alert name;
     *
     * @return String
     */
    String name() default "";

    /**
     * This field describes the priority of the alert.
     *
     * @return Severity
     */
    Severity severity() default Severity.MINOR;

}
