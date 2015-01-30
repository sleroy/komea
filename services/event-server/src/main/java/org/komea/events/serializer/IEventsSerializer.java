package org.komea.events.serializer;

import org.komea.events.dto.KomeaEvent;
import org.komea.events.dto.SerializerType;

public interface IEventsSerializer {

    byte[] serialize(KomeaEvent event);

    KomeaEvent deserialize(byte[] serializedEvent);

    void setSerializerType(SerializerType serializerType);

}
