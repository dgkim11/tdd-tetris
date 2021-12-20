package example.tdd.tetris.board

import spock.lang.Specification

class TetrisBoardSpec extends Specification  {
    def "테트리스의 보드는 가로 20칸, 세로 50칸으로 이루어진다."()   {
        when:
        TetrisBoard sut = new TetrisBoard()

        then:
        sut.getBlockCountOfWidth() == 20
        sut.getBlockCountOfHeight() == 50
    }

}
