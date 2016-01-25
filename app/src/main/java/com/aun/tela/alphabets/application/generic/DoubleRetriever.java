package com.aun.tela.alphabets.application.generic;


/**
 * A simple generic Retriever interface that retrieves two data types
 * @param <T> the first data type to be retrieved
 * @param <V> the second data type to be retrieved
 * @see Retriever
 */
public interface DoubleRetriever<T, V> {
    T retrieveFirst();
    V retrieveSecond();
}