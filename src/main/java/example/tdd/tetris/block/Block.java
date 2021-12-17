package example.tdd.tetris.block;

import lombok.Getter;

@Getter
public abstract class Block {
    protected int topPosition;
    protected int leftPosition;
    protected int width;
    protected int height;
    protected Direction direction;
    protected int rgbColor;
    protected int[][] cells;

    public Block(int rgbColor)  {
        this(Direction.NORTH, rgbColor);
    }

    public Block(Direction direction, int rgbColor) {
        this.rgbColor = rgbColor;   // 주의사항. rgbColor 설정이 setDirection보다 먼저 호출되어야 함.
        setDirection(direction);
    }

    public abstract void setDirection(Direction direction);
}
