package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/29
 * comment:
 * Given n and m which are the dimensions of a matrix initialized by zeros and given an array indices where indices[i] = [ri, ci]. For each pair of [ri, ci] you have to increment all cells in row ri and column ci by 1.
 *
 * Return the number of cells with odd values in the matrix after applying the increment to all indices.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: n = 2, m = 3, indices = [[0,1],[1,1]]
 * Output: 6
 * Explanation: Initial matrix = [[0,0,0],[0,0,0]].
 * After applying first increment it becomes [[1,2,1],[0,1,0]].
 * The final matrix will be [[1,3,1],[1,3,1]] which contains 6 odd numbers.
 * Example 2:
 *
 *
 * Input: n = 2, m = 2, indices = [[1,1],[0,0]]
 * Output: 0
 * Explanation: Final matrix = [[2,2],[2,2]]. There is no odd number in the final matrix.
 */
@Slf4j
public class Title1252 {

    public static void main(String[] args) {
        int[][] indices = {{0,1},{1,1}};
        int result = oddCells(2, 3, indices);
        log.info("result:{}",result);
    }

    public static int oddCells(int n, int m, int[][] indices) {
        int[][] arrays= new int[n][m];
        for(int i=0;i<indices.length;i++){
            int toAddRowNum = indices[i][0];
            int toAddColumnNum = indices[i][1];
            addArrayNum(arrays,toAddRowNum,toAddColumnNum);
        }
        int result = 0;
        for(int i=0;i<arrays.length;i++){
            for(int j=0;j<arrays[0].length;j++){
                if(arrays[i][j]%2!=0){
                    result++;
                }
            }
        }
        return result;
    }

    public static void addArrayNum(int[][] arrays,int rowNum,int columnNum){
        int rowLength = arrays.length;
        int columnLength = arrays[0].length;
        for(int i=0;i<rowLength;i++){
            arrays[i][columnNum]++;
        }

        for(int j=0;j<columnLength;j++){
            arrays[rowNum][j]++;
        }
    }

}
