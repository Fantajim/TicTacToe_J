import javafx.scene.control.Button;


public class Cell extends Button {

   private char symbol;

   public Cell(){
      super();
      this.setPrefSize(150,150);
      setStyle("-fx-border-color: black");
      symbol = ' ';
   }
   public char getSymbol(){ return symbol; }

   public void setSymbol(char c) { this.symbol = c; }

   public double getCenterX(){
      return this.getLayoutX()+75;
   }

   public double getCenterY(){
      return this.getLayoutY()+75;
   }
}
