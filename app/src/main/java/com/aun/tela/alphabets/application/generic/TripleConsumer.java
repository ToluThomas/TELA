package com.aun.tela.alphabets.application.generic;

public interface TripleConsumer<T, U, V> {
    T consumeFirst();
    U consumeSecond();
    V consumeThird();
}