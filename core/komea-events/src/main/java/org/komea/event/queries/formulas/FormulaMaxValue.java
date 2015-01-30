package org.komea.event.queries.formulas;

import java.util.List;

import org.komea.event.model.impl.KomeaEvent;

public class FormulaMaxValue extends AbstractFormula {

    public FormulaMaxValue(final String key) {
        super(key);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        Number max = null;
        for (final KomeaEvent event : events) {
            final Number value = getEventValue(event);
            max = max(max, value);
        }
        return max;
    }

    public static Number max(final Number n1, final Number n2) {
        if (n1 == null) {
            return n2;
        } else if (n2 == null) {
            return n1;
        } else {
            return Math.max(n1.doubleValue(), n2.doubleValue());
        }
    }

}
