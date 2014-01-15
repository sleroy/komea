package org.komea.product.backend.plugin.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * This interface defines the list of events provided by a plugin.
 * 
 * @author sleroy
 * 
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.TYPE })
public @interface EventTypes {
	EventTypeDef[] value();
}
