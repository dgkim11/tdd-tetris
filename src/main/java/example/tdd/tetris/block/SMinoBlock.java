package example.tdd.tetris.block;

public class SMinoBlock extends Block {
    public SMinoBlock(Direction direction, int rgbColor, int xPos) {
        super(direction, rgbColor, xPos);
        updateCells();
    }
    public SMinoBlock(Direction direction, int rgbColor, int xPos, int yPos) {
        super(direction, rgbColor, xPos, yPos);
        updateCells();
    }

    @Override
    public Block setDirection(Direction direction) {
        return new SMinoBlock(direction, rgbColor, xPos, yPos);
    }

    @Override
    public Block moveLeft() {
        return new SMinoBlock(direction, rgbColor, xPos-1, yPos);
    }

    @Override
    public Block moveRight() {
        return new SMinoBlock(direction, rgbColor, xPos+1, yPos);
    }

    @Override
    public Block turnLeft() {
        return new SMinoBlock(getNextDirection(Turn.LEFT), rgbColor, xPos, yPos);
    }

    @Override
    public Block turnRight() {
        return new SMinoBlock(getNextDirection(Turn.RIGHT), rgbColor, xPos, yPos);
    }

    @Override
    public Block moveDown() {
        return new SMinoBlock(direction, rgbColor, xPos, yPos+1);
    }

    public void updateCells() {
        if(direction == Direction.NORTH || direction == Direction.SOUTH)    {
            cells = new int[2][3];
            cells[0][0] = -1;
            cells[0][1] = rgbColor;
            cells[0][2] = rgbColor;
            cells[1][0] = rgbColor;
            cells[1][1] = rgbColor;
            cells[1][2] = -1;
        }
        else if(direction == Direction.EAST || direction == Direction.WEST) {
            cells = new int[3][2];
            cells[0][0] = rgbColor;
            cells[0][1] = -1;
            cells[1][0] = rgbColor;
            cells[1][1] = rgbColor;
            cells[2][0] = -1;
            cells[2][1] = rgbColor;
        }
    }
}
