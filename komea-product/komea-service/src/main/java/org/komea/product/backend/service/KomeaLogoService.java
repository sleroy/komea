/**
 * 
 */

package org.komea.product.backend.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class KomeaLogoService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("komea");
    
    
    
    /**
     * 
     */
    public KomeaLogoService() {
    
    
        LOGGER.info(" /$$   /$$  /$$$$$$  /$$      /$$ /$$$$$$$$  /$$$$$$ ");
        LOGGER.info("| $$  /$$/ /$$__  $$| $$$    /$$$| $$_____/ /$$__  $$");
        LOGGER.info("| $$ /$$/ | $$  \\ $$| $$$$  /$$$$| $$      | $$  \\ $$");
        LOGGER.info("| $$$$$/  | $$  | $$| $$ $$/$$ $$| $$$$$   | $$$$$$$$");
        LOGGER.info("| $$  $$  | $$  | $$| $$  $$$| $$| $$__/   | $$__  $$");
        LOGGER.info("| $$\\  $$ | $$  | $$| $$\\  $ | $$| $$      | $$  | $$");
        LOGGER.info("| $$ \\  $$|  $$$$$$/| $$ \\/  | $$| $$$$$$$$| $$  | $$");
        LOGGER.info("|__/  \\__/ \\______/ |__/     |__/|________/|__/  |__/");
        
        
    }
}
