package com.aun.tela.alphabets.application.generic;

/**
 * A simple generic Collector + Retriever interface which can be implemented to pass down a value of
 * type {@param <T>} and
 * @param <T> the data type to be collected
 * @param <V> the data type to be retrieved
 * @see Collector
 * @see Retriever
 */
public interface CollectorRetriever<T, V> {

    void collect(T t);

    V retrieve();

}
