/**
 *
 */
package org.komea.event.generator.impl;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.komea.event.generator.IEventArrayDefinition;
import org.komea.event.generator.IEventDefinition;
import org.komea.event.generator.KpiRange;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.komea.event.utils.date.impl.IntervalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 */
public class EventGenerator {

    private final IEventDefinition definition;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EventGenerator.class);

    private final IEventStorage storage;

    public EventGenerator(final IEventStorage _storage,
            final IEventArrayDefinition _definition) {
        this(_storage, new ArrayEventDefinition(_definition));
    }

    public EventGenerator(final IEventStorage _storage,
            final IEventDefinition _definition) {
        storage = _storage;
        definition = _definition;

    }

    /**
     * Generates a list of events
     *
     * @param _numberOfEventsPerDay
     * @param _interval
     * @param _numberOfIntervals
     * @return
     */
    public int generate(final int _numberOfEventsPerDay,
            final KpiRange _interval, final Interval _period) {
        DateTime iter = _period.getStart();
        int res = 0;
        while (iter.isBefore(_period.getEnd())) {
            for (int j = 0; j < _numberOfEventsPerDay; ++j) {
                final KomeaEvent flatEvent = new KomeaEvent();
                flatEvent.setDateTime(iter);
                definition.decorateEvent(iter, j, flatEvent);
                storage.storeEvent(flatEvent);
                ++res;
            }

            iter = IntervalUtils.plus(iter, _interval);
        }
        return res;
    }

}
