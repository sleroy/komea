/**
 *
 */

package org.komea.product.backend.csv.service;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.database.dto.KpiResult;



/**
 * @author sleroy
 */
public class CSVRowKpi implements IQuery<KpiResult>
{
    
    
    /**
     * @param _i
     */
    public void beginCol(final int _i) {


        // TODO Auto-generated method stub

    }
    
    
    /**
     * @param _i
     */
    public void beginRow(final int _i) {
    
    
        // TODO Auto-generated method stub
        
    }


    public void defineColumns(final String... columns) {


        // TODO Auto-generated method stub

    }
    
    
    /**
     * @param _string
     * @param _class
     */
    public void defineColumnType(final String _string, final Class<Double> _class) {


        // TODO Auto-generated method stub

    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
     */
    @Override
    public BackupDelay getBackupDelay() {
    
    
        // TODO Auto-generated method stub
        return null;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getResult()
     */
    @Override
    public KpiResult getResult() {
    
    
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * @param _string
     */
    public void loadFromFile(final String _string) {


        // TODO Auto-generated method stub

    }
    
    
    /**
     * @param _csvConverter
     */
    public void setCsvConverter(final CSVConverter _csvConverter) {


        // TODO Auto-generated method stub

    }
    
}
