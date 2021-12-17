package example.tdd.tetris.board;

import java.util.ArrayList;
import java.util.List;

/**
 * 보드 상에 블럭이 다 찬 row를 찾아서 알려준다.
 */
public class FilledRowsFinder {
    public static List<Integer> findFilledRows(int[][] rows)    {
        List<Integer> filledRows = new ArrayList<>();

        for(int row = 0;row < rows.length;row++)    {
            if(isFilledRow(rows[row]))
                filledRows.add(row);
        }
        return filledRows;
    }

    private static boolean isFilledRow(int[] row)   {
        for(int i = 0;i < row.length;i++)   {
            if(row[i] == -1) return false;
        }
        return true;
    }
}
