package org.komea.event.queries.formulas;

import java.util.List;

import org.komea.event.model.impl.KomeaEvent;

public interface IFormula {

    Number calculate(List<KomeaEvent> events);

}
