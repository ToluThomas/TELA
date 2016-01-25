package com.aun.tela.alphabets.application.generic;

<<<<<<< HEAD
public interface DoubleConsumer<T, V> {
    T consumeFirst();
    V consumeSecond();
}
=======
/**
 * Created by Joseph Dalughut on 29/12/15 at 10:45 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * A simple generic Consumer interface which can collect two data types and return another data type
 * @param <T> the data type to be returned
 * @param <U> a data type to be consumed
 * @param <V> a data type to be consumed
 * @see Consumer
 */
public interface DoubleConsumer<T, U, V> {
    T consume(U u, V v);
}
>>>>>>> 6f985d95ba92fb5c71815fabe8a04fe66a0f7d7a
