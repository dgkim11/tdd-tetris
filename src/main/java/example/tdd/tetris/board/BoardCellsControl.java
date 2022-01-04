package example.tdd.tetris.board;

import example.tdd.tetris.block.Block;
import lombok.Getter;

import java.util.List;

/**
 * Board의 모든 cell들의 상태를 관리한다.
 */
@Getter
public class BoardCellsControl {
    private int widthSize;
    private int heightSize;
    private final int EMPTY_CELL = -1;

    private int[][] boardCells;

    private Block currentBlock;

    public void setSize(int width, int height)   {
        this.widthSize = width;
        this.heightSize = height;
        this.boardCells = new int[height][width];
        clearBoardCells();
    }

    public void clearBoardCells() {
        for(int row = 0;row < boardCells.length;row++)  {
            for(int col = 0;col < boardCells[row].length;col++) {
                boardCells[row][col] = -1;
            }
        }
    }

    /**
     * 위치나 모양이 변경된 block으로 다시 update 한다. 만약, 위치나 모양이 바뀔 수 없는 경우는 현재 블럭으로 유지한다.
     *
     * @param block 신규 블럭.
     * @return update된 경우 true, update 할 수 없는 경우 false.
     */
    public boolean updateBlock(Block block)  {
        // 블럭을 이동시키려면 현재의 블럭을 먼저 지운 후 다음 위치로 블럭을 다시 그린다.
        if(currentBlock != null) removeCurrentBlock();

        // 해당 위치에 블럭을 위치시킬 수 있는지.
        if(! canLocateBlock(block))    {
            if(currentBlock != null) attachBlockToBoard(currentBlock);
            return false;
        }

        attachBlockToBoard(block);
        currentBlock = block;

        return true;
    }

    public UpdateBoardResult updateBoard(Block block)   {
        if(! canMoveDown(block))    {   // 더 이상 내려갈 수 없다면 cell이 모두 찬 row를 제거한다.
            List<Integer> filledRows = FilledRowsFinder.findFilledRows(boardCells);
            if(filledRows.size() > 0)   {
                removeRows(filledRows);
                currentBlock = null;
                return removedRowsResult(filledRows);
            } else {
                if (block.getYPos() == 0)    {
                    return gameOverResult();
                } else  {
                    currentBlock = null;
                    return newBlockResult();
                }
            }
        }
        return continueResult();
    }

    private UpdateBoardResult removedRowsResult(List<Integer> filledRows) {
        return new UpdateBoardResult(true, true, filledRows.size(), false);
    }

    private UpdateBoardResult continueResult() {
        return new UpdateBoardResult(true, false, 0, false);
    }

    private UpdateBoardResult newBlockResult() {
        return new UpdateBoardResult(true, true, 0, false);
    }

    private UpdateBoardResult gameOverResult() {
        return new UpdateBoardResult(false, false, 0, true);
    }

    private boolean canLocateBlock(Block block)    {
        if(block.getYPos() + block.getHeight() > boardCells.length) return false;  // 보드의 바닥인 경우
        if(block.getXPos() < 0) return false;       // 왼쪽 보드 영역을 벗어난 경우
        if(block.getXPos() + block.getWidth() > boardCells[0].length) return false;  // 오른쪽 보드 영역을 벗어난 경우
        for(int row = 0;row < block.getHeight();row++)   {
            for(int col = 0;col < block.getWidth();col++) {
                if(boardCells[row + block.getYPos()][col + block.getXPos()] != -1 && block.getCells()[row][col] != -1)  {   // 이미 다른 블럭이 공간을 차지하고 있는 경우
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 현재의 block
     * @param block
     * @return
     */
    private boolean canMoveDown(Block block) {
        int row = block.getYPos() + block.getHeight();
        if(row >= boardCells.length) return false;  // 보드의 바닥인 경우
        for(int col = 0;col < block.getWidth();col++) {
            if(boardCells[row][col + block.getXPos()] != -1) return false;            // 아래에 블럭이 쌓여 있는 경우
        }
        return true;
    }

    private void removeCurrentBlock() {
        int[][] blockCells = currentBlock.getCells();
        for(int row = 0;row < blockCells.length;row++)    {
            for(int col = 0;col < blockCells[row].length;col++) {
                if(blockCells[row][col] != -1) boardCells[currentBlock.getYPos() + row][currentBlock.getXPos() + col] = -1;
            }
        }
    }

    /**
     * 블럭의 위치와 모양대로 board에 나오도록 cells을 갱신한다.
     * @param block
     */
    private void attachBlockToBoard(Block block) {
        int[][] blockCells = block.getCells();
        for(int row = 0;row < blockCells.length;row++)  {
            for(int col = 0;col < blockCells[0].length;col++)   {
                if(blockCells[row][col] != -1)  {
                    boardCells[row + block.getYPos()][col + block.getXPos()] = blockCells[row][col];
                }
            }
        }
    }

    /**
     * 보드에서 삭제되어야 할 row들을 삭제한다.
     *
     * @param rows 삭제되어야 할 row 목록
     */
    private void removeRows(List<Integer> rows)  {
        rows.forEach(this::removeRow);
    }

    private void removeRow(int row) {
        for(int i = row;i > 0;i--)  {
            boardCells[i] = boardCells[i-1];
        }

        boardCells[0] = new int[widthSize];
        for(int i = 0;i < boardCells[0].length;i++) boardCells[0][i] = EMPTY_CELL;
    }
}
