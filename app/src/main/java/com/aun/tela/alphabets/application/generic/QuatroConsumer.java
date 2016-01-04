package com.aun.tela.alphabets.application.generic;

public interface QuatroConsumer<T, U, V, W> {
    void consume(T t, U u, V v, W w);
}