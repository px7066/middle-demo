package com.github.leetcode.houserobber;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    Map<TreeNode, Integer> checked = new HashMap<TreeNode, Integer>();

    Map<TreeNode, Integer> noChecked = new HashMap<TreeNode, Integer>();

    public int rob(TreeNode root) {
        dfs(root);
        return Math.max(checked.getOrDefault(root,0 ), noChecked.getOrDefault(root, 0));
    }

    public void dfs(TreeNode node){
        if(node == null){
            return;
        }
        dfs(node.left);
        dfs(node.right);
        checked.put(node, noChecked.getOrDefault(node.left, 0) + noChecked.getOrDefault(node.right, 0) + node.val);
        noChecked.put(node, Math.max(checked.getOrDefault(node.left, 0), noChecked.getOrDefault(node.left, 0)) +
                Math.max(checked.getOrDefault(node.right, 0), noChecked.getOrDefault(node.right, 0)));
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode treeNode = new TreeNode();
        treeNode.val = 3;
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        treeNode.left.left = null;
        treeNode.left.right = new TreeNode(3);
        treeNode.right.left = null;
        treeNode.right.right = new TreeNode(1);
        System.out.println(solution.rob(treeNode));
    }
}
