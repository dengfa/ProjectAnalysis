package com.vincent.interview.android.reflect;

import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 2019-12-22.
 * Des:
 */
public class Son extends Parent implements IInterface {

    String pdef;

    public    String ppub;
    private   String ppri;
    protected String ppro;

    /**
     * 执行顺序
     * Parent 静态代码块2
     * Parent 静态代码块1
     * son 静态代码块2
     * son 静态代码块1
     * Parent 代码块1
     * Parent 代码块2
     * Parent constructor
     * son 代码块1
     * son 代码块2
     * son constructor
     */

    public Son() {
        LogUtil.d("Reflect", "son constructor");
    }

    static {
        LogUtil.d("Reflect", "son 静态代码块2");
    }

    static {
        LogUtil.d("Reflect", "son 静态代码块1");
    }

    {
        LogUtil.d("Reflect", "son 代码块1");
    }

    {
        LogUtil.d("Reflect", "son 代码块2");
    }

    @Override
    public void getStr() {

    }

    public void spubMothd() {

    }

    private void spriMothd() {

    }

    protected void sproMothd() {

    }
}
