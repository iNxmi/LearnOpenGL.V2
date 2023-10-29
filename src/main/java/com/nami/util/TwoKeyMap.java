package com.nami.util;

import java.util.Map;
import java.util.TreeMap;

public record TwoKeyMap<T, U, V>(Map<T, Map<U, V>> maps) {

    public TwoKeyMap() {
        this(new TreeMap<>());
    }

    public TwoKeyMap put(T k1, U k2, V value) {
        if (!maps().containsKey(k1))
            maps().put(k1, new TreeMap<>());

        Map<U, V> map = maps().get(k1);
        if (!map.containsKey(k2))
            map.put(k2, value);

        return this;
    }

    public V get(T k1, U k2) {
        if (!maps().containsKey(k1))
            return null;

        Map<U, V> map = maps().get(k1);
        if (!map.containsKey(k2))
            return null;

        return map.get(k2);
    }

    public TwoKeyMap remove(T k1, U k2) {
        if (!maps().containsKey(k1))
            return this;

        Map<U, V> map = maps().get(k1);
        if (!map.containsKey(k2))
            map.remove(k2);

        return this;
    }

    public boolean containsKeys(T k1, U k2) {
        if (!maps().containsKey(k1))
            return false;

        Map<U, V> map = maps().get(k1);
        if (!map.containsKey(k2))
            return false;

        return true;
    }

}
