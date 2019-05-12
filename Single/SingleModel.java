public class SingleModel extends Model {

    ServiceLocator serviceLocator;
    private char currentPlayer;

    public SingleModel() {

        SinglePlayer player1 = new SinglePlayer('X', "John");
        SinglePlayer player2 = new SinglePlayer('O', "Jim");


        randomizePlayer();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single model initialized");

    }

    public Cell[][] createCells() {

        Cell[][] cells = new Cell[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();

            }
        }
        return cells;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void toggleCurrentPlayer() {
        if (currentPlayer == 'X') currentPlayer = 'O';
        else currentPlayer = 'X';
    }

    public void randomizePlayer() {
        double turn = Math.random();
        if (turn < 0.5) currentPlayer = 'X';
        else currentPlayer = 'O';
    }

    public boolean isWinLogic(Cell[][] cells) {
        boolean result = false;
        Cell[][] temp = cells;

        if (temp[0][0].getIdent() == temp[0][1].getIdent() && temp[0][0].getIdent() == temp[0][2].getIdent() && temp[0][0].getIdent() != ' ') {
            result = true;
        } else if (temp[1][0].getIdent() == temp[1][1].getIdent() && temp[1][0].getIdent() == temp[1][2].getIdent() && temp[1][0].getIdent() != ' ') {
            result = true;
        } else if (temp[2][0].getIdent() == temp[2][1].getIdent() && temp[2][0].getIdent() == temp[2][2].getIdent() && temp[2][0].getIdent() != ' ') {
            result = true;
        } else if (temp[0][0].getIdent() == temp[1][0].getIdent() && temp[0][0].getIdent() == temp[2][0].getIdent() && temp[0][0].getIdent() != ' ') {
            result = true;
        } else if (temp[0][1].getIdent() == temp[1][1].getIdent() && temp[0][1].getIdent() == temp[2][1].getIdent() && temp[0][1].getIdent() != ' ') {
            result = true;
        } else if (temp[0][2].getIdent() == temp[1][2].getIdent() && temp[0][2].getIdent() == temp[2][2].getIdent() && temp[0][2].getIdent() != ' ') {
            result = true;
        } else if (temp[0][0].getIdent() == temp[1][1].getIdent() && temp[0][0].getIdent() == temp[2][2].getIdent() && temp[0][0].getIdent() != ' ') {
            result = true;
        } else if (temp[0][2].getIdent() == temp[1][1].getIdent() && temp[0][2].getIdent() == temp[2][0].getIdent() && temp[0][2].getIdent() != ' ') {
            result = true;
        }
        return result;
    }

    public boolean isDrawLogic(Cell[][] cells) {
        boolean result = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j].getIdent() == ' ') {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
