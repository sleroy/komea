package org.komea.events.serializer;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.komea.events.dao.EventStorageException;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.dto.SerializerType;

public class EventsSerializerTest {

    @Test
    public void test() {
        EventsSerializer serializer = new EventsSerializer();
        for (final SerializerType type : SerializerType.values()) {
            serializer.setSerializerType(type);
            final KomeaEvent event = new KomeaEvent("jenkins", "builds", new Date());
            final byte[] bytes = serializer.serialize(event);
            Assert.assertTrue(bytes.length > 0);
            final KomeaEvent deserialize = serializer.deserialize(bytes);
            Assert.assertEquals(event.getDate(), deserialize.getDate());
            Assert.assertEquals(event.getProvider(), deserialize.getProvider());
            Assert.assertEquals(event.getEventType(), deserialize.getEventType());
        }
    }

    @Test(expected = EventStorageException.class)
    public void testJacksonDeserializeException() {
        final EventsSerializer serializer = new EventsSerializer();
        serializer.setSerializerType(SerializerType.JACKSON);
        serializer.deserialize(new byte[1]);
    }
}
