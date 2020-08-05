package com.github.leetcode.courseschedule;

import java.util.ArrayList;
import java.util.List;

/****
 * <p>课程表</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/8/4 13:54
 */
public class Solution {

    List<List<Integer>> lists = new ArrayList<List<Integer>>();
    int[] visited;
    boolean valid = true;

    public boolean canFinish(int numCourses, int[][] prerequisites) {

        for(int i=0; i< numCourses; i++){
            lists.add(new ArrayList<Integer>());
        }
        visited = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            lists.get(prerequisite[1]).add(prerequisite[0]);
        }
        for(int i=0; i< numCourses && valid; i++){
            if(visited[i] == 0){
                dfs(i);
            }
        }
        return valid;

    }

    private void dfs(int i) {
        visited[i] = 1;
        for(int v:lists.get(i)){
            if(visited[v] == 0){
                dfs(v);
                if(!valid){
                    return;
                }
            }else if(visited[v] == 1){
                valid =false;
                return;
            }
        }
        visited[i] = 2;

    }
}
