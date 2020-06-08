package xo_game;

import java.util.Random;

public class Logic {
    static int SIZE;
    static int DOTS_TO_WIN;
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

        for(y = 0; y < SIZE; ++y) {
            for (x = 0; x < SIZE; ++x) {
                if (map[y][x] == 'O') {
                    int xr = aiCheckLine(y, x, 0, 1);
                    int yr = aiCheckLine(y, x, 1, 0);
                    int d1 = aiCheckLine(y, x, 1, 1);
                    int d2 = -1; //aiCheckLine(y, x, -1, 1);
                    if (xr >= yr && xr >= d1 && xr >= d2) {
                        boolean isMarked = false;
                        for (int k = 1; x+k < SIZE; k++) {
                            if (isCellValid(y, x+k)) {
                                map[y][x+k] = 'O';
                                isMarked = true;
                                break;
                            }
                        }
                        if (!isMarked) {
                            for (int k = 1; x-k >= 0; k++) {
                                if (isCellValid(y, x - k)) {
                                    map[y][x - k] = 'O';
                                    isMarked = true;
                                    return;
                                }
                            }
                        }
                        return;
                    } else if (yr >= xr && yr >= d1 && yr >= d2) {
                        boolean isMarked = false;
                        for (int k = 1; y+k < SIZE; k++) {
                            if (isCellValid(y+k, x)) {
                                map[y+k][x] = 'O';
                                isMarked = true;
                                break;
                            }
                        }
                        if (!isMarked) {
                            for (int k = 1; y-k >= 0; k++) {
                                if (isCellValid(y - k, x)) {
                                    map[y - k][x] = 'O';
                                    isMarked = true;
                                    return;
                                }
                            }
                        }
                        return;
                    } else if (d1 >= yr && d1 >= xr && d1 >= d2) {
                        boolean isMarked = false;
                        for (int k = 1; x+k < SIZE; k++) {
                            if (isCellValid(y+k, x+k)) {
                                map[y+k][x+k] = 'O';
                                isMarked = true;
                                break;
                            }
                        }
                        if (!isMarked) {
                            for (int k = 1; x-k >= 0; k++) {
                                if (isCellValid(y - k, x-k)) {
                                    map[y - k][x-k] = 'O';
                                    isMarked = true;
                                    return;
                                }
                            }
                        }
                        return;
                    } else if (d2 >= yr && d2 >= xr && d2 >= d1) {
                        boolean isMarked = false;
                        for (int k = 1; k+x < SIZE; k++) {
                            if (isCellValid(y-k, x+k)) {
                                map[y-k][x+k] = 'O';
                                isMarked = true;
                                break;
                            }
                        }
                        if (!isMarked) {
                            for (int k = 0; k-x >= 0; k++) {
                                if (isCellValid(y + k, x-k)) {
                                    map[y - k][x-k] = 'O';
                                    isMarked = true;
                                    return;
                                }
                            }
                        }

                    }
                }
            }
        }
        x = random.nextInt(SIZE);
        y = random.nextInt(SIZE);
        while (!isCellValid(y , x)) {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        }
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

    static int aiCheckLineX(int y, int x) {
            int max = SIZE - 1;
            int min = 0;
            if (x + DOTS_TO_WIN - 1 < SIZE) {
                max = x + DOTS_TO_WIN - 1;
            }
            else {max = SIZE - 1;}
            if (x - DOTS_TO_WIN - 1 > 0) {
                min = x - DOTS_TO_WIN;
            }
            else {min = 0;}

            int dot = 0;
            int o = 0;
            for(int i = min; i <= max; i++) {
                if (map[y][i] == 'X') {
                    dot = 0;
                    o = 0;
                } else if (map[y][i] == '.') {
                    dot++;
                } else {
                    o++;
                }
                if (o + dot == DOTS_TO_WIN) {
                    break;
                }
            }
            if (dot+o < DOTS_TO_WIN) {return -1;}
            else {return o;}
    }

    static int aiCheckLineY(int y, int x) {
        int max = SIZE - 1;
        int min = 0;
        if (y + DOTS_TO_WIN - 1 < SIZE) {
            max = y + DOTS_TO_WIN - 1;
        }
        else {max = SIZE - 1;}
        if (y - DOTS_TO_WIN - 1 > 0) {
            min = y - DOTS_TO_WIN;
        }
        else {min = 0;}

        int dot = 0;
        int o = 0;
        for(int i = min; i <= max; i++) {
            if (map[i][x] == 'X') {
                dot = 0;
                o = 0;
            } else if (map[i][x] == '.') {
                dot++;
            } else {
                o++;
            }
            if (o + dot == DOTS_TO_WIN) {
                break;
            }
        }
        if (dot+o < DOTS_TO_WIN) {return -1;}
        else {return o;}
    }
    static int aiCheckLineXY(int y, int x) {
        int max;
        int min;
        if (y + DOTS_TO_WIN - 1 < SIZE) {
            max = y + DOTS_TO_WIN - 1;
        }
        else max =
            int dot = 0;
            int o = 0;
            for (int i = 0; i < DOTS_TO_WIN; i++) {
                if (map[y+i][x+i] == 'X') {
                    dot = 0;
                    o = 0;
                } else if (map[i][x] == '.') {
                    dot++;
                } else {
                    o++;
                }
                if (o + dot == DOTS_TO_WIN) {
                    break;
                }
            }

        }
//        int minX, minY, maxX, maxY, min, max;
//        if (x - DOTS_TO_WIN - 1 > 0) {
//            minX = x - DOTS_TO_WIN;
//        }
//        else {minX = 0;}
//        if (y - DOTS_TO_WIN - 1 > 0) {
//            minY = y - DOTS_TO_WIN;
//        }
//        else {minY = 0;}
//
//        if (x + DOTS_TO_WIN - 1 < SIZE) {
//            maxX = x + DOTS_TO_WIN - 1;
//        }
//        else {maxX = SIZE;}
//
//        if (y + DOTS_TO_WIN - 1 < SIZE) {
//            maxY = y + DOTS_TO_WIN - 1;
//        }
//        else {maxY = SIZE;}
//
//        if (minX <= minY) {min = minX;}
//        else {min = minY;}
//
//        if (maxX <= maxY ) {max = maxY;}
//        else {max = maxX;}

        int dot = 0;
        int o = 0;
        for(int i = min; i <= max; i++) {
            if (map[i][x] == 'X') {
                dot = 0;
                o = 0;
            } else if (map[i][x] == '.') {
                dot++;
            } else {
                o++;
            }
            if (o + dot == DOTS_TO_WIN) {
                break;
            }
        }
        if (dot+o < DOTS_TO_WIN) {return -1;}
        else {return o;}
    }
    static int aiCheckLineY(int y, int x) {
        int max = SIZE - 1;
        int min = 0;
        if (y + DOTS_TO_WIN - 1 < SIZE) {
            max = y + DOTS_TO_WIN - 1;
        }
        else {max = SIZE - 1;}
        if (y - DOTS_TO_WIN > 0) {
            min = y - DOTS_TO_WIN;
        }
        else {min = 0;}

        int dot = 0;
        int o = 0;
        for(int i = min; i <= max; i++) {
            if (map[i][x] == 'X') {
                dot = 0;
                o = 0;
            } else if (map[i][x] == '.') {
                dot++;
            } else {
                o++;
            }
            if (o + dot == DOTS_TO_WIN) {
                break;
            }
        }
        if (dot+o < DOTS_TO_WIN) {return -1;}
        else {return o;}
    }
}
