
package org.komea.product.web.rest.api;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.service.dto.ManyHistoricalMeasureRequest;
import org.komea.product.service.dto.HistoryStringKeyList;
import org.komea.product.service.dto.LimitCriteria;
import org.komea.product.service.dto.MeasureResult;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;

public class MeasureControllerTest extends AbstractSpringWebIntegrationTestCase {
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @Autowired
    @InjectMocks
    private MeasuresController    measureController;
    
    @Mock
    private IKpiAPI               service;
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
        
    }
    
    @Ignore("deprecated")
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
        ManyHistoricalMeasureRequest request = new ManyHistoricalMeasureRequest();
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
        ManyHistoricalMeasureRequest request = new ManyHistoricalMeasureRequest(history, limit);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        ManyHistoricalMeasureRequest res = IntegrationTestUtil.convertJSONToObject(jsonMessage, ManyHistoricalMeasureRequest.class);
        Assert.assertNotNull(res);
        // Assert.assertEquals(Integer.MAX_VALUE, res.getLimit().getLimitNumber().intValue());
    }
}
