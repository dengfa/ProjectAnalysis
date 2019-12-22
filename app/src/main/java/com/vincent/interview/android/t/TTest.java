package com.vincent.interview.android.t;

/**
 * Created by dengfa on 2019-12-22.
 * Des:
 */
public class TTest<T, M extends Fruit, V extends Apple> {

    T t;
    V v;
    M m;

    public T get(T t) {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }

    public <W extends M> void setW(W m) {
        this.m = m;
    }
}
