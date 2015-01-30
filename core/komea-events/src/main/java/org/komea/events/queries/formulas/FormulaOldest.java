package org.komea.events.queries.formulas;

import java.util.List;
import org.komea.events.dto.KomeaEvent;

public class FormulaOldest extends AbstractFormula {

    public FormulaOldest(final String key) {
        super(key);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        for (int i = events.size() - 1; i >= 0; i--) {
            final KomeaEvent event = events.get(i);
            final Number value = getEventValue(event);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

}
