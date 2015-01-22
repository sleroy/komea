package org.komea.event.storage;

import java.io.Closeable;
import java.util.Collection;
import org.komea.event.model.DateInterval;
import org.komea.event.model.KomeaEvent;
import org.skife.jdbi.v2.ResultIterator;

public interface IEventDB extends Closeable {

    /**
     * Count the number of values
     *
     * @return the number of values.
     */
    long count();

    /**
     * Load All Values. This is good for in-memory caches that have some keys
     * that are persistent.
     *
     * @return
     */
    ResultIterator<KomeaEvent> loadAll();

    /**
     * Load All Values. This is good for in-memory caches that have some keys
     * that are persistent.
     *
     * @param _period
     * @return
     */
    ResultIterator<KomeaEvent> loadOnPeriod(DateInterval _period);

    /**
     * Puts a events into the storage.
     *
     * @param _flatEvent the flat event.
     */
    void put(KomeaEvent _flatEvent);

    /**
     * Put all values.
     *
     * @param values
     */
    void putAll(Collection<KomeaEvent> values);

    /**
     * Remove all values
     */
    void removeAll();
}
