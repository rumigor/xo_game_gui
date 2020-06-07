package xo_game;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_POS_X = 450;
    private static final int WINDOW_POS_Y = 450;
    private static final int WINDOW_WIDTH = 405;
    private static final int WINDOW_HEIGHT = 400;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    static final int MODE_H_VS_A = 0;
    static final int MODE_H_VS_H = 1;
    private GameWindow gameWindow;
    private JRadioButton radioButtonHvsAi = new JRadioButton("Человек vs Компьютер", true);
    private JRadioButton radioButtonHvsH = new JRadioButton("Человек vs Человек");
    private ButtonGroup gameMode = new ButtonGroup();
    private JSlider sliderFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
    private JSlider sliderDotsToWin = new JSlider(MIN_FIELD_SIZE, MIN_FIELD_SIZE, MIN_FIELD_SIZE);

    public SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.setBounds(WINDOW_POS_X , WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("Настройки игры");
        this.setLayout(new GridLayout(8, 1));
        this.add(new JLabel(" Выберите режим игры:"));
        this.add(this.radioButtonHvsAi);
        this.add(this.radioButtonHvsH);
        this.gameMode.add(this.radioButtonHvsAi);
        this.gameMode.add(this.radioButtonHvsH);
        this.add(new JLabel(" Выберите размер поля: "));
        this.sliderFieldSize.setMajorTickSpacing(1);
        this.sliderFieldSize.setPaintLabels(true);
        this.sliderFieldSize.setPaintTicks(true);
        this.add(this.sliderFieldSize);
        this.add(new JLabel(" Выберите длину линии для победы: "));
        this.sliderDotsToWin.setMajorTickSpacing(1);
        this.sliderDotsToWin.setPaintLabels(true);
        this.sliderDotsToWin.setPaintTicks(true);
        this.add(this.sliderDotsToWin);
        this.sliderFieldSize.addChangeListener((e) -> {
            this.sliderDotsToWin.setMaximum(this.sliderFieldSize.getValue());
        });
        JButton buttonStartNewGame = new JButton("Начать новую игру");
        buttonStartNewGame.setBackground(Color.cyan);
        this.add(buttonStartNewGame);
        buttonStartNewGame.addActionListener((e) -> {
            this.setVisible(false);
            BattleMap.label.setVisible(false);
            GameWindow.buttonStartNewGame.setText("Начать заново");
            byte mode;
            BattleMap.dot = 'X';
            if (this.radioButtonHvsAi.isSelected()) {
                mode = MODE_H_VS_A;
                int fieldSize = this.sliderFieldSize.getValue();
                int dotsToWin = this.sliderDotsToWin.getValue();
                Logic.SIZE = fieldSize;
                Logic.DOTS_TO_WIN = dotsToWin;
                Logic.initMap();
                Logic.gameFinished = false;
                gameWindow.startNewGame(mode, fieldSize, fieldSize, dotsToWin);
            } else {
                mode = MODE_H_VS_H;
                int fieldSize = this.sliderFieldSize.getValue();
                int dotsToWin = this.sliderDotsToWin.getValue();
                LogicPvP.SIZE = fieldSize;
                LogicPvP.DOTS_TO_WIN = dotsToWin;
                LogicPvP.initMap();
                LogicPvP.gameFinished = false;
                gameWindow.startNewGame(mode, fieldSize, fieldSize, dotsToWin);
            }


        });
        this.setVisible(false);
    }
}
