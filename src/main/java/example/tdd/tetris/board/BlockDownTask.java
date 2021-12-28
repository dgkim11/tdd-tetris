package example.tdd.tetris.board;

import java.util.TimerTask;

public class BlockDownTask extends TimerTask {
    private TetrisBoard tetrisBoard;
    private boolean needNewBlock;

    public BlockDownTask(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
        this.needNewBlock = true;
    }

    @Override
    public void run() {
        UpdateBoardResult result;
        if(needNewBlock)  {
            result = tetrisBoard.newBlock();
        }
        else {
            result = tetrisBoard.moveDownBlock();
        }

        if(result.isGameOver()) { tetrisBoard.gameOver(); return; }

        needNewBlock = result.isNeedNewBlock();
    }
}
