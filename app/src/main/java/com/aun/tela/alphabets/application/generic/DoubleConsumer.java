package com.aun.tela.alphabets.application.generic;

public interface DoubleConsumer<T, V> {
    T consumeFirst();
    V consumeSecond();
}
