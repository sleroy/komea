package org.komea.events.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.dto.SerializerType;
import org.komea.events.serializer.jackson.JacksonByteArrayToObjectConverter;
import org.komea.events.serializer.jackson.JacksonObjectToByteArrayConverter;
import org.komea.events.serializer.kryo.KryoByteArrayToObjectConverter;
import org.komea.events.serializer.kryo.KryoObjectToByteArrayConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventsSerializer implements IEventsSerializer {

    private Function<KomeaEvent, byte[]> serializer;
    private Function<byte[], KomeaEvent> unserializer;

    @Value("${org.komea.microservices.events.storage.serializer:JACKSON}")
    private String serializerName;

    @PostConstruct
    public void init() {
        final SerializerType serializerType = SerializerType.valueOf(serializerName);
        switch (serializerType) {
            case KRYO:
                final Kryo kryo = initKryo();
                unserializer = new KryoByteArrayToObjectConverter<>(kryo,
                        KomeaEvent.class);
                serializer = new KryoObjectToByteArrayConverter<>(kryo);
                break;
            default:
                final ObjectMapper jackson = initJackson();
                unserializer = new JacksonByteArrayToObjectConverter<>(jackson,
                        KomeaEvent.class);
                serializer = new JacksonObjectToByteArrayConverter<>(jackson);
                break;
        }
    }

    @Override
    public void setSerializerType(final SerializerType serializerType) {
        this.serializerName = serializerType.name();
        init();
    }

    @Override
    public byte[] serialize(final KomeaEvent event) {
        return serializer.apply(event);
    }

    @Override
    public KomeaEvent deserialize(final byte[] serializedEvent) {
        return unserializer.apply(serializedEvent);
    }

    private ObjectMapper initJackson() {
        final JsonFactory jf = new JsonFactory();
        return new ObjectMapper(jf);
    }

    private Kryo initKryo() {
        final Kryo kryo = new Kryo();
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(HashMap.class);
        kryo.register(Map.class);
        kryo.register(HashSet.class);
        kryo.register(String.class);
        kryo.register(KomeaEvent.class);
        return kryo;
    }

}
