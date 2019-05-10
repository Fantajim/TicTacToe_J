import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.Date;

public class ClassicSingle {

   private Cell[][] cell = new Cell[3][3];
   private GridPane grid;
   private BorderPane pane;
   private TextArea console;
   private LocalDateTime ts;
   Scene scene;
   ServiceLocator serviceLocator;

   public ClassicSingle(){

      grid = new GridPane();

      for (int i = 0; i < 3;i++){
         for (int j = 0; j < 3;j++){
         cell[i][j] = new Cell();
         grid.add(cell[i][j],j,i);
         }
      }

      ts = LocalDateTime.now();
      pane = new BorderPane();
      console = new TextArea();
      console.setText(ts + " Classic SinglePlayer Game has started");

      pane.setCenter(grid);
      pane.setRight(console);

      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Classic SinglePlayer Game has been started");

      scene = new Scene(pane);



   }

}
