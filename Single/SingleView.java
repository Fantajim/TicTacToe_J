
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SingleView extends View<SingleModel> {

   ServiceLocator serviceLocator;
   TextArea console;
   private SingleModel model;
   private Stage stage;
   private Cell[][] cells;
   private GridPane grid;
   private BorderPane pane;
   private LocalDateTime ts;
   private String fts;
   private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
   private DateTimeFormatter formatter;

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
      cells = new Cell[3][3];

      for (int i = 0;i<3;i++){
         for( int j = 0;j< 3;j++){
            cells[i][j] = new Cell();
            grid.add(cells[i][j],j,i);
         }
      }

      ts = LocalDateTime.now();
      formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
      fts = ts.format(formatter);
      pane = new BorderPane();
      console = new TextArea();
      console.setPrefWidth(300);
      console.setWrapText(true);
      console.setText("Classic SinglePlayer Game has started "+"\n"+ fts);

      pane.setCenter(grid);
      pane.setRight(console);

      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Classic SinglePlayer Game has been started");

      scene = new Scene(pane);
      return scene;
   }

   /*public GridPane getGrid() {
      return grid;
   }*/

   public Cell[][] getCells(){
      return cells;
   }

   public Cell getCell(int i, int j){
      return cells[i][j];
   }


   //method for creating a new playboard
   public void createBoard(){

      grid = new GridPane();
      for (int i = 0;i<3;i++){
         for( int j = 0;j< 3;j++){
            cells[i][j] = new Cell();
            grid.add(cells[i][j],j,i);
         }
      }
      pane.setCenter(grid);
   }


   //method for easy console access
   public void addToConsole(String s){
      ts = LocalDateTime.now();
      fts = ts.format(formatter);
      String temp = console.getText();
      temp = temp.concat("\n"+"\n"+s+"\n"+fts);
      console.setText(temp);
   }
}
