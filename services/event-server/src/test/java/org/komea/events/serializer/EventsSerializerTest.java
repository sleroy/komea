package org.komea.events.serializer;

import org.junit.Test;
import org.komea.events.dto.SerializerType;

public class EventsSerializerTest {

    @Test
    public void init() {
        EventsSerializer serializer = new EventsSerializer();
        for (final SerializerType type : SerializerType.values()) {
            serializer.setSerializerType(type);
        }
    }
}
