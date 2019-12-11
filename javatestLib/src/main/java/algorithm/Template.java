package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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

    //BFS
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        Queue<TreeNode> lq = new LinkedList<>();
        lq.offer(root);
        int level = 0;
        while (lq.size() > 0) {
            List<Integer> cur = new ArrayList<>();
            res.add(cur);
            level++;
            int size = lq.size();
            for (int i = 0; i < size; i++) {
                TreeNode curRoot = lq.poll();
                cur.add(curRoot.val);
                if (curRoot.left != null) {
                    lq.offer(curRoot.left);
                }
                if (curRoot.right != null) {
                    lq.offer(curRoot.right);
                }
            }
        }
        return res;
    }

    /**
     * 深度优先遍历，相当于先根遍历
     * 采用非递归实现
     * 需要辅助数据结构：栈
     */
    public void depthOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * 四个方向搜索
     */
    public int[][] scans = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
}
