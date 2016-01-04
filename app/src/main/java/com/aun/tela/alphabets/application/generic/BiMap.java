package com.aun.tela.alphabets.application.generic;

import java.util.HashMap;
import java.util.Map;

public class BiMap<K, V> extends HashMap<K, V> {

    private Map<V, K> mirror = new HashMap<>();

    @Override
    public V put(K key, V value) {
        mirror.put(value, key);
        return super.put(key, value);
    }

    public K getKey(V value){
        return mirror.get(value);
    }

    @Override
    public V remove(Object key) {
        mirror.remove(mirror.get(key));
        return super.remove(key);
    }
}