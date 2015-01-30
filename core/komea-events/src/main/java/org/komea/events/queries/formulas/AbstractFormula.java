package org.komea.events.queries.formulas;

import org.komea.events.dto.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFormula implements IFormula {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFormula.class.getName());

    protected String key;

    public AbstractFormula(final String key) {
        this.key = key;
    }

    public Number getEventValue(final KomeaEvent event) {
        final Object object = event.field(key);
        if (object == null) {
            return null;
        } else if (object instanceof Number) {
            return (Number) object;
        } else if (object instanceof String) {
            final String s = (String) object;
            if (!s.isEmpty()) {
                try {
                    return Double.valueOf(s);
                } catch (NumberFormatException ex) {
                    LOGGER.error("Value cannot be cast to Number : " + object, ex);
                }
            }
        } else {
            LOGGER.error("Value is not a Number nor a String : " + object);
        }
        return null;
    }

}
