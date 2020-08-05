package com.github.leetcode.integerbreak;

public class Solution {
    public int integerBreak(int n) {
        if(n < 4){
            return n;
        }
        int result=1;
        while (n > 4){
            n -=3;
            result *= 3;
        }
        return result*n;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.integerBreak(15));
    }
}
