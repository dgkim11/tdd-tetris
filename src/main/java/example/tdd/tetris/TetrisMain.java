package example.tdd.tetris;

import example.tdd.tetris.block.BlockFactory;
import example.tdd.tetris.board.BoardCellsControl;
import example.tdd.tetris.board.TetrisBoard;
import example.tdd.tetris.board.TetrisBoardListener;
import example.tdd.tetris.gui.TetrisBoardGui;

import javax.swing.*;
import java.awt.*;

public class TetrisMain extends JFrame {
    private JLabel statusbar;

    public TetrisMain() {
        statusbar = new JLabel("게임시작은 S 버튼 누르세요");
        statusbar.setFont(new Font(statusbar.getFont().getFontName(), Font.PLAIN, 18));
        add(statusbar, BorderLayout.SOUTH);
    }

    public static void main(String[] args)  {
        TetrisMain theApp = new TetrisMain();
        TetrisBoard tetrisBoard = new TetrisBoard(new BoardCellsControl(), new BlockFactory());
        TetrisBoardGui tetrisBoardGui = new TetrisBoardGui(tetrisBoard);
        tetrisBoard.setListener(new TetrisBoardListenerImpl(theApp, tetrisBoardGui));
        theApp.add(tetrisBoardGui);

        theApp.setSize(TetrisBoard.WIDTH_BLOCKS * 20,TetrisBoard.HEIGHT_BLOCKS * 20);
        theApp.setLocationRelativeTo(null);
        theApp.setVisible(true);
    }

    private void setStatusText(String text) {
        statusbar.setText(text);
    }

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
}
