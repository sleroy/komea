
package org.komea.product.rest.client;


import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.dto.SearchHistoricalMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.MeasureHistoricalResultDto;
import org.komea.product.service.dto.MeasureResultDto;
import org.komea.product.service.dto.errors.InternalServerException;

import com.google.common.collect.Lists;

public class MeasuresAPIIT extends AbstractRestClientIntegrationTestCase {
    
    @Test(expected = InternalServerException.class)
    public void testLastMeasure() throws Exception {
    
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("lines", EntityType.PERSON, 1);
        List<MeasureResultDto> measures = measuresAPI.lastMeasuresForKpiKeys(Lists.newArrayList(kpiKey));
        Assert.assertEquals(12D, measures.get(0).getMeasure().getValue().doubleValue(), 0.1);
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        // Assert.assertFalse(projects.isEmpty());
    }
    @Test(expected = InternalServerException.class)
    public void testLastListMeasure() throws Exception {
    
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("lines", EntityType.PERSON, 1);
        double val = measuresAPI.lastMeasuresForKpiKey(kpiKey);
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        // Assert.assertFalse(projects.isEmpty());
    }
    
    @Test
    public void testHistoricalMeasures() throws Exception {
    
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
        
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setStart(new Date(2014, 1, 1));
        searchHistoricalMeasure.setEnd(new Date());
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        
    }
    
    @Test(expected = InternalServerException.class)
    public void testHistoricalMeasuresInvalideDate() throws Exception {
    
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
        
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setEnd(new Date(2014, 1, 1));
        searchHistoricalMeasure.setStart(new Date(2014, 2, 1));
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        
        measuresAPI.historicalMeasures(searchHistoricalMeasure);
        
    }
    
    @Test(expected = InternalServerException.class)
    public void testHistoricalMeasuresByNumbers() throws Exception {
    
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
        
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        searchHistoricalMeasure.setNumber(25);
        
        List<MeasureHistoricalResultDto> measuresRes = measuresAPI.historicalMeasures(searchHistoricalMeasure);
        
        Assert.assertFalse(measuresRes.isEmpty());
    }
    
    @Test(expected = InternalServerException.class)
    public void testHistoricalMeasuresByNegativeNumbers() throws Exception {
    
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
        
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        searchHistoricalMeasure.setNumber(-25);
        
        measuresAPI.historicalMeasures(searchHistoricalMeasure);
        
    }
}
