
package org.komea.product.rest.client.api;


import java.net.ConnectException;

import javax.validation.Valid;

import org.komea.product.backend.service.kpi.KPIValueTable;
import org.komea.product.database.api.IEntity;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.errors.InternalServerException;
import org.springframework.web.bind.annotation.RequestBody;

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
     * Returns the kPI double value.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the kpi double value.
     * @throws ConnectException
     *             launch if it can't connect to the server
     */
    <T extends IEntity> KPIValueTable<T> getKpiRealTimeValues(@Valid @RequestBody final KpiKey _kpiKey) throws InternalServerException,
            ConnectException;;
    
}
