package example.tdd.tetris.board;

public interface TetrisBoardListener {
    void updated(int[][] boardCells, long score);
    void gameOver(long score);
}
