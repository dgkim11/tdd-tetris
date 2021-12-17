package example.tdd.tetris.board

import example.tdd.tetris.block.BlockTestHelper
import spock.lang.Specification

class BoardCellsControlSpec extends Specification {
    def "row가 삭제되면 그 위의 블럭들이 아래로 내려온다."()   {
        BoardCellsControl sut = new BoardCellsControl(5,5)
        given:
        int[][] cells = sut.getBoardCells()
        cells[0] = [-1,-1,1,1,1]
        cells[1] = [1,-1,1,1,1]
        cells[2] = [1,1,1,1,1]  // row 제거
        cells[3] = [-1,1,1,1,1]
        cells[4] = [1,1,1,1,1]  // row 제거

        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,-1,-1,-1,-1]
        updatedCells[1] = [-1,-1,-1,-1,-1]
        updatedCells[2] = [-1,-1,1,1,1]
        updatedCells[3] = [1,-1,1,1,1]
        updatedCells[4] = [-1,1,1,1,1]

        when:
        sut.updateCells()

        then:
        BlockTestHelper.equalCells(cells, updatedCells)
    }
}
