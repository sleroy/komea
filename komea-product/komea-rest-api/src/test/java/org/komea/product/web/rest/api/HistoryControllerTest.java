
package org.komea.product.web.rest.api;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.TimeSerie;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.EntityStringKey;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class HistoryControllerTest
{


    @Mock
    private IEntityService    entityService;
    @InjectMocks
    private HistoryController historyController;


    @Mock
    private IKPIService       kpiService;


    private MockMvc           mockMvc;
    @Mock
    private IStatisticsAPI    statisticsAPI;



    @Before
    public void setUp() {
    
    
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();

    }


    @Test
    public void testCheckSerializationHIstory() throws Exception {
    
    
        // Check the serialization of history
        final IEntity entity = mock(IEntity.class);
        when(kpiService.selectByKeyOrFail(Mockito.anyString())).thenReturn(new Kpi());
        when(entity.getEntityKey()).thenReturn(EntityKey.of(EntityType.DEPARTMENT));
        final EntityStringKey entityStringKey = Mockito.any(EntityStringKey.class);
        when(entityService.findEntityByEntityStringKey(entityStringKey)).thenReturn(entity);
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.get("/history/getwhole/1/KPI/12042014"));

        httpRequest.andDo(MockMvcResultHandlers.print());

        // httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        when(
                statisticsAPI.buildTimeSeries(Mockito.any(TimeSerieOptions.class),
                        Mockito.any(EntityKey.class))).thenReturn(mock(TimeSerie.class));
    }

}
