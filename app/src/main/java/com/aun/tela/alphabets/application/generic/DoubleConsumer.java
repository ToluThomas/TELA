package com.aun.tela.alphabets.application.generic;


/**
 * A simple generic Consumer interface which can collect two data types and return another data type
 * @param <T> the data type to be returned
 * @param <U> a data type to be consumed
 * @param <V> a data type to be consumed
 * @see Consumer
 */
public interface DoubleConsumer<T, U, V> {
    T consume(U u, V v);
}