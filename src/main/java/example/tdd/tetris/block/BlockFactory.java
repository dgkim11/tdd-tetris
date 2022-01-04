package example.tdd.tetris.block;

import example.tdd.tetris.board.TetrisBoard;
import lombok.Getter;

import java.util.Random;

public class BlockFactory {
    private static final int BLOCK_TYPES_COUNT = 7;      // 블럭종류 개수
    private static final int DIRECTION_COUNT = 4;       // 동서남북
    private static final int COLOR_COUNT = 4;           // 색깔 종류

    @Getter
    public enum Color   {
        BLUE(0x0000ff), YELLOW(0xffff00), RED(0xff0000), GREEN(0x00ff00);

        int rgbColor;
        Color(int rgbColor) {
            this.rgbColor = rgbColor;
        }
    }

    public Block generateRandomBlock() {
        Random random = new Random();
        int blockType = random.nextInt(BLOCK_TYPES_COUNT);
        int direction = random.nextInt(DIRECTION_COUNT);
        int colorType = random.nextInt(COLOR_COUNT);
        int xPos = random.nextInt(TetrisBoard.WIDTH_BLOCKS-4);  // 최대 block의 width가 4이다. 따라서, xPos의 max는 width - 4 이다.
        switch (blockType)  {
            case 0:
                return new IMinoBlock(randomDirection(direction), randomColor(colorType), xPos, 0);
            case 1:
                return new JMinoBlock(randomDirection(direction), randomColor(colorType), xPos, 0);
            case 2:
                return new LMinoBlock(randomDirection(direction), randomColor(colorType), xPos, 0);
            case 3:
                return new OMinoBlock(randomDirection(direction), randomColor(colorType), xPos, 0);
            case 4:
                return new SMinoBlock(randomDirection(direction), randomColor(colorType), xPos, 0);
            case 5:
                return new TMinoBlock(randomDirection(direction), randomColor(colorType), xPos, 0);
            case 6:
                return new ZMinoBlock(randomDirection(direction), randomColor(colorType), xPos, 0);
        }

        throw new RuntimeException("unknown block type. block type:" + blockType);
    }

    private int randomColor(int colorType) {
        switch (colorType)  {
            case 0:
                return Color.RED.getRgbColor();
            case 1:
                return Color.GREEN.getRgbColor();
            case 2:
                return Color.BLUE.getRgbColor();
            case 3:
                return Color.YELLOW.getRgbColor();
        }
        return Color.YELLOW.getRgbColor();      // default.
    }

    private Direction randomDirection(int direction) {
        switch (direction)  {
            case 0:
                return Direction.NORTH;
            case 1:
                return Direction.EAST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.WEST;
        }

        return Direction.NORTH;         // default
    }
}
