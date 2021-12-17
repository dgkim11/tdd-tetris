package example.tdd.tetris.block;

public class OMinoBlock extends Block {
    public OMinoBlock(int rgbColor) {
        super(rgbColor);
    }

    public OMinoBlock(Direction direction, int rgbColor) {
        super(direction, rgbColor);
    }

    @Override
    public void setDirection(Direction direction) {
        cells = new int[2][2];
        cells[0][0] = rgbColor;
        cells[0][1] = rgbColor;
        cells[1][0] = rgbColor;
        cells[1][1] = rgbColor;
    }
}
