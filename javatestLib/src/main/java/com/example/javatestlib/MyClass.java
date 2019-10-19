package com.example.javatestlib;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("Hello JAVA!");
        try {
            Solution.isValid(")}{({))[{{[}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Solution.maxSlidingWindow3(new int[]{}, 0);

        int[] nums = {2, 6, 2, 8, 3, 5, 7, 11, 9, 0, 4};
        //Solution.bubbleSort(nums);
        //Solution.insertionSort(nums);
        //Solution.MergeSort(nums, 0, nums.length - 1);
        Solution.quickSort(nums, 0, nums.length - 1);
    }

    static class Solution {

        public static void bubbleSort(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                for (int j = 0; j < nums.length - i - 1; j++) {
                    if (nums[j] > nums[j + 1]) {
                        swap(nums, j, j + 1);
                    }
                }
            }
            System.out.println("bubbleSort" + Arrays.toString(nums));
        }

        public static void swap(int[] nums, int... indexs) {
            if (indexs.length == 2) {
                int temp = nums[indexs[0]];
                nums[indexs[0]] = nums[indexs[1]];
                nums[indexs[1]] = temp;
            }
        }

        public static void insertionSort(int[] nums) {
            for (int i = 1; i < nums.length; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (nums[i] < nums[j]) {
                        swap(nums, i, j);
                    }
                }
            }
            System.out.println("insertionSort: " + Arrays.toString(nums));
        }

        public static void MergeSort(int[] nums, int lo, int hi) {
            if (lo >= hi) {
                return;
            }
            int mid = (lo + hi) / 2;
            MergeSort(nums, lo, mid);
            MergeSort(nums, mid + 1, hi);
            mergeSortedArray(nums, lo, mid, hi);
        }

        public static void mergeSortedArray(int[] nums1, int lo, int mid, int hi) {
            int[] copy = nums1.clone();
            int p1 = lo;
            int p2 = mid + 1;
            int k = lo;
            while (p1 <= mid && p2 <= hi) {
                if (copy[p1] < copy[p2]) {
                    nums1[k++] = copy[p1++];
                } else {
                    nums1[k++] = copy[p2++];
                }
            }
            while (p1 <= mid) {
                nums1[k++] = copy[p1++];
            }
            System.out.println("mergeSortedArray: " + Arrays.toString(nums1));
        }

        public static void quickSort(int[] nums, int lo, int hi) {
            if (lo >= hi) {
                return;
            }
            int partition = partition(nums, lo, hi);
            quickSort(nums, lo, partition - 1);
            quickSort(nums, partition + 1, hi);
        }

        /**
         * 按随机的基准值将数组分成两块！！！
         */
        public static int partition(int[] nums, int lo, int hi) {
            int random = getRandom(lo, hi);
            System.out.println("random: " + random);
            swap(nums, random, hi);
            int i = lo, j = lo;
            while (j < hi) {
                if (nums[j] < nums[hi]) {
                    swap(nums, i++, j);
                }
                j++;
            }
            swap(nums, i, hi);
            System.out.println("quickSort: " + Arrays.toString(nums));
            return i;
        }


        //获取[m,n]之间的随机数（0<=m<=n）
        public static int getRandom(int m, int n) {
            return (int) (m + Math.random() * (n - m + 1));
        }

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
                } else {
                    window.offerLast(i);
                }
            }

            return result;
        }


        public static boolean isValid(String s) throws Exception {
            HashMap<Character, Character> checkMap = new HashMap<>();
            checkMap.put('(', ')');
            checkMap.put('[', ']');
            checkMap.put('{', '}');
            Stack<Character> checkStack = new Stack<>();
            for (char check : s.toCharArray()) {
                if (checkMap.containsKey(check)) {
                    checkStack.push(check);
                } else {
                    Character pop = checkStack.isEmpty() ? '*' : checkMap.get(checkStack.pop());
                    if (pop != check) {
                        return false;
                    }
                }
            }
            return checkStack.isEmpty();
        }

        public static int[] maxSlidingWindow3(int[] nums, int k) {
            if (nums == null || k < 1 || nums.length < k) {
                return nums;
            }
            int[] result = new int[nums.length - k + 1];
            ArrayDeque<Integer> deque = new ArrayDeque<>();
            for (int i = 0; i < nums.length; i++) {
                if (i >= k && deque.peekFirst() <= i - k) {
                    deque.pollFirst();
                }
                while (deque.size() > 0 && nums[deque.peekLast()] <= nums[i]) {
                    deque.pollLast();
                }
                deque.addLast(i);
                if (i >= k - 1) {
                    result[i - k + 1] = nums[deque.peekFirst()];
                }
            }
            return result;
        }
    }
}

