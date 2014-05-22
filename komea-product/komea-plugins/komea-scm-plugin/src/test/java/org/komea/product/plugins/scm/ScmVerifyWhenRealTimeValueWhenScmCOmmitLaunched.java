/**
 * 
 */

package org.komea.product.plugins.scm;



import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.ScmCommitDto;
import org.komea.product.database.model.Project;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.komea.product.web.rest.api.ScmCommitController;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



public class ScmVerifyWhenRealTimeValueWhenScmCOmmitLaunched extends
        AbstractSpringWebIntegrationTestCase
{
    
    
    private static final Logger       LOGGER           =
                                                               LoggerFactory
                                                                       .getLogger(ScmVerifyWhenRealTimeValueWhenScmCOmmitLaunched.class);
    
    
    @Autowired
    private final ScmCommitController commitController = new ScmCommitController();
    
    
    @Autowired
    private IKpiQueryService          kpiValueService;
    
    
    private MockMvc                   mockMvc;
    
    
    @Autowired
    private IProjectService           projectService;
    
    @Autowired
    private WebApplicationContext     webAppContext;
    
    
    
    @Before
    public void setUp() {
    
    
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.web.rest.api.ScmCommitController#pushNewCommit(java.lang.String, java.lang.String, org.komea.product.database.dto.ScmCommitDto)}
     * .
     */
    @Test
    public void testPushNewCommit() throws Exception {
    
    
        final Project scertifyProject = projectService.getOrCreate("SCERTIFY");
        // I CHECK PROJECT IS PRESENT
        assertTrue(projectService.exists("SCERTIFY"));
        
        // WHEN I LAUNCH A HOOK WITH COMMIT
        final ScmCommitDto scmCommitDto = new ScmCommitDto();
        scmCommitDto.setMessage("This is a new commit");
        String jsonMessage;
        final ObjectMapper mapper = new ObjectMapper();
        jsonMessage = mapper.writeValueAsString(scmCommitDto);
        LOGGER.info("JSON Message {}", jsonMessage);
        
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.get("/scm/new_commit/SCERTIFY/sleroy")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
        // I OBTAIN THE REAL TIME VALUE OF A KPI
        final KpiResult realTimeValue =
                kpiValueService
                        .evaluateRealTimeValues(ScmKpiPlugin.NUMBER_COMMITS_PROJECT.getKey());
        // VALUES SHOULD NOT BE NULL
        assertNotNull(realTimeValue);
        // AND NOT EMPTY
        assertFalse(realTimeValue.isEmpty());
        // AND I SHOULD HAVE THE VALUE '1' for the projec SCERTIFY
        assertEquals(Double.valueOf(1.0d),
                realTimeValue.getDoubleValue(scertifyProject.getEntityKey()));
        
    }
}
