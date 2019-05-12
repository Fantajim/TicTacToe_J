public class SingleModel extends Model{

  ServiceLocator serviceLocator;
  private char currentPlayer;

  public SingleModel(){

      SinglePlayer player1 = new SinglePlayer('X', "John");
      SinglePlayer player2 = new SinglePlayer('O', "Jim");

      double turn = Math.random();
      if (turn < 0.5)currentPlayer = 'X';
      else currentPlayer = 'O';



      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Single model initialized");

  }

    public Cell[][] createCells(){

       Cell[][] cells = new Cell[3][3];

        for (int i = 0;i<3;i++){
            for( int j = 0;j< 3;j++){
                cells[i][j] = new Cell();

            }
        }
      return cells;
    }

  public char getCurrentPlayer(){
      return currentPlayer;
  }

  public void toggleCurrentPlayer(){
      if (currentPlayer=='X') currentPlayer = 'O';
      else currentPlayer = 'X';
  }

}
