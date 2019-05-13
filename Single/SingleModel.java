import java.util.Random;

public class SingleModel extends Model {

    ServiceLocator serviceLocator;
    public SinglePlayer player1;
    public SinglePlayer player2;
    private char symbol1;
    private char symbol2;
    boolean cpuPlayer = TicTacToeGame.getCpuPlayer();
    private int[] lastTurn;
    private Cell[][] lookCurrentGrid;

    public SingleModel() {
        lastTurn = new int[2];
        randomizePlayerSymbol();
        player1 = new SinglePlayer(symbol1, "Player 1");
        player2 = new SinglePlayer(symbol2, "Player 2");

        randomizePlayer();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single model initialized");

       if (cpuPlayer == true ){ player2.setName("Hal"); }

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

    public int[] cpuFirstTurn(){
        int[] firstTurn = new int[2];
        double random = Math.random();

        if (random > 0.5)firstTurn[0]=0;
        else firstTurn[0] = 2;

        random = Math.random();

        if (random > 0.5)firstTurn[1]=0;
        else firstTurn[1] = 2;

        return firstTurn;
    }

    public void setLastTurn(int[] i){
        lastTurn[0] = i[0];
        lastTurn[1] = i[1];
    }

    public boolean isCpuTurn(){
        boolean cpuTurn = false;
        char symbol = getCurrentPlayer();
        if (cpuPlayer == true && symbol == player2.getSymbol()){
            cpuTurn = true;
        }
        else {
            cpuTurn = false;
        }
        return cpuTurn;
    }
}
