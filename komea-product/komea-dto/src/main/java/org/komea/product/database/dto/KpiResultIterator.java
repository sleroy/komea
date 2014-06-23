/**
 *
 */

package org.komea.product.database.dto;



import org.komea.product.service.dto.EntityKey;



/**
 * @author sleroy
 */
public interface KpiResultIterator
{


    public void iterate(final EntityKey _key, final Number _number);
}
