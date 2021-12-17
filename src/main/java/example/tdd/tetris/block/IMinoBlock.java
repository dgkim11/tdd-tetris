package example.tdd.tetris.block;

public class IMinoBlock extends Block {
    public IMinoBlock(int rgbColor) {
        super(rgbColor);
    }

    public IMinoBlock(Direction direction, int rgbColor) {
        super(direction, rgbColor);
    }

    @Override
    public void setDirection(Direction direction)   {
        this.direction = direction;
        updateCells(direction);
    }

    private void updateCells(Direction direction)   {
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
