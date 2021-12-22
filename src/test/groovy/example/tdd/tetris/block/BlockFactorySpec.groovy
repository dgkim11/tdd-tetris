package example.tdd.tetris.block

import example.tdd.tetris.board.TetrisBoard
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class BlockFactorySpec extends Specification {
    def "블럭의 모양, 색깔, 방향등은 random으로 생성된다."() {
        BlockFactory sut = new BlockFactory()

        when:
        Block block1 = sut.generateRandomBlock()
        Block block2 = sut.generateRandomBlock()

        then:
        BlockTestHelper.equalCells(block1.getCells(), block2.getCells()) == false   // 하지만, 확율은 적지만 true일 가능성도 있다.
    }

    def "최초 블럭 생성 시 블럭의 위치는 보드를 벗어날 수 없다."()    {
        BlockFactory sut = new BlockFactory()

        when:
        Block block = sut.generateRandomBlock()

        then:
        block.getXPos() + block.getWidth() < TetrisBoard.WIDTH_BLOCKS
    }
}
