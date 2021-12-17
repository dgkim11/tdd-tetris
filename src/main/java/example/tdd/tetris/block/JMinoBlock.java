package example.tdd.tetris.block;

public class JMinoBlock extends Block {
    public JMinoBlock(int rgbColor) {
        super(rgbColor);
    }

    public JMinoBlock(Direction direction, int rgbColor) {
        super(direction, rgbColor);
    }

    @Override
    public void setDirection(Direction direction)   {
        this.direction = direction;
        updateCells(direction);
    }

    private void updateCells(Direction direction)   {
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
