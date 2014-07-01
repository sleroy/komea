/**
 *
 */

package org.komea.product.backend.csv.service;



import org.komea.product.database.api.IEntity;



/**
 * @author sleroy
 */
public interface IEntityResolver
{


    public IEntity resolveEntity(
            final CSVEntry _entry,
            final IEntityResolutionService _resolutionService);
}
