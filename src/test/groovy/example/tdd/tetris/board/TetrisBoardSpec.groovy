package example.tdd.tetris.board

import example.tdd.tetris.block.Block
import example.tdd.tetris.block.BlockFactory
import spock.lang.Specification

class TetrisBoardSpec extends Specification  {
    def "테트리스의 보드는 가로 20칸, 세로 50칸으로 이루어진다."()   {
        when:
        TetrisBoard sut = new TetrisBoard(null, null)

        then:
        sut.getBlockCountOfWidth() == 20
        sut.getBlockCountOfHeight() == 50
    }

    def "1초마다 블럭을 아래로 내린다."()   {
        TetrisBoard sut = new TetrisBoard(
                new BoardCellsControl(TetrisBoard.WIDTH_BLOCKS, TetrisBoard.HEIGHT_BLOCKS),
                new BlockFactory())

        when:
        sut.startGame()
        try { Thread.sleep(2500) } catch(Exception e) {}
        Block block = sut.getCurrentBlock()

        then:
        block.getYPos() == 2
    }

    def "cell이 다 찬 row가 하나 제거될 때 점수가 10점씩 올라간다."()  {
        BoardCellsControl boardCellsControl = Stub(BoardCellsControl)
        boardCellsControl.updateBlock(_) >> new UpdateBoardResult(true, true, 2, false)
        TetrisBoard sut = new TetrisBoard(boardCellsControl, new BlockFactory())

        given: "테트리스 게임을 시작한다."
        sut.startGame()

        when: "1초 동안 수행하기. 0초에 update block, 1초에 update block 일어나며 각 2개의 row(총 4개)가 삭제된다."
        try { Thread.sleep(1100) }catch(Exception e) {}

        then: "점수는 제거된 row * 10 점이다."
        sut.getScore() == 40
    }
}
