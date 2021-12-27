package example.tdd.tetris.block

import spock.lang.Specification

class IMinoBlockSpec extends Specification  {
    def "I 미노의 기본 방향의 cell 배열정보 검증"()    {
        given: "I미노 생성"
        IMinoBlock iMinoBlock = new IMinoBlock(Direction.NORTH, 10, 0)

        when: "기본방향으로 설정"
        int[][] cells = iMinoBlock.setDirection(Direction.NORTH).getCells()

        then:
        cells.length == 4            // height
        cells[0].length == 1         // width
        cells[0][0] == 10
        cells[1][0] == 10
        cells[2][0] == 10
        cells[3][0] == 10
    }

    def "I 미노의 East 방향 cell 배열정보 검증"()    {
        given: "I미노 생성"
        IMinoBlock block = new IMinoBlock(Direction.NORTH, 10, 0)

        when: "East 방향 설정"
        int[][] cells = block.setDirection(Direction.EAST).getCells()

        then:
        cells.length == 1            // height
        cells[0].length == 4         // width
        cells[0][0] == 10
        cells[0][1] == 10
        cells[0][2] == 10
        cells[0][3] == 10
    }

    def "I 미노의 South 방향은 North와 동일"()    {
        given: "I미노 생성"
        IMinoBlock iMinoBlock = new IMinoBlock(Direction.NORTH, 10, 0)

        when: "South 방향 설정"
        int[][] cells = iMinoBlock.setDirection(Direction.SOUTH).getCells()

        then: "North 방향과 동일"
        BlockTestHelper.equalCells(cells, new IMinoBlock(Direction.NORTH, 10, 0).getCells())
    }

    def "I 미노의 West 방향은 East와 동일"()    {
        given: "I미노 생성"
        IMinoBlock iMinoBlock = new IMinoBlock(Direction.NORTH, 10, 0)

        when: "West 방향 설정"
        int[][] cells = iMinoBlock.setDirection(Direction.WEST).getCells()

        then: "East 방향과 동일"
        BlockTestHelper.equalCells(cells, new IMinoBlock(Direction.EAST, 10, 0).getCells())
    }
}
