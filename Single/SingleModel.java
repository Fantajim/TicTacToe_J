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

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    //toggles playerturn
    public void toggleCurrentPlayer() {
        if (currentPlayer == 'X') currentPlayer = 'O';
        else currentPlayer = 'X';
    }


    //randomizer for playerturn
    public void randomizePlayer() {
        double turn = Math.random();
        if (turn < 0.5) currentPlayer = 'X';
        else currentPlayer = 'O';
    }


    //Logic part of win
    public boolean isWinLogic(Cell[][] cells) {
        boolean result = false;
        Cell[][] temp = cells;


        //check if three cells are equal
        if (temp[0][0].getIdent() == temp[0][1].getIdent() && temp[0][0].getIdent() == temp[0][2].getIdent() && temp[0][0].getIdent() != ' ') {
            result = true; // first row horizontal
        } else if (temp[1][0].getIdent() == temp[1][1].getIdent() && temp[1][0].getIdent() == temp[1][2].getIdent() && temp[1][0].getIdent() != ' ') {
            result = true; // 2nd row horizontal
        } else if (temp[2][0].getIdent() == temp[2][1].getIdent() && temp[2][0].getIdent() == temp[2][2].getIdent() && temp[2][0].getIdent() != ' ') {
            result = true; // 3rd row horizontal
        } else if (temp[0][0].getIdent() == temp[1][0].getIdent() && temp[0][0].getIdent() == temp[2][0].getIdent() && temp[0][0].getIdent() != ' ') {
            result = true; //first row vertical
        } else if (temp[0][1].getIdent() == temp[1][1].getIdent() && temp[0][1].getIdent() == temp[2][1].getIdent() && temp[0][1].getIdent() != ' ') {
            result = true; // 2nd row vertical
        } else if (temp[0][2].getIdent() == temp[1][2].getIdent() && temp[0][2].getIdent() == temp[2][2].getIdent() && temp[0][2].getIdent() != ' ') {
            result = true; //3rd row vertical
        } else if (temp[0][0].getIdent() == temp[1][1].getIdent() && temp[0][0].getIdent() == temp[2][2].getIdent() && temp[0][0].getIdent() != ' ') {
            result = true; //diagonal
        } else if (temp[0][2].getIdent() == temp[1][1].getIdent() && temp[0][2].getIdent() == temp[2][0].getIdent() && temp[0][2].getIdent() != ' ') {
            result = true; //diagonal
        }
        return result;
    }


    //Logic part of draw to check if there's an empty cell left
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
