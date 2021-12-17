package example.tdd.tetris.board;

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

    public BoardCellsControl(int widthSize, int heightSize) {
        this.widthSize = widthSize;
        this.heightSize = heightSize;
        this.boardCells = new int[heightSize][widthSize];
    }

    public void updateCells() {
        List<Integer> filledRows = FilledRowsFinder.findFilledRows(boardCells);
        removeRows(filledRows);
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
