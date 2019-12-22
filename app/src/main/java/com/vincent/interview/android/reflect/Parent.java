package com.vincent.interview.android.reflect;

import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 2019-12-22.
 * Des:
 */
public class Parent {

    String pdef;
    public    String ppub;
    private   String ppri;
    protected String ppro;

    public Parent(){
        LogUtil.d("Reflect", "Parent constructor");
    }

    static {
        LogUtil.d("Reflect", "Parent 静态代码块2");
    }

    static {
        LogUtil.d("Reflect", "Parent 静态代码块1");
    }

    {
        LogUtil.d("Reflect", "Parent 代码块1");
    }

    {
        LogUtil.d("Reflect", "Parent 代码块2");
    }

    public void ppubMothd() {

    }

    private void ppriMothd() {

    }

    protected void pproMothd() {

    }
}
