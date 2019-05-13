
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.util.Duration;


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

   public void setIdent(char c) {
      this.ident = c;
         if (ident == 'X') {

            Line line1 = new Line();
            line1.setStartX(20);
            line1.setStartY(20);
            line1.setEndX(this.getWidth() - 20);
            line1.setEndY(this.getHeight() - 20);

            Line line2 = new Line();
            line2.setStartX(20);
            line2.setStartY(this.getHeight() - 20);
            line2.setEndX(this.getWidth() - 20);
            line2.setEndY(20);

            getChildren().addAll(line1, line2);

            this.setDisable(true);
            this.ident = 'X';

         } else if (ident == 'O') {

            Ellipse ellipse1 = new Ellipse();
            ellipse1.setCenterX(this.getWidth() / 2);
            ellipse1.setCenterY(this.getHeight() / 2);
            ellipse1.setRadiusX(this.getWidth() / 2 - 20);
            ellipse1.setRadiusY(this.getHeight() / 2 - 20);

            ellipse1.setFill(Color.BLACK);

            getChildren().add(ellipse1);
            this.setDisable(true);
            this.ident = 'O';

         }
   }
}
