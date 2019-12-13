package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Normal {

    public static class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static class ListNode {
        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        Solution.letterCombinations("2345");
        ListNode head = new ListNode(5);
        ListNode next = new ListNode(3);
        head.next = next;
        ListNode next1 = new ListNode(1);
        next.next = next1;
        ListNode next2 = new ListNode(6);
        next1.next = next2;
        ListNode next3 = new ListNode(2);
        next2.next = next3;
        ListNode subsort = HighFrequency.subsort(head);
        StringBuilder sb = new StringBuilder();
        while (subsort != null) {
            sb.append(subsort.val + " -> ");
            subsort = subsort.next;
        }
        System.out.println(sb.toString());


        String str = "a b c";
        for (int i = 0; i < str.length(); i++) {
            System.out.println("-" + str.charAt(i) + "-");
            System.out.println("-" + (' ' == str.charAt(i)) + "-");

        }

        HighFrequency.getRow(5);
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
        public void combineBacktrack(int n, int k, int first, List<List<Integer>> combined,
                                     LinkedList<Integer> temp) {
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
         * 给定一个只包含正整数的非空数组。
         * 是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
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
         * 给定不同面额的硬币 coins 和一个总金额 amount。
         * 编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
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
         * 给定一个由正整数组成且不存在重复数字的数组，
         * 找出和为给定目标正整数的组合的个数。
         * 示例:
         * nums = [1, 2, 3]
         * target = 4
         * 所有可能的组合为：
         * (1, 1, 1, 1)
         * (1, 1, 2)
         * (1, 2, 1)
         * (1, 3)
         * (2, 1, 1)
         * (2, 2)
         * (3, 1)
         * 请注意，顺序不同的序列被视作不同的组合。
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

        /**
         * 54. 螺旋矩阵
         * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），
         * 请按照顺时针螺旋顺序，返回矩阵中的所有元素。
         * 输入:
         * [
         * [ 1, 2, 3 ],
         * [ 4, 5, 6 ],
         * [ 7, 8, 9 ]
         * ]
         * 输出: [1,2,3,6,9,8,7,4,5]
         */
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> res = new ArrayList<>();
            if (matrix == null || matrix.length == 0) {
                return res;
            }
            int c1 = 0, c2 = matrix[0].length - 1;
            int r1 = 0, r2 = matrix.length - 1;
            while (c1 <= c2 && r1 <= r2) {
                for (int c = c1; c <= c2; c++) {
                    res.add(matrix[r1][c]);
                }
                for (int r = r1 + 1; r <= r2; r++) {
                    res.add(matrix[r][c2]);
                }
                if (c1 < c2 && r1 < r2) {
                    for (int c = c2 - 1; c > c1; c--) {
                        res.add(matrix[r2][c]);
                    }
                    for (int r = r2; r > r1; r--) {
                        res.add(matrix[r][c1]);
                    }
                }
                c1++;
                c2--;
                r1++;
                r2--;
            }
            return res;
        }
    }

    /**
     * 9. 回文数
     * 判断一个整数是否是回文数。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     */
    static class HighFrequency {

        public boolean isPalindrome(int x) {
            //注意去掉一些特殊情况，否则会有问题
            if (x < 0 || (x % 10 == 0 && x != 0))
                return false;
            int nums = x;
            int revert = 0;
            //怎么判断反转了一半，很巧妙！ 如果全部反转可能会溢出
            while (nums > revert) {
                revert = revert * 10 + nums % 10;
                nums /= 10;
            }
            return nums == revert || nums == revert / 10;
        }

        /**
         * 5. 最长回文子串
         * 给定一个字符串 s，找到 s 中最长的回文子串。
         * 你可以假设 s 的最大长度为 1000。
         */
        public String longestPalindrome(String s) {
            if (s == null || s.length() < 1)
                return "";
            int maxL = 0;
            int maxR = 0;
            for (int i = 0; i < s.length(); i++) {
                int len1 = expand(s, i, i);
                int len2 = expand(s, i, i + 1);
                int len = Math.max(len1, len2);
                if (maxR - maxL < len) {
                    maxL = i - (len - 1) / 2;
                    maxR = i + len / 2;
                }
            }
            return s.substring(maxL, maxR + 1);
        }

        public String longestPalindrome2(String s) {
            int n = s.length();
            boolean[][] dp = new boolean[n][n];
            String res = "";
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i; j < n; j++) {
                    //j - i < 2这个判断很巧妙
                    dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1]);
                    if (dp[i][j] && j - i + 1 > res.length()) {
                        res = s.substring(i, j + 1);
                    }
                }
            }
            return res;
        }

        public int expand(String s, int left, int right) {
            int L = left, R = right;
            while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
                L--;
                R++;
            }
            //这里注意！！！画图看看
            return R - L - 1;
        }

        /**
         * 61. 旋转链表
         * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
         * 输入: 1->2->3->4->5->NULL, k = 2
         * 输出: 4->5->1->2->3->NULL
         * 解释:
         * 向右旋转 1 步: 5->1->2->3->4->NULL
         * 向右旋转 2 步: 4->5->1->2->3->NULL
         */
        public ListNode rotateRight(ListNode head, int k) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode temp = head;
            int n = 0;
            while (temp != null) {
                n++;
                temp = temp.next;
            }
            k %= n;
            ListNode newTail = head;
            ListNode oldTail = head;
            for (int i = 0; i < k; i++) {
                oldTail = oldTail.next;
            }
            while (oldTail.next != null) {
                oldTail = oldTail.next;
                newTail = newTail.next;
            }
            oldTail.next = head;
            head = newTail.next;
            newTail.next = null;
            return head;
        }

        /**
         * 78.子集
         */
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> subsets(int[] nums) {
            List<Integer> cur = new ArrayList<>();
            sub(nums, 0, cur);
            return res;
        }

        public void sub(int[] nums, int index, List<Integer> cur) {
            if (index == nums.length) {
                res.add(new ArrayList<Integer>(cur));
                return;
            }
            cur.add(nums[index]);
            sub(nums, index + 1, cur);
            cur.remove(cur.size() - 1);
            sub(nums, index + 1, cur);
        }

        int max = Integer.MIN_VALUE;

        //二叉树最大路径和
        public int maxPathSum(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftMax = Math.max(maxPathSum(root.left), 0);
            int rightMax = Math.max(maxPathSum(root.right), 0);
            int newMax = root.val + leftMax + rightMax;
            max = Math.max(max, newMax);
            return root.val + Math.max(leftMax, rightMax);
        }

        public static ListNode subsort(ListNode head) {
            if (head.next == null) {
                return head;
            }
            ListNode subList = subsort(head.next);
            return insertList(head, subList);
        }

        /**
         * 有序链表插入一个节点
         *
         * @param insertNode
         * @param head
         * @return
         */
        public static ListNode insertList(ListNode insertNode, ListNode head) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode find = head;
            while (find != null) {
                if (insertNode.val <= find.val) {
                    ListNode temp = new ListNode(find.val);
                    find.val = insertNode.val;
                    temp.next = find.next;
                    find.next = temp;
                    break;
                }
                if (find.next == null) {
                    find.next = insertNode;
                    insertNode.next = null;
                    break;
                }
                find = find.next;
            }
            return dummy.next;
        }

        /**
         * 删除排序链表中的重复元素
         */
        public ListNode deleteDuplicatesI(ListNode head) {
            ListNode cur = head;
            ListNode next = head;
            while (cur != null && next != null) {
                if (cur.val == next.val) {
                    next = next.next;
                    if (next == null) {
                        cur.next = null;
                    }
                } else {
                    cur.next = next;
                    cur = next;
                    next = next.next;
                }
            }
            return head;
        }

        /**
         * 557. 反转字符串中的单词 III
         * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
         * <p>
         * 示例 1:
         * <p>
         * 输入: "Let's take LeetCode contest"
         * 输出: "s'teL ekat edoCteeL tsetnoc" 
         * 注意：在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。
         */
        public String reverseWords(String s) {
            Stack<Character> stack = new Stack<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') {
                    while (!stack.isEmpty()) {
                        sb.append(stack.pop());
                    }
                    sb.append(' ');
                } else {
                    stack.push(s.charAt(i));
                }
            }
            while (!stack.isEmpty()) {
                sb.append(stack.pop());
            }
            return sb.toString();
        }

        /**
         * 108. 将有序数组转换为二叉搜索树
         * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
         * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
         */
        public TreeNode subSortedArrayToBST(int[] nums, int start, int end) {
            if (start > end) {
                return null;
            }
            int mid = (start + end) >>> 1;
            TreeNode leftNode = subSortedArrayToBST(nums, mid + 1, end);
            TreeNode rightNode = subSortedArrayToBST(nums, start, mid - 1);
            TreeNode node = new TreeNode(nums[mid]);
            node.left = leftNode;
            node.right = rightNode;
            return node;
        }

        /**
         * 105. 从前序与中序遍历序列构造二叉树
         * 没通过！！！
         */
        public TreeNode subBuildTree(int[] preorder, int pl, int pr,
                                     int[] inorder, int il, int ir) {
            if (pl > pr || il > ir) {
                return null;
            }
            TreeNode node = new TreeNode(preorder[pl]);
            for (int i = il; i <= ir; i++) {
                if (preorder[pl] == inorder[i]) {
                    node.left = subBuildTree(preorder, pl + 1, i,
                            inorder, il, i - 1);
                    node.right = subBuildTree(preorder, i + 1, pr,
                            inorder, i + 1, ir);
                }
            }
            return node;
        }

        /**
         * 121. 买卖股票的最佳时机
         * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
         * <p>
         * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
         * <p>
         * 注意你不能在买入股票前卖出股票。
         */
        public int maxProfit(int[] prices) {
            int maxProfit = 0;
            int minPrice = Integer.MAX_VALUE;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] < minPrice) {
                    minPrice = prices[i];
                } else {
                    maxProfit = Math.max(maxProfit, prices[i] - minPrice);
                }
            }
            return maxProfit;
        }

        /**
         * 122. 买卖股票的最佳时机 II
         * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
         * <p>
         * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
         * <p>
         * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
         */
        public int maxProfit2(int[] prices) {
            int maxProfit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        /**
         * 123. 买卖股票的最佳时机 III
         * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
         * <p>
         * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
         * <p>
         * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
         */
        public int maxProfit3(int[] prices) {
            int[][][] dp = new int[prices.length][3][2];
            //初始条件怎么确定？
            dp[0][1][1] = -prices[0];
            for (int i = 1; i < prices.length; i++) {
                for (int j = 1; j < dp[0].length; j++) {
                    //多余，数组初始化默认就是0
                    /*if (j == 0) {
                        dp[i][j][0] = 0;
                        continue;
                    }*/
                    dp[i][j][0] = Math.max(dp[i - 1][j - 1][1] + prices[i],
                            dp[i - 1][j - 1][0]);
                    dp[i][j][1] = Math.max(dp[i - 1][j][1],
                            dp[i - 1][j - 1][0] - prices[i]);
                }
            }
            return Math.max(dp[prices.length - 1][0][0],
                    Math.max(dp[prices.length - 1][1][0], dp[prices.length - 1][2][0]));
        }


        /**
         * 118. 杨辉三角
         */
        public static List<List<Integer>> generate(int numRows) {
            List<List<Integer>> res = new ArrayList<>(numRows);
            for (int i = 0; i < numRows; i++) {
                List<Integer> temp = new ArrayList<>(i + 1);
                for (int j = 0; j <= i; j++) {
                    if (j == 0 || j == i) {
                        temp.add(j, 1);
                    } else {
                        temp.add(j, res.get(i - 1).get(j) + res.get(i - 1).get(j - 1));
                    }
                }
                res.add(temp);
            }
            return res;
        }


        /**
         * 119. 杨辉三角II
         */
        public static List<Integer> getRow(int rowIndex) {
            List<Integer> res = new ArrayList<>(rowIndex + 1);
            for (int i = 0; i < rowIndex + 1; i++) {
                res.add(i, 1);
                for (int j = i - 1; j >= 0; j--) {
                    if (j == 0 || j == i) {
                        res.set(j, 1);
                    } else {
                        res.set(j, res.get(j) + res.get(j - 1));
                    }
                }
            }
            return res;
        }

        /**
         * 120. 三角形最小路径和
         * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
         * <p>
         * 例如，给定三角形：
         * <p>
         * [
         * [2],
         * [3,4],
         * [6,5,7],
         * [4,1,8,3]
         * ]
         * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
         */
        public int minimumTotal(List<List<Integer>> triangle) {
            int[] dp = new int[triangle.size()];
            dp[0] = triangle.get(0).get(0);
            for (int i = 1; i < triangle.size(); i++) {
                for (int j = i; j >= 0; j--) {
                    if (j == 0) {
                        dp[j] = dp[j] + triangle.get(i).get(j);
                    } else if (j == i) {
                        dp[j] = dp[j - 1] + triangle.get(i).get(j);
                    } else {
                        dp[j] = Math.min(dp[j], dp[j - 1]) + triangle.get(i).get(j);
                    }
                }
            }
            int min = Integer.MAX_VALUE;
            for (int i : dp) {
                min = Math.min(i, min);
            }
            return min;
        }

        /**
         * 392. 判断子序列
         * 错！
         */
        public boolean isSubsequence(String s, String t) {
            int sl = s.length();
            int tl = t.length();
            int[][] dp = new int[sl + 1][tl + 1];
            /*dp[0][0] = 0;
            for (int i = 0; i <= sl; i++) {
                for (int j = 0; j <= tl; j++) {
                    if (s.charAt(i) == t.charAt(j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = dp[i][j - 1] == 1 || dp[i - 1][j] == 1 ? 1 : 0;
                    }
                }
            }*/
            return false;
        }

        /**
         * 104. 二叉树的最大深度
         */
        public int subMaxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return 1 + Math.max(subMaxDepth(root.left), subMaxDepth(root.right));
        }

        public int maxDepth(TreeNode root) {
            /*Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
            stack.push(new Pair<>(root, 1));
            int maxDepth = 0;
            while (!stack.isEmpty()) {
                Pair<TreeNode, Integer> pop = stack.pop();
                TreeNode node = pop.fst;
                int curDepth = pop.snd;
                maxDepth = Math.max(maxDepth, curDepth);
                if (node.left != null) {
                    stack.push(new Pair<>(node.left, curDepth + 1));
                }
                if (node.right != null) {
                    stack.push(new Pair<>(node.right, curDepth + 1));
                }
            }
            return maxDepth;*/
            return 0;
        }

        public void DFS(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode p = root;
            List<Integer> res = new ArrayList<>();
            while (!stack.isEmpty() || p != null) {
                while (p != null) {
                    stack.push(p);
                    res.add(p.val);
                    p = p.left;
                }
                TreeNode node = stack.pop();
                if (node.right != null) {
                    p = node.right;
                }
            }
        }

        /**
         * 98. 验证二叉搜索树
         */
        public boolean helper(TreeNode root, Integer lower, Integer upper) {
            if (root == null) {
                return true;
            }
            if (lower != null && root.val < lower)
                return false;
            if (upper != null && root.val > upper)
                return false;
            if (!helper(root.left, lower, root.val))
                return false;
            if (!helper(root.right, root.val, upper))
                return false;
            return true;
        }

        public boolean inorder(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode p = root;
            long prevalue = Long.MIN_VALUE;
            while (!stack.isEmpty() || p != null) {
                while (p != null) {
                    stack.push(p);
                    p = p.left;
                }
                TreeNode node = stack.pop();
                if (node.val <= prevalue) {
                    return false;
                }
                prevalue = node.val;
                p = node.right;
            }
            return true;
        }

        public int firstUniqChar(String s) {

            for (int i = 0; i < s.length(); i++) {
                if (s.lastIndexOf(s.charAt(i)) == i) {
                    return i;
                }
            }
            return -1;
        }
    }
}

