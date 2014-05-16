/**
 * 
 */

package org.komea.product.backend.service.kpi;

import java.io.Serializable;
import java.util.List;

import org.komea.product.model.timeserie.TimeCoordinate;
import org.komea.product.service.dto.EntityKey;

/**
 * @author sleroy
 */
public interface TimeSerie extends Serializable {

	/**
	 * Returns the coordinates
	 * 
	 * @return the list of coordinates.
	 */
	List<TimeCoordinate> getCoordinates();

	/**
	 * Returns the entity key used to produced the time serie.
	 * 
	 * @return the entity key.
	 */
	EntityKey getEntityKey();

}
