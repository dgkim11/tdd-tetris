package example.tdd.tetris.block;

public class OMinoBlock extends Block {
    public OMinoBlock(Direction direction, int rgbColor, int xPos) {
        super(direction, rgbColor, xPos);
        upateCells();
    }
    public OMinoBlock(Direction direction, int rgbColor, int xPos, int yPos) {
        super(direction, rgbColor, xPos, yPos);
        upateCells();
    }

    @Override
    public Block setDirection(Direction direction) {
        return new OMinoBlock(direction, rgbColor, xPos, yPos);
    }
    @Override
    public Block moveLeft() {
        return new OMinoBlock(direction, rgbColor, xPos-1, yPos);
    }

    @Override
    public Block moveRight() {
        return new OMinoBlock(direction, rgbColor, xPos+1, yPos);
    }

    @Override
    public Block moveDown() {
        return new OMinoBlock(direction, rgbColor, xPos, yPos+1);
    }

    public void upateCells() {
        cells = new int[2][2];
        cells[0][0] = rgbColor;
        cells[0][1] = rgbColor;
        cells[1][0] = rgbColor;
        cells[1][1] = rgbColor;
    }
}
