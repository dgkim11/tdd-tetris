package example.tdd.tetris.gui;

import example.tdd.tetris.board.TetrisBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        super.paint(g);

        boolean panelResized = false;
        Dimension size = getSize();
        if(width != size.getWidth()) { width = (int)size.getWidth(); panelResized = true; }
        if(height != size.getHeight()) { height = (int)size.getHeight(); panelResized = true; }

        if(panelResized)    {
            screenBuffer = createImage((int)size.getWidth(), (int)size.getHeight());
            graphicsBuffer = screenBuffer.getGraphics();
        }
        graphicsBuffer.setColor(Color.DARK_GRAY);
        graphicsBuffer.fillRect(0, 0, width, height);
        drawBoard(tetrisBoard.getBoardCells(), width, height);

        g.drawImage(screenBuffer, 0, 0, null);
    }

    private void drawBoard(int[][] cells, int width, int height)   {
        graphicsBuffer.setColor(Color.LIGHT_GRAY);
        int cellWidth = width / cells[0].length;
        int cellHeight = height / cells.length;
        // 가로줄 그리기.
        for(int row = 1;row < cells.length;row++)   {
            graphicsBuffer.drawLine(0, cellHeight * row, width, cellHeight * row);
        }
        // 세로줄 그리기.
        for(int col = 1;col < cells[0].length;col++)   {
            graphicsBuffer.drawLine(cellWidth * col, 0, cellWidth * col, height);
        }
        // 블럭들 그리기.
        for(int row = 0;row < cells.length;row++)   {
            for(int col = 0;col < cells[row].length;col++)  {
                if(cells[row][col] != -1)   {
                    graphicsBuffer.setColor(new Color(cells[row][col]));
                    graphicsBuffer.fillRect(cellWidth * col+1, cellHeight * row+1, cellWidth-2, cellHeight-2);
                }
            }
        }
    }

    public void updateBoard() {
        paint(getGraphics());
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
}
