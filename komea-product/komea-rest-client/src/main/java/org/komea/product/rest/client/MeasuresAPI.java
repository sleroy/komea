
package org.komea.product.rest.client;


import java.net.ConnectException;

import org.komea.product.backend.service.kpi.KPIValueTable;
import org.komea.product.database.api.IEntity;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.service.dto.KpiKey;
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
    
    @Override
    public <T extends IEntity> KPIValueTable<T> getKpiRealTimeValues(final KpiKey _kpiKey) throws InternalServerException, ConnectException {
    
        String url = MEASURES_PATH + "/realtime";
        return post(url, _kpiKey, KPIValueTable.class);
    }
    
}
