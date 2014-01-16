
package org.komea.product.web.rest.api;


import org.komea.product.backend.service.IPluginIntegrationService;
import org.komea.product.database.dto.ProviderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/providers")
public class ProvidersController
{
    
    private static final Logger       LOGGER = LoggerFactory.getLogger(ProvidersController.class);
    
    @Autowired
    private IPluginIntegrationService pluginService;
    
    /**
     * This method register an external provider into komea.
     * After this, providers can use komea to send alert
     * 
     * @param _provider
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void registerProvider(@RequestBody final ProviderDto _provider) {
    
        LOGGER.debug("call rest method /providers/register");
        pluginService.registerProvider(_provider);
    }
    
}
