package example.tdd.tetris.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateBoardResult {
    private boolean udpated;
    private boolean needNewBlock;
    /**
     * 블럭이 바닥에 닿았을 때 제거된 row 개수. 
     */
    private int removedRows;
    private boolean gameOver;
}
