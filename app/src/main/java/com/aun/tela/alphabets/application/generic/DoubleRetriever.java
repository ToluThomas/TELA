package com.aun.tela.alphabets.application.generic;

<<<<<<< HEAD
public interface DoubleRetriever<T, U, V> {
    T retrieve(U u, V v);
}
=======
/**
 * Created by Joseph Dalughut on 29/12/15 at 10:45 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

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
>>>>>>> 6f985d95ba92fb5c71815fabe8a04fe66a0f7d7a
