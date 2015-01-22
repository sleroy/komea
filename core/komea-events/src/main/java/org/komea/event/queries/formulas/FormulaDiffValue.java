package org.komea.event.queries.formulas;

import java.util.List;
import org.komea.event.model.KomeaEvent;

public class FormulaDiffValue extends AbstractFormula {

    public FormulaDiffValue(final String key) {
        super(key);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        Number min = null;
        Number max = null;
        for (final KomeaEvent event : events) {
            final Number value = getEventValue(event);
            max = FormulaMaxValue.max(max, value);
            min = FormulaMinValue.min(min, value);
        }
        if (min == null || max == null) {
            return null;
        }
        return max.doubleValue() - min.doubleValue();
    }

}
