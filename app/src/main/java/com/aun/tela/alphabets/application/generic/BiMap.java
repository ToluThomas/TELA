package com.aun.tela.alphabets.application.generic;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic Map implementation which is aware of it's keys
 * To avoid unexpected behaviour, make sure that both keys and values are all unique. In the case of duplicate values,
 * the method getKey({@param<V>} value) would return the key of the last duplicate value. This is not vice-versa for
 *                                    getValue({@param<K>} key) however
 * @param <K> the key type
 * @param <V> the value type
 */
public class BiMap<K, V> extends HashMap<K, V> {

    private Map<V, K> mirror = new HashMap<>(); //a map backing the values with their keys.

    @Override
    public V put(K key, V value) {
        mirror.put(value, key);
        return super.put(key, value);
    }

    /**
     * Get the key tied to this value
     * @param value
     * @return
     */
    public K getKey(V value){
        return mirror.get(value);
    }

    @Override
    public V remove(Object key) {
        mirror.remove(mirror.get(key));
        return super.remove(key);
    }
}
