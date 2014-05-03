/**
 * 
 */
package org.komea.product.backend.criterias;

import java.util.Comparator;

import org.komea.product.database.model.Measure;

/**
 * @author sleroy
 */
public final class MeasureDateComparator implements Comparator<Measure>
{
    
    
    @Override
    public int compare(final Measure o1, final Measure o2) {
    
    
        return o2.getDate().compareTo(o1.getDate());
    }
}