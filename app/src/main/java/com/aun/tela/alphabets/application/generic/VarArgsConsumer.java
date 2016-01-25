package com.aun.tela.alphabets.application.generic;

/**
 * A simple generic Consumer interface which can collect an infinite ammount of a data type and return another data type
 * @param <T> the data type to be returned
 * @param <V> the data type to be consumed
 */
public interface VarArgsConsumer<T, V> {
    T retrieve(V... vargs);
}
