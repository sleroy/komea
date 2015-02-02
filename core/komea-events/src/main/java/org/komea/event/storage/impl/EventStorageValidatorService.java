package org.komea.event.storage.impl;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.komea.event.model.IKomeaEvent;
import org.komea.event.model.impl.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventStorageValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventStorageValidatorService.class.getName());

    private final Map<String, List< ? extends Class<? extends Serializable>>> requiredFields = Maps.newHashMap();

    public EventStorageValidatorService() {
        requiredFields.put(IKomeaEvent.FIELD_DATE, Arrays.asList(Date.class, Long.class));
        requiredFields.put(IKomeaEvent.FIELD_EVENT_TYPE, Arrays.asList(String.class));
        requiredFields.put(IKomeaEvent.FIELD_PROVIDER, Arrays.asList(String.class));
    }

    private boolean validateClass(final Object value, final String requiredField) {
        final List<? extends Class<? extends Serializable>> expectedClasses = requiredFields.get(requiredField);
        for (final Class<? extends Serializable> expectedClass : expectedClasses) {
            if (expectedClass.isInstance(value)) {
                return true;
            }
        }
        LOGGER.error("Invalid instance of field '{}' : '{}' instead of expected classes '{}'",
                requiredField, value.getClass().getName(), expectedClasses);
        return false;
    }

    public boolean validate(final KomeaEvent event) {
        boolean valid = true;
        for (final String requiredField : requiredFields.keySet()) {
            final Object value = event.field(requiredField);
            if (!event.containsField(requiredField)) {
                LOGGER.error("Missing field '{}'", requiredField);
                valid = false;
            } else if (value == null) {
                LOGGER.error("Null value of field '{}'", requiredField);
                valid = false;
            } else if (!validateClass(value, requiredField)) {
                valid = false;
            }
        }
        return valid;
    }
}
