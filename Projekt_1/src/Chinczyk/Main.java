package Chinczyk;
import java.util.Scanner;
import java.util.Random;


public class Main {
    private static int numberOfPlayers;
    private static char[] pawnColors;

    private static boolean gameEnded = false;
    private static int[] pawnPositions;

    private static int[][] movePositions = {
            {2, 21},
            {3, 21},
            {4, 21},
            {5, 21},
            {6, 21},
            // 1
            {6, 24},
            {6, 27},
            {6, 30},
            {6, 33},
            // 2
            {7, 33},
            {8, 33},
            // 3
            {8, 30},
            {8, 27},
            {8, 24},
            {8, 21},
            // 4
            {9, 21},
            {10, 21},
            {11, 21},
            {12, 21},
            // 5
            {12, 18},
            {12, 15},
            // 6
            {11, 15},
            {10, 15},
            {9, 15},
            {8, 15},
            // 7
            {8, 12},
            {8, 9},
            {8, 6},
            {8, 3},
            // 8
            {7, 3},
            {6, 3},
            // 9
            {6, 6},
            {6, 9},
            {6, 12},
            {6, 15},
            // 10
            {5, 15},
            {4, 15},
            {3, 15},
            {2, 15},
            // 11
            {2, 18}
    };
    private static int [][] aBeginPosition={{2,33},{2,30},{3,33},{3,30}};
    private static int [][] bBeginPosition={{12,33},{12,30},{11,33},{11,30}};
    private static int [][] cBeginPosition={{12,3},{12,6},{11,3},{11,6}};
    private static int [][] dBeginPosition={{2,3},{2,6},{3,3},{3,6}};

    private static int[][] beginPositions = {{2,33}, {12,33}, {12,3}, {2,3}};
    private static int[][] winPositions = {{3,18}, {7,30}, {11,18}, {7,6}};
    private static Random rand = new Random();

    private static int[] moveCounts = new int[4];
    private static int HEIGHT = 15;
    private static int WIDTH = 37;
    private static char[][] picture = new char[HEIGHT][WIDTH];

    public static void main(String[] args) {
        start();
    }

    private static void initializePicture() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                picture[i][j] = ' ';
            }
        }
    }

    private static void drawLines() {
        for (int j = 0; j < 5; j++) {
            picture[(HEIGHT / 2) - 1][(j * 3) + 3] = 'x';
        }

        for (int i = 0; i < 4; i++) {
            picture[(HEIGHT / 2 - i - 1) - 1][(4 * 3) + 3] = 'x';
        }

        for (int j = 0; j < 2; j++) {
            picture[(HEIGHT / 2 - 4) - 1][(4 * 3 + (j + 1) * 3) + 3] = 'x';
        }

        for (int i = 0; i < 4; i++) {
            picture[(HEIGHT / 2 - 4 + i + 1) - 1][(6 * 3) + 3] = 'x';
        }

        for (int j = 0; j < 4; j++) {
            picture[(HEIGHT / 2) - 1][(6 * 3 + (j + 1) * 3) + 3] = 'x';
        }

        for (int i = 0; i < 2; i++) {
            picture[(HEIGHT / 2 + i + 1) - 1][(10 * 3) + 3] = 'x';
        }

        for (int j = 0; j < 4; j++) {
            picture[(HEIGHT / 2 + 2) - 1][(10 * 3 - (j + 1) * 3) + 3] = 'x';
        }

        for (int i = 0; i < 4; i++) {
            picture[(HEIGHT / 2 + 2 + i + 1) - 1][(6 * 3) + 3] = 'x';
        }

        for (int j = 0; j < 2; j++) {
            picture[(HEIGHT - 3)][(6 * 3 - (j + 1) * 3) + 3] = 'x';
        }

        for (int i = 0; i < 4; i++) {
            picture[(HEIGHT - 1 - (i + 1)) - 2][(4 * 3) + 3] = 'x';
        }

        for (int j = 0; j < 4; j++) {
            picture[(HEIGHT / 2 + 2) - 1][(4 * 3 - (j + 1) * 3) + 3] = 'x';
        }

        for (int i = 0; i < 2; i++) {
            picture[(HEIGHT / 2 + 2 - (i + 1)) - 1][3] = 'x';
        }
        picture[1][21] = '0';

        picture[8][35] = '1';
        picture[8][36] = '0';

        picture[13][15] = '2';
        picture[13][16] = '0';

        picture[6][0] = '3';
        picture[6][1] = '0';

        picture[2][33] = 'a';
        picture[2][30] = 'a';
        picture[3][33] = 'a';
        picture[3][30] = 'a';

        picture[12][33] = 'b';
        picture[12][30] = 'b';
        picture[11][33] = 'b';
        picture[11][30] = 'b';

        picture[12][3] = 'c';
        picture[12][6] = 'c';
        picture[11][3] = 'c';
        picture[11][6] = 'c';

        picture[2][3] = 'd';
        picture[2][6] = 'd';
        picture[3][3] = 'd';
        picture[3][6] = 'd';


    }

    private static void printBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(picture[i][j]);
            }
            System.out.println();
        }
    }

    public static void start() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj ilość graczy (od 2 do 4): ");
        numberOfPlayers = scan.nextInt();
        while (numberOfPlayers < 2 || numberOfPlayers > 4) {
            System.out.print("Nieprawidłowa liczba graczy. Podaj liczbę graczy (od 2 do 4): ");
            numberOfPlayers = scan.nextInt();
        }

        initializePicture();
        drawLines();

        pawnColors = new char[4];
        pawnPositions = new int[4];
        for (int i = 0; i < 4; i++) {
            if (i < numberOfPlayers) {
                pawnColors[i] = (char) ('a' + i);
                pawnPositions[i] = -1;
            } else {
                pawnColors[i] = ' ';
                pawnPositions[i] = -1;
            }
        }

        int currentPlayer = 0;
        int count=0;

        while (!gameEnded) {
            System.out.println();
            System.out.println("-------------------------------");
            System.out.println("Ruch gracza " + pawnColors[currentPlayer]);
            System.out.println("-------------------------------");

            boolean playerMoved = false;

            if (pawnPositions[currentPlayer] < 0) {
                for (int i = 0; i < 3; i++) {
                    int roll = rand.nextInt(6) + 1;
                    System.out.println("Wylosowano: " + roll);

                    if (roll == 6 && !playerMoved) {
                        pawnPositions[currentPlayer] = currentPlayer * 10;
                        System.out.println("Gracz " + pawnColors[currentPlayer] + " wyrzucil 6 i umiescil pionek na planszy!");
                        playerMoved = true;
                        break;
                    }

                    if (!playerMoved) {
                        System.out.println("Gracz " + pawnColors[currentPlayer] + " nie wyrzucil 6. Rzucaj jeszcze " + (2 - i) + " razy.");
                    }
                }

                if (!playerMoved) {
                    System.out.println("Gracz " + pawnColors[currentPlayer] + " nie wyrzucił 6. Kolejny gracz.");
                }
            } else {
                int roll = rand.nextInt(6) + 1;
                System.out.println("Wylosowano: " + roll);
                System.out.println("Gracz " + pawnColors[currentPlayer] + ", podaj numer pola, na ktorym stoi pionek ktory nalezy przesunac:");
                int chosenPawn = scan.nextInt();
                movePlayer(currentPlayer, roll);
            }


            if (moveCounts[currentPlayer] >= 40) {
                System.out.println("Gracz " + pawnColors[currentPlayer] + " umieszcza pionek do domu");
                count++;
                gameEnded = true;
            }


            currentPlayer = (currentPlayer + 1) % numberOfPlayers;


            initializePicture();
            drawLines();
            for (int i = 0; i < numberOfPlayers; i++) {
                if (pawnPositions[i] >= 0) {
                    int[] pos = movePositions[pawnPositions[i]];
                    picture[pos[0]][pos[1]] = pawnColors[i];
                    int[] beginPos = beginPositions[i];
                    picture[beginPos[0]][beginPos[1]] = ' ';

                }
            }
            if(count>0){
                for(int i=0; i<4; i++) {
                    if(moveCounts[i]>40){
                        switch (i) {
                            case 0 -> picture[3][18] = pawnColors[i];
                            case 1 -> picture[7][30] = pawnColors[i];
                            case 2 -> picture[11][18] = pawnColors[i];
                            case 3 -> picture[7][6] = pawnColors[i];
                        }
                    }
                }
            }
            gameEnded=false;


            printBoard();
        }
    }

    public static void movePlayer(int player, int roll) {
        int[] oldPos = movePositions[pawnPositions[player]];
        picture[oldPos[0]][oldPos[1]] = '-';

        pawnPositions[player] = (pawnPositions[player] + roll) % movePositions.length;
        moveCounts[player] += roll;


        int[] newPos = movePositions[pawnPositions[player]];
        if (picture[newPos[0]][newPos[1]] != '-' && picture[newPos[0]][newPos[1]] != 'x') {
            System.out.println("Gracz " + pawnColors[player] + " spotkał na swojej drodze gracza " + picture[newPos[0]][newPos[1]]);
            for (int i = 0; i < numberOfPlayers; i++) {
                if (pawnColors[i] == picture[newPos[0]][newPos[1]]) {
                    pawnPositions[i] = -1;
                    moveCounts[i] = 0;
                    int[] startPos = beginPositions[i];
                    picture[startPos[0]][startPos[1]] = pawnColors[i];
                }
            }
        }
        picture[newPos[0]][newPos[1]] = pawnColors[player];
    }


    public static void start(int numOfPlayers, int[][] pawnColorsAndPositions, int[] rollResults, int[] moveDecisions) {
        numberOfPlayers=numOfPlayers;
        initializePicture();
        drawLines();

        pawnColors = new char[4];
        pawnPositions = new int[4];
        for (int i = 0; i < 4; i++) {
            if (i < numberOfPlayers) {
                pawnColors[i] = (char) ('a' + i);
                pawnPositions[i] = -1;
            } else {
                pawnColors[i] = ' ';
                pawnPositions[i] = -1;
            }
        }

        int currentPlayer = 0;
        int count=0;

        while (!gameEnded) {
            System.out.println();
            System.out.println("-------------------------------");
            System.out.println("Ruch gracza " + pawnColors[currentPlayer]);
            System.out.println("-------------------------------");

            boolean playerMoved = false;

            if (pawnPositions[currentPlayer] < 0) {
                for (int i = 0; i < 3; i++) {
                    int roll = rand.nextInt(6) + 1;
                    System.out.println("Wylosowano: " + roll);

                    if (roll == 6 && !playerMoved) {
                        pawnPositions[currentPlayer] = currentPlayer * 10;
                        System.out.println("Gracz " + pawnColors[currentPlayer] + " wyrzucił 6 i umieścił pionek na planszy!");
                        playerMoved = true;
                        break;
                    }

                    if (!playerMoved) {
                        System.out.println("Gracz " + pawnColors[currentPlayer] + " nie wyrzucił 6. Rzucaj jeszcze " + (2 - i) + " razy.");
                    }
                }

                if (!playerMoved) {
                    System.out.println("Gracz " + pawnColors[currentPlayer] + " nie wyrzucił 6. Kolejny gracz.");
                }
            } else {
                int roll = rand.nextInt(6) + 1;
                System.out.println("Wylosowano: " + roll);
                System.out.println("Gracz " + pawnColors[currentPlayer] + ", podaj numer pola, na ktorym stoi pionek ktory nalezy przesunac:");
                movePlayer(currentPlayer, roll);
            }

            for (int[] a:pawnColorsAndPositions) {
                pawnPositions=pawnColorsAndPositions[0];
            }

            System.out.print("Wczesniejsze proby: ");
            for(int x : rollResults) {
                System.out.print(x+" ");
            }

            System.out.println("Wczesniejsze ruchy: ");
            for(int x : moveDecisions) {
                System.out.print(x+" ");
            }


            if (moveCounts[currentPlayer] >= 40) {
                System.out.println("Gracz " + pawnColors[currentPlayer] + " umieszcza pionek do domu");
                count++;
                gameEnded = true;
            }

            currentPlayer = (currentPlayer + 1) % numberOfPlayers;

            initializePicture();
            drawLines();
            for (int i = 0; i < numberOfPlayers; i++) {
                if (pawnPositions[i] >= 0) {
                    int[] pos = movePositions[pawnPositions[i]];
                    picture[pos[0]][pos[1]] = pawnColors[i];
                    int[] beginPos = beginPositions[i];
                    picture[beginPos[0]][beginPos[1]] = ' ';

                }
            }
            if(count>0){
                for(int i=0; i<4; i++) {
                    if(moveCounts[i]>40){
                        switch (i) {
                            case 0 -> picture[3][18] = pawnColors[i];
                            case 1 -> picture[7][30] = pawnColors[i];
                            case 2 -> picture[11][18] = pawnColors[i];
                            case 3 -> picture[7][6] = pawnColors[i];
                        }
                    }
                }
            }
            gameEnded=false;


            printBoard();
        }

    }

}

