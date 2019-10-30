package com.example.javatestlib;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        int[] nums = {2, 0, 0, 0, 1, 1, 1, 4, 5, 6};
        Solution.bubbleSort(nums);
        //Solution.insertionSort(nums);
        //Solution.MergeSort(nums, 0, nums.length - 1);
        //Solution.quickSort(nums, 0, nums.length - 1);
    }

    static class Solution {

        public static void bubbleSort(int[] nums) {
            /*for (int i = 0; i < nums.length; i++) {
                for (int j = 0; j < nums.length - i - 1; j++) {
                    if (nums[j] > nums[j + 1]) {
                        swap(nums, j, j + 1);
                    }
                }
            }*/
            //优化：如果有一轮冒泡全都没有交换顺序，说明已经是有序的了不用再继续冒泡了！
            boolean hasSwap = true;
            for (int i = 0; i < nums.length && hasSwap; i++) {
                hasSwap = false;
                for (int j = 0; j < nums.length - i - 1; j++) {
                    if (nums[j] > nums[j + 1]) {
                        swap(nums, j, j + 1);
                        hasSwap = true;
                    }
                }
                System.out.println("bubbleSort" + Arrays.toString(nums));
            }
        }

        public static void swap(int[] nums, int... indexs) {
            if (indexs.length == 2) {
                int temp = nums[indexs[0]];
                nums[indexs[0]] = nums[indexs[1]];
                nums[indexs[1]] = temp;
            }
        }

        public static void insertionSort(int[] nums) {
            for (int i = 1, j; i < nums.length; i++) {
                int current = nums[i];
                for (j = i - 1; j >= 0 && current < nums[j]; j--) {
                    nums[j + 1] = nums[j];
                }
                nums[j + 1] = current;
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

        public static boolean isAnagram(String s, String t) {
            if (s.length() != t.length())
                return false;
            Map<Character, Integer> count = new HashMap<>();
            for (char sc : s.toCharArray()) {
                if (count.containsKey(sc)) {
                    count.put(sc, count.get(sc) + 1);
                } else {
                    count.put(sc, 1);
                }
            }
            for (char tc : t.toCharArray()) {
                if (!count.containsKey(tc) || count.get(tc) - 1 < 0) {
                    return false;
                } else {
                    count.put(tc, count.get(tc) - 1);
                }
            }
            return true;
        }

        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 1; i++) {
                if (i > 0 && nums[i] == nums[i - 1])
                    continue;
                int lo = i + 1;
                int hi = nums.length - 1;
                while (lo < hi) {
                    if (nums[lo] + nums[hi] + nums[i] == 0) {
                        result.add(Arrays.asList(nums[lo], nums[i], nums[hi]));
                        while (lo < hi && nums[lo + 1] == nums[lo])
                            lo++;
                        while (lo < hi && nums[hi - 1] == nums[hi])
                            hi--;
                    } else if (nums[lo] + nums[hi] + nums[i] > 0) {
                        hi--;
                    } else {
                        lo++;
                    }
                }
            }
            return result;
        }

        //16. 最接近的三数之和
        public int threeSumClosest(int[] nums, int target) {
            if (nums.length < 3) {
                return 0;
            }
            Arrays.sort(nums);
            int min = Math.abs(nums[0] + nums[1] + nums[2] - target);
            for (int i = 0; i < nums.length - 2; i++) {
                int lo = i + 1;
                int hi = nums.length - 1;
                while (lo < hi) {
                    int sum = nums[lo] + nums[i] + nums[hi];
                    min = Math.min(min, sum - target);
                    if (sum == target) {
                        return sum;
                    } else if (sum < target) {
                        lo++;
                    } else {
                        hi--;
                    }
                }
            }
            return min;
        }

        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(nums);
            for (int j = 0; j < nums.length - 3; j++) {
                if (j > 0 && nums[j] == nums[j - 1])
                    continue;
                int tempTarget = target - nums[j];
                for (int i = j + 1; i < nums.length - 2; i++) {
                    if (i > j + 1 && nums[i] == nums[i - 1])
                        continue;
                    int lo = i + 1;
                    int hi = nums.length - 1;
                    while (lo < hi) {
                        if (nums[lo] + nums[hi] + nums[i] == tempTarget) {
                            result.add(Arrays.asList(nums[j], nums[i], nums[lo], nums[hi]));
                            while (lo < hi && nums[lo + 1] == nums[lo])
                                lo++;
                            while (lo < hi && nums[hi - 1] == nums[hi])
                                hi--;
                        } else if (nums[lo] + nums[hi] + nums[i] > tempTarget) {
                            hi--;
                        } else {
                            lo++;
                        }
                        hi--;
                        lo++;
                    }
                }
            }
            return result;
        }

        //98. 验证二叉搜索树
        public boolean isValidBST(TreeNode root) {

            double pre = Integer.MIN_VALUE;
            if (root.left == null && root.right == null) {
                return true;
            }
            if (root.left != null) {
                isValidBST(root.left);
            }
            if (root.right != null) {
                isValidBST(root.right);
            }
            if (root.left != null && root.left.val > root.val) {
                return false;
            }
            if (root.right != null && root.right.val < root.val) {
                return false;
            }
            HashMap<String, Object> map = new HashMap<>();
            for (String s : map.keySet()) {
                Object o = map.get(s);
            }
            return true;
        }
    }

    public class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}

