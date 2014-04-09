/**
 * 
 */
package org.komea.product.backend.service.kpi;

import java.util.Comparator;

import org.komea.product.database.model.Measure;

/**
 * @author sleroy
 */
public final class DateComparator implements Comparator<Measure>
{
    
    
    @Override
    public int compare(final Measure o1, final Measure o2) {
    
    
        return o2.getDate().compareTo(o1.getDate());
    }
}