/**
 *
 */
package org.komea.event.queries.result.impl;

import java.util.Collections;
import java.util.List;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.result.IResultMapper;
import org.komea.event.queries.rows.IRow;
import org.komea.event.queries.rows.impl.PojoRow;

/**
 * This result mapper returns the events matching the clause as rows.
 *
 * @author sleroy
 */
public class EventResultMapper implements IResultMapper<FlatEvent> {

    /*
     * (non-Javadoc)
     * @see org.komea.event.queries.IResultMapper#begin()
     */
    @Override
    public void begin() {
    }

    /*
     * (non-Javadoc)
     * @see org.komea.event.queries.IResultMapper#end()
     */
    @Override
    public List<IRow> end() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.event.queries.IResultMapper#process(java.lang.Object)
     */
    @Override
    public List<? extends IRow> process(final FlatEvent _event) {
        return Collections.singletonList(new PojoRow(_event));
    }

}
