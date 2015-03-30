package org.komea.event.queries.formulas;

import java.util.List;

import org.komea.event.model.impl.KomeaEvent;

public class FormulaSum extends AbstractFormula {

    public FormulaSum(final String key) {
        super(key);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        Number sum = 0;
        for (final KomeaEvent event : events) {
            final Number value = getEventValue(event);
            if (value != null) {
                sum = sum.doubleValue() + value.doubleValue();
            }
        }
        return sum;
    }

}
