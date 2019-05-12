public class SingleModel extends Model {

    ServiceLocator serviceLocator;
    private char currentPlayer;
    public SinglePlayer player1;
    public SinglePlayer player2;
    private char symbol1;
    private char symbol2;

    public SingleModel() {

        randomizePlayerSymbol();
        player1 = new SinglePlayer(symbol1, "Player 1");
        player2 = new SinglePlayer(symbol2, "Player 2");

        randomizePlayer();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single model initialized");

       // if (TicTacToeGame.getCpuPlayer() == true &&){


       // }

    }

    public char getCurrentPlayer() {
        char symb;
        if (player1.getTurn()== true){
            symb = player1.getSymbol();
        }
        else {
            symb = player2.getSymbol();
        }
        return symb;
    }

    public String getCurrentPlayerName(){
        String name;
        if (player1.getTurn()== true){
            name = player1.getName();
        }
        else {
            name = player2.getName();
        }
        return name;
    }

    //toggles playerturn
  /*  public void toggleCurrentPlayer() {
        if (currentPlayer == 'X') currentPlayer = 'O';
        else currentPlayer = 'X';
    }*/

  public void toggleCurrentPlayer(){
      if (player1.getTurn()== true){
          player1.setTurn(false);
          player2.setTurn(true);
      }
      else {
          player2.setTurn(false);
          player1.setTurn(true);
      }
  }


   /* //randomizer for playerturn
    public void randomizePlayer() {
        double turn = Math.random();
        if (turn < 0.5) currentPlayer = 'X';
        else currentPlayer = 'O';
    }*/

    public void randomizePlayer() {
        double turn = Math.random();
        if (turn < 0.5) player1.setTurn(true);
        else player2.setTurn(true);
    }

    public void randomizePlayerSymbol(){
        double temp = Math.random();
        if (temp < 0.5) {
            symbol1 = 'X';
            symbol2 = 'O';
        }
        else {
            symbol1 = 'O';
            symbol2 = 'X';
        }
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
