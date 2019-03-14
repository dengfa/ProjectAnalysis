package com.example.javatestlib;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MyClass {

    public static void main(String[] args) {
        System.out.print("Hello Android Studio!");
    }

    class Solution {

        public int[] maxSlidingWindow(int[] nums, int k) {
            int[] result = new int[]{};
            //大顶堆
            PriorityQueue<Integer> window = new PriorityQueue<>(k, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            if (nums == null)
                return result;
            for (int i = 0; i < nums.length; i++) {
                if (window.size() < k) {
                    window.offer(nums[i]);
                } else {
                    nums[i - k] = window.peek();
                }
            }
            return result;
        }

        public int[] maxSlidingWindow2(int[] nums, int k) {
            int[] result = new int[]{};
            ArrayDeque<Integer> window = new ArrayDeque<>(k);
            for (int i = 0; i < nums.length; i++) {
                if (i >= k && window.peekFirst() <= i - k) {
                    window.pollFirst();
                    while (window.peekFirst() < window.peekLast()) {
                        window.pollFirst();
                    }
                    result[i - k] = window.peekFirst();
                }else {
                    window.offerLast(i);
                }
            }

            return result;
        }
    }
}
