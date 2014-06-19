/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.Serializable;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;



/**
 * This interface defines a kpi and its query.
 *
 * @author sleroy
 */
public class KpiDefinition implements IQuery<KpiResult>, Serializable
{


    /**
     *
     */
    private static final long serialVersionUID = 2714853671066832899L;


    private Kpi               kpi;


    private IQuery<KpiResult> query;



    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
     */
    @Override
    public BackupDelay getBackupDelay() {


        return query.getBackupDelay();
    }


    /**
     * Returns the value of the field kpi.
     *
     * @return the kpi
     */
    public Kpi getKpi() {


        return kpi;
    }


    /**
     * Returns the value of the field query.
     *
     * @return the query
     */
    public IQuery<KpiResult> getQuery() {


        return query;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getResult()
     */
    @Override
    public KpiResult getResult() {


        return query.getResult();
    }
    
    
    /**
     * Sets the field kpi with the value of _kpi.
     *
     * @param _kpi
     *            the kpi to set
     */
    public void setKpi(final Kpi _kpi) {


        kpi = _kpi;
    }


    /**
     * Sets the field query with the value of _query.
     *
     * @param _query
     *            the query to set
     */
    public void setQuery(final IQuery<KpiResult> _query) {


        query = _query;
    }
}
