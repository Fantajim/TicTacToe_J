public class SingleModel extends Model {

    ServiceLocator serviceLocator;
    public SinglePlayer player1;
    public SinglePlayer player2;
    private SinglePlayer currentPlayer;
    private char symbol1;
    private char symbol2;
    boolean cpuPlayer = TicTacToeGame.getCpuPlayer();
    private int[] lastTurn;

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

   //Method for toggling currentPlayer
  public void toggleCurrentPlayer(){
      if (getCurrentPlayer()==player1){
          setCurrentPlayer(player2);
      }
      else { setCurrentPlayer(player1); }
  }

    //Method for randomizing current Player
    public void randomizePlayer() {
        double turn = Math.random();
        if (turn <= 0.5) setCurrentPlayer(player1);
        else setCurrentPlayer(player2);
    }


    //Method for randomizing PlayerSymbol
    public void randomizePlayerSymbol(){
        double temp = Math.random();
        if (temp <= 0.5) {
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


        //check if three cells are equal
        if (cells[0][0].getSymbol() == cells[0][1].getSymbol() && cells[0][0].getSymbol() == cells[0][2].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; // first row horizontal
        } else if (cells[1][0].getSymbol() == cells[1][1].getSymbol() && cells[1][0].getSymbol() == cells[1][2].getSymbol() && cells[1][0].getSymbol() != ' ') {
            result = true; // 2nd row horizontal
        } else if (cells[2][0].getSymbol() == cells[2][1].getSymbol() && cells[2][0].getSymbol() == cells[2][2].getSymbol() && cells[2][0].getSymbol() != ' ') {
            result = true; // 3rd row horizontal
        } else if (cells[0][0].getSymbol() == cells[1][0].getSymbol() && cells[0][0].getSymbol() == cells[2][0].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; //first row vertical
        } else if (cells[0][1].getSymbol() == cells[1][1].getSymbol() && cells[0][1].getSymbol() == cells[2][1].getSymbol() && cells[0][1].getSymbol() != ' ') {
            result = true; // 2nd row vertical
        } else if (cells[0][2].getSymbol() == cells[1][2].getSymbol() && cells[0][2].getSymbol() == cells[2][2].getSymbol() && cells[0][2].getSymbol() != ' ') {
            result = true; //3rd row vertical
        } else if (cells[0][0].getSymbol() == cells[1][1].getSymbol() && cells[0][0].getSymbol() == cells[2][2].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; //diagonal
        } else if (cells[0][2].getSymbol() == cells[1][1].getSymbol() && cells[0][2].getSymbol() == cells[2][0].getSymbol() && cells[0][2].getSymbol() != ' ') {
            result = true; //diagonal
        }
        return result;
    }


    //Logic part of draw to check if there's an empty cell left
    public boolean isDrawLogic(Cell[][] cells) {
        boolean result = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j].getSymbol() == ' ') {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    //Logic for CPU to choose which starting cell to press
    public int[] cpuFirstTurn(){
        int[] firstTurn = new int[2];

        double random = Math.random();
        if (random <= 0.5)firstTurn[0]= 0;
        else firstTurn[0] = 2;

        random = Math.random();
        if (random <= 0.5)firstTurn[1]=0;
        else firstTurn[1] = 2;
        return firstTurn;
    }

    public void setLastTurn(int[] i){
        lastTurn[0] = i[0];
        lastTurn[1] = i[1];
    }

    public boolean isCpuTurn(){
        boolean cpuTurn = false;
        if (currentPlayer == player2){
            cpuTurn = true;
        }
        return cpuTurn;
    }

    public SinglePlayer getCurrentPlayer() { return currentPlayer; }

    public void setCurrentPlayer(SinglePlayer player){ currentPlayer = player; }
}
