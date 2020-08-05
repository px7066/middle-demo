package com.github.leetcode.intergerreplacement;

public class Solution {
    public int integerReplacement(int n) {
        int count =0;
        while (n != 1){
            if(n == 3){
                count += 2;
                return count;
            }
            if((n&1) == 0){
                n = n >>> 1;
                count++;
            }else {
                if((n&2) == 0){
                    n = n-1;
                    count++;
                }else {
                    n = n+1;
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.integerReplacement(65535));;
    }
}
