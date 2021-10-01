package mainpkg.test;

//доска
class Board {
    private int[][] board;

    //пустая доска
    public Board() {
        board = new int[8][8];
    }

    //доска с массива
    public Board(int[][] arr) {
        if (arr.length == 8)
            for (int i = 0; i < 8; i++)
                if (arr[i].length != 8) throw new
                        IllegalArgumentException("Wrong array size! (must be 8x8)");
        board = arr;
    }

    //доска со строки
    public Board(String s) {
        if (s.length() != 8) throw new
                IllegalArgumentException("Wrong queen placement (must be 1 per column)");
        board = new int[8][8];
        for (int i = 0; i < 8; i++)
            board[i][Character.getNumericValue(s.charAt(i))] = 1;
    }

    //поставить ферзя
    public void setQueen(int row, int column) {
        board[row][column] = 1;
    }

    //поиск угроз (вся доска)
    public boolean checkBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != 0 && !checkQueen(i, j))
                    return false;
            }
        }
        return true;
    }

    //проверка угроз (заданный ферзь)
    public boolean checkQueen(int row, int column) {
        //колонны
        for (int j = 0; j < 8; j++)
            if (column != j && board[row][j] == 1)
                return false;
        //ряды
        for (int i = 0; i < 8; i++)
            if (row != i && board[i][column] == 1)
                return false;
        //диагональ право-низ
        for (int i = row, j = column; i < 8 && j < 8; i++, j++)
            if (row != i && column != j && board[i][j] == 1)
                return false;
        //диагональ лево-вверх
        for (int i = row, j = column; i >= 0 && j >= 0; i--, j--)
            if (row != i && column != j && board[i][j] == 1)
                return false;
        //диагональ лево-низ
        for (int i = row, j = column; i < 8 && j >= 0; i++, j--)
            if (row != i && column != j && board[i][j] == 1)
                return false;
        //диагональ право-вверх
        for (int i = row, j = column; i >= 0 && j < 8; i--, j++)
            if (row != i && column != j && board[i][j] == 1)
                return false;
        return true;
    }

    //смена колон местами
    public void switchColumns(int col1, int col2) {
        int row1 = getQueenRow(col1);
        int row2 = getQueenRow(col2);
        board[row1][col1] = 0;
        board[row2][col2] = 0;
        board[row1][col2] = 1;
        board[row2][col1] = 1;
    }

    //смена рядов местами
    public void switchRows(int row1, int row2) {
        int column1 = getQueenColumn(row1);
        int column2 = getQueenColumn(row2);
        board[row2][column2] = 0;
        board[row1][column1] = 0;
        board[row1][column2] = 1;
        board[row2][column1] = 1;
    }

    //ряд ферзя
    public int getQueenRow(int column) {
        int row = -1;
        for (int i = 0; i < 8; i++)
            if (board[i][column] != 0)
                row = i;
        return row;
    }

    //колона ферзя
    public int getQueenColumn(int row) {
        int col = -1;
        for (int j = 0; j < 8; j++)
            if (board[row][j] != 0)
                col = j;
        return col;
    }

    //проверка ряда на наличие клетки, в которой ферзя не будут бить
    public boolean rowIsSuitable(int row) {
        for (int j = 0; j < 8; j++) {
            if (checkQueen(row, j)) {
                board[row][j] = 1;
                return true;
            }
        }
        return false;
    }

    //проверка ряда на соответствие условию (убирает королеву если не)
    public boolean rowIsOk(int row) {
        for (int j = 0; j < 8; j++) {
            if (board[row][j] != 0 && !checkQueen(row, j)) {
                board[row][j] = 0;
                return false;
            }
        }
        return true;
    }

    //найти пустую колонну
    public int getEmptyColumn() {
        int col = -1;
        for (int j = 0; j < 8; j++) {
            int tmp = 0;
            for (int i = 0; i < 8; i++) {
                if (board[i][j] != 0) {
                    tmp++;
                    break;
                }
            }
            if (tmp == 0) {
                col = j;
                break;
            }
        }
        return col;
    }

    //вывод
    public String toString() {
        StringBuilder s = new StringBuilder("――――――――――――――――――――――――――\n");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1)
                    s.append("| () ");
                else
                    s.append("|    ");
            }
            s.append("|\n" + "――――――――――――――――――――――――――\n");
        }
        return s.toString();
    }
}
