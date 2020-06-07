package xo_game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BattleMap extends JPanel {
    private GameWindow gameWindow;
    private int gameMode;
    private int fieldSizeX;
    private int fieldSizeY;
    private int dotsToWin;
    private int cellHeight;
    private int cellWidth;
    private boolean isInit = false;
    protected static char dot = 'X';
    public static JLabel label = new JLabel();

    public BattleMap(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.add(label);
        this.setBackground(Color.ORANGE);
        this.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (BattleMap.this.isInit) {
                    BattleMap.this.mouseReleasedUpdate(e);
                }

            }
        });
    }

    private void mouseReleasedUpdate(MouseEvent e) {
        int cellX = e.getX() / this.cellWidth;
        int cellY = e.getY() / this.cellHeight;
        switch (this.gameMode) {
            case 0:
                if (!Logic.gameFinished) {
                    Logic.setHumanCoords(cellX, cellY);
                    System.out.printf("cellX: %d  cellY: %d \n", cellX, cellY);
                    this.repaint();
                    if (Logic.gameFinished) {
                       if (Logic.checkWinLines('X')) {
                           gameOver("Игрок победил!");
                           dot = 'X';
                        }
                       else if (Logic.checkWinLines('O')) {
                           gameOver("Компьютер победил!");
                           dot = 'O';
                       }
                       else {
                           gameOver("Ничья!");
                           dot = '.';
                       }
                    }

                }
                break;
            case 1:
                if (!LogicPvP.gameFinished) {
                    if (dot == 'X') {
                        LogicPvP.setHumanTurn(cellX, cellY, 'X');
                        dot = 'O';
                    } else {
                        LogicPvP.setHumanTurn(cellX, cellY, 'O');
                        dot = 'X';
                    }
                    System.out.printf("cellX: %d  cellY: %d \n", cellX, cellY);
                        this.repaint();
                        if (LogicPvP.gameFinished) {
                            dot = 'X';
                            if (LogicPvP.checkWinLines('X')) {
                                gameOver("Игрок №1 победил!");
                                dot = 'X';
                            }
                            else if (LogicPvP.checkWinLines('O')) {
                                gameOver("Игрок №2 победил!");
                                dot = 'O';
                            }
                            else {
                                gameOver("Ничья!");}
                        }
                }
                break;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.render(g);
    }

    private void render(Graphics g) {
        if (this.isInit) {
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            this.cellHeight = panelHeight / this.fieldSizeY;
            this.cellWidth = panelWidth / this.fieldSizeX;

            int i;
            int j;
            for (i = 0; i < this.fieldSizeY; ++i) {
                j = i * this.cellHeight;
                g.drawLine(0, j, panelWidth, j);
            }

            for (i = 0; i < this.fieldSizeX; ++i) {
                j = i * this.cellWidth;
                g.drawLine(j, 0, j, panelHeight);
            }


            switch (this.gameMode) {
                case 0:
                    for (i = 0; i < Logic.SIZE; ++i) {
                        for (j = 0; j < Logic.SIZE; ++j) {
                            if (Logic.map[i][j] == 'X') {
                                this.drawX(g, j, i);
                            } else if (Logic.map[i][j] == 'O') {
                                this.drawO(g, j, i);
                            }
                        }
                    }
                    if ((Logic.gameFinished) && (dot == 'X' || dot == 'O')) {
                        for (int y = 0; y < Logic.SIZE; ++y) {
                            for (int x = 0; x < Logic.SIZE; ++x) {
                                if (Logic.checkLine(y, x, 0, 1, dot)) {
                                    line(g, y, x, 0, 1);
                                }
                                else if (Logic.checkLine(y, x, 1, 0, dot)) {
                                    line(g, y, x, 1, 0);
                                }
                                else if (Logic.checkLine(y, x, 1, 1, dot)) {
                                    line(g, y, x, 1, 1);
                                }
                                else if (Logic.checkLine(y, x, -1, 1, dot)){
                                    line(g, y, x, -1, 1);
                                }
                            }
                        }
                        break;
                    }
                    break;
                case 1:
                    for (i = 0; i < LogicPvP.SIZE; ++i) {
                        for (j = 0; j < LogicPvP.SIZE; ++j) {
                            if (LogicPvP.map[i][j] == 'X') {
                                this.drawX(g, j, i);
                            } else if (LogicPvP.map[i][j] == 'O') {
                                this.drawO(g, j, i);
                            }
                        }
                    }
                    if (LogicPvP.gameFinished) {
                        for (int y = 0; y < LogicPvP.SIZE; ++y) {
                            for (int x = 0; x < LogicPvP.SIZE; ++x) {
                                if (LogicPvP.checkLine(y, x, 0, 1, dot)) {
                                    line(g, y, x, 0, 1);
                                }
                                else if (LogicPvP.checkLine(y, x, 1, 0, dot)) {
                                    line(g, y, x, 1, 0);
                                }
                                else if (LogicPvP.checkLine(y, x, 1, 1, dot)) {
                                    line(g, y, x, 1, 1);
                                }
                                else if (LogicPvP.checkLine(y, x, -1, 1, dot)){
                                    line(g, y, x, -1, 1);
                                }
                            }
                        }
                        break;
                    }
            }
        }
    }

    private void drawX(Graphics g, int cellX, int cellY) {
        Graphics2D g2 = (Graphics2D)g;
        int paintWidth = (this.cellWidth) / 10;
        BasicStroke pen1 = new BasicStroke(paintWidth);
        g2.setStroke(pen1);
        g2.setColor(Color.red);
        g2.drawLine((cellX * this.cellWidth) + paintWidth, (cellY * this.cellHeight) + paintWidth, ((cellX + 1) * (this.cellWidth))-paintWidth, ((cellY + 1) * (this.cellHeight))-paintWidth);
        g2.drawLine(((cellX + 1) * (this.cellWidth))-paintWidth, (cellY * (this.cellHeight))+paintWidth, (cellX * (this.cellWidth))+paintWidth, ((cellY + 1) * (this.cellHeight))-paintWidth);
    }

    private void drawO(Graphics g, int cellX, int cellY) {
        Graphics2D g2 = (Graphics2D)g;
        int paintWidth = (this.cellWidth) / 10;
        BasicStroke pen1 = new BasicStroke(paintWidth);
        g2.setStroke(pen1);
        g2.setColor(Color.blue);
        g2.drawOval(cellX*(this.cellWidth) + paintWidth/2, cellY*(this.cellHeight) + paintWidth/2, (this.cellWidth)-paintWidth, (this.cellHeight) - paintWidth);

    }
    private void line(Graphics g, int y, int x, int vy, int vx) {
        Graphics2D g2 = (Graphics2D) g;
        int paintWidth = (this.cellWidth) / 10;
        BasicStroke pen1 = new BasicStroke(paintWidth);
        g2.setStroke(pen1);
        g2.setColor(Color.black);
        if (vy == 0 && vx == 1) {
            g2.drawLine((x) * this.cellWidth, (y) * this.cellHeight + this.cellHeight / 2, (x + dotsToWin ) * this.cellWidth, (y) * this.cellHeight + this.cellHeight / 2);
        }
        else if (vy == 1 && vx == 0) {
            g2.drawLine((x) * this.cellWidth + this.cellWidth / 2, (y) * this.cellHeight, (x) * this.cellWidth + this.cellWidth / 2, (y+dotsToWin) * this.cellHeight);
        }
        else if (vy == 1 && vx == 1) {
            g2.drawLine((x) * this.cellWidth, (y) * this.cellHeight, (x+dotsToWin) * this.cellWidth, (y+dotsToWin) * this.cellHeight);
        }
        else if (vy == -1 && vx == 1) {
            g2.drawLine((x) * this.cellWidth, (y+1) * this.cellHeight, (x+dotsToWin) * this.cellWidth, (y+1-dotsToWin) * this.cellHeight);
        }
    }


    public void startNewGame(int gameMode, int fieldSizeX, int fieldSizeY, int dotsToWin) {
        this.gameMode = gameMode;
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.dotsToWin = dotsToWin;
        this.isInit = true;
        this.repaint();
    }

    public void gameOver (String s) {
        this.add(label);
        label.setText(s);
        Font font = new Font("Arial", 1, 40);
        label.setFont(font);
        label.setForeground(Color.GREEN);
        label.setBackground(Color.gray);
        label.setOpaque(true);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVisible(true);
        GameWindow.buttonStartNewGame.setText("Начать новую игру");

    }


}