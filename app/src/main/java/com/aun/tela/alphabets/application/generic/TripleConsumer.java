package com.aun.tela.alphabets.application.generic;

/**
 * A simple generic interface that returns a value by consuming three different data types
 * @param <T> the data type to be returned
 * @param <U> the first data type to be consumed
 * @param <V> the second data type to be consumed
 * @param <W> the third data type to be consumed
 */
public interface TripleConsumer<T, U, V, W> {
    T consume(U u, V v, W w);
}