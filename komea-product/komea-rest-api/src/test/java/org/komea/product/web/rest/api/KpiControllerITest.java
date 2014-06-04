
package org.komea.product.web.rest.api;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IKpiLoadingService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath*:/spring/application-context-test.xml", "classpath*:/spring/dispatcher-servlet-test.xml" })
@TransactionConfiguration(defaultRollback = true)
@DatabaseTearDown(value = "measures.xml", type = DatabaseOperation.DELETE_ALL)
public class KpiControllerITest extends AbstractSpringDBunitIntegrationTest {
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @Autowired
    private IKpiLoadingService    kpiLoading;
    
    @Before
    public void setUp() {
    
        kpiLoading.initLoadingService();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    //
    @Test
    @DatabaseSetup("measures.xml")
    public void testAllKpis() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/kpis/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].kpiKey", Matchers.equalToIgnoringCase("LINE_COVERAGE")));
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void testgetKpiGoals() throws Exception {
    
        KpiStringKey request = KpiStringKey.ofKpiNameAndEntityDetails("LINE_COVERAGE", EntityType.PROJECT, "KOMEA");
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/kpis/goals")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        httpRequest.andDo(MockMvcResultHandlers.print());
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].entityID", Matchers.equalTo(4)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].idKpi", Matchers.equalTo(1)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].value", Matchers.equalTo(75.0)));
        
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].kpiKey", Matchers.equalToIgnoringCase("kpi1")));
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void testgetKpiGoals_not_existing_KPI() throws Exception {
    
        KpiStringKey request = KpiStringKey.ofKpiNameAndEntityDetails("NOT_EXIST", EntityType.PROJECT, "KOMEA");
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/kpis/goals")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        httpRequest.andDo(MockMvcResultHandlers.print());
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
        
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void testgetKpiGoals_not_existing_Entity() throws Exception {
    
        KpiStringKey request = KpiStringKey.ofKpiNameAndEntityDetails("LINE_COVERAGE", EntityType.PROJECT, "NOT_EXIST");
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/kpis/goals")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        httpRequest.andDo(MockMvcResultHandlers.print());
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
        
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].kpiKey", Matchers.equalToIgnoringCase("kpi1")));
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void testgetKpiGoals_null_entity() throws Exception {
    
        KpiStringKey request = KpiStringKey.ofKpiNameAndEntityDetails(null, EntityType.PROJECT, "?OT_EXIST");
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/kpis/goals")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        httpRequest.andDo(MockMvcResultHandlers.print());
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
        
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].kpiKey", Matchers.equalToIgnoringCase("kpi1")));
    }
}
