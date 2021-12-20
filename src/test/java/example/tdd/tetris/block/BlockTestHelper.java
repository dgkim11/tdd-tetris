package example.tdd.tetris.block;

import java.util.ArrayList;
import java.util.List;

public class BlockTestHelper {
    public static boolean equalCells(int[][] leftCells, int[][] rightCells)   {
        if(leftCells.length != rightCells.length || leftCells[0].length != rightCells[0].length) return false;
        for(int i = 0;i < leftCells.length;i++)    {
            for(int j = 0;j < leftCells[i].length;j++)     {
                if(leftCells[i][j] != rightCells[i][j]) return false;
            }
        }

        return true;
    }

    /**
     * 두개의 cell들간의 차이가 있는 부분을 찾아서 알려준다.
     * @param leftCells
     * @param rightCells
     * @return
     */
    public static List<DiffPoint> findDiffs(int[][] leftCells, int[][] rightCells) {
        List<DiffPoint> diffPoints = new ArrayList<>();

        for(int row = 0;row < leftCells.length;row++) {
            for(int col = 0;col < leftCells[row].length;col++)  {
                if(leftCells[row][col] != rightCells[row][col])
                    diffPoints.add(new DiffPoint(col, row, leftCells[row][col], rightCells[row][col]));
            }
        }

        return diffPoints;
    }

    public static int[][] copyArray(int[][] source) {
        int[][] copy = new int[source.length][source[0].length];
        for(int row = 0;row < source.length;row++)  {
            for(int col = 0;col < source[row].length;col++) {
                copy[row][col] = source[row][col];
            }
        }
        return copy;
    }
}
