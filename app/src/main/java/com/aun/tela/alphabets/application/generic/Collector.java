package com.aun.tela.alphabets.application.generic;

/**
 * Created by Joseph Dalughut on 01/01/16 at 12:51 PM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

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
