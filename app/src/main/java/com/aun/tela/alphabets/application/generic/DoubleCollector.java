package com.aun.tela.alphabets.application.generic;

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
