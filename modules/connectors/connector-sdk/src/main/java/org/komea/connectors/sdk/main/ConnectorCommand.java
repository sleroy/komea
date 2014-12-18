package org.komea.connectors.sdk.main;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface ConnectorCommand {
	/**
	 * Provides an extra definition of the command.
	 *
	 * @return the description.
	 */
	String description() default "";

	/**
	 * Indicates the name of the command to launch.
	 *
	 * @return the name of the command
	 */
	String value();

}
