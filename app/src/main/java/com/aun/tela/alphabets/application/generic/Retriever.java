package com.aun.tela.alphabets.application.generic;

/**
 * Created by Joseph Dalughut on 29/12/15 at 10:45 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * A simple generic interface that retrieves a value
 * @param <T> the data type to be retrieved
 */
public interface Retriever<T> {
    T retrieve();
}
