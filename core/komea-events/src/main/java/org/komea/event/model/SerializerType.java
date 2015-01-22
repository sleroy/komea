package org.komea.event.model;

public enum SerializerType {

    JACKSON, KRYO;

    public static final SerializerType DEFAULT_SERIALIZER = SerializerType.JACKSON;
}
