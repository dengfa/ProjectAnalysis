package com.vincent.interview.android.reconstructure.base;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public interface IDiasplayStrategy<T, VH> {

    abstract void display(T data, VH holder);
}
