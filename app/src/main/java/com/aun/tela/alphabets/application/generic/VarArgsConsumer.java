package com.aun.tela.alphabets.application.generic;

/**
 * Created by Joseph Dalughut on 29/12/15 at 10:45 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * A simple generic Consumer interface which can collect an infinite ammount of a data type and return another data type
 * @param <T> the data type to be returned
 * @param <V> the data type to be consumed
 */
public interface VarArgsConsumer<T, V> {
    T retrieve(V... vargs);
}
