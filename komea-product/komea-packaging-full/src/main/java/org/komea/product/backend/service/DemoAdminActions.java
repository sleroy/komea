/**
 * 
 */

package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class DemoAdminActions
{
    
    
    @Autowired
    private IAdminActionServices adminActionServices;
    
    @Autowired
    private IAdminAction         scmKpiHistoryBuilder;
    
    
    
    @PostConstruct
    public void init() {
    
    
        adminActionServices.registerAdminAction(scmKpiHistoryBuilder);
        
    }
}
