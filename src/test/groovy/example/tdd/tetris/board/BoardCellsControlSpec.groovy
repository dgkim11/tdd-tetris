package example.tdd.tetris.board

import example.tdd.tetris.block.Block
import example.tdd.tetris.block.BlockTestHelper
import example.tdd.tetris.block.DiffPoint
import example.tdd.tetris.block.Direction
import example.tdd.tetris.block.IMinoBlock
import example.tdd.tetris.block.TMinoBlock
import spock.lang.Specification

class BoardCellsControlSpec extends Specification {
    def "보드에서 블럭이 아래로 한칸 내려간다."() {
        BoardCellsControl sut = new BoardCellsControl(5,5)

        given: "보드에 블럭이 존재한다."
        Block block = new TMinoBlock(Direction.EAST, 100, 0)       // ㅏ 모양
        sut.updateCells(block)
        int[][] curCells = BlockTestHelper.copyArray(sut.getBoardCells())

        when: "보드에서 한칸 내려간다."
        Block upatedBlock = block.moveDown()
        sut.updateCells(upatedBlock)
        List<DiffPoint> diffPointList = BlockTestHelper.findDiffs(curCells, sut.getBoardCells())

        then: "보드의 cell들이 갱신된다."
        diffPointList.size() == 4       // ㅏ 모양이 아래로 내라겨면 제일위, 제일 아래, 오른쪽 첨자 부분이 한칸 아래로 내려간다.
        diffPointList.get(0).getXPos() == 0
        diffPointList.get(0).getYPos() == 0
        diffPointList.get(0).getLeftCellsValue() == 100
        diffPointList.get(0).getRightCellsValue() == -1
        diffPointList.get(1).getXPos() == 1
        diffPointList.get(1).getYPos() == 1
        diffPointList.get(1).getLeftCellsValue() == 100
        diffPointList.get(1).getRightCellsValue() == -1
        diffPointList.get(2).getXPos() == 1
        diffPointList.get(2).getYPos() == 2
        diffPointList.get(2).getLeftCellsValue() == -1
        diffPointList.get(2).getRightCellsValue() == 100
        diffPointList.get(3).getXPos() == 0
        diffPointList.get(3).getYPos() == 3
        diffPointList.get(3).getLeftCellsValue() == -1
        diffPointList.get(3).getRightCellsValue() == 100
    }

    def "블럭이 바닥에 닿으면 더 이상 내려가지 못하며 다음 블럭을 요청한다."()  {
        BoardCellsControl sut = new BoardCellsControl(5,5)
        given:
        IMinoBlock block = new IMinoBlock(Direction.EAST, 50, 0, 4)    // ㅡ 모양으로 바닥에 떨어짐.
        when:
        boolean nextBlock = sut.updateCells(block)
        int[][] boardCells = new int[5][5]
        boardCells[0] = [-1,-1,-1,-1,-1]
        boardCells[1] = [-1,-1,-1,-1,-1]
        boardCells[2] = [-1,-1,-1,-1,-1]
        boardCells[3] = [-1,-1,-1,-1,-1]
        boardCells[4] = [50,50,50,50,-1]

        then:
        nextBlock
        BlockTestHelper.equalCells(boardCells, sut.getBoardCells())
    }
    def "row가 삭제되면 그 위의 블럭들이 아래로 내려온다."()   {
        BoardCellsControl sut = new BoardCellsControl(5,5)

        given:
        IMinoBlock block1 = new IMinoBlock(Direction.EAST, 50, 0, 4)    // ㅡ 모양으로 바닥에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 4, 1)   // ㅣ 모양으로 오른쪽 끝에 떨어짐.

        when:
        sut.updateCells(block1)
        sut.updateCells(block2)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,-1,-1,-1,-1]
        updatedCells[1] = [-1,-1,-1,-1,-1]
        updatedCells[2] = [-1,-1,-1,-1,50]
        updatedCells[3] = [-1,-1,-1,-1,50]
        updatedCells[4] = [-1,-1,-1,-1,50]

        then:
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }

}
