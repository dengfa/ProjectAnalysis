package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
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


        /**
         * 416. 分割等和子集
         * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
         * <p>
         * 注意:
         * 每个数组中的元素不会超过 100
         * 数组的大小不会超过 200
         */
        int[][] memo;

        //记忆搜索
        public boolean canPartition(int[] nums) {
            if (nums == null || nums.length < 2) {
                return false;
            }
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (sum % 2 != 0) {
                return false;
            }
            memo = new int[nums.length][sum / 2 + 1];
            return tryPartition(nums, nums.length - 1, sum / 2);
        }

        public boolean tryPartition(int[] nums, int index, int sum) {
            if (sum == 0) {
                return true;
            }

            if (sum < 0 || index < 0) {
                return false;
            }
            if (memo[index][sum] != 0) {
                return memo[index][sum] == 1;
            }
            memo[index][sum] = tryPartition(nums, index - 1, sum)
                    || tryPartition(nums, index - 1, sum - nums[index]) ? 1 : 2;
            return memo[index][sum] == 1;
        }

        //动态规划 背包解法
        public boolean canPartition1(int[] nums) {
            if (nums == null || nums.length < 2) {
                return false;
            }
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (sum % 2 != 0) {
                return false;
            }

            int c = sum / 2;
            boolean[] memo = new boolean[c + 1];
            //构造第一行
            for (int i = 0; i <= c; i++) {
                memo[i] = nums[0] == i;
            }
            for (int i = 1; i < nums.length; i++) {
                //优化空间，需要倒着处理
                for (int j = c; j >= nums[i]; j--) {
                    memo[j] = memo[j] || memo[j - nums[i]];
                }
            }
            return memo[c];
        }

        /**
         * 322. 零钱兑换
         * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
         * 如果没有任何一种硬币组合能组成总金额，返回 -1。
         * <p>
         * 示例 1:
         * <p>
         * 输入: coins = [1, 2, 5], amount = 11
         * 输出: 3
         * 解释: 11 = 5 + 5 + 1
         * 示例 2:
         * <p>
         * 输入: coins = [2], amount = 3
         * 输出: -1
         * 说明:
         * 你可以认为每种硬币的数量是无限的。
         */
        public int coinChange(int[] coins, int amount) {
            if (coins == null || coins.length < 1) {
                return -1;
            }
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, amount + 1);
            dp[0] = 0;
            for (int i = 1; i < amount + 1; i++) {
                for (int coin : coins) {
                    if (i >= coin) {
                        dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                    }
                }
            }
            return dp[amount] < amount + 1 ? dp[amount] : -1;
        }

        /**
         * 377. 组合总和 Ⅳ
         * 给定一个由正整数组成且不存在重复数字的数组，找出和为给定目标正整数的组合的个数。
         *
         * 示例:
         *
         * nums = [1, 2, 3]
         * target = 4
         *
         * 所有可能的组合为：
         * (1, 1, 1, 1)
         * (1, 1, 2)
         * (1, 2, 1)
         * (1, 3)
         * (2, 1, 1)
         * (2, 2)
         * (3, 1)
         *
         * 请注意，顺序不同的序列被视作不同的组合。
         *
         * 因此输出为 7。
         * 进阶：
         * 如果给定的数组中含有负数会怎么样？
         * 问题会产生什么变化？
         * 我们需要在题目中添加什么限制来允许负数的出现？
         *
         * 致谢：
         * 特别感谢 @pbrother 添加此问题并创建所有测试用例。
         *
         * 在真实的面试中遇到过这道题？
         *
         * 来源：力扣（LeetCode）
         * 链接：https://leetcode-cn.com/problems/combination-sum-iv
         * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
         */
        public int combinationSum4(int[] nums, int target) {
            int[] dp = new int[target + 1];
            dp[0] = 1;
            for (int i = 0; i < target + 1; i++) {
                for (int num : nums) {
                    if (i >= num) {
                        dp[i] = dp[i] + dp[i - num];
                    }
                }
            }
            return dp[target];
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

