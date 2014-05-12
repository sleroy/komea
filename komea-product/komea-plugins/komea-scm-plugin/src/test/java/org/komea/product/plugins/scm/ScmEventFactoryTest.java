/**
 * 
 */

package org.komea.product.plugins.scm;


import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.plugins.IPluginIntegrationService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.repository.model.ScmType;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
public class ScmEventFactoryTest extends AbstractSpringIntegrationTestCase {
    
    @Autowired
    private IEventPushService         eventPushService;
    
    @Autowired
    private IPluginIntegrationService gitPlugin;
    
    @Autowired
    private IScmRepositoryService     scmRepositoryService;
    
    /**
     * Test method for {@link org.komea.product.plugins.scm.ScmEventFactory#sendFetchFailed()}.
     */
    @Test
    public final void testSendFetchFailed() throws Exception {
    
        final ScmRepositoryDefinition scmRepositoryDefinition = new ScmRepositoryDefinition();
        scmRepositoryDefinition.setType(ScmType.GIT);
        scmRepositoryDefinition.setRepoName("GNI GNI GNI");
        final ScmEventFactory scmEventFactory = new ScmEventFactory(scmRepositoryDefinition);
        final EventSimpleDto sendFetchFailed = scmEventFactory.sendFetchFailed();
        
        eventPushService.sendEventDto(sendFetchFailed);
    }
    
}
