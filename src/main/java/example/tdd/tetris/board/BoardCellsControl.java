package example.tdd.tetris.board;

import example.tdd.tetris.block.Block;
import lombok.Getter;

import java.util.List;

/**
 * Board의 모든 cell들의 상태를 관리한다.
 */
@Getter
public class BoardCellsControl {
    private final int widthSize;
    private final int heightSize;
    private final int EMPTY_CELL = -1;

    private int[][] boardCells;

    private Block currentBlock;

    public BoardCellsControl(int widthSize, int heightSize) {
        this.widthSize = widthSize;
        this.heightSize = heightSize;
        this.boardCells = new int[heightSize][widthSize];
        clearBoardCells();
    }

    private void clearBoardCells() {
        for(int row = 0;row < boardCells.length;row++)  {
            for(int col = 0;col < boardCells[row].length;col++) {
                boardCells[row][col] = -1;
            }
        }
    }

    /**
     * 위치나 모양이 변경된 block으로 다시 update 한다. 만약, 위치나 모양이 바뀔 수 없는 경우는 현재 블럭으로 유지한다.
     *
     * @param block
     * @return 블럭이 갱신되었는지 여부. true - 갱신됨, false - 갱신되지 않음.
     */
    public boolean updateBlock(Block block) {
        if(currentBlock != null) removeCurrentBlock();
        if(! canMove(block))    {
            if(currentBlock != null) attachBlockToBoard(currentBlock);       // 현재 블럭으로 다시 원위치.
            return false;      // 해당 위치로 이동할 수 없는 경우 block을 갱신하지 않는다.
        }
        currentBlock = block;
        attachBlockToBoard(block);
        if(! canMoveDown(block))    {   // 더 이상 내려갈 수 없다면 cell이 모두 찬 row를 제거한다.
            List<Integer> filledRows = FilledRowsFinder.findFilledRows(boardCells);
            if(filledRows.size() > 0)   {
                removeRows(filledRows);
            }
            currentBlock = null;
        }
        return true;
    }

    private boolean canMove(Block block)    {
        int row = block.getYPos() + block.getHeight();
        if(row >= boardCells.length + 1) return false;  // 보드의 바닥인 경우
        if(block.getXPos() < 0) return false;       // 왼쪽 보드 영역을 벗어난 경우
        if(block.getXPos() + block.getWidth() >= boardCells[0].length + 1) return false;  // 오른쪽 보드 영역을 벗어난 경우
        for(int i = 0;i < block.getHeight();i++)   {
            for(int j = 0;j < block.getWidth();j++) {
                if(boardCells[i + block.getYPos()][j + block.getXPos()] != -1 && block.getCells()[i][j] != -1)  {   // 이미 다른 블럭이 공간을 차지하고 있는 경우
                    return false;
                }
            }
        }
        return true;
    }

    private boolean needNewBlock()  {
        return currentBlock == null;
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
                boardCells[currentBlock.getYPos() + row][currentBlock.getXPos() + col] = -1;
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
