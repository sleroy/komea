package org.komea.product.database.dto;

import java.io.Serializable;

public class Pair<K, V> implements Serializable, Comparable<Pair<K, V>> {

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair<?, ?>)) {
            return false;
        }
        Pair<?, ?> other = (Pair<?, ?>) o;
        return key.equals(other.getKey()) && value.equals(other.getValue());
    }

    @Override
    public int hashCode() {
        return key.hashCode() * 13 + value.hashCode() * 7;
    }

    @Override
    public int compareTo(Pair<K, V> o) {
        return this.getValue().toString().toLowerCase().compareTo(
                o.getValue().toString().toLowerCase());
    }

    @Override
    public String toString() {
        return "Pair{" + "key=" + key + ", value=" + value + '}';
    }

}
