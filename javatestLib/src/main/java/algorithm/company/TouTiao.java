package algorithm.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by dengfa on 2019-12-08.
 * Des:
 */
public class TouTiao {

    /**
     * 3. 无重复字符的最长子串
     * 滑动窗口
     */
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        int i = 0;
        int j = 0;
        for (int start = 0, end = 0; end < s.length(); end++) {
            if (map.containsKey(s.charAt(end))) {
                //start之前的记录没有移除！！！
                start = Math.max(map.get(s.charAt(end)) + 1, start);
            }
            max = Math.max(end - start + 1, max);
            map.put(s.charAt(end), end);
        }
        return max;
    }

    /**
     * 71. 简化路径
     *
     * @param path
     * @return
     */
    public String simplifyPath(String path) {
        String[] ss = path.split("/");
        Stack<String> stack = new Stack<>();
        for (String s : ss) {
            if (!s.equals("") && !s.equals("..") && !s.equals(".")) {
                stack.push(s);
            } else if (!stack.isEmpty() && s.equals("..")) {
                stack.pop();
            }
        }
        if (stack.isEmpty()) {
            return "/";
        }
        StringBuilder sb = new StringBuilder();
        //Stack继承自Vector，可以通过索引取值
        for (int i = 0; i < stack.size(); i++) {
            sb.append("/" + stack.get(i));
        }
        return sb.toString();
    }

    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int start = i + 1;
            int end = nums.length - 1;
            int target = -nums[i];
            //!!!1
            if (nums[i] > 0) {
                break;
            }
            //!!!2去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            while (start < end) {
                if (target < nums[start] + nums[end]) {
                    end--;
                } else if (target > nums[start] + nums[end]) {
                    start++;
                } else {
                   /* ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);
                    temp.add(nums[start]);
                    temp.add(nums[end]);
                    res.add(temp);*/
                    //!!!3
                    res.add(Arrays.asList(nums[i], nums[start], nums[end]));
                    while (start < end && nums[start] == nums[start + 1]) {
                        start++;
                    }
                    while (start < end && nums[end] == nums[end - 1]) {
                        end--;
                    }
                    //!!!4
                    start++;
                    end--;
                }
            }
        }
        return res;
    }

    /**
     * 695. 岛屿的最大面积
     *
     * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    max = Math.max(dfs(grid, i, j), max);
                }
            }
        }
        return max;
    }

    public int[][] scans = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public int dfs(int[][] grid, int r, int c) {
        if (!check(grid, r, c) || grid[r][c] == 0) {
            return 0;
        }
        int res = 0;
        if (grid[r][c] == 1) {
            res++;
            grid[r][c] = 0;
        }
        for (int[] scan : scans) {
            res += dfs(grid, r + scan[0], c + scan[1]);
        }
        return res;
    }

    public <T> boolean check(int[][] grid, int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }

    /**
     * 200. 岛屿数量
     */
    public int numIslands(char[][] grid) {
        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    num++;
                    dfs(grid, i, j);
                }
            }
        }
        return num;
    }

    public int dfs(char[][] grid, int r, int c) {
        if (!check(grid, r, c) || grid[r][c] == '0') {
            return 0;
        }
        int res = 0;
        if (grid[r][c] == '1') {
            res++;
            grid[r][c] = '0';
        }
        for (int[] scan : scans) {
            res += dfs(grid, r + scan[0], c + scan[1]);
        }
        return res;
    }

    public boolean check(char[][] grid, int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }


    /**
     * 33. 搜索旋转排序数组
     */
    public int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target)
                return mid;
            //在任意一个点划开，要么前半段有序，要么后半段有序
            if (nums[mid] >= nums[lo]) {
                if (nums[mid] > target && target >= nums[lo]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 69. x 的平方根
     */
    public int mySqrt(int x) {
        if (x == 0)
            return 0;
        int lo = 1;
        int hi = x / 2;
        while (lo <= hi) {
            int mid = (lo + hi + 1) >> 1;
            long sqrt = mid * mid;
            if (sqrt > x) {
                hi = mid - 1;
            } else {
                lo = mid;
            }
        }
        return lo;
    }

    /**
     * 121. 买卖股票的最佳时机
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        for (int i = 0; i < prices.length; i++) {
            if (i - 1 < 0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[prices.length - 1][0];
    }

    //优化空间
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length < 1) {
            return 0;
        }
        int rest = 0;
        int buy = 0;
        for (int i = 0; i < prices.length; i++) {
            if (i - 1 < 0) {
                rest = 0;
                buy = -prices[i];
                continue;
            }
            rest = Math.max(rest, buy + prices[i]);
            buy = Math.max(buy, -prices[i]);
        }
        return rest;
    }


    /**
     * 309. 最佳买卖股票时机含冷冻期
     */
    public int maxProfit309(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 < 0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            //错！！！
            dp[i][1] = Math.max(dp[i - 1][1], i - 2 < 0 ? 0 : dp[i - 2][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    public int maxProfit309right(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][2];
        int pre = 0;//记录dp[i - 2][0]
        for (int i = 0; i < n; i++) {
            if (i - 1 < 0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            int temp = dp[i - 1][0];
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], pre - prices[i]);
            pre = temp;
        }
        return dp[n - 1][0];
    }

    /**
     * 53. 最大子序和
     * 最优子结构问题
     */
    public int maxSubArray(int[] nums) {
        /*if (nums == null || nums.length < 1) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                dp[i] = nums[i];
                continue;
            }
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        }
        return dp[n - 1];*/
        //以上错误：要理解dp的定义，这里定义的是包含i位置元素时的最大子序和，它不一定是整个的最大值
        //所以还需要一个值记录最大的值

        if (nums == null || nums.length < 1) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        int max = nums[0];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    //空间优化
    public int maxSubArray2(int[] nums) {
        if (nums == null || nums.length < 1) {
            return 0;
        }
        int max = nums[0];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum > 0) {
                sum += nums[i];
            } else {
                sum = nums[i];
            }
            max = Math.max(max, sum);
        }
        return max;
    }

    //分治
    public int maxSubArray3(int[] nums) {
        return divid(nums, 0, nums.length - 1);
    }

    public int divid(int[] nums, int left, int right) {
        if (left > right) {
            return 0;
        }
        int mid = left + (right - left) / 2;
        int maxLeft = divid(nums, left, mid);
        int maxRight = divid(nums, mid + 1, right);

        int maxSumLeft = 0;
        int sumLeft = 0;
        for (int i = mid; i >= left; i--) {
            sumLeft += nums[i];
            maxSumLeft = Math.max(maxLeft, sumLeft);
        }
        int maxSumRight = 0;
        int sumRight = 0;
        for (int i = mid + 1; i <= right; i++) {
            sumRight += nums[i];
            maxSumRight = Math.max(maxSumRight, sumRight);
        }
        return Math.max(maxSumLeft + maxSumRight, Math.max(maxLeft, maxRight));
    }
}
