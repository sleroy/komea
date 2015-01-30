package org.komea.events.queries.formulas;

import java.util.List;
import org.komea.events.dto.KomeaEvent;

public class FormulaMinValue extends AbstractFormula {

    public FormulaMinValue(final String key) {
        super(key);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        Number min = null;
        for (final KomeaEvent event : events) {
            final Number value = getEventValue(event);
            min = min(min, value);
        }
        return min;
    }

    public static Number min(final Number n1, final Number n2) {
        if (n1 == null) {
            return n2;
        } else if (n2 == null) {
            return n1;
        } else {
            return Math.min(n1.doubleValue(), n2.doubleValue());
        }
    }

}
