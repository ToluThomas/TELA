package com.aun.tela.alphabets.application.generic;

public interface Retriever<T, V> {
    T retrieve(V v);
}