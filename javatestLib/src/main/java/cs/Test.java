package cs;

import java.util.Arrays;

import cs.productor_consumer.blockingqueue.ProductorAndConsumer;

/**
 * Created by dengfa on 2019-11-16.
 * Des:
 */
public class Test {

    public static void main(String[] args) {
        ProductorAndConsumer.getInstance().start();

        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.7f;
        System.out.println("float a == b " + (a == b));

        String[] strs = {"a", "b", "c"};
        int[] ints = {1, 2, 3};
        Integer ir = 1;
        int ir2 = 1;
        String str = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        System.out.println("ir == ir2 " + (ir == ir2));
        System.out.println("ir = " + ir);
        System.out.println("ir2 = " + ir2);
        change(str,ints,strs,ir2, ir);
        System.out.println("ir = " + ir);
        System.out.println("ir2 = " + ir2);
        System.out.println("strs = " + Arrays.toString(strs));
        System.out.println("ints = " + Arrays.toString(ints));

    }

    public static void change(String str, int[] is, String[] strs, int i, Integer ir) {
        str += "d";
        ir = 1000;
        i = 1000;
        strs[0] = "d";
        is[0] = 999;
    }
}
