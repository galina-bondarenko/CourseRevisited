import java.util.Random;
import java.util.Scanner;

public class XOgame {

    private static int fieldSize;
    private static char[][] field;

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static final char HUMAN_DOT = 'X';
    private static final char AI_DOT = 'O';
    private static final char EMPTY_DOT = '.';

    // init field
    private static void initMap() {
        fieldSize = 3;
        field = new char[fieldSize][fieldSize];
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                field[y][x] = EMPTY_DOT;
            }
        }
    }

    // print field
    private static void printMap() {
        System.out.println("-------");
        for (int y = 0; y < fieldSize; y++) {
            System.out.print("|");
            for (int x = 0; x < fieldSize; x++) {
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }
    }

    // human turn
    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.printf("Введите координаты хода X и Y (от 1 до %d) через пробел: ", fieldSize);
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!(isEmptyCell(x, y) && isValidCell(y, x)));
        field[x][y] = HUMAN_DOT;
    }

    // is cell empty
    private static boolean isEmptyCell(int x, int y) {
        return field[x][y] == EMPTY_DOT;
    }

    // is cell valid
    private static boolean isValidCell(int y, int x) {
        return x >= 0 && x < fieldSize && y >= 0 && y < fieldSize;
    }

    /*
    "умный" поиск следующего хода.
    Подсчитывает "вес" для каждой горизонтали, вертикали и диагонали, чем он больше - тем лучше.
    Отрицательный вес означает, что именно в этом направлении сосредоточил свои усилия игрок и надо ему помешать
     */
    private static int[] searchAiPlace() {

        //место следующего хода. -1 означает, что ещё ничего не нашли
        int[] place = new int[]{-1, -1};

        //Суммы по строкам и столбцам
        int sumRow, sumColumn;

        for (int i = 0; i < fieldSize; i++) {
            sumRow = sumColumn = 0; //обнуляем веса
            for (int j = 0; j < fieldSize; j++) {
                //если в ячейке стоит знак ИИ, увеличиваем вес; если знак игрока - уменьшаем
                sumRow += (field[i][j] == AI_DOT) ? 1 : (field[i][j] == EMPTY_DOT) ? 0 : -1;
                sumColumn += (field[j][i] == AI_DOT) ? 1 : (field[j][i] == EMPTY_DOT) ? 0 : -1;
            }
            //критическая ситуация: либо мы в шаге от победы, либо игрок. случай для строки
            if (fieldSize - 1 == sumRow || 1 - fieldSize == sumRow) {
                //ищем свободную ячейку
                for (int j = 0; j < fieldSize; j++) {
                    if (isEmptyCell(i, j)) place = new int[]{i, j};
                }
                //если ход предотвращает проигрыш, возвращаем его
                if (1 - fieldSize == sumRow) return place;
                //в противном случае смотрим, нет ли хода получше
            }

            //аналогичная проверка для столбца
            if (fieldSize - 1 == sumColumn || 1 - fieldSize == sumColumn) {
                for (int j = 0; j < fieldSize; j++) {
                    if (isEmptyCell(j, i)) place = new int[]{j, i};
                }
                if (1 - fieldSize == sumColumn) return place;
            }
        }

        //теперь проверяем диагонали
        int sumDiagB, sumDiagS;
        sumDiagB = sumDiagS = 0;
        for (int i = 0; i < fieldSize; i++) {
            sumDiagB += (field[i][i] == AI_DOT) ? 1 : (field[i][i] == EMPTY_DOT) ? 0 : -1;
            sumDiagS += (field[i][fieldSize - 1 - i] == AI_DOT) ? 1 : (field[i][fieldSize - 1 - i] == EMPTY_DOT) ? 0 : -1;
        }
        if (1 - fieldSize == sumDiagB) {
            for (int i = 0; i < fieldSize; i++) {
                if (isEmptyCell(i, i)) return new int[]{i, i};
            }
        }
        if (1 - fieldSize == sumDiagS) {
            for (int i = 0; i < fieldSize; i++) {
                if (isEmptyCell(i, fieldSize - 1 - i)) return new int[]{i, fieldSize - 1 - i};
            }
        }
        if (fieldSize - 1 == sumDiagB) {
            for (int i = 0; i < fieldSize; i++) {
                if (isEmptyCell(i,i)) place = new int[]{i, i};
            }
        }
        if (fieldSize - 1 == sumDiagS) {
            for (int i = 0; i < fieldSize; i++) {
                if (isEmptyCell(i, fieldSize - 1 - i)) place = new int[]{i, fieldSize - 1 - i};
            }
        }

        return place;


    }

    private static void aiTurn() {
        int[] turn = searchAiPlace();
        if (turn[0] == -1) {
            do {
                turn = new int[]{RANDOM.nextInt(fieldSize), RANDOM.nextInt(fieldSize)};
            } while (!(isValidCell(turn[0], turn[1]) && isEmptyCell(turn[0], turn[1])));
        }
        System.out.println("Компьютер выбрал ячейку " + (turn[1] + 1) + " " + (turn[0] + 1));
        field[turn[0]][turn[1]] = AI_DOT;

    }

    // check win
    private static boolean checkWin(char z) {
        //проверяем горизонтали
        boolean win;
        for (int y = 0; y < fieldSize; y++) {
            win = true;
            for (int x = 0; x < fieldSize; x++) {
                if (field[y][x] != z) {
                    win = false;
                }
            }
            if (win) {
                return true;
            }
        }

        //проверяем вертикали
        for (int y = 0; y < fieldSize; y++) {
            win = true;
            for (int x = 0; x < fieldSize; x++) {
                if (field[x][y] != z) {
                    win = false;
                }
            }
            if (win) {
                return true;
            }
        }
        //проверяем главную диагональ
        win = true;
        for (int i = 0; i < fieldSize; i++) {
            if (field[i][i] != z) {
                win = false;
            }
        }
        if (win) {
            return true;
        }
        //проверяем побочную диагональ
        win = true;
        for (int i = 0; i < fieldSize; i++) {
            if (field[i][fieldSize - i - 1] != z) {
                win = false;
            }
        }
        return win;
    }

    // check draw
    private static boolean isMapFull() {
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                if (isEmptyCell(y, x)) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        while (true) {
            initMap();
            printMap();
            while (true) {
                humanTurn();
                printMap();
                if (gameChecks(HUMAN_DOT, "Поздравляю! Победа Ваша.")) break;
                aiTurn();
                printMap();
                if (gameChecks(AI_DOT, "Вы проиграли. В следующий раз будьте внимательнее.")) break;
            }
            System.out.println("Сыграем ещё?");
            if (!SCANNER.next().equalsIgnoreCase("Y"))
                break;
        }
        SCANNER.close();
    }

    private static boolean gameChecks(char aiDot, String s) {
        if (checkWin(aiDot)) {
            System.out.println(s);
            return true;
        }
        if (isMapFull()) {
            System.out.println("Ничья.");
            return true;
        }
        return false;
    }
}
