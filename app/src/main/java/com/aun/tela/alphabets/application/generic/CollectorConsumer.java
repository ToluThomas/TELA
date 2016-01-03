package com.aun.tela.alphabets.application.generic;

public interface CollectorConsumer<T, V> {

    void collect(T t);

    V consume();

}
