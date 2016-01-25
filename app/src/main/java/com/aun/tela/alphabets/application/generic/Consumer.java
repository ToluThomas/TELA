package com.aun.tela.alphabets.application.generic;

/**
 * A simple generic Consumer interface which returns a data type by consuming another
 * @param <T> the data type to be returned
 * @param <V> the data type to be consumed
 */
public interface Consumer<T, V> {
    T consume(V v);
}
