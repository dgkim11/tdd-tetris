package example.tdd.tetris.block;

public class SMinoBlock extends Block {
    public SMinoBlock(int rgbColor) {
        super(rgbColor);
    }

    public SMinoBlock(Direction direction, int rgbColor) {
        super(direction, rgbColor);
    }

    @Override
    public void setDirection(Direction direction) {
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
