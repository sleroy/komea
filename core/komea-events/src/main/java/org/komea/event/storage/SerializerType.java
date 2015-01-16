package org.komea.event.storage;

public enum SerializerType {

    JACKSON, KRYO;

    public static final SerializerType DEFAULT_SERIALIZER = SerializerType.JACKSON;
}
