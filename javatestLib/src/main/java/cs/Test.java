package cs;

import java.util.Arrays;

/**
 * Created by dengfa on 2019-11-16.
 * Des:
 */
public class Test {

    public static void main(String[] args) {
        //ProductorAndConsumer.getInstance().start();
        //floatProblem();
        //reference();
        //split();
        //StringTest();
        autoBoxing();
    }

    private static void StringTest() {
        String x = new String("goeasyway");
        change(x);
        System.out.println(x);
    }

    private static void split() {
        String ip = "..12&8.100.&.2.3....";
        String[] split = ip.split("\\.|&");
        String[] split2 = ip.split("\\.|&", -1);//最后会补空字符串
        Integer.parseInt("128");
        System.out.println("split = " + Arrays.toString(split));
        System.out.println("split2 = " + Arrays.toString(split2));
    }

    private static void floatProblem() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.7f;
        System.out.println("float a == b " + (a == b));
    }

    private static void reference() {
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
        change(str, ints, strs, ir2, ir);
        System.out.println("ir = " + ir);
        System.out.println("ir2 = " + ir2);
        System.out.println("strs = " + Arrays.toString(strs));
        System.out.println("ints = " + Arrays.toString(ints));
    }

    public static void autoBoxing() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;

        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));

    }

    public static void change(String str, int[] is, String[] strs, int i, Integer ir) {
        str += "d";
        ir = 1000;
        i = 1000;
        strs[0] = "d";
        is[0] = 999;
    }

    public static void change(String x) {
        x = "even";
    }
}
