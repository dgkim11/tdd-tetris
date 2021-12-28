package example.tdd.tetris.board

import example.tdd.tetris.block.Block
import example.tdd.tetris.block.BlockFactory
import spock.lang.Specification

class TetrisBoardSpec extends Specification  {
    def "테트리스의 보드는 가로 20칸, 세로 50칸으로 이루어진다."()   {
        when:
        TetrisBoard sut = new TetrisBoard(null)

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
}
