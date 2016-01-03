package com.aun.tela.alphabets.application.generic;

/**
 * Created by Joseph Dalughut on 01/01/16 at 12:51 PM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public interface CollectorConsumer<T, V> {

    void collect(T t);

    V consume();

}
