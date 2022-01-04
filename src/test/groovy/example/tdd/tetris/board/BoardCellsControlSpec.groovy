package example.tdd.tetris.board

import example.tdd.tetris.block.Block
import example.tdd.tetris.block.BlockTestHelper
import example.tdd.tetris.block.DiffPoint
import example.tdd.tetris.block.Direction
import example.tdd.tetris.block.IMinoBlock
import example.tdd.tetris.block.JMinoBlock
import example.tdd.tetris.block.LMinoBlock
import example.tdd.tetris.block.TMinoBlock
import example.tdd.tetris.block.ZMinoBlock
import spock.lang.Specification

class BoardCellsControlSpec extends Specification {
    BoardCellsControl sut

    def setup() {
        sut = new BoardCellsControl()
        sut.setSize(5,5)
    }

    def "보드에서 블럭이 아래로 한칸 내려간다."() {
        given: "보드에 블럭이 존재한다."
        Block block = new TMinoBlock(Direction.WEST, 100, 0)       // ㅏ 모양
        sut.updateBlock(block)
        int[][] curCells = BlockTestHelper.copyArray(sut.getBoardCells())

        when: "보드에서 한칸 내려간다."
        sut.updateBlock(block.moveDown())
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
        given:
        IMinoBlock block = new IMinoBlock(Direction.EAST, 50, 0, 4)    // ㅡ 모양으로 바닥에 떨어짐.
        when:
        sut.updateBlock(block)
        UpdateBoardResult result = sut.updateBoard(block)
        int[][] boardCells = new int[5][5]
        boardCells[0] = [-1,-1,-1,-1,-1]
        boardCells[1] = [-1,-1,-1,-1,-1]
        boardCells[2] = [-1,-1,-1,-1,-1]
        boardCells[3] = [-1,-1,-1,-1,-1]
        boardCells[4] = [50,50,50,50,-1]

        then:
        result.isNeedNewBlock()
        BlockTestHelper.equalCells(boardCells, sut.getBoardCells())
    }
    def "row가 삭제되면 그 위의 블럭들이 아래로 내려온다."()   {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.EAST, 50, 0, 4)    // ㅡ 모양으로 바닥에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 4, 1)   // ㅣ 모양으로 오른쪽 끝에 떨어짐.

        when:
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,-1,-1,-1,-1]
        updatedCells[1] = [-1,-1,-1,-1,-1]
        updatedCells[2] = [-1,-1,-1,-1,50]
        updatedCells[3] = [-1,-1,-1,-1,50]
        updatedCells[4] = [-1,-1,-1,-1,50]

        then:
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }

    def "오른쪽으로 이동할 수 있는 공간이 있는 경우 오른쪽으로 한칸 이동한다."() {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 4, 1)   // ㅣ 모양으로 오른쪽 끝에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 3, 1)   // ㅣ 모양으로 오른쪽 두번째.
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)

        when:
        TMinoBlock tblock = new TMinoBlock(Direction.NORTH, 30, 1, 0)
        sut.updateBlock(tblock)
        sut.updateBoard(tblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,30,30,30,-1]
        updatedCells[1] = [-1,-1,30,50,50]
        updatedCells[2] = [-1,-1,-1,50,50]
        updatedCells[3] = [-1,-1,-1,50,50]
        updatedCells[4] = [-1,-1,-1,50,50]

        then:
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }

    def "오른쪽으로 이동할 수 없는 경우 제자리에 있는다."() {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 4, 1)   // ㅣ 모양으로 오른쪽 끝에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 3, 1)   // ㅣ 모양으로 오른쪽 두번째.
        TMinoBlock tblock = new TMinoBlock(Direction.NORTH, 30, 1, 0)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(tblock)
        sut.updateBoard(tblock)
        when:
        tblock = tblock.moveRight()
        sut.updateBlock(tblock)
        sut.updateBoard(tblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,30,30,30,-1]
        updatedCells[1] = [-1,-1,30,50,50]
        updatedCells[2] = [-1,-1,-1,50,50]
        updatedCells[3] = [-1,-1,-1,50,50]
        updatedCells[4] = [-1,-1,-1,50,50]

        then:
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }

    def "왼쪽으로 이동할 수 있는 공간이 있는 경우 왼쪽으로 한칸 이동한다."() {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 4, 1)   // ㅣ 모양으로 오른쪽 끝에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 3, 1)   // ㅣ 모양으로 오른쪽 두번째.
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)

        when:
        TMinoBlock tblock = new TMinoBlock(Direction.NORTH, 30, 0, 0)
        sut.updateBlock(tblock)
        sut.updateBoard(tblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [30,30,30,-1,-1]
        updatedCells[1] = [-1,30,-1,50,50]
        updatedCells[2] = [-1,-1,-1,50,50]
        updatedCells[3] = [-1,-1,-1,50,50]
        updatedCells[4] = [-1,-1,-1,50,50]

        then:
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }
    def "왼쪽으로 이동할 수 없는 경우 제자리에 있는다."() {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)   // ㅣ 모양으로 왼쪽 끝에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 1, 1)   // ㅣ 모양으로 왼쪽 두번째.
        TMinoBlock tblock = new TMinoBlock(Direction.NORTH, 30, 1, 0)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(tblock)
        sut.updateBoard(tblock)

        when:
        tblock = tblock.moveLeft()
        sut.updateBlock(tblock)
        UpdateBoardResult result = sut.updateBoard(tblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,30,30,30,-1]
        updatedCells[1] = [50,50,30,-1,-1]
        updatedCells[2] = [50,50,-1,-1,-1]
        updatedCells[3] = [50,50,-1,-1,-1]
        updatedCells[4] = [50,50,-1,-1,-1]

        then:
        ! result.isUpdated()
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }
    def "우회전할 수 있는 경우 우회전 한다."()    {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)   // ㅣ 모양으로 왼쪽 끝에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 1, 1)   // ㅣ 모양으로 왼쪽 두번째.
        TMinoBlock tblock = new TMinoBlock(Direction.NORTH, 30, 2, 0)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(tblock)
        sut.updateBoard(tblock)
        when:
        tblock = tblock.turnRight();
        sut.updateBlock(tblock)
        UpdateBoardResult result = sut.updateBoard(tblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,-1,-1,30,-1]
        updatedCells[1] = [50,50,30,30,-1]
        updatedCells[2] = [50,50,-1,30,-1]
        updatedCells[3] = [50,50,-1,-1,-1]
        updatedCells[4] = [50,50,-1,-1,-1]

        then:
        result.isUpdated()
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }
    def "좌회전할 수 있는 경우 좌회전 한다."()    {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 4, 1)   // ㅣ 모양으로 왼쪽 끝에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 3, 1)   // ㅣ 모양으로 왼쪽 두번째.
        LMinoBlock lblock = new LMinoBlock(Direction.NORTH, 30, 0, 0)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(lblock)
        sut.updateBoard(lblock)
        when:
        lblock = lblock.turnLeft();
        sut.updateBlock(lblock)
        UpdateBoardResult result = sut.updateBoard(lblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,-1,30,-1,-1]
        updatedCells[1] = [30,30,30,50,50]
        updatedCells[2] = [-1,-1,-1,50,50]
        updatedCells[3] = [-1,-1,-1,50,50]
        updatedCells[4] = [-1,-1,-1,50,50]

        then:
        result.isUpdated()
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }
    def "우회전할 수 없는 경우 블럭은 갱신되지 않는다."()    {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 1, 1)
        ZMinoBlock zblock = new ZMinoBlock(Direction.NORTH, 30, 1, 0)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(zblock)
        sut.updateBoard(zblock)
        when:
        zblock = zblock.turnRight()
        sut.updateBlock(zblock)
        UpdateBoardResult result = sut.updateBoard(zblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,30,30,-1,-1]
        updatedCells[1] = [50,50,30,30,-1]
        updatedCells[2] = [50,50,-1,-1,-1]
        updatedCells[3] = [50,50,-1,-1,-1]
        updatedCells[4] = [50,50,-1,-1,-1]

        then:
        ! result.isUpdated()
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }
    def "좌회전할 수 없는 경우 블럭은 갱신되지 않는다."()    {
        given:
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)   // ㅣ 모양으로 왼쪽 끝에 떨어짐.
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 1, 1)   // ㅣ 모양으로 왼쪽 두번째.
        JMinoBlock jblock = new JMinoBlock(Direction.WEST, 30, 1, 0)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(jblock)
        sut.updateBoard(jblock)

        when:
        jblock = jblock.turnLeft()
        sut.updateBlock(jblock.turnLeft())
        UpdateBoardResult result = sut.updateBoard(jblock)
        int[][] updatedCells = new int[5][5]
        updatedCells[0] = [-1,30,30,30,-1]
        updatedCells[1] = [50,50,-1,30,-1]
        updatedCells[2] = [50,50,-1,-1,-1]
        updatedCells[3] = [50,50,-1,-1,-1]
        updatedCells[4] = [50,50,-1,-1,-1]

        then:
        ! result.isUpdated()
        BlockTestHelper.equalCells(sut.getBoardCells(), updatedCells)
    }

    def "블럭이 갱신된 후 지워진 row의 개수를 리턴한다."()    {
        given: "블럭들이 존재한다."
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 1, 1)
        IMinoBlock block3 = new IMinoBlock(Direction.NORTH, 50, 2, 1)
        IMinoBlock block4 = new IMinoBlock(Direction.NORTH, 50, 4, 1)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(block3)
        sut.updateBoard(block3)
        sut.updateBlock(block4)
        sut.updateBoard(block4)

        when: "신규 블럭이 바닥에 닿고 꽉찬 row들이 제거된다."
        LMinoBlock lMinoBlock = new LMinoBlock(Direction.SOUTH, 30, 2, 0)
        sut.updateBlock(lMinoBlock)
        UpdateBoardResult result = sut.updateBoard(lMinoBlock)

        then: "UpdateBoardResult에 지워진 row의 개수가 리턴된다."
        result.isNeedNewBlock()
        result.getRemovedRows() == 2
    }

    def "블럭이 제일 위에서 움직일 수 없으면 게임이 종료된다."()  {
        given: "신규 블럭이 움직일 수 없을만큼 cell들이 채워져 있다."
        IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)
        IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 2, 1)
        IMinoBlock block3 = new IMinoBlock(Direction.NORTH, 50, 4, 1)
        sut.updateBlock(block1)
        sut.updateBoard(block1)
        sut.updateBlock(block2)
        sut.updateBoard(block2)
        sut.updateBlock(block3)
        sut.updateBoard(block3)

        when: "신규 블럭을 보드에 추가한다."
        JMinoBlock jMinoBlock = new JMinoBlock(Direction.NORTH, 30, 0, 0)
        sut.updateBlock(jMinoBlock)
        UpdateBoardResult result = sut.updateBoard(jMinoBlock)
        then: "게임이 종료된다."
        result.isGameOver()
    }
}
