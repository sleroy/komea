/**
 * 
 */
package org.komea.product.backend.service.olap;

import org.komea.product.model.timeserie.TimeCoordinate;
import org.komea.product.model.timeserie.table.dto.PointDTO;
import org.springframework.core.convert.converter.Converter;

/**
 * @author sleroy
 *
 */
final class TimeCoordinateToPointDTO implements Converter<TimeCoordinate, PointDTO>
{
    
    
    @Override
    public PointDTO convert(final TimeCoordinate _source) {
    
    
        return new PointDTO(_source.getDate().toDate(), _source.getValue());
    }
}