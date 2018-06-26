package com.vincent.projectanalysis.java;

import com.vincent.projectanalysis.utils.LogUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dengfa on 18/4/26.
 */

public class Test {

    static String str = "asdf";

    public static void test() {
        firstUniqChar("cc");
        char c = str.charAt(0);
        str.toLowerCase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LogUtil.d("vincent", (int) System.currentTimeMillis() % 1000000 + "");
                }
            }
        }).run();
        myAtoi("-42");
    }

    public static int firstUniqChar(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char curChar = s.charAt(i);
            if (set.contains(curChar)) {
                continue;
            } else {
                set.add(curChar);
            }
            int lastIndex = s.lastIndexOf(curChar);
            if (lastIndex == i) {
                return i;
            }
        }
        return -1;
    }

    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a'] += 1;
        }
        for (int j = 0; j < t.length(); j++) {
            count[t.charAt(j) - 'a'] -= 1;
            if (count[t.charAt(j) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    public static int myAtoi(String str) {
        str = str.trim();
        if (str == null || str.length() < 1) {
            return 0;
        }
        int index = 0;
        boolean isNegate = false;
        if (str.charAt(0) == '+') {
            index++;
            isNegate = false;
        } else if (str.charAt(0) == '-') {
            index++;
            isNegate = true;
        }
        int num = 0;
        for (int i = index; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (curChar >= '0' && curChar <= '9') {
                if (isNegate) {
                    int i1 = Integer.MAX_VALUE + num * 10;
                    if (i1 < (curChar - '0')) {
                        num = Integer.MIN_VALUE;
                        break;
                    } else {
                        num = -(Math.abs(num) * 10) - (curChar - '0');
                    }
                } else {
                    if (Integer.MAX_VALUE - num * 10 < (curChar - '0')) {
                        num = Integer.MAX_VALUE;
                        break;
                    } else {
                        num = num * 10 + (curChar - '0');
                    }
                }
            } else {
                break;
            }
        }
        return num;
    }
}
