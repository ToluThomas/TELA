package com.aun.tela.alphabets.application.generic;

/**
 * A simple generic interface that retrieves three different data types
 * @param <T> the first data type to be retrieved
 * @param <U> the second data type to be retrieved
 * @param <V> the thrid data type to be retrieved
 */
public interface TripleRetriever<T, U, V> {
    T consumeFirst();
    U consumeSecond();
    V consumeThird();
}