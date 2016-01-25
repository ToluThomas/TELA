package com.aun.tela.alphabets.application.generic;

<<<<<<< HEAD
public interface TripleConsumer<T, U, V> {
    T consumeFirst();
    U consumeSecond();
    V consumeThird();
}
=======
/**
 * Created by Joseph Dalughut on 29/12/15 at 10:45 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

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
>>>>>>> 6f985d95ba92fb5c71815fabe8a04fe66a0f7d7a
