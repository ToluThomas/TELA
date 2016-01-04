package com.aun.tela.alphabets.application.generic;

public interface TripleRetriever<T, U, V, W> {
    T retrieve(U u, V v, W w);
}