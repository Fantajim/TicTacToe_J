import javafx.scene.control.Button;


public class Cell extends Button {

   private char symbol;

   public Cell(){
      super();
      this.setPrefSize(450/TicTacToeGame.dimension,450/TicTacToeGame.dimension);
      setStyle("-fx-border-color: black");
      symbol = ' ';
   }
   public char getSymbol(){ return symbol; }

   public void setSymbol(char c) { this.symbol = c; }

   public double getCenterX(){
      return this.getLayoutX()+this.getWidth()/2;
   }

   public double getCenterY(){
      return this.getLayoutY()+this.getHeight()/2;
   }
}
