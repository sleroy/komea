
package org.komea.product.rest.client.api;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.MeasureResultDto;
import org.komea.product.service.dto.errors.InternalServerException;

/**
 * Komea rest api client to manage measures
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 7 févr. 2014
 */
public interface IMeasuresAPI {
    
    /**
     * This method get the last measure for a kpi type on an entity
     * 
     * @param _kpiKey
     *            the kpi type
     * @param _entityKey
     *            the entity
     * @return the last measure value
     * @throws ConnectException
     *             launch if it can't connect to the server
     */
    double lastMeasuresForKpiKey(final KpiKey _kpiKey) throws InternalServerException, ConnectException;
    
    /**
     * This method return a list of last measures for of list of kpiKeys
     * 
     * @param _kpiKeys
     *            the kpi key list
     * @return the measure list
     * @throws InternalServerException
     *             launch if it can't connect to the server
     * @throws ConnectException
     *             launch if exceptions happened in the server
     */
    List<MeasureResultDto> lastMeasuresForKpiKeys(final List<KpiKey> _kpiKey) throws InternalServerException, ConnectException;
}
