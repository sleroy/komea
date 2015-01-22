package org.komea.event.queries.formulas;

import java.util.List;
import org.komea.event.model.KomeaEvent;

public class FormulaMostRecent extends AbstractFormula {

    public FormulaMostRecent(final String key) {
        super(key);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            final KomeaEvent event = events.get(i);
            final Number value = getEventValue(event);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

}
