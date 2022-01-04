package example.tdd.tetris.board;

import example.tdd.tetris.block.Block;
import example.tdd.tetris.block.BlockFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.Timer;

public class TetrisBoard {
    public static final int WIDTH_BLOCKS = 20;
    public static final int HEIGHT_BLOCKS = 50;
    private BoardCellsControl boardCellsControl;
    private BlockFactory blockFactory;
    @Setter
    private TetrisBoardListener listener;

    @Getter
    private Block currentBlock;
    private static int INTERVAL = 1000;
    private static int POINT = 10;
    @Getter
    private long score;
    @Getter
    private boolean gameOvered = false;
    @Getter
    private boolean gameStarted;
    public int getBlockCountOfWidth() {
        return WIDTH_BLOCKS;
    }
    public int getBlockCountOfHeight() {
        return HEIGHT_BLOCKS;
    }

    private Timer timer;

    public TetrisBoard(BoardCellsControl boardCellsControl, BlockFactory blockFactory) {
        this.boardCellsControl = boardCellsControl;
        this.boardCellsControl.setSize(WIDTH_BLOCKS, HEIGHT_BLOCKS);
        this.blockFactory = blockFactory;
    }

    public void newBlock()  {
        currentBlock = blockFactory.generateRandomBlock();
        System.out.println("### newBlock: " + currentBlock);
        System.out.println("new block:" + currentBlock.getClass().getName());
        updateBlock(currentBlock);
    }

    public void moveDownBlock() {
        System.out.println("### moveDownBlock. currentBlock:" + currentBlock);
        if(currentBlock == null) return;
        currentBlock = currentBlock.moveDown();
        System.out.println("move down. ypos:" + currentBlock.getYPos());
        updateBlock(currentBlock);
    }

    private void updateBlock(Block block) {
        System.out.println("### updateBlock");
        boardCellsControl.updateBlock(block);
        currentBlock = boardCellsControl.getCurrentBlock();
        if(listener != null) listener.updated(getBoardCells(), score);
    }

    public void startGame() {
        this.score = 0;
        this.gameOvered = false;
        this.gameStarted = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new NextActionTask(this), INTERVAL, INTERVAL);
        boardCellsControl.clearBoardCells();
        System.out.println("game started.");
    }

    public void gameOver() {
        timer.cancel();
        this.gameOvered = true;
        this.gameStarted = false;
        if(listener != null) listener.gameOver(score);
        System.out.println("Game over!");
    }

    public void moveLeftBlock() {
        if(currentBlock == null) return;
        currentBlock = currentBlock.moveLeft();
        updateBlock(currentBlock);
    }

    public void moveRightBlock() {
        if(currentBlock == null) return;
        currentBlock = currentBlock.moveRight();
        updateBlock(currentBlock);
    }

    public void turnRightBlock() {
        if(currentBlock == null) return;
        currentBlock = currentBlock.turnRight();
        updateBlock(currentBlock);
    }

    public int[][] getBoardCells()   {
        return boardCellsControl.getBoardCells();
    }

    private boolean needNewBlock = true;
    public void doNextAction() {
        System.out.println("### doNextAction. needNextBlock:" + needNewBlock);

        if(gameOvered)  { gameOver(); return; }

        if(needNewBlock) { newBlock(); needNewBlock = false; }
        else moveDownBlock();

        UpdateBoardResult result = boardCellsControl.updateBoard(currentBlock);
        if(result.isGameOver()) {
            gameOver();
            return;
        }
        if(result.isNeedNewBlock())  {
            if(result.getRemovedRows() > 0) {
                score += (long) result.getRemovedRows() * POINT;
            }
            needNewBlock = true;
        }
        currentBlock = boardCellsControl.getCurrentBlock();
    }
}
