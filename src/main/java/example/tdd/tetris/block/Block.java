package example.tdd.tetris.block;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Block {
    protected enum Turn { RIGHT, LEFT }
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
     * 좌회전
     * @return
     */
    public abstract Block turnLeft();

    /**
     * 우회전
     * @return
     */
    public abstract Block turnRight();
    /**
     * 아래로 한칸 이동한다.
     */
    public abstract Block moveDown();

    protected Direction getNextDirection(Turn turn) {
        switch(direction)   {
            case NORTH:
                return turn == Turn.LEFT ? Direction.WEST : Direction.EAST;
            case EAST:
                return turn == Turn.LEFT ? Direction.NORTH : Direction.SOUTH;
            case SOUTH:
                return turn == Turn.LEFT ? Direction.EAST : Direction.WEST;
            case WEST:
                return turn == Turn.LEFT ? Direction.SOUTH : Direction.NORTH;
        }
        throw new RuntimeException("undefined direction");
    }
}
