package com.github.leetcode.addstring;

public class Solution {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() -1;
        int add = 0;
        char zero = '0';
        while (i>=0 || j>=0 || add != 0) {
            if(i >= 0){
                add += num1.charAt(i--) - zero;
            }
            if(j >=0){
                add += num2.charAt(j--) - zero;
            }
            sb.append(add % 10);
            add = add/10;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.addStrings("1", "9"));
    }
}
