package example.tdd.tetris.block;

public class LMinoBlock extends Block {
    public LMinoBlock(Direction direction, int rgbColor, int xPos) {
        super(direction, rgbColor, xPos);
        updateCells();
    }
    public LMinoBlock(Direction direction, int rgbColor, int xPos, int yPos) {
        super(direction, rgbColor, xPos, yPos);
        updateCells();
    }

    @Override
    public Block setDirection(Direction direction) {
        return new LMinoBlock(direction, rgbColor, xPos, yPos);
    }

    @Override
    public Block moveLeft() {
        return new LMinoBlock(direction, rgbColor, xPos-1, yPos);
    }

    @Override
    public Block moveRight() {
        return new LMinoBlock(direction, rgbColor, xPos+1, yPos);
    }

    @Override
    public Block turnLeft() {
        return new LMinoBlock(getNextDirection(Turn.LEFT), rgbColor, xPos, yPos);
    }

    @Override
    public Block turnRight() {
        return new LMinoBlock(getNextDirection(Turn.RIGHT), rgbColor, xPos, yPos);
    }

    @Override
    public Block moveDown() {
        return new LMinoBlock(direction, rgbColor, xPos, yPos+1);
    }

    public void updateCells() {
        switch (direction)    {
            case NORTH:
                cells = new int[3][2];
                cells[0][0] = rgbColor;
                cells[0][1] = -1;
                cells[1][0] = rgbColor;
                cells[1][1] = -1;
                cells[2][0] = rgbColor;
                cells[2][1] = rgbColor;
                break;

            case EAST:
                cells = new int[2][3];
                cells[0][0] =  rgbColor;
                cells[0][1] =  rgbColor;
                cells[0][2] =  rgbColor;
                cells[1][0] =  rgbColor;
                cells[1][1] = -1;
                cells[1][2] = -1;
                break;
            case SOUTH:
                cells = new int[3][2];
                cells[0][0] =  rgbColor;
                cells[0][1] =  rgbColor;
                cells[1][0] = -1;
                cells[1][1] =  rgbColor;
                cells[2][0] = -1;
                cells[2][1] =  rgbColor;
                break;
            case WEST:
                cells = new int[2][3];
                cells[0][0] = -1;
                cells[0][1] = -1;
                cells[0][2] =  rgbColor;
                cells[1][0] =  rgbColor;
                cells[1][1] =  rgbColor;
                cells[1][2] =  rgbColor;
                break;
        }
    }

}
