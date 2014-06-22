/**
 *
 */

package org.komea.product.plugins.bugzilla.service;



import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j2bugzilla.rpc.GetLegalValues.Fields;



/**
 * @author sleroy
 */
public class BugZillaInformationService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BugZillaInformationService.class);
    
    
    
    public void displayInformations(final BZServerConfiguration configuration) {
    
    
        final BZServerProxyFactory bzServerProxyFactory = new BZServerProxyFactory();
        final IBZServerProxy connector = bzServerProxyFactory.newConnector(configuration);
        LOGGER.info("Priorities {}", connector.getPriorities());
        LOGGER.info("Severities {}", connector.getSeverities());
        LOGGER.info("Products {}", connector.getProductNames());
        LOGGER.info("Resolution {}", connector.GetLegalValues(Fields.RESOLUTION));
        LOGGER.info("Priority {}", connector.GetLegalValues(Fields.PRIORITY));
        LOGGER.info("Status {}", connector.GetLegalValues(Fields.STATUS));
        LOGGER.info("Component {}", connector.GetLegalValues(Fields.COMPONENT));
        
    }
}
