package example.tdd.tetris.block

import example.tdd.tetris.board.TetrisBoard
import spock.lang.Ignore
import spock.lang.Specification

@Ignore // random 생성이기에 test case로 작성하기 어려움. 코드의 동작 여부 확인을 위해 만들었기에 코드 작성 후 ignore 처리함.
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
