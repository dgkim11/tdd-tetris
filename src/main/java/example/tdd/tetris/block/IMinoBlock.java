package example.tdd.tetris.block;

public class IMinoBlock extends Block {
    public IMinoBlock(Direction direction, int rgbColor, int xPos) {
        super(direction, rgbColor, xPos);
        updateCells();
    }
    public IMinoBlock(Direction direction, int rgbColor, int xPos, int yPos) {
        super(direction, rgbColor, xPos, yPos);
        updateCells();
    }

    @Override
    public IMinoBlock setDirection(Direction direction)   {
        return new IMinoBlock(direction, rgbColor, xPos, yPos);
    }

    @Override
    public Block moveLeft() {
        return new IMinoBlock(direction, rgbColor, xPos-1, yPos);
    }

    @Override
    public Block moveRight() {
        return new IMinoBlock(direction, rgbColor, xPos+1, yPos);
    }

    @Override
    public Block turnLeft() {
        return new IMinoBlock(getNextDirection(Turn.LEFT), rgbColor, xPos, yPos);
    }

    @Override
    public Block turnRight() {
        return new IMinoBlock(getNextDirection(Turn.RIGHT), rgbColor, xPos, yPos);
    }

    @Override
    public Block moveDown() {
        return new IMinoBlock(direction, rgbColor, xPos, yPos+1);
    }

    private void updateCells()   {
        if(direction == Direction.NORTH || direction == Direction.SOUTH)    {
            this.cells = new int[4][1];
            this.cells[0][0] = rgbColor;
            this.cells[1][0] = rgbColor;
            this.cells[2][0] = rgbColor;
            this.cells[3][0] = rgbColor;
        }
        else if(direction == Direction.EAST || direction == Direction.WEST)    {
            this.cells = new int[1][4];
            this.cells[0][0] = rgbColor;
            this.cells[0][1] = rgbColor;
            this.cells[0][2] = rgbColor;
            this.cells[0][3] = rgbColor;
        }

    }
}
