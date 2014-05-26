/**
 * 
 */

package org.komea.product.web.rest.api;



import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.ScmCommitDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



/**
 * @author sleroy
 */
public class ScmCommitControllerTest
{
    
    
    private static final Logger       LOGGER           =
                                                               LoggerFactory
                                                                       .getLogger(ScmCommitControllerTest.class);
    
    
    @InjectMocks
    private final ScmCommitController commitController = new ScmCommitController();
    
    
    @Mock
    private IEventPushService         eventEngineService;
    
    
    private MockMvc                   mockMvc;
    
    
    @Mock
    private IPersonService            personService;
    
    @Mock
    private IProjectService           projectService;
    
    
    
    @Before
    public void setUp() {
    
    
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commitController).build();
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.web.rest.api.ScmCommitController#pushNewCommit(java.lang.String, java.lang.String, org.komea.product.database.dto.ScmCommitDto)}
     * .
     */
    @Test
    public void testPushNewCommit() throws Exception {
    
    
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
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.web.rest.api.ScmCommitController#pushNewCommitInline(java.lang.String, java.lang.String, java.lang.String, int, int, int, int)}
     * .
     */
    @Test
    public void testPushNewCommitInline() throws Exception {
    
    
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/scm/new_commit_inline/project/user/message/1/2/3/4"));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.web.rest.api.ScmCommitController#pushNewCommit(java.lang.String, java.lang.String, org.komea.product.database.dto.ScmCommitDto)}
     * .
     */
    @Test
    public void testPushNewCommitLight() throws Exception {
    
    
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.get(
                        "/scm/new_commit_light/SCERTIFY/sleroy/new commit").contentType(
                        MediaType.APPLICATION_JSON));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
