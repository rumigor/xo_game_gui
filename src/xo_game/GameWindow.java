package xo_game;

import java.awt.*;
import javax.swing.*;

public class GameWindow extends JFrame {
    static final int WINDOW_POS_X = 400;
    static final int WINDOW_POS_Y = 400;
    static final int WINDOW_WIDTH = 505;
    static final int WINDOW_HEIGHT = 555;
    private SettingsWindow settingsWindow;
    private BattleMap battleMap;
    protected static JButton buttonStartNewGame = new JButton("Начать игру");

    public GameWindow() {
        this.setDefaultCloseOperation(3);
        this.setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("Крестики-нолики");
        this.settingsWindow = new SettingsWindow(this);
        this.battleMap = new BattleMap(this);
        this.add(this.battleMap, "Center");
        JPanel panel = new JPanel(new GridLayout(1, 2));
        buttonStartNewGame.setBackground(Color.cyan);
        panel.add(buttonStartNewGame);
        JButton buttonExit = new JButton("Выход");
        buttonExit.setBackground(Color.PINK);
        panel.add(buttonExit);
        buttonExit.addActionListener((e) -> {
            System.exit(0);
        });
        buttonStartNewGame.addActionListener((e) -> {
            this.settingsWindow.setVisible(true);
        });
        this.add(panel, "South");

        this.setVisible(true);
    }

    public void startNewGame(int gameMode, int fieldSizeX, int fieldSizeY, int dotsToWin) {
        this.battleMap.startNewGame(gameMode, fieldSizeX, fieldSizeY, dotsToWin);
    }


}
