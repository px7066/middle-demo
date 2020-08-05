package com.github.leetcode.treetolinklist;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public void flatten(TreeNode root) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        getNodes(root, treeNodes);
        for(int i=0; i< treeNodes.size(); i++){
            TreeNode current = treeNodes.get(i);
            current.left = null;
            if(i+1< treeNodes.size()){
                current.right = treeNodes.get(i+1);
            }
        }
    }

    private void getNodes(TreeNode root, List<TreeNode> treeNodes) {
        if(root != null){
            treeNodes.add(root);
            getNodes(root.left, treeNodes);
            getNodes(root.right, treeNodes);
        }
    }

    public static void main(String[] args) {

    }
}
