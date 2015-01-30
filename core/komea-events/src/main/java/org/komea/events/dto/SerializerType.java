package org.komea.events.dto;

public enum SerializerType {

    JACKSON, KRYO;

    public static final SerializerType DEFAULT_SERIALIZER = SerializerType.JACKSON;
}
