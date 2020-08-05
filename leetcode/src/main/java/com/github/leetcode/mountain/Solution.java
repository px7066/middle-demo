package com.github.leetcode.mountain;

public class Solution {
    public int longestMountain(int[] A) {
        int result = 0;
        int al = A.length;
        boolean sw = true;
        int count = 1;
        for(int i=0; i< al; i++){
            if(i == al -1){
                if(count >= 3 && !sw){
                    result = Math.max(result, count);
                }
                break;
            }
            if(sw){
                if(A[i] < A[i+1]){
                    count++;
                }else if(A[i] > A[i+1]){
                    if(count != 1){
                        count++;
                        sw = false;
                    }
                }else {
                    count = 1;
                }
            }else {
                if(A[i] > A[i+1]){
                    count++;
                }else {
                    if(count >= 3){
                        result = Math.max(result, count);
                    }
                    if(A[i] == A[i+1]){
                        count = 1;
                    }else {
                        count = 2;
                    }
                    sw = true;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] a = new int[]{0,1,0,0,1,0,0};
        System.out.println(solution.longestMountain(a));
    }

}
