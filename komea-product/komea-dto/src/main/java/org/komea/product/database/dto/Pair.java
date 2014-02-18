package org.komea.product.database.dto;

import java.io.Serializable;

public class Pair<K, V> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static <K, V> Pair<K, V> create(K k, V v) {
        return new Pair<K, V>(k, v);
    }

    private K key;
    private V value;

    public Pair() {
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

}
