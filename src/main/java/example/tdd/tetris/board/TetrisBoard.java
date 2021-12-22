package example.tdd.tetris.board;

import example.tdd.tetris.block.Block;
import example.tdd.tetris.block.Direction;
import example.tdd.tetris.block.IMinoBlock;

public class TetrisBoard {
    public static final int WIDTH_BLOCKS = 20;
    public static final int HEIGHT_BLOCKS = 50;
    private BoardCellsControl boardCellsControl = new BoardCellsControl(WIDTH_BLOCKS, HEIGHT_BLOCKS);
    private Block currentBlock;

    public int getBlockCountOfWidth() {
        return WIDTH_BLOCKS;
    }
    public int getBlockCountOfHeight() {
        return HEIGHT_BLOCKS;
    }

    private Block generateCurrentBlock()  {
        currentBlock = new IMinoBlock(Direction.NORTH, 50, 0);
        return currentBlock;
    }

    private void moveDown() {
        currentBlock.moveDown();
        boardCellsControl.updateCells(currentBlock);
    }
}
