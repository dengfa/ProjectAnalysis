package com.vincent.interview.android.t;

/**
 * Created by dengfa on 2019-12-22.
 * Des:
 */
public class Test {

    public void test() {

        TTest<String, Apple, RedApple> tTest = new TTest<>();
        tTest.setW(new RedApple());
        tTest.setW(new Apple());

    }
}
