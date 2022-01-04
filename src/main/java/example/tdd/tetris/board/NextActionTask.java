package example.tdd.tetris.board;

import java.util.TimerTask;

public class NextActionTask extends TimerTask {
    private TetrisBoard tetrisBoard;

    public NextActionTask(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
    }

    @Override
    public void run() {
        tetrisBoard.doNextAction();
    }
}
