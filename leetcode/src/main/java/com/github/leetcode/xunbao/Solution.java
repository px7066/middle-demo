package com.github.leetcode.xunbao;

import java.util.*;

public class Solution {
    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};
    int n,m;

    public int minimalSteps(String[] maze) {
        n = maze.length;
        m = maze[0].length();
        List<int[]> buttons = new ArrayList<int[]>();
        List<int[]> stones = new ArrayList<int[]>();
        int sx = -1, sy = -1, tx = -1, ty = -1;
        for(int i=0; i<n; i++){
            for(int j=0; j< m; j++){
                char c = maze[i].charAt(j);
                if(c == 'M'){
                    buttons.add(new int[]{i, j});
                }else if(c == 'S'){
                    sx = i;
                    sy = j;
                }else if(c == 'T'){
                    tx = i;
                    ty = j;
                }else if(c == 'O'){
                    stones.add(new int[]{i, j});
                }
            }
        }
        int[][] startDist = bfs(sx, sy, maze);
        if(buttons.isEmpty()){
            return startDist[tx][ty];
        }



        return 0;


    }

    // ["S#O", "M..", "M.T"]

    public int[][] bfs(int x, int y, String[] maze) {
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(ret[i], -1);
        }
        ret[x][y] = 0;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int curx = p[0], cury = p[1];
            for (int k = 0; k < 4; k++) {
                int nx = curx + dx[k], ny = cury + dy[k];
                if (inBound(nx, ny) && maze[nx].charAt(ny) != '#' && ret[nx][ny] == -1) {
                    ret[nx][ny] = ret[curx][cury] + 1;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        return ret;
    }

    public boolean inBound(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }
}
