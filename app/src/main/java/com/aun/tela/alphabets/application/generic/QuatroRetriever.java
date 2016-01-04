package com.aun.tela.alphabets.application.generic;

public interface QuatroRetriever<T, U, V, W, X> {
    T retrieve(U u, V v, W w, X x);
}