import java.util.ArrayList;
import java.util.Random;

public class SingleModel extends Model {

    ServiceLocator serviceLocator;
    public SinglePlayer player1;
    public SinglePlayer player2;
    private SinglePlayer currentPlayer;
    private char symbol1;
    private char symbol2;
    boolean cpuPlayer = TicTacToeGame.getCpuPlayer();
    private int[] lastTurn;
    private int totalTurns = 0;
    private int[]cpuLastTurn = new int[2];
    ArrayList<int [][]> savedCpuTurns = new ArrayList<>();

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

    //Logic for CPU to choose which starting Cell to take
    public int[] cpuFirstTurn(){
        int[] firstTurn = new int[2];
        double random = Math.random();
        if (random <= 0.5){
            firstTurn[0]= 1;
            firstTurn[1]= 1;
        }
        else {
            random = Math.random();
            if (random <= 0.5) firstTurn[0] = 0;
            else firstTurn[0] = 2;

            random = Math.random();
            if (random <= 0.5) firstTurn[1] = 0;
            else firstTurn[1] = 2;
        }
        return firstTurn;
    }

    public void setLastTurn(int[] i){
        lastTurn[0] = i[0];
        lastTurn[1] = i[1];
        totalTurns +=1;
    }

    public int getTotalTurns(){ return totalTurns;}

    public void setTotalTurns(){ totalTurns=0;}

    public boolean isCpuTurn(){
        boolean cpuTurn = false;
        if (currentPlayer == player2){
            cpuTurn = true;
        }
        return cpuTurn;
    }

    public boolean isAllEmpty(Cell[][] cells) {
        boolean empty = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j].getSymbol() != ' ') empty = false;
            }
        }
        return empty;
    }

    public int[] cpuGetCorner(){
        int[] corner = new int[2];
        double random = Math.random();
        if (random <= 0.5) corner[0] = 0;
        else corner[0] = 2;

        random = Math.random();
        if (random <= 0.5) corner[1] = 0;
        else corner[1] = 2;
        return corner;
    }

    public int[][] cpuFindMoveBest(int[][] moves, Cell[][]cells){
        for (int[]move:moves){
            if (cells[move[0]][move[1]].getSymbol() == ' ') {
                int[][] temp = {{move[0],move[1]}};
                return temp;
            }
        }

      return null;
    }


    public int[][] cpuFindMoveRandom(int[][] moves, Cell[][]cells){
        for (int[]move:moves){
        if (cells[move[0]][move[1]].getSymbol() == ' ') {
            int[][] temp = {{move[0],move[1]}};
            savedCpuTurns.add(temp);
        }
        }

        int number = generateRandom(savedCpuTurns.size());
        int[][] preferedMove = savedCpuTurns.get(number-1);
        return preferedMove;
    }

    public int[][] doesntMatterItsTie(Cell[][] cells){
        int[][] preferedMove = new int[1][1];

        return preferedMove;
    }

    public void setCpuLastTurn(int i, int j){
        cpuLastTurn[0] = i;
        cpuLastTurn[1] = j;
       // savedCpuTurns.add(cpuLastTurn);
    }
    public int[] getLastTurn(){
        return lastTurn;
    }

    //TODO give computer cell back so that player doesnt have 3 in a row

    public int[][] checkTwo(Cell[][] cells){
    //int[] result= new int[2];

        //horizontal check
        if (cells[0][0].getSymbol() == cells[0][1].getSymbol() && cells[0][0].getSymbol() == player1.getSymbol()) {
            int [][]result = {{0,2}};
            return result;
        } else if (cells[0][2].getSymbol() == cells[0][1].getSymbol() && cells[0][2].getSymbol() == player1.getSymbol()) {
            int [][]result = {{0,0}};
            return result;
        } else if (cells[1][0].getSymbol() == cells[1][1].getSymbol() && cells[1][0].getSymbol() == player1.getSymbol()) {
            int [][]result = {{1,2}};
            return result;
        } else if (cells[1][2].getSymbol() == cells[1][1].getSymbol() && cells[1][2].getSymbol() == player1.getSymbol()) {
            int [][]result = {{1,0}};
            return result;
        } else if (cells[2][0].getSymbol() == cells[2][1].getSymbol() && cells[2][0].getSymbol() == player1.getSymbol()) {
            int [][]result = {{2,2}};
            return result;
        } else if (cells[2][2].getSymbol() == cells[2][1].getSymbol() && cells[2][2].getSymbol() == player1.getSymbol()) {
            int [][]result = {{2,0}};
            return result;
        //vertical check
        } else if (cells[0][0].getSymbol() == cells[1][0].getSymbol() && cells[0][0].getSymbol() == player1.getSymbol()) {
            int [][]result = {{2,0}};
            return result;
        } else if (cells[2][0].getSymbol() == cells[1][0].getSymbol() && cells[2][0].getSymbol() == player1.getSymbol()) {
            int [][]result = {{0,0}};
            return result;
        } else if (cells[0][1].getSymbol() == cells[1][1].getSymbol() && cells[0][1].getSymbol() == player1.getSymbol()) {
            int [][]result = {{2,1}};
            return result;
        } else if (cells[2][1].getSymbol() == cells[1][1].getSymbol() && cells[1][1].getSymbol() == player1.getSymbol()) {
            int [][]result = {{0,1}};
            return result;
        } else if (cells[0][2].getSymbol() == cells[1][2].getSymbol() && cells[0][2].getSymbol() == player1.getSymbol()) {
            int [][]result = {{2,2}};
            return result;
        } else if (cells[2][2].getSymbol() == cells[1][2].getSymbol() && cells[2][2].getSymbol() == player1.getSymbol()) {
            int [][]result = {{0,2}};
            return result;
         //diagonal check
        } else if (cells[0][0].getSymbol() == cells[1][1].getSymbol() && cells[0][0].getSymbol() == player1.getSymbol()) {
            int [][]result = {{2,2}};
            return result;
        } else if (cells[2][2].getSymbol() == cells[1][1].getSymbol() && cells[2][2].getSymbol() == player1.getSymbol()) {
            int[][] result = {{0, 0}};
            return result;
        }

        return null;
    }

    public int generateRandom(int i){
        int number;
        Random random = new Random();
        number = random.nextInt(i)+1;
        return number;
    }

    public int[] getCpuLastTurn(){ return cpuLastTurn; }

    public SinglePlayer getCurrentPlayer() { return currentPlayer; }

    public void setCurrentPlayer(SinglePlayer player){ currentPlayer = player; }
}
