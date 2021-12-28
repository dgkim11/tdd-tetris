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

    public Block newBlock()  {
        currentBlock = blockFactory.generateRandomBlock();
        boardCellsControl.newBlock(currentBlock);
        return currentBlock;
    }

    public void moveDownBlock() {
        currentBlock = currentBlock.moveDown();
        boardCellsControl.updateBlock(currentBlock);
    }

    public void startGame() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new BlockDownTask(this), 0, INTERVAL);
    }

    public boolean needNewBlock() {
        return boardCellsControl.isNeedNewBlock();
    }
}
