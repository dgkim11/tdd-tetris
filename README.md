
# TDD로 테트리스 만들기
## 개요
TDD는 들어봤고 어떤 개념인지는 알지만 실제로 어떻게 TDD 방법으로 개발해야 하는지 막막한 경우에 도움이 되었으면 하는 마음으로 작성하였다. TDD에 대한 이해는 별도의 서적이나 싸이트를 통해서 좀 더 자세히 읽어보길 바라며 여기서는 TDD로 개발하는 과정을 설명하기로 한다. 물론, 여기서 설명한 방법이 TDD의 정석은 아니다. TDD란 개념에 가깝고 TDD에서 설명하는 정석적 방식으로 개발하는 것도 맞을 수 있지만 중요한 것은 아래의 사항을 목표로 두고 개발하는 것이다.
Test 가능한 코드를 작성한다. 이것은 매우 중요하다. 모든 비즈니스 로직이 있는 것들은 테스트가 되어야 한다. 개발 시작시에 Test를 고려하지 않고 작성하면 나중에 Test case를 작성하기 어려운 구조로 코드가 작성되어 test case를 만들지 않거나 억지로 test case를 만들어서 무엇을 테스트하려고 하는지 이해할 수 없는 test case가 나오게 된다.
Test case가 하나의 클래스나 메서드가 무엇을 하는 것인지에 대한 specification 문서 역할을 해야 한다. 개발을 시작하기 전에 어떤 역할이나 기능들이 필요한지를 생각하고 그러한 기능들에 대한 specification을 정의하고 그 명세에 따라서 test case를 작성하여 test case가 클래스나 메서드의 명세서 역할을 하여야 한다.
test case를 나중에 작성하면 안되고 test case를 먼저 작성하거나 test case와 개발을 같이 진행해야 한다. 이렇게 해야지만 위와 같은 목표를 이룰 수 있다.

# 테트리스 주요 Features
* 테트리스의 보드는 가로 20칸, 세로 50칸으로 이루어진다.
* 블럭의 종류는 I미노, O미노, Z미노, S미노, J미노, L미노, T미노가 있다.
* 블럭의 색깔은 파란색, 노란색, 빨간색, 녹색, 파란색으로 이루어진다.
* 블럭으로 모두 꽉 채워진 가로줄은 제거되고 그 위에 있던 블럭들은 내려온다.
* 블럭이 다른 블럭이나 바닥에 닿으면 그 위에 쌓인다.
* 다음 블럭의 종류, 색깔, 방향은 random으로 만들어진다.
* 사용자가 블럭을 왼쪽, 오른쪽, 아래로 이동시킬 수 있다.
* 사용자가 블럭을 왼쪽, 오른쪽으로 회전시킬 수 있다.
* 블럭이 왼쪽 오른쪽으로 이동하거나 회전할 때 주변에 다른 블럭으로 인해 막혀있으면 움직일 수 없다.
* 블럭은 1초에 한칸씩 자동으로 내려간다.
* 꽉 채워진 가로줄 하나당 10점씩 점수가 올라간다.
* 블록이 땅에 닿으면 다음 블럭이 나타난다.
* 현재의 블럭이 한칸도 내려가지 못하고 지붕에 닿으면 게임은 끝난다.

## TDD로 개발 시작하기
위에서 언급한 features들에 대해서 하나씩 TDD 기반으로 구현해보자.

### Feature. 테트리스의 보드는 가로 20칸, 세로 50칸으로 이루어진다.
간단하게 아래의 test case를 추가한다. 가로와 세로의 블록의 수를 리턴하는 메소드를 호출하고 그 결과에 대해서 검증한다.
~~~
class TetrisBoardSpec extends Specification  {
    def "테트리스의 보드는 가로 20칸, 세로 50칸으로 이루어진다."()   {
        when:
        TetrisBoard sut = new TetrisBoard()
        
        then:
        sut.getBlockCountOfWidth() == 20
        sut.getBlockCountOfHeight() == 50
    }
}
~~~
TetrisBoard는 아직 해당 메서드가 없기 때문에 컴파일 오류가 난다. 따라서, 아래처럼 메소드를 구현하고 test를 수행하면 test case가 성공하게 된다.
~~~
private static final int WIDTH_BLOCKS = 20;
private static final int HEIGHT_BLOCKS = 50;

public int getBlockCountOfWidth() {
return WIDTH_BLOCKS;
}

public int getBlockCountOfHeight() {
return HEIGHT_BLOCKS;
}
~~~

### Feature. 블럭의 종류는 아래와 같다.
잘 보면, 하나의 블럭은 네모난 정사각형(cell)이 모여서 이루어진다. 그리고,  Block을 이루는 cell이 어떠한 모양으로 붙어있는지에 따라서 블럭의 종류가 달라진다. 이번 요구사항은 먼저 블럭에 대한 자료구조를 정의할 필요가 있다. 일단, 보드는 가로 20칸, 세로 50칸의 사각형으로 구성된 공간이다. 그 공간 안에 블럭이 존재하게 되는데 이 때 블럭을 구성하는 cell이 있는 경우에 1, 없는 경우에 0으로 값을 설정함으로써 cell의 유무를 판단할 수 있다. 또한, 블럭의 위치를 알기 위해서는 블럭의 top과 left를 좌표로 가지며 block의 크기를 알기 위해서 width와 height가 필요하다. 또한, 블럭은 회전이 가능하다. 동일한 모양도 어떤 방향으로 있느냐에 따라서 cell의 위치가 달라진다. 아래는 각 블럭별 cell의 위치를 1과 0으로 구성된 2차원 배열로 표현한 것이다. 그리고 cell이 있는 경우 1, 없는 경우에 0이다.

* I미노

  * 기본방향 : width:1, height:4, cell[4][1] = {1[0,0], 1[1,0], 1[2,0], 1[3,0]}
  * 90도 : width:4, height:1, cell[1][4] = {1[0,0], 1[0,1], 1[0,2], 1[0,3]}
  * 180도 : 기본방향과 동일
  * 270도 : 90도와 동일

* O미노

  * 기본방향 : width:2, height:2, cell[2][2] = {1[0,0], 1[0,1], 1[1,0], 1[1,1]}
  * 나머지 모든 방향도 기본방향과 동일

* Z미노

  * 기본뱡향(Z) : width:3, height:2, cell[2][3] = { 1[0,0], 1[0,1], 0[0,2], 0[1,0], 1[1,1], 1[1,2]}
  * 90도(N) : width:2, height:3, cell[3][2] = { 0[0,0], 1[0,1], 1[1,0], 1[1,1], 1[2,0], 0[2,1]}
  * 180도 : 기본방향과 동일
  * 270도 : 90도와 동일

* S미노

  * 기본방향(S) : width:3, height:2, cell[2][3] = { 1[0,0], 1[0,1], 0[0,2], 0[1,0], 1[1,1], 1[1,2]}
  * 90도(N자를 뒤집은 모양) : width:2, height:3, cell[3][2] = { 0[0,0], 1[0,1], 1[1,0], 1[1,1], 1[2,0], 0[2,1]}
  * 180도 : 기본방향과 동일
  * 270도 : 90도와 동일

J미노, L미노, T 미노에 대해서도 동일하게 작성한다.

이렇게 정의된 specification을 test case로 작성해보자. TDD의 정석대로라면 먼저 실패하는 test case를 작성하고 그 다음 구현하며 리팩토링하는 3단계로 진행한다. 그래서 아주 기본적인 test case부터 시작하는 경향이 있으니 이미 어떻게 코드를 작성해야 하는지를 안다면 단계를 축소해서 진행해도 괜찮다. 예로, 블럭은 공통적으로 width, height, cell 배열, 위치 정보, 뱡향 등등을 갖는다. 이런 것들을 블럭 객체별로 따로 가지고 있기 보다는 공통 속성을 가지는 상위 클래스를 정의하고 하위 클래스는 상속을 받는 구조로 바로 구현하도록 하겠다. 아래는 상위 클래스에 대한 정의이다.

~~~
@Getter
public class Block {
protected int topPosition;
protected int leftPosition;
protected int width;
protected int height;
protected Direction direction;
protected int[][] cells;
}
~~~

그리고 실제 블록들은 아래처럼 Block을 상속받는다.
~~~
public class IMinoBlock extends Block {
}
~~~
그러면, 이제 I 미노에 대해서 위의 spec을 만족하는 test case를 작성해보자. 아래의 코드는 I 미노에 대한 test case 이다.
~~~
class IMinoBlockSpec extends Specification  {
    def "I 미노의 기본 방향의 cell 배열정보 검증"()    {
        given: "I미노 생성"
        IMinoBlock iMinoBlock = new IMinoBlock()
    
        when: "기본방향으로 설정"
        iMinoBlock.setDirection(Direction.NORTH)
        int[][] cells = iMinoBlock.getCells()
        
        then: "cell의 배열정보는 {1[0,0], 1[1,0], 1[2,0], 1[3,0]} 여야 한다."
        cells.length == 4            // height
        cells[0].length == 1         // width
        cells[0][0] == 1
        cells[1][0] == 1
        cells[2][0] == 1
        cells[3][0] == 1
    }
}
~~~
여기서 setDirection이란 메서드가 있는데 현재 존재하지 않는다. 이 메서드도 Block 클래스에 정의한다.
~~~
public void setDirection(Direction direction)   {
}  
~~~      
일단, 빈 메서드로서 test case를 수행하면 실패할 것이다. 이제 I 미노에 대한 setDirection을 구현해보자. I 미노에 대한 setDirection은 아래처럼 구현한다.

~~~
@Override
public void setDirection(Direction direction)   {
    this.direction = direction;
    updateCells(direction);
}

private void updateCells(Direction direction)   {
    if(direction == Direction.NORTH)    {
        // cell[4][1] = {1[0,0], 1[1,0], 1[2,0], 1[3,0]}
        this.cells = new int[4][1];
        this.cells[0][0] = 1;
        this.cells[1][0] = 1;
        this.cells[2][0] = 1;
        this.cells[3][0] = 1;
    }
}
~~~
여기서 한가지, setDirection 메서드가 모든 블럭에 공통적으로 구현해야 하는 메서드이기에 setDirection은 Block 클래스에 abstract 메서드로 정의하고 각각의 하위 블럭 클래스들이 구현을 담당한다.  이제 나머지 블럭에 대해서도 동일한 형태로 test case를 작성한다.
~~~
@Getter
public abstract class Block {
    protected int topPosition;
    protected int leftPosition;
    protected int width;
    protected int height;
    protected Direction direction;
    protected int[][] cells;
    
    public abstract void setDirection(Direction direction);
}
~~~
각각의 Block에 대해서 동서남북 방향의 cell들의 값이 맞는지 test case로 검증한다. 추가로, J 미노에 대한 test case이다.

~~~
class JMinoBlockSpec extends Specification {
    def "J 미노의 기본 방향의 cell 배열정보 검증"()    {
        given: "J 미노 생성"
        JMinoBlock jMinoBlock = new JMinoBlock()

        when: "기본방향으로 설정"
        jMinoBlock.setDirection(Direction.NORTH)
        int[][] cells = jMinoBlock.getCells()

        then: "cell의 배열정보는 {0[0,0], 1[0,1], 0[1,0], 1[1,1], 1[2,0], 1[2,1]} 여야 한다."
        cells.length == 3            // height
        cells[0].length == 2         // width
        cells[0][0] == 0
        cells[0][1] == 1
        cells[1][0] == 0
        cells[1][1] == 1
        cells[2][0] == 1
        cells[2][1] == 1
    }
}
~~~


### Feature. 블럭의 색깔은 노란색, 빨간색, 녹색, 파란색으로 이루어진다.
앞에서는 블럭이 있으면 1, 없으면 0이었으나 이제 색깔을 표현해야 한다. 내 생각에는 블럭이 없으면 0은 그대로 유지하나 블럭이 있는 경우 색깔을 표시하기 위해서 1 대신에 RGB 색깔에 해당하는 integer 값을 넣는 것이 간단하게 문제를 해결하고 가독성도 떨어뜨리지 않는다고 판단된다. 따라서, 블럭을 생성할 때 방향과 함께 색깔도 함께 넘기는 생성자를 만들도록 한다. 그리고 이제 cell에서 리턴하는 값은 1이 아니라 색깔에 해당하는 rgb 값이다. 이것을 test case로 만들어보자. 그전에 먼저 기존 test case에 대해서 ‘== 1’로 비교하던 것을' == <rgbColor>' 로 바꾸는 것이 필요한다. 또한, white 색깔의 경우 값이 0이기 때문에 이제 0이 아니라 -1인 경우를 블럭이 없는 것으로 수정하도록 한다.
따라서, test case는 아래처럼 변경된다.

~~~
def "I 미노의 기본 방향의 cell 배열정보 검증"()    {
given: "I미노 생성"
IMinoBlock iMinoBlock = new IMinoBlock(10)

    when: "기본방향으로 설정"
    iMinoBlock.setDirection(Direction.NORTH)
    int[][] cells = iMinoBlock.getCells()

    then:
    cells.length == 4            // height
    cells[0].length == 1         // width
    cells[0][0] == 10
    cells[1][0] == 10
    cells[2][0] == 10
    cells[3][0] == 10
}
~~~

그리고, rgbColor도 모든 블럭의 공통 속성이기 때문에 Block 클래스에 속성을 추가하고, 각각의 하위 클래스들은 setDirection() 호출 시에 rgbColor 값으로 cell의 값을 설정한다.
~~~
public abstract class Block {
    ...
    public Block(int rgbColor)  {
        this(Direction.NORTH, rgbColor);
    }
    
    public Block(Direction direction, int rgbColor) {
        this.rgbColor = rgbColor;   // 주의사항. rgbColor 설정이 setDirection보다 먼저 호출되어야 함.
        setDirection(direction);
    }
}
~~~
~~~
public class IMinoBlock extends Block {
    public IMinoBlock(int rgbColor) {
        super(rgbColor);
    }

    public IMinoBlock(Direction direction, int rgbColor) {
        super(direction, rgbColor);
    }

    @Override
    public void setDirection(Direction direction)   {
        this.direction = direction;
        updateCells(direction);
    }

    private void updateCells(Direction direction)   {
        if(direction == Direction.NORTH || direction == Direction.SOUTH)    {
            this.cells = new int[4][1];
            this.cells[0][0] = rgbColor;
            this.cells[1][0] = rgbColor;
            this.cells[2][0] = rgbColor;
            this.cells[3][0] = rgbColor;
        }
        else if(direction == Direction.EAST || direction == Direction.WEST)    {
            this.cells = new int[1][4];
            this.cells[0][0] = rgbColor;
            this.cells[0][1] = rgbColor;
            this.cells[0][2] = rgbColor;
            this.cells[0][3] = rgbColor;
        }

    }
}
~~~
Feature에 정의된 블럭의 색깔들은 나중에 블럭은 random으로 생성되는 feature에서 다루도록 한다.

### Feature. 블럭으로 모두 꽉 채워진 가로줄은 제거되고 그 위에 있던 블럭들은 내려온다. & 블럭이 다른 블럭이나 바닥에 닿으면 그 위에 쌓인다.
TetrisBoard에 쌓여있는 블럭들의 전체 모습이 담겨있다. 따라서, TetrisBoard가 가로줄 제거를 해야하는 책임을 가진다. 다만, TetrisBoard가 직접 그 역할을 하기보다는 그 역할을 수행하는 클래스를 만들고 TetrisBoard는 delegate 패턴으로 해당 객체를 참조하도록 한다. delegate란 위임을 말한다. 자신의 역할이나 책임을 다른 대상에게 위임하는 것이다. 이유는 하나의 객체가 너무 많은 일을 할 수 있기 때문이다. 사람도 마찬가지로 자신에게 책임이 있지만 본인이 일이 많으면 일부 업무를 다른 사람에게 위임할 수 있다.  객체도 마찬가지로 이렇게 역할을 위임하여 개발하면 좀 더 SRP 원칙을 잘 지킬 수 있다. 또한, test 가능한 코드를 만드려면 중요한 로직들은 명시적으로 테스트 되는 것이 필요하고, 위임받은 역할을 수행하는 객체에 대해서 명시적으로 test case를 작성할 수 있다. 물론, 이 방식 또한 전통적인 TDD에는 맞지 않는다. 전통적 TDD는 실패하는 test case를 해결하는 것이 먼저이고 잘 설계된 코드로의 리팩토링 등은 필요한 시점에 하기를 권장하고 있다.
row가 다 찼는지를 판단하는 역할의 클래스로 FilledRowsFinder 클래스를 만들고, 그것에 대한 test case로 아래와 같이 작성한다. FilledRowsFinder가 그 위임받은 역할을 수행하는 것이다.
~~~
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
~~~
FilledRowsFinder는 static 메서드로 2차원 배열을 받아서 모두 cell이 찬 row의 index를 List<Integer>로 리턴한다. 이제 이 test case가 성공하도록 구현해보도록 한다.

두번째로, row가 삭제된 후에는 위에 있던 블럭들이 아래로 내려와야 한다. 이것또한 TetrisBoard에서 수행되는 일이나 이것을 별도의 클래스로 역할을 delegation 할 것이다. 클래스 이름을 BoardCellsControl 이라고 명명하고 이 클래스가 board의 cell들을 관리하도록 한다. 이렇게 BoardCellsControl에 board에 있는 cell들을 관리하는 역할을 하게되면 cell들 데이터를 해당 클래스가 가지도록 한다. 또한, 삭제할 row를 찾고 해당 row를 삭제하는 것도 BoardCellsControl이 그 역할을 수행하는 것이 맞다고 판단된다. 따라서, FilledRowsFinder를 BoardCellsControl에서 호출하도록 할 것이다.
~~~
    public boolean updateCells(Block block) {
        if(currentBlock != null) removeCurrentBlock();
        currentBlock = block;
        attachNewBlockToBoard(block);
        if(! canMoveDown(block))    {   // 더 이상 내려갈 수 없다면 cell이 모두 찬 row를 제거하
            List<Integer> filledRows = FilledRowsFinder.findFilledRows(boardCells);
            if(filledRows.size() > 0)   {
                removeRows(filledRows);
            }
            currentBlock = null;
            return true;
        }
        return false;
    }

    private boolean canMoveDown(Block block) {
        int row = block.getYPos() + block.getHeight();
        if(row >= boardCells.length - 1) return false;  // 보드의 바닥인 경우
        for(int col = 0;col < block.getWidth();col++) {
            if(boardCells[row + 1][col + block.getXPos()] != -1) return false;            // 아래에 블럭이 쌓여 있는 경우
        }
        return true;
    }

    private void removeCurrentBlock() {
        int[][] blockCells = currentBlock.getCells();
        for(int row = 0;row < blockCells.length;row++)    {
            for(int col = 0;col < blockCells[row].length;col++) {
                boardCells[currentBlock.getYPos() + row][currentBlock.getXPos() + col] = -1;
            }
        }
    }

    /**
     * 블럭의 위치와 모양대로 board에 나오도록 cells을 갱신한다.
     * @param block
     */
    private void attachNewBlockToBoard(Block block) {
        int[][] blockCells = block.getCells();
        for(int row = 0;row < blockCells.length;row++)  {
            for(int col = 0;col < blockCells[0].length;col++)   {
                boardCells[row + block.getYPos()][col + block.getXPos()] = blockCells[row][col];
            }
        }
    }

    /**
     * 보드에서 삭제되어야 할 row들을 삭제한다.
     *
     * @param rows 삭제되어야 할 row 목록
     */
    private void removeRows(List<Integer> rows)  {
        rows.forEach(this::removeRow);
    }
~~~

여기서 구현을 하면서 많은 부분들이 리팩토링 되었다. 가장 중요한 것중에 하나가 모든 Block 객체가 immutable 객체가 된 것이다. BoardCellsControl 에서 현재의 block이 표시되고 방향 전환이나 화살표로 블럭의 위치를 변경하는 경우 거기에 맞게 board의 모든 cell들이 갱신이 되려면 현재의 블럭의 위치와 다음 블럭의 위치를 바탕으로 board의 cell을 갱신하여야 한다. 따라서, block의 상태가 immutable이 아니면 이전 상태와 다음 상태와 변화를 알 수가 없다. updateCells() 메서드를 보면 먼저 현재의 block을 board에서 제거한 후에 위치가 변경된 block을 board에 첨부하는 형태로 새로운 board의 모습을 그리도록 하였다. 또한, 아래로 더 이상 내려갈 수 없다면 row를 삭제하는 로직도 함께 들어가 있다.
여기에 따른 test case는 아래처럼 작성하였다.
~~~
    def "보드에서 블럭이 아래로 한칸 내려간다."() {
        ...
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
        ...
    }
~~~

### Feature. 다음 블럭의 종류, 색깔, 방향은 random으로 만들어진다.
BlockFactory 클래스에서 새로운 block을 생성하도록 한다. 아래와 같이 두개의 test case를 작성하고 거기에 맞도록 구현을 한다. 다만, 여기에 있는 test case는 random으로 블럭이 생성되기에 항상 결과가 true라고 장담할 수가 없다. 따라서, spec에 대한 문서 역할과 기능을 구현할 때만 사용하고 그 이후에는 Ignore 처리를 하는게 맞을 것으로 보인다.

~~~
class BlockFactorySpec extends Specification {
    def "블럭의 모양, 색깔, 방향등은 random으로 생성된다."() {
        BlockFactory sut = new BlockFactory()

        when:
        Block block1 = sut.generateRandomBlock()
        Block block2 = sut.generateRandomBlock()

        then:
        BlockTestHelper.equalCells(block1.getCells(), block2.getCells()) == false   // 하지만, 확율은 적지만 true일 가능성도 있다.
    }

    def "최초 블럭 생성 시 블럭의 위치는 보드를 벗어날 수 없다."()    {
        BlockFactory sut = new BlockFactory()

        when:
        Block block = sut.generateRandomBlock()

        then:
        block.getXPos() + block.getWidth() < TetrisBoard.WIDTH_BLOCKS
    }
}
~~~
generateRandom() 메서드에서 random하게 블럭을 생성하는 로직을 구현한다.

### Feature. 사용자가 블럭을 왼쪽, 오른쪽, 아래로 이동시킬 수 있다 & 사용자가 블럭을 왼쪽, 오른쪽으로 회전시킬 수 있다. & 블럭이 왼쪽 오른쪽으로 이동하거나 회전할 때 주변에 다른 블럭으로 인해 막혀있으면 움직일 수 없다.
블럭이 이동할 수 있는 공간이 있는 경우는 이동이 가능하나 그렇지 않은 경우는 이동할 수 없다. 여러가지 움직임에 대해서 이동이 가능한 경우와 그렇지 않은 경우로  test case를 구현한다. 그리고 해당 기능 역시 BoardCellsControl에서 이루어진다. updateCells 메서드를 updateBlock으로 변경하였다. updateCells는 너무 구현에 촛점이 맞추어진 이름이라 what-to-do에 촛점을 맞추어 updateBlock이라 하였다. 또한, 기능도 변경되었다. 리턴값은 block이 갱신되었는지의 여부를 리턴한다.
~~~
    public boolean updateBlock(Block block) {
        if(currentBlock != null) removeCurrentBlock();
        if(! canMove(block))    {
            if(currentBlock != null) attachBlockToBoard(currentBlock);       // 현재 블럭으로 다시 원위치.
            return false;      // 해당 위치로 이동할 수 없는 경우 block을 갱신하지 않는다.
        }
        currentBlock = block;
        attachBlockToBoard(block);
        if(! canMoveDown(block))    {   // 더 이상 내려갈 수 없다면 cell이 모두 찬 row를 제거한다.
            List<Integer> filledRows = FilledRowsFinder.findFilledRows(boardCells);
            if(filledRows.size() > 0)   {
                removeRows(filledRows);
            }
            currentBlock = null;
        }
        return true;
    }

    private boolean canMove(Block block)    {
        int row = block.getYPos() + block.getHeight();
        if(row >= boardCells.length + 1) return false;  // 보드의 바닥인 경우
        if(block.getXPos() < 0) return false;       // 왼쪽 보드 영역을 벗어난 경우
        if(block.getXPos() + block.getWidth() >= boardCells[0].length + 1) return false;  // 오른쪽 보드 영역을 벗어난 경우
        for(int i = 0;i < block.getHeight();i++)   {
            for(int j = 0;j < block.getWidth();j++) {
                if(boardCells[i + block.getYPos()][j + block.getXPos()] != -1 && block.getCells()[i][j] != -1)  {   // 이미 다른 블럭이 공간을 차지하고 있는 경우
                    return false;
                }
            }
        }
        return true;
    }
~~~
아래는 추가된 test case의 종류이다.
~~~
def "오른쪽으로 이동할 수 있는 공간이 있는 경우 오른쪽으로 한칸 이동한다."()
def "오른쪽으로 이동할 수 없는 경우 제자리에 있는다."()
def "왼쪽으로 이동할 수 있는 공간이 있는 경우 왼쪽으로 한칸 이동한다."()
def "왼쪽으로 이동할 수 없는 경우 제자리에 있는다."()
def "우회전할 수 있는 경우 우회전 한다."()
def "좌회전할 수 있는 경우 좌회전 한다."()
def "우회전할 수 없는 경우 블럭은 갱신되지 않는다."()
def "좌회전할 수 없는 경우 블럭은 갱신되지 않는다."()
~~~

### Feature. 블럭은 1초에 한칸씩 자동으로 내려간다 & 블록이 땅에 닿으면 다음 블럭이 나타난다.
이 기능을 구현하기 위해서 한번 생각해보자. 가장 먼저 이 역할을 누가 하는게 맞을지를 고민해보자. 정답은 없다. 내 생각에는 블럭의 동작을 처리하는 것 또한 TetrisBoard의 역할이다.  TetrisBoard는 1초마다 현재의 블럭을 한칸 아래로 내려가도록  내려가도록 위치를 변경하고 그에 따라 BoardCellsControl이 현재의 board의 cell을 갱신한다. 사실 이 기능을 test case로 구현하는 것은 unit test라고 보기 힘들다. unit test는 각각의 메서드나 클래스가 제대로 동작하는지를 확인하는 최소 단위 test인데, 1초 단위로 블럭을 내리려면 1초마다 스케줄러가 동작하도록 하는 부분을 테스트해야 하는데 해당 사항은 test 대상이 아니다. 따라서, 이러한 테스트는 functional test 레벨에서 이루어지는 것이 바람직하나 이 글의 목적이 test 를 작성하는 예제이기 때문에 unit test로 작성하도록 하겠다. 1초에 한번씩 event를 발생시키려면 여러가지가 있지만 가장 간단한 방법은 java의 Timer 객체를 이용하는 것이다. 그래서 TetrisBoard 객체가 게임을 시작할 때 아래처럼 주기적으로 event를 만들도록 timer를 생성한다. timer의 파라미터로 BlockDownTask가 있는데 여기서 블럭을 아래로 한칸 이동시키면 된다. INTERVAL은 1000ms로 설정되었다.
~~~
public void startGame() {
timer = new Timer();
timer.scheduleAtFixedRate(new BlockDownTask(this), 0, INTERVAL);
}
~~~
~~~
public class BlockDownTask extends TimerTask {
private TetrisBoard tetrisBoard;

    public BlockDownTask(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
    }

    @Override
    public void run() {
        if(tetrisBoard.needNewBlock())  {
            tetrisBoard.newBlock();
        }
        else {
            tetrisBoard.moveDownBlock();
        }

    }
}
~~~
BlockDownTask의 run 메서드가 1초에 한번씩 호출된다. 여기서 블럭을 아래로 한칸 내리는 것 외에 추가로 들어간 로직은 블럭이 땅에 닿아서 다음 블럭이 필요한 경우에 새로운 블럭을 생성하는 것이다.  그래서 1초 단위로 현재의 블럭을 내리는 것과 블럭이 땅에 닿아서 다음 블럭을 새로 만드는 두가지 작업 중에 하나가 1초마다 발생해야 한다. 아래는 test case이다. TetrisBoardSpec test case 안에 아래의 test case를 추가한다.

~~~
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
~~~

startGame()으로 게임이 시작되면 0초에 신규 블럭을 만들고 1초에 해당 블럭을 내리고, 그 다음 1초에 한번 더 블럭을 내릴 것이다. 따라서, 블럭의 y 위치가 2가 되어야 한다.

### Feature. 꽉 채워진 가로줄 하나당 10점씩 점수가 올라간다. & 현재의 블럭이 한칸도 내려가지 못하고 지붕에 닿으면 게임은 끝난다.
BoardCellsControl의 updateBlock() 메서드가 블럭이 다 찼을 경우 해당 row를 삭제하고, 위의 블럭들을 내리고, 블럭이 땅에 닿았는지 여부 등등을 판단하는 작업을 한다. 이 메서드에 리턴값이 현재 블럭이 업데이트 되었는지 여부만을 리턴하고 있는데 땅에 닿아서 다음 블럭이 필요한지, 몇개의 row가 지워졌는지 등등 여러가지 정보를 리턴해줄 필요가 있어 보인다. 그래서 단순히 boolean 값이 아니라 UpdateBlockResult 라는 객체를 리턴하여 다양한 정보를 TetrisBoard에게 알려줄 필요가 있어 보인다. 그리고, 점수에 대한 관리도 TetrisBoard가 하는 것이 맞다고 판단된다. 그리고, TetrisBoard가 BoardCellsControl에게 새로운 블럭이 필요한지 여부를 물어봤는데 이제는 updateBlock의 결과값으로 새로운 블럭의 필요 여부를 알 수 있기때문에 관련 필드와 메서드를 제거하였다. UpdateBoardResult는 아래와 같이 블럭이 갱신되었는지, 신규 블럭이 필요한지, cell이 제거된 row가 있는지, 게임이 종료하였는지에 대한 정보를 전달한다.
~~~
public class UpdateBoardResult {
    private boolean udpated;
    private boolean needNewBlock;
    /**
    * 블럭이 바닥에 닿았을 때 제거된 row 개수.
    */
    private int removedRows;
    private boolean gameOver;
}
~~~
updateBlock() 메서드는 아래와 같이 변경된다.
~~~
    public UpdateBoardResult updateBlock(Block block) {
        if(currentBlock != null) removeCurrentBlock();

        // 블럭이 해당 위치로 움직일 수 없는 경우
        if(! canMove(block))    {
            if (block.getYPos() == 0)         // 블럭의 위치가 가장 높은 곳인데도 움직일 수 없다면 game over.
                return new UpdateBoardResult(false, false, 0, true);
            if(currentBlock != null) {
                attachBlockToBoard(currentBlock);       // 현재 블럭으로 다시 원위치.
            }
            return new UpdateBoardResult(false, false, 0, false);      // 해당 위치로 이동할 수 없는 경우 block을 갱신하지 않는다.
        }

        // 블럭이 해당 위치로 이동할 수 있는 경우
        currentBlock = block;
        attachBlockToBoard(block);
        if(! canMoveDown(block))    {   // 더 이상 내려갈 수 없다면 cell이 모두 찬 row를 제거한다.
            List<Integer> filledRows = FilledRowsFinder.findFilledRows(boardCells);
            if(filledRows.size() > 0)   {
                removeRows(filledRows);
            }
            currentBlock = null;
            return new UpdateBoardResult(true, true, filledRows.size(), false);
        }
        return new UpdateBoardResult(true, false, 0, false);
    }
~~~
BoardCellsControl 관련 test case가 아래와 같이 추가된다.
~~~
def "블럭이 갱신된 후 지워진 row의 개수를 리턴한다."()    {
    BoardCellsControl sut = new BoardCellsControl(5,5)

    given: "블럭들이 존재한다."
    IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)
    IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 1, 1)
    IMinoBlock block3 = new IMinoBlock(Direction.NORTH, 50, 2, 1)
    IMinoBlock block4 = new IMinoBlock(Direction.NORTH, 50, 4, 1)
    sut.updateBlock(block1)
    sut.updateBlock(block2)
    sut.updateBlock(block3)
    sut.updateBlock(block4)

    when: "신규 블럭이 바닥에 닿고 꽉찬 row들이 제거된다."
    LMinoBlock lMinoBlock = new LMinoBlock(Direction.SOUTH, 30, 2, 0)
    UpdateBoardResult result = sut.updateBlock(lMinoBlock)

    then: "UpdateBoardResult에 지워진 row의 개수가 리턴된다."
    result.isNeedNewBlock()
    result.getRemovedRows() == 2
}

def "블럭이 제일 위에서 움직일 수 없으면 게임이 종료된다."()  {
    BoardCellsControl sut = new BoardCellsControl(5,5)

    given: "신규 블럭이 움직일 수 없을만큼 cell들이 채워져 있다."
    IMinoBlock block1 = new IMinoBlock(Direction.NORTH, 50, 0, 1)
    IMinoBlock block2 = new IMinoBlock(Direction.NORTH, 50, 2, 1)
    IMinoBlock block3 = new IMinoBlock(Direction.NORTH, 50, 4, 1)
    sut.updateBlock(block1)
    sut.updateBlock(block2)
    sut.updateBlock(block3)

    when: "신규 블럭을 보드에 추가한다."
    JMinoBlock jMinoBlock = new JMinoBlock(Direction.NORTH, 30, 0, 0)
    UpdateBoardResult result = sut.updateBlock(jMinoBlock)

    then: "게임이 종료된다."
    result.isGameOver()
}
~~~
TetrisBoard는 아래의 test가 추가된다.
~~~
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
~~~
## 화면 만들기
이렇게 하여, 테트리스의 중요 기능들은 모두 구현되었고 test도 작성되었다. 이제는 화면을 만들 차례이다. 개발된 언어가 Java라서 Swing을 이용한 애플리케이션을 작성할 것이다.
Swing의 기본 구조는 JFrame이라는 frame 안에 JPanel 이란 컴포넌트를 넣어서 layout을 구성하게 된다. 여기서는 테트리스 보드 하나만 있으면 되기 때문에 TetrisBoardGui 라는 panel 하나를 JFrame안에 추가하였다.
아래는 TetrisMain 클래스인데 TetrisBoardListener가 있다. 이것은 TetrisBoard가 갱신될 때마다 다시 화면을 그려줘야 하는데 그러기 위해서는 TetrisBoard가 갱신되었음을 지속적으로 listening 해야 한다. 보드가 갱신되면 updated() 메서드가 호출되며 tetrisBoardGui 객체에 updateBoard()를 통해 화면을 갱신한다.

~~~
public class TetrisMain extends JFrame {
private JLabel statusbar;

    public TetrisMain() {
        statusbar = new JLabel("게임시작은 S 버튼 누르세요");
        statusbar.setFont(new Font(statusbar.getFont().getFontName(), Font.PLAIN, 18));
        add(statusbar, BorderLayout.SOUTH);
    }
...
private static class TetrisBoardListenerImpl implements TetrisBoardListener {
private TetrisBoardGui tetrisBoardGui;
private TetrisMain tetrisMain;

        public TetrisBoardListenerImpl(TetrisMain tetrisMain, TetrisBoardGui tetrisBoardGui) {
            this.tetrisMain = tetrisMain;
            this.tetrisBoardGui = tetrisBoardGui;
        }

        @Override
        public void updated(int[][] boardCells, long score) {
            tetrisBoardGui.updateBoard();
        }

        @Override
        public void gameOver(long score) {
            tetrisMain.setStatusText("Game Over !!. 게임시작은 S 버튼 누르세요.");
        }
    }
~~~

아래는 JPanel 부분으로 TetrisKeyListener가 있는데 사용자가 화살표키를 누르면 거기에 맞게 TetrisBoard에게 보드의 방향을 바꾸도록 호출한다.
~~~
public class TetrisBoardGui extends JPanel {
private Image screenBuffer = null;
private Graphics graphicsBuffer = null;
private TetrisBoard tetrisBoard;
private int width;
private int height;

    public TetrisBoardGui(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
        addKeyListener(new TetrisKeyListener(tetrisBoard));
        setFocusable(true);
    }

    public void paint(Graphics g)
    {
    ...
    }
    private static class TetrisKeyListener extends KeyAdapter {
        private TetrisBoard tetrisBoard;
    
        public TetrisKeyListener(TetrisBoard tetrisBoard) {
            this.tetrisBoard = tetrisBoard;
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            if(keycode == 's' || keycode == 'S')    {
                tetrisBoard.startGame();
            }
            if(tetrisBoard.isGameStarted()) {
                switch(keycode)  {
                    case KeyEvent.VK_LEFT:
                        tetrisBoard.moveLeftBlock(); break;
                    case KeyEvent.VK_RIGHT:
                        tetrisBoard.moveRightBlock(); break;
                    case KeyEvent.VK_DOWN:
                        tetrisBoard.moveDownBlock(); break;
                    case KeyEvent.VK_UP:
                        tetrisBoard.turnRightBlock(); break;
                }
            }
        }
    }

~~~

BlockDownTask는 1초에 한번씩 블럭을 내리면서 다음 action을 판단하는 부분도 있어서 TetrisBoard에 doNextAction() 이란 메서드를 만들고 여기에 해당 코드들을 이동시켰다. 또한, 기존에는 updateBoard() 메서드만 있었는데 테트리스가 기본적으로 블럭이 이동한 후 즉시 보드가 갱신되는 것이 아니라 블럭이 이동한 후에 1초가 지나서 보드가 업데이트 되기 때문에 BoardCellsControl 객체에 updateBlock()과 updateBoard() 라는 메서드로 나누었다.
수정 후에 test case를 다시 돌리니 test case가 깨지는 것들이 많이 나왔다. 이렇게 탄탄하게 test case가 있으니 코드를 수정한 후에 생기는 버그나 오동작을 test case를 통해서 쉽게 발견하고 해결할 수 있게 된다.





