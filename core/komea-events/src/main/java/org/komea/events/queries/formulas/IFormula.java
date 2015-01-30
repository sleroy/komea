package org.komea.events.queries.formulas;

import java.util.List;
import org.komea.events.dto.KomeaEvent;

public interface IFormula {

    Number calculate(List<KomeaEvent> events);

}
