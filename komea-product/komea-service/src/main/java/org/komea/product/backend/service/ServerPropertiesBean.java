/**
 * 
 */

package org.komea.product.backend.service;



import java.io.File;

import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.springframework.stereotype.Component;



/**
 * @author sleroy
 *         Server properties.
 */
@Properties({
        @Property(
                key = "logfile_path",
                description = "Specify the path to access logs",
                type = String.class,
                value = "komea.log"),
        @Property(
                key = KomeaFS.STORAGE_PATH_KEY,
                description = "Path to store informations of the plugins",
                type = File.class,
                value = ".komea") })
@Component
public class ServerPropertiesBean
{
    //
}
