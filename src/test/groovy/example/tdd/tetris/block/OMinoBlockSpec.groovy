package example.tdd.tetris.block

import spock.lang.Specification

class OMinoBlockSpec extends Specification {
    def "O 미노의 모든 방향의 cell 배열정보는 동일하다."()    {
        when: "모든 방행의 O미노 생성"
        OMinoBlock north = new OMinoBlock(Direction.NORTH, 10, 0)
        OMinoBlock east = new OMinoBlock(Direction.NORTH, 10, 0)
        OMinoBlock south = new OMinoBlock(Direction.NORTH, 10, 0)
        OMinoBlock west = new OMinoBlock(Direction.NORTH, 10, 0)

        then: "모두 동일한 cell 배열을 가진다."
        BlockTestHelper.equalCells(north.getCells(), east.getCells())
        BlockTestHelper.equalCells(east.getCells(), south.getCells())
        BlockTestHelper.equalCells(south.getCells(), west.getCells())
        BlockTestHelper.equalCells(west.getCells(), north.getCells())
    }
}
