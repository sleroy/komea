/**
 *
 */

package org.komea.product.backend.csv.service;



import org.springframework.core.convert.converter.Converter;

import com.google.common.base.Predicate;



/**
 * @author sleroy
 */
public class CSVConverter
{


    /**
     * @param _csvRemainingColumnsConverter
     */
    public void convertRemaininColumns(
            final CSVRemainingColumnsConverter _csvRemainingColumnsConverter) {


        // TODO Auto-generated method stub

    }
    
    
    public void defineColumns(final String... columns) {


        // TODO Auto-generated method stub

    }


    /**
     * @param _string
     * @param _class
     */
    public void defineColumnType(final String _string, final Class<?> _class) {
    
    
        //

    }
    
    
    public void defineColumnType(final String _string, final Converter<String, ?> _converter) {


        //
        
    }


    /**
     * @param _string
     * @param _string2
     */
    public void filterColumns(final String... _columns) {


        // TODO Auto-generated method stub

    }
    
    
    /**
     * @param _predicate
     */
    public void filterRows(final Predicate<CSVEntry> _predicate) {


        // TODO Auto-generated method stub

    }


    /**
     * @param _string
     * @param _string2
     */
    public void filterRowsWithValue(final String _columnName, final String _value) {
    
    
        // TODO Auto-generated method stub
        
    }
    
    
    /**
     * @param _columnName
     */
    public void groupBy(final String _columnName) {
    
    
        // TODO Auto-generated method stub
        
    }
    
}
