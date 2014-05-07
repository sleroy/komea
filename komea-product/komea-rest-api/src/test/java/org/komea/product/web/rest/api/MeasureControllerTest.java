
package org.komea.product.web.rest.api;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.service.dto.HistoricalMeasureRequest;
import org.komea.product.service.dto.HistoryStringKeyList;
import org.komea.product.service.dto.LimitCriteria;
import org.komea.product.service.dto.MeasureResult;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath*:/spring/application-context-test.xml", "classpath*:/spring/dispatcher-servlet-test.xml", })
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class MeasureControllerTest {
    
    @Autowired
    private WebApplicationContext  context;
    
    private MockMvc                mockMvc;
    
    @Autowired
    @InjectMocks
    private MeasuresController     measureController;
    
    @Mock
    private IMeasureHistoryService service;
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
        
    }
    
    @Test
    public void testFindMeasures() throws Exception {
    
        List<MeasureResult> measures = Lists.newArrayList();
        MeasureResult result = new MeasureResult();
        result.addHistoricalValue(12D, new Date());
        measures.add(result);
        Mockito.when(service.getHistoricalMeasures(Matchers.any(HistoryStringKeyList.class), Matchers.any(LimitCriteria.class)))
                .thenReturn(measures);
        
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        HistoricalMeasureRequest request = new HistoricalMeasureRequest();
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historic")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    @Test
    public void testConvertJsonToLimitCriteria() throws JsonGenerationException, JsonMappingException, IOException {
    
        HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        HistoricalMeasureRequest request = new HistoricalMeasureRequest(history, limit);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        HistoricalMeasureRequest res = IntegrationTestUtil.convertJSONToObject(jsonMessage, HistoricalMeasureRequest.class);
        Assert.assertNotNull(res);
        // Assert.assertEquals(Integer.MAX_VALUE, res.getLimit().getLimitNumber().intValue());
    }
}
