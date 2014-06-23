/**
 *
 */

package org.komea.product.backend.plugin.api;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Defines a groovy query requiring Spring.
 *
 * @author sleroy
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {
        ElementType.FIELD })
@Inherited
public @interface RequiresSpring {

}
