package com.mairuis.algorithm.leetcode.binarytree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 107. 二叉树的层次遍历 II
 *
 * @author Mairuis
 * @date 2019/6/24
 */
public class LevelOrderBottom {

    private static class Solution {
        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            LinkedList<List<Integer>> result = new LinkedList();
            if (root == null) {
                return result;
            }
            Queue<TreeNode> queue = new LinkedList();
            queue.add(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> level = new ArrayList(size);
                for (int i = 0; i < size; i += 1) {
                    TreeNode node = queue.poll();
                    level.add(node.val);
                    if (node.left != null) queue.add(node.left);
                    if (node.right != null) queue.add(node.right);
                }
                result.offerFirst(level);
            }
            return result;
        }
    }

    private static class MainClass {
        public static TreeNode stringToTreeNode(String input) {
            input = input.trim();
            input = input.substring(1, input.length() - 1);
            if (input.length() == 0) {
                return null;
            }

            String[] parts = input.split(",");
            String item = parts[0];
            TreeNode root = new TreeNode(Integer.parseInt(item));
            Queue<TreeNode> nodeQueue = new LinkedList<>();
            nodeQueue.add(root);

            int index = 1;
            while (!nodeQueue.isEmpty()) {
                TreeNode node = nodeQueue.remove();

                if (index == parts.length) {
                    break;
                }

                item = parts[index++];
                item = item.trim();
                if (!item.equals("null")) {
                    int leftNumber = Integer.parseInt(item);
                    node.left = new TreeNode(leftNumber);
                    nodeQueue.add(node.left);
                }

                if (index == parts.length) {
                    break;
                }

                item = parts[index++];
                item = item.trim();
                if (!item.equals("null")) {
                    int rightNumber = Integer.parseInt(item);
                    node.right = new TreeNode(rightNumber);
                    nodeQueue.add(node.right);
                }
            }
            return root;
        }

        public static String integerArrayListToString(List<Integer> nums, int length) {
            if (length == 0) {
                return "[]";
            }

            String result = "";
            for (int index = 0; index < length; index++) {
                Integer number = nums.get(index);
                result += Integer.toString(number) + ", ";
            }
            return "[" + result.substring(0, result.length() - 2) + "]";
        }

        public static String integerArrayListToString(List<Integer> nums) {
            return integerArrayListToString(nums, nums.size());
        }

        public static String int2dListToString(List<List<Integer>> nums) {
            StringBuilder sb = new StringBuilder("[");
            for (List<Integer> list : nums) {
                sb.append(integerArrayListToString(list));
                sb.append(",");
            }

            sb.setCharAt(sb.length() - 1, ']');
            return sb.toString();
        }

        public static void main(String[] args) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = in.readLine()) != null) {
                TreeNode root = stringToTreeNode(line);

                List<List<Integer>> ret = new Solution().levelOrderBottom(root);

                String out = int2dListToString(ret);

                System.out.print(out);
            }
        }
    }
}
