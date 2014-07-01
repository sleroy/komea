/**
 *
 */

package org.komea.product.backend.csv.service;



/**
 * @author sleroy
 */
public interface CSVRemainingColumnsConverter
{


    public void groupColumns(final CSVEntry[] _entries, final CSVEntry _newEntry);
}
