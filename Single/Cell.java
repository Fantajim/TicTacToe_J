
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;


public class Cell extends Button {

   private char ident;

   public Cell(){
      super();
      this.setPrefSize(150,150);
      setStyle("-fx-border-color: black");
      ident = ' ';

   }


   public char getIdent(){
      return ident;
   }

   public void setIdent(char c){
      this.ident = c;

      if (ident == 'X'){
         //Timeline tlX = new Timeline();

         Line line1 = new Line(20,20,this.getWidth()-20,this.getHeight()-20);
         Line line2 = new Line(20,this.getHeight()-20,this.getWidth()-20,20);
         getChildren().addAll(line1,line2);
         this.setDisable(true);
         this.ident = 'X';
      }

      else if(ident == 'O'){

         Ellipse ellipse1 = new Ellipse(this.getWidth()/2,this.getHeight()/2,this.getWidth()/2-20,this.getHeight()/2-20);
         ellipse1.setFill(Color.BLACK);
         getChildren().add(ellipse1);
         this.setDisable(true);
         this.ident = 'O';

      }
   }
}
