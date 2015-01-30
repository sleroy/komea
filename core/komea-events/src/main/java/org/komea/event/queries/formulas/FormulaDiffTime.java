package org.komea.event.queries.formulas;

import java.util.List;

import org.komea.event.model.impl.KomeaEvent;

public class FormulaDiffTime extends AbstractFormula {

    public FormulaDiffTime(final String key) {
        super(key);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        Number oldestValue = null;
        Number mostRecentValue = null;
        for (int i = events.size() - 1; i >= 0; i--) {
            final KomeaEvent event = events.get(i);
            final Number value = getEventValue(event);
            if (value != null) {
                oldestValue = value;
                break;
            }
        }
        for (int i = 0; i < events.size(); i++) {
            final KomeaEvent event = events.get(i);
            final Number value = getEventValue(event);
            if (value != null) {
                mostRecentValue = value;
                break;
            }
        }
        if (oldestValue == null || mostRecentValue == null) {
            return null;
        }
        return mostRecentValue.doubleValue() - oldestValue.doubleValue();
    }

}
