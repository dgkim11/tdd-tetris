package example.tdd.tetris.block;

import lombok.Getter;

@Getter
public abstract class Block {
    protected int yPos;
    protected int xPos;
    protected Direction direction;
    protected int rgbColor;
    protected int[][] cells;

    public Block(Direction direction, int rgbColor, int xPos) {
        this.direction = direction;
        this.rgbColor = rgbColor;
        this.xPos = xPos;
    }

    public Block(Direction direction, int rgbColor, int xPos, int yPos) {
        this.direction = direction;
        this.rgbColor = rgbColor;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getWidth()   {
        return cells[0].length;
    }
    public int getHeight()  {
        return cells.length;
    }

    public abstract Block setDirection(Direction direction);

    public abstract Block moveLeft();
    public abstract Block moveRight();
    /**
     * 아래로 한칸 이동한다.
     */
    public abstract Block moveDown();
}
