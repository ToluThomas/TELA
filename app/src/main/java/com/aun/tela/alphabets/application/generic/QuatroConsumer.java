package com.aun.tela.alphabets.application.generic;

/**
 * A simple generic interface able to collect four different data types and return one
 * @param <T> the data type to be returned
 * @param <U> the first data type to be consumed
 * @param <V> the second data type to be consumed
 * @param <W> the third data type to be consumed
 * @param <X> the fourth data type to be consumed
 */
public interface QuatroConsumer<T, U, V, W, X> {
    T consume(U u, V v, W w, X x);
}