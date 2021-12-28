package example.tdd.tetris.board;

import example.tdd.tetris.block.Block;
import example.tdd.tetris.block.BlockFactory;
import lombok.Getter;

import java.util.Timer;

public class TetrisBoard {
    public static final int WIDTH_BLOCKS = 20;
    public static final int HEIGHT_BLOCKS = 50;
    private BoardCellsControl boardCellsControl;
    private BlockFactory blockFactory;
    @Getter
    private Block currentBlock;
    private static int INTERVAL = 1000;
    private static int POINT = 10;
    @Getter
    private long score;

    public int getBlockCountOfWidth() {
        return WIDTH_BLOCKS;
    }
    public int getBlockCountOfHeight() {
        return HEIGHT_BLOCKS;
    }

    private Timer timer;

    public TetrisBoard(BoardCellsControl boardCellsControl, BlockFactory blockFactory) {
        this.boardCellsControl = boardCellsControl;
        this.blockFactory = blockFactory;
    }

    public UpdateBoardResult newBlock()  {
        currentBlock = blockFactory.generateRandomBlock();
        return updateBlock(currentBlock);
    }

    public UpdateBoardResult moveDownBlock() {
        currentBlock = currentBlock.moveDown();
        return updateBlock(currentBlock);
    }

    private UpdateBoardResult updateBlock(Block block) {
        UpdateBoardResult result = boardCellsControl.updateBlock(block);
        score += (long) result.getRemovedRows() * POINT;
        System.out.println("score:" + score);
        return result;
    }

    public void startGame() {
        this.score = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new BlockDownTask(this), 0, INTERVAL);
    }

    public void gameOver() {
        timer.cancel();
    }
}
