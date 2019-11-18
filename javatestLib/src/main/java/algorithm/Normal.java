package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Normal {

    public static void main(String[] args) {
        Solution.letterCombinations("2345");
    }

    /**
     * 常考算法整理！！！
     */
    static class Solution {

        public Solution() {

        }

        static void log(String log) {
            System.out.println(log);
        }

        /**
         * 17. 电话号码的字母组合
         */
        static Map<Character, String> phone  = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        static List<String>           output = new ArrayList<String>();

        static List<String> letterCombinations(String digits) {
            if (digits == null || "".equals(digits)) {
                return null;
            }
            letterCombine(digits, 0, "");
            return output;
        }

        static void letterCombine(String digits, int index, String s) {
            if (index == digits.length()) {
                log(s);
                output.add(s);
                return;
            }
            char cur = digits.charAt(index);
            if (!phone.containsKey(cur)) {
                return;
            }
            String curLetters = phone.get(cur);
            for (int i = 0; i < curLetters.length(); i++) {
                letterCombine(digits, index + 1, s + curLetters.charAt(i));
            }
        }


        /**
         * 46. 全排列
         * 给定一个没有重复数字的序列，返回其所有可能的全排列。
         */
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> output = new ArrayList<>();
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int num : nums) {
                temp.add(num);
            }
            backtrack(output, temp, 0);
            return output;
        }

        public void backtrack(List<List<Integer>> output, List<Integer> nums, int first) {
            if (first == nums.size()) {
                output.add(new ArrayList(nums));
                return;
            }
            //for (int i = first + 1; i < nums.size(); i++) {
            for (int i = first; i < nums.size(); i++) {
                Collections.swap(nums, first, i);
                backtrack(output, nums, first + 1);
                Collections.swap(nums, first, i);
            }
        }

        /**
         * 77. 组合
         * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
         */
        public void combineBacktrack(int n, int k, int first, List<List<Integer>> combined, LinkedList<Integer> temp) {
            if (temp.size() == k) {
                combined.add(new LinkedList<>(temp));
                return;
            }

            //for (int i = first; i <= n; i++) {   优化：剪枝
            for (int i = first; i <= n - (k - temp.size()) + 1; i++) {
                temp.add(i);
                //combineBacktrack(n, k, first + 1, combined, temp); 错！
                combineBacktrack(n, k, i + 1, combined, temp);
                temp.removeLast();
            }
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

