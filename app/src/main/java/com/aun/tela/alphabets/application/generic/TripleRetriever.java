package com.aun.tela.alphabets.application.generic;

<<<<<<< HEAD
public interface TripleRetriever<T, U, V, W> {
    T retrieve(U u, V v, W w);
}
=======
/**
 * Created by Joseph Dalughut on 29/12/15 at 10:45 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

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
>>>>>>> 6f985d95ba92fb5c71815fabe8a04fe66a0f7d7a
