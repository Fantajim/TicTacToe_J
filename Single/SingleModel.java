public class SingleModel extends Model{

  ServiceLocator serviceLocator;
  private char currentPlayer;

  public SingleModel(){

      SinglePlayer player1 = new SinglePlayer('X', "John");
      SinglePlayer player2 = new SinglePlayer('O', "Jim");

      int turn = (int)Math.random();
      if (turn == 0)currentPlayer = 'X';
      else currentPlayer = 'O';



      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Single model initialized");

  }

  public char getCurrentPlayer(){
      return currentPlayer;
  }

  public void toggleCurrentPlayer(){
      if (currentPlayer=='X') currentPlayer = 'O';
      else currentPlayer = 'X';
  }

}
