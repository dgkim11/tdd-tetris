package example.tdd.tetris.board;

import java.util.TimerTask;

public class BlockDownTask extends TimerTask {
    private TetrisBoard tetrisBoard;

    public BlockDownTask(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
    }

    @Override
    public void run() {
        if(tetrisBoard.needNewBlock())  {
            tetrisBoard.newBlock();
        }
        else {
            tetrisBoard.moveDownBlock();
        }

    }
}
