package example.tdd.tetris.block;

public class JMinoBlock extends Block {
    public JMinoBlock(Direction direction, int rgbColor, int xPos) {
        super(direction, rgbColor, xPos);
        updateCells();
    }
    public JMinoBlock(Direction direction, int rgbColor, int xPos, int yPos) {
        super(direction, rgbColor, xPos, yPos);
        updateCells();
    }

    @Override
    public JMinoBlock setDirection(Direction direction)   {
        return new JMinoBlock(direction, rgbColor, xPos, yPos);
    }

    @Override
    public Block moveLeft() {
        return new JMinoBlock(direction, rgbColor, xPos-1, yPos);
    }

    @Override
    public Block moveRight() {
        return new JMinoBlock(direction, rgbColor, xPos+1, yPos);
    }

    @Override
    public Block turnLeft() {
        return new JMinoBlock(getNextDirection(Turn.LEFT), rgbColor, xPos, yPos);
    }

    @Override
    public Block turnRight() {
        return new JMinoBlock(getNextDirection(Turn.RIGHT), rgbColor, xPos, yPos);
    }

    @Override
    public Block moveDown() {
        return new JMinoBlock(direction, rgbColor, xPos, yPos+1);
    }

    private void updateCells()   {
        if(direction == Direction.NORTH)    {   //
            cells = new int[3][2];
            cells[0][0] = -1;
            cells[0][1] = rgbColor;
            cells[1][0] = -1;
            cells[1][1] = rgbColor;
            cells[2][0] = rgbColor;
            cells[2][1] = rgbColor;
        } else if(direction == Direction.EAST)  {
            cells = new int[2][3];
            cells[0][0] = -1;
            cells[0][1] = rgbColor;
            cells[0][2] = -1;
            cells[1][0] = rgbColor;
            cells[1][1] = rgbColor;
            cells[1][2] = rgbColor;
        } else if(direction == Direction.SOUTH)  {
            cells = new int[3][2];
            cells[0][0] = rgbColor;
            cells[0][1] = -1;
            cells[1][0] = -1;
            cells[1][1] = rgbColor;
            cells[2][0] = rgbColor;
            cells[2][1] = rgbColor;
        } else   {              // WEST
            cells = new int[2][3];
            cells[0][0] = rgbColor;
            cells[0][1] = rgbColor;
            cells[0][2] = rgbColor;
            cells[1][0] = -1;
            cells[1][1] = -1;
            cells[1][2] = rgbColor;
        }
    }
}
