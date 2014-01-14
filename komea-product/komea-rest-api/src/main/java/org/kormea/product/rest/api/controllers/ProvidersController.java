
package org.kormea.product.rest.api.controllers;


import org.komea.product.database.dto.ProviderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/providers")
public class ProvidersControler
{
    
    Logger LOGGER = LoggerFactory.getLogger(ProvidersControler.class);
    
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public void registerProvider(@RequestBody final ProviderDto _provider) {
    
        this.LOGGER.debug("call rest method §providers/register to register provider {}", _provider.getProvider().getProviderKey());
        // TODO
    }
}
