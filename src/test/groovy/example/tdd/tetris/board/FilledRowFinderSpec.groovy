package example.tdd.tetris.board

import spock.lang.Specification

class FilledRowFinderSpec extends Specification  {
    def "하나의 row에 있는 모든 cell의 값이 0 이상이면 다 찬 것이다"()  {
        given: "전체 rows 중에 두개의 row만 fill이다."
        int[][] rows = [ [1,2,3,4,5,6,7,8], [1,2,3,4,-1,6,7,8], [1,2,3,4,5,6,7,8]]

        when:
        List<Integer> filledRows = FilledRowsFinder.findFilledRows(rows)

        then: "두개의 row에 대해 filledRows로 리턴한다."
        filledRows.size() == 2      // 두개의 row
        filledRows.get(0) == 0
        filledRows.get(1) == 2
    }
}
