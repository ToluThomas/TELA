package com.aun.tela.alphabets.application.generic;

public interface DoubleRetriever<T, U, V> {
    T retrieve(U u, V v);
}
