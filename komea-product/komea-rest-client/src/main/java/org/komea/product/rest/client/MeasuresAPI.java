
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.dto.SearchHistoricalMeasuresDto;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.MeasureHistoricalResultDto;
import org.komea.product.service.dto.MeasureResultDto;
import org.komea.product.service.dto.errors.InternalServerException;

public class MeasuresAPI extends AbstractRestCientAPI implements IMeasuresAPI {
    
    private static final String MEASURES_PATH = "measures";
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IMeasuresAPI#lastMeasuresForKpiKey(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public double lastMeasuresForKpiKey(final KpiKey _kpiKey) throws InternalServerException, ConnectException {
    
        String url = MEASURES_PATH + "/last";
        return post(url, _kpiKey, Double.class);
    }
    //
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IMeasuresAPI#lastMeasuresForKpiKeys(java.util.List)
     */
    @Override
    public List<MeasureResultDto> lastMeasuresForKpiKeys(final List<KpiKey> _kpiKeys) throws InternalServerException, ConnectException {
    
        String url = MEASURES_PATH + "/lastList";
        return post(url, _kpiKeys, new GenericType<List<MeasureResultDto>>() {
        });
    }
    
    @Override
    public List<MeasureHistoricalResultDto> historicalMeasures(final SearchHistoricalMeasuresDto _searchHistoricalMeasure)
            throws InternalServerException, ConnectException {
    
        String url = MEASURES_PATH + "/lastList";
        return post(url, _searchHistoricalMeasure, new GenericType<List<MeasureHistoricalResultDto>>() {
        });
    }
}
