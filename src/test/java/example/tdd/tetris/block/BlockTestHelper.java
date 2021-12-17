package example.tdd.tetris.block;

public class BlockTestHelper {
    public static boolean equalCells(int[][] cells1, int[][] cells2)   {
        if(cells1.length != cells2.length || cells1[0].length != cells2[0].length) return false;
        for(int i = 0;i < cells1.length;i++)    {
            for(int j = 0;j < cells1[i].length;j++)     {
                if(cells1[i][j] != cells2[i][j]) return false;
            }
        }

        return true;
    }
}
