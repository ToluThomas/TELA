package com.aun.tela.alphabets.application.generic;

/**
 * A simple Generic Collector interface which can be implemented to pass down a value of type {@param <T>}
 * @param <T> the value type of this interface instance
 */
public interface Collector<T> {

    /**
     * Passes down a value to a class which implements this interface
     * @param t the value to pass down
     */
    void collect(T t);
}