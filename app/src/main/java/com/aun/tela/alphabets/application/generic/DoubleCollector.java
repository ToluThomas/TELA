package com.aun.tela.alphabets.application.generic;

/**
 * Created by Joseph Dalughut on 01/01/16 at 12:51 PM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * A simple Generic Collector interface which can be implemented to pass down two values of type {@param <T>} and {@param <V>}
 * @param <T> the first value type of this interface instance
 * @param <V> the second value type of this interface instance
 */
public interface DoubleCollector<T, V> {

    void collect(T t, V v);

    void collectFirst(T t);

    void collectSecond(V v);

}
