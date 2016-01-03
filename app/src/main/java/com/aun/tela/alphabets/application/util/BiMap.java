package com.aun.tela.alphabets.application.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joseph Dalughut on 29/12/15 at 11:38 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
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
