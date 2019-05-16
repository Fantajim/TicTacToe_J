import java.util.ArrayList;
import java.util.Random;

public class SingleModel extends Model {

    ServiceLocator serviceLocator;
    public SinglePlayer player1;
    public SinglePlayer player2;
    private SinglePlayer currentPlayer;
    private char symbol1;
    private char symbol2;
    String cpuDifficulty = TicTacToeGame.getCpuDifficulty();
    boolean cpuPlayer = TicTacToeGame.getCpuPlayer();
    private Cell[] winnerCombo = new Cell[3];
    ArrayList<int [][]> savedCpuTurns = new ArrayList<>();

    public SingleModel() {
        randomizePlayerSymbol();
        player1 = new SinglePlayer(symbol1, "Player 1");
        player2 = new SinglePlayer(symbol2, "Player 2");
        randomizePlayer();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single model initialized");

       if (cpuPlayer == true && cpuDifficulty.equals("hard") ){ player2.setName("Hal"); }
       else if (cpuPlayer == true && cpuDifficulty.equals("medium") ){ player2.setName("Tron"); }
       else if (cpuPlayer == true && cpuDifficulty.equals("easy") ){ player2.setName("Wall-E"); }
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

        //TODO make loops instead of being a caveman
        //check if three cells are equal
        if (cells[0][0].getSymbol() == cells[0][1].getSymbol() && cells[0][0].getSymbol() == cells[0][2].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; // first row horizontal
            winnerCombo[0] = cells[0][0];
            winnerCombo[1] = cells[0][1];
            winnerCombo[2] = cells[0][2];
        } else if (cells[1][0].getSymbol() == cells[1][1].getSymbol() && cells[1][0].getSymbol() == cells[1][2].getSymbol() && cells[1][0].getSymbol() != ' ') {
            result = true; // 2nd row horizontal
            winnerCombo[0] = cells[1][0];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[1][2];
        } else if (cells[2][0].getSymbol() == cells[2][1].getSymbol() && cells[2][0].getSymbol() == cells[2][2].getSymbol() && cells[2][0].getSymbol() != ' ') {
            result = true; // 3rd row horizontal
            winnerCombo[0] = cells[2][0];
            winnerCombo[1] = cells[2][1];
            winnerCombo[2] = cells[2][2];
        } else if (cells[0][0].getSymbol() == cells[1][0].getSymbol() && cells[0][0].getSymbol() == cells[2][0].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; //first row vertical
            winnerCombo[0] = cells[0][0];
            winnerCombo[1] = cells[1][0];
            winnerCombo[2] = cells[2][0];
        } else if (cells[0][1].getSymbol() == cells[1][1].getSymbol() && cells[0][1].getSymbol() == cells[2][1].getSymbol() && cells[0][1].getSymbol() != ' ') {
            result = true; // 2nd row vertical
            winnerCombo[0] = cells[0][1];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[2][1];
        } else if (cells[0][2].getSymbol() == cells[1][2].getSymbol() && cells[0][2].getSymbol() == cells[2][2].getSymbol() && cells[0][2].getSymbol() != ' ') {
            result = true; //3rd row vertical
            winnerCombo[0] = cells[0][2];
            winnerCombo[1] = cells[1][2];
            winnerCombo[2] = cells[2][2];
        } else if (cells[0][0].getSymbol() == cells[1][1].getSymbol() && cells[0][0].getSymbol() == cells[2][2].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; //diagonal
            winnerCombo[0] = cells[0][0];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[2][2];
        } else if (cells[0][2].getSymbol() == cells[1][1].getSymbol() && cells[0][2].getSymbol() == cells[2][0].getSymbol() && cells[0][2].getSymbol() != ' ') {
            result = true; //diagonal
            winnerCombo[0] = cells[0][2];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[2][0];
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

    public boolean isCpuTurn(){
        boolean cpuTurn = false;
        if (currentPlayer == player2){
            cpuTurn = true;
        }
        return cpuTurn;
    }

    //Finds random cell within provided array
    public int[][] cpuFindMoveRandom(int[][] moves, Cell[][]cells){
        for (int[]move:moves){
        if (cells[move[0]][move[1]].getSymbol() == ' ') {
            int[][] temp = {{move[0],move[1]}};
            savedCpuTurns.add(temp);
        }
        }
        if (savedCpuTurns.size() > 0) {
            int number = generateRandom(savedCpuTurns.size());
            int[][] preferedMove = savedCpuTurns.get(number - 1);
            savedCpuTurns.clear();
            return preferedMove;
        }
        else return null;
    }

    //Check to see if there's a combination of two equal symbols
    public int[][] checkTwo(Cell[][] cells, char c){

        //TODO make loops instead of being a caveman
        //horizontal check
        if (cells[0][0].getSymbol() == cells[0][1].getSymbol() && cells[0][0].getSymbol() == c && cells[0][2].getSymbol() == ' ' ) {
            int [][]result = {{0,2}};
            return result;
        } else if (cells[0][2].getSymbol() == cells[0][1].getSymbol() && cells[0][2].getSymbol() == c && cells[0][0].getSymbol() == ' ') {
            int [][]result = {{0,0}};
            return result;
        } else if (cells[0][0].getSymbol() == cells[0][2].getSymbol() && cells[0][2].getSymbol() == c && cells[0][1].getSymbol() == ' ') {
            int [][]result = {{0,1}};
            return result;
        } else if (cells[1][0].getSymbol() == cells[1][1].getSymbol() && cells[1][0].getSymbol() == c && cells[1][2].getSymbol() == ' ') {
            int [][]result = {{1,2}};
            return result;
        } else if (cells[1][2].getSymbol() == cells[1][1].getSymbol() && cells[1][2].getSymbol() == c && cells[1][0].getSymbol() == ' ') {
            int [][]result = {{1,0}};
            return result;
        } else if (cells[1][0].getSymbol() == cells[1][2].getSymbol() && cells[1][2].getSymbol() == c && cells[1][1].getSymbol() == ' ') {
            int [][]result = {{1,1}};
            return result;
        } else if (cells[2][0].getSymbol() == cells[2][1].getSymbol() && cells[2][0].getSymbol() == c && cells[2][2].getSymbol() == ' ') {
            int [][]result = {{2,2}};
            return result;
        } else if (cells[2][2].getSymbol() == cells[2][1].getSymbol() && cells[2][2].getSymbol() == c && cells[2][0].getSymbol() == ' ') {
            int[][] result = {{2, 0}};
            return result;
        } else if (cells[2][0].getSymbol() == cells[2][2].getSymbol() && cells[2][2].getSymbol() == c && cells[2][1].getSymbol() == ' ') {
                int [][]result = {{2,1}};
                return result;

        //vertical check
        } else if (cells[0][0].getSymbol() == cells[1][0].getSymbol() && cells[0][0].getSymbol() == c && cells[2][0].getSymbol() == ' ') {
            int [][]result = {{2,0}};
            return result;
        } else if (cells[2][0].getSymbol() == cells[1][0].getSymbol() && cells[2][0].getSymbol() == c && cells[0][0].getSymbol() == ' ') {
            int [][]result = {{0,0}};
            return result;
        } else if (cells[0][0].getSymbol() == cells[2][0].getSymbol() && cells[2][0].getSymbol() == c && cells[1][0].getSymbol() == ' ') {
            int [][]result = {{1,0}};
            return result;
        }else if (cells[0][1].getSymbol() == cells[1][1].getSymbol() && cells[0][1].getSymbol() == c && cells[2][1].getSymbol() == ' ') {
            int [][]result = {{2,1}};
            return result;
        } else if (cells[2][1].getSymbol() == cells[1][1].getSymbol() && cells[1][1].getSymbol() == c && cells[0][1].getSymbol() == ' ') {
            int [][]result = {{0,1}};
            return result;
        } else if (cells[0][1].getSymbol() == cells[2][1].getSymbol() && cells[2][1].getSymbol() == c && cells[1][1].getSymbol() == ' ') {
            int [][]result = {{1,1}};
            return result;
        }else if (cells[0][2].getSymbol() == cells[1][2].getSymbol() && cells[0][2].getSymbol() == c && cells[2][2].getSymbol() == ' ') {
            int [][]result = {{2,2}};
            return result;
        } else if (cells[2][2].getSymbol() == cells[1][2].getSymbol() && cells[2][2].getSymbol() == c && cells[0][2].getSymbol() == ' ') {
            int[][] result = {{0, 2}};
            return result;
        } else if (cells[0][2].getSymbol() == cells[2][2].getSymbol() && cells[2][2].getSymbol() == c && cells[1][2].getSymbol() == ' ') {
            int [][]result = {{1,2}};
            return result;

            //diagonal check
        } else if (cells[0][0].getSymbol() == cells[1][1].getSymbol() && cells[0][0].getSymbol() == c && cells[2][2].getSymbol() == ' ') {
            int [][]result = {{2,2}};
            return result;
        } else if (cells[0][0].getSymbol() == cells[2][2].getSymbol() && cells[0][0].getSymbol() == c && cells[1][1].getSymbol() == ' ') {
            int [][]result = {{1,1}};
            return result;
        } else if (cells[1][1].getSymbol() == cells[2][2].getSymbol() && cells[1][1].getSymbol() == c && cells[0][0].getSymbol() == ' ') {
            int [][]result = {{0,0}};
            return result;//first diagonal
        } else if (cells[0][2].getSymbol() == cells[1][1].getSymbol() && cells[0][2].getSymbol() == c && cells[2][0].getSymbol() == ' ') {
            int[][] result = {{2, 0}};
            return result;
        } else if (cells[0][2].getSymbol() == cells[2][0].getSymbol() && cells[0][2].getSymbol() == c && cells[1][1].getSymbol() == ' ') {
            int [][]result = {{1,1}};
            return result;
        } else if (cells[1][1].getSymbol() == cells[2][0].getSymbol() && cells[1][1].getSymbol() == c && cells[0][2].getSymbol() == ' ') {
            int[][] result = {{0, 2}};
            return result;//second diagonal
        }

        return null;
    }

    //Generates a random number with provided int range, used for cpuFindMoveRandom
    public int generateRandom(int i){
        int number;
        Random random = new Random();
        number = random.nextInt(i)+1;
        return number;
    }

    public SinglePlayer getCurrentPlayer() { return currentPlayer; }

    public void setCurrentPlayer(SinglePlayer player){ currentPlayer = player; }

    public Cell[] getWinnerCombo(){ return winnerCombo; }

}
