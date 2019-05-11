
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class SingleView extends View<SingleModel> {

   ServiceLocator serviceLocator;
   private MainMenuModel model;
   private Stage stage;
   private Cell[][] cell;
   private GridPane grid;
   private BorderPane pane;
   private TextArea console;
   private LocalDateTime ts;
   Scene scene;

   public SingleView(Stage stage, SingleModel model) {

      super(stage, model);
      stage.setTitle("Classic SinglePlayer");

      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Single view initialized");

   }

   @Override
   protected Scene create_GUI() {

   grid = new GridPane();
      cell = new Cell[3][3];
      for (int i = 0; i < 3;i++){
         for (int j = 0; j < 3;j++){
         cell[i][j] = new Cell();
         grid.add(cell[i][j], j, i);
         }
      }

      ts = LocalDateTime.now();
      pane = new BorderPane();
      console = new TextArea();
      console.setPrefWidth(300);
      console.setWrapText(true);
      console.setText("Classic SinglePlayer Game has started ("+ ts+")");

      pane.setCenter(grid);
      pane.setRight(console);

      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Classic SinglePlayer Game has been started");

      scene = new Scene(pane);


      return scene;
   }

   public GridPane getGrid() {
      return grid;
   }
}
