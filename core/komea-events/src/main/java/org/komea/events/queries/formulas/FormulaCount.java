package org.komea.events.queries.formulas;

import java.util.List;
import org.komea.events.dto.KomeaEvent;

public class FormulaCount extends AbstractFormula {

    public FormulaCount() {
        super(null);
    }

    @Override
    public Number calculate(final List<KomeaEvent> events) {
        return events.size();
    }

}
