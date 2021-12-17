package example.tdd.tetris.block;

public class LMinoBlock extends Block {
    public LMinoBlock(int rgbColor) {
        super(rgbColor);
    }

    public LMinoBlock(Direction direction, int rgbColor) {
        super(direction, rgbColor);
    }

    @Override
    public void setDirection(Direction direction) {
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
