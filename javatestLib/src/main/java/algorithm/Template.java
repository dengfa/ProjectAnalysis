package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by dengfa on 2019-11-20.
 * Des:
 */
public class Template {

    public void UsefulMethods() {
        int[] intArray = new int[]{5, 2, 3, 4, 1};

        //构造集合
        List<String> testList = new ArrayList<String>() {{
            add("aa");
            add("bb");
            add("cc");
        }};

        List<Integer> intlist = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }};

        //使用toArray(T[] a)方法
        String[] array = testList.toArray(new String[testList.size()]);

        //array to list
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));

        List<String> list2 = new ArrayList<String>(array.length);
        Collections.addAll(list2, array);
        //sort(T[] var0, Comparator<? super T> var1)
        Arrays.sort(intArray);
    }

    //反转int    反转后有可能超过int最大值
    public int revertInt(int num) {
        int revert = 0;
        while (num != 0) {
            revert = revert * 10 + num % 10;
            num /= 10;
        }
        return revert;
    }
}
