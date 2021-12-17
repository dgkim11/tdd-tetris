package example.tdd.tetris.block

import spock.lang.Specification

class SMinoBlockSpec extends Specification {
    def "S 미노의 기본 방향의 cell 배열정보 검증"()    {
        given: "S 미노 생성"
        SMinoBlock block = new SMinoBlock(10)

        when: "기본방향으로 설정"
        block.setDirection(Direction.NORTH)
        int[][] cells = block.getCells()

        then:
        cells.length == 2            // height
        cells[0].length == 3         // width
        cells[0][0] == -1
        cells[0][1] == 10
        cells[0][2] == 10
        cells[1][0] == 10
        cells[1][1] == 10
        cells[1][2] == -1
    }

    def "S 미노의 East 방향의 cell 배열정보 검증"()    {
        given: "S 미노 생성"
        SMinoBlock block = new SMinoBlock(10)

        when: "East 방향 설정"
        block.setDirection(Direction.EAST)
        int[][] cells = block.getCells()

        then:
        cells.length == 3            // height
        cells[0].length == 2         // width
        cells[0][0] == 10
        cells[0][1] == -1
        cells[1][0] == 10
        cells[1][1] == 10
        cells[2][0] == -1
        cells[2][1] == 10
    }

    def "S 미노의 South 방향은 North 방향과 동일"()    {
        given: "S 미노 생성"
        SMinoBlock block = new SMinoBlock(10)

        when: "South 방향 설정"
        block.setDirection(Direction.SOUTH)

        then: "North와 동일"
        BlockTestHelper.equalCells(block.getCells(), new SMinoBlock(Direction.NORTH, 10).getCells())
    }

    def "S 미노의 West 방향은 East 방향과 동일"()    {
        given: "S 미노 생성"
        SMinoBlock block = new SMinoBlock(10)

        when: "West 방향 설정"
        block.setDirection(Direction.WEST)

        then: "East 방향과 동일"
        BlockTestHelper.equalCells(block.getCells(), new SMinoBlock(Direction.EAST, 10).getCells())
    }
}
