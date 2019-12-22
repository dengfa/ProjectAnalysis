package com.vincent.interview.android.reflect;

/**
 * Created by dengfa on 2019-12-22.
 */
public interface IInterface {

    public              String pubstr            = "istr";
    public static       String pubstaticstr      = "pubstaticstr";
    public static final String pubstaticfinalstr = "pubstaticstr";
    //protected String prostr = "prostr";
    //private String pristr = "pristr";

    /**
     * 接口中不允许有代码块或者静态代码块 -> 为什么？
     * 接口中的变量默认是public static final -> 为什么？
     * 接口中的方法默认是public abstract -> 为什么？
     */
    public abstract void getStr();
}
