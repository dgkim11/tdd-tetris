package example.tdd.tetris.block

import spock.lang.Specification

class LMinoBlockSpec extends Specification {
    def "L 미노의 기본 방향의 cell 배열정보 검증"()    {
        given: "L 미노 생성"
        LMinoBlock block = new LMinoBlock(Direction.NORTH, 10, 0)

        when: "기본방향으로 설정"
        int[][] cells = block.setDirection(Direction.NORTH).getCells()

        then:
        cells.length == 3            // height
        cells[0].length == 2         // width
        cells[0][0] == 10
        cells[0][1] == -1
        cells[1][0] == 10
        cells[1][1] == -1
        cells[2][0] == 10
        cells[2][1] == 10
    }

    def "L 미노의 East의 cell 배열정보 검증"()    {
        given: "L 미노 생성"
        LMinoBlock block = new LMinoBlock(Direction.NORTH, 10, 0)

        when: "East 방향 설정"
        int[][] cells = block.setDirection(Direction.EAST).getCells()

        then:
        cells.length == 2            // height
        cells[0].length == 3         // width
        cells[0][0] == 10
        cells[0][1] == 10
        cells[0][2] == 10
        cells[1][0] == 10
        cells[1][1] == -1
        cells[1][2] == -1
    }
    def "L 미노의 South 방향의 cell 배열정보 검증"()    {
        given: "L 미노 생성"
        LMinoBlock block = new LMinoBlock(Direction.NORTH, 10, 0)

        when: "South 방향 설정"
        int[][] cells = block.setDirection(Direction.SOUTH).getCells()

        then:
        cells.length == 3            // height
        cells[0].length == 2         // width
        cells[0][0] == 10
        cells[0][1] == 10
        cells[1][0] == -1
        cells[1][1] == 10
        cells[2][0] == -1
        cells[2][1] == 10
    }
    def "L 미노의 West 방향의 cell 배열정보 검증"()    {
        given: "L 미노 생성"
        LMinoBlock block = new LMinoBlock(Direction.NORTH, 10, 0)

        when: "West 방향 설정"
        int[][] cells = block.setDirection(Direction.WEST).getCells()

        then:
        cells.length == 2            // height
        cells[0].length == 3         // width
        cells[0][0] == -1
        cells[0][1] == -1
        cells[0][2] == 10
        cells[1][0] == 10
        cells[1][1] == 10
        cells[1][2] == 10
    }
}
