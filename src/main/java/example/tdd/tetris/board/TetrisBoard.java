package example.tdd.tetris.board;

import example.tdd.tetris.block.Block;

public class TetrisBoard {
    private static final int WIDTH_BLOCKS = 20;
    private static final int HEIGHT_BLOCKS = 50;
    private BoardCellsControl boardCellsControl = new BoardCellsControl(WIDTH_BLOCKS, HEIGHT_BLOCKS);
    private Block currentBlock;

    public int getBlockCountOfWidth() {
        return WIDTH_BLOCKS;
    }
    public int getBlockCountOfHeight() {
        return HEIGHT_BLOCKS;
    }


}
