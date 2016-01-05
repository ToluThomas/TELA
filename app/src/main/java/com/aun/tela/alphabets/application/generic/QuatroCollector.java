package com.aun.tela.alphabets.application.generic;

/**
 * Created by Joseph Dalughut on 29/12/15 at 10:45 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * A simple generic interface that's able to collect up to four different data types
 * @param <T> the first data type
 * @param <U> the second data type
 * @param <V> the third data type
 * @param <W> the fourth data type
 */
public interface QuatroCollector<T, U, V, W> {
    void collect(T t, U u, V v, W w);
}
