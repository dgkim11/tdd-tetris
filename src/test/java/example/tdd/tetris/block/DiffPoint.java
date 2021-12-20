package example.tdd.tetris.block;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiffPoint {
    int xPos;
    int yPos;
    int leftCellsValue;
    int rightCellsValue;
}
