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
            if (nums[i] > 0){
                break;
            }
            //!!!2去重
            if (i > 0 && nums[i] == nums[i-1]){
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
                    while (start < end && nums[start] == nums[start+1]){
                        start++;
                    }
                    while (start < end && nums[end] == nums[end-1]){
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
}
