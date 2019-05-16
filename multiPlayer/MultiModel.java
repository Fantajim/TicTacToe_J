import java.util.ArrayList;
import java.util.Random;

public class MultiModel extends Model {

    ServiceLocator serviceLocator;
    public Player player1;
    public Player player2;
    private Player currentPlayer;
    private char symbol1;
    private char symbol2;
    private Cell[] winnerCombo = new Cell[3];

    public MultiModel() {
        randomizePlayerSymbol();
        player1 = new Player(symbol1, "Player 1");
        player2 = new Player(symbol2, "Player 2");
        randomizePlayer();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Multi model initialized");

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

    public Player getCurrentPlayer() { return currentPlayer; }

    public void setCurrentPlayer(Player player){ currentPlayer = player; }

    public Cell[] getWinnerCombo(){ return winnerCombo; }

}
