package com.github.leetcode.reversestring;

public class Solution {
    public void reverseString(char[] s) {
        int n = s.length;
        for (int i = 0; i < n / 2; ++i) {
            int j = n - 1 - i;
            s[i] ^= s[j];
            s[j] ^= s[i];
            s[i] ^= s[j];
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[] chars = new char[]{'撒','大','打','苏','o'};
        solution.reverseString(chars);
        System.out.println(chars);
    }


}
