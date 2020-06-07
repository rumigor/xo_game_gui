package xo_game;

import java.util.Random;

public class Logic {
    static int SIZE = 3;
    static int DOTS_TO_WIN = 3;
    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';
    static char[][] map;
    static boolean gameFinished = false;
    static Random random = new Random();

    public Logic() {
    }

    public static void go() {
        gameFinished = true;
        printMap();
        if (checkWinLines('X')) {
            System.out.println("Ты победил!");
        } else if (isFull()) {
            System.out.println("Ничья");
        } else {
            aiTurn();
            printMap();
            if (checkWinLines('O')) {
                System.out.println("Компьютер победил!");
            } else if (isFull()) {
                System.out.println("Ничья");
            } else {
                gameFinished = false;
            }
        }
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];

        for(int i = 0; i < SIZE; ++i) {
            for(int j = 0; j < SIZE; ++j) {
                map[i][j] = '.';
            }
        }

    }

    public static void printMap() {
        System.out.print("  ");

        int i;
        for(i = 0; i < SIZE; ++i) {
            System.out.print(i + 1 + " ");
        }

        System.out.println();

        for(i = 0; i < SIZE; ++i) {
            System.out.print(i + 1 + " ");

            for(int j = 0; j < SIZE; ++j) {
                System.out.print(map[i][j] + " ");
            }

            System.out.println();
        }

    }

    public static void setHumanCoords(int x, int y) {
        if (isCellValid(y, x)) {
            map[y][x] = 'X';
            go();
        }

    }

    public static boolean isCellValid(int y, int x) {
        if (x >= 0 && y >= 0 && x < SIZE && y < SIZE) {
            return map[y][x] == '.';
        } else {
            return false;
        }
    }

    public static void aiTurn() {
        int i;
        int j;
        for(i = 0; i < SIZE; ++i) {
            for(j = 0; j < SIZE; ++j) {
                if (isCellValid(i, j)) {
                    map[i][j] = 'O';
                    if (checkWinLines('O')) {
                        return;
                    }

                    map[i][j] = '.';
                }
            }
        }

        for(i = 0; i < SIZE; ++i) {
            for(j = 0; j < SIZE; ++j) {
                if (isCellValid(i, j)) {
                    map[i][j] = 'X';
                    if (checkWinLines('X')) {
                        map[i][j] = 'O';

                        return;
                    }

                    map[i][j] = '.';
                }
            }
        }

        int x;
        int y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while(!isCellValid(y, x));

        map[y][x] = 'O';
    }

    public static boolean isFull() {
        for(int i = 0; i < SIZE; ++i) {
            for(int j = 0; j < SIZE; ++j) {
                if (map[i][j] == '.') {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean checkLine(int cy, int cx, int vy, int vx, char dot) {
        if (cx + vx * (DOTS_TO_WIN - 1) <= SIZE - 1 && cy + vy * (DOTS_TO_WIN - 1) <= SIZE - 1 && cy + vy * (DOTS_TO_WIN - 1) >= 0) {
            for(int i = 0; i < DOTS_TO_WIN; ++i) {
                if (map[cy + i * vy][cx + i * vx] != dot) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    static boolean checkWinLines(char dot) {
        for(int i = 0; i < SIZE; ++i) {
            for(int j = 0; j < SIZE; ++j) {
                if (checkLine(i, j, 0, 1, dot) || checkLine(i, j, 1, 0, dot) || checkLine(i, j, 1, 1, dot) || checkLine(i, j, -1, 1, dot)) {
                    return true;
                }
            }
        }

        return false;
    }
}
