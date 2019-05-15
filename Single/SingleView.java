
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SingleView extends View<SingleModel> {

   ServiceLocator serviceLocator;
   TextArea console;
   private Cell[][] cells;
   private Button backButton;
   private Button restartButton;
   private GridPane grid;
   private BorderPane pane;
   private LocalDateTime ts;
   private String fts;
   private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
   private DateTimeFormatter formatter;
   private Label player1Label;
   private Label player2Label;
   private Label playerTurnLabel;
   private Scene scene;
   private VBox groupGrid;
   private Pane mainPane;
   private Line winLine = new Line();


   public SingleView(Stage stage, SingleModel model) {

      super(stage, model);
      stage.setTitle("Classic SinglePlayer");
      player1Label.setText(model.player1.getName() + " = "+ model.player1.getSymbol());
      player2Label.setText(model.player2.getName() + " = "+ model.player2.getSymbol());
      playerTurnLabel.setText("Current turn: "+model.getCurrentPlayer().getName());
      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Single view initialized");
      updateTurnLabel();

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
      //Setup console with timestamp
      ts = LocalDateTime.now();
      formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
      fts = ts.format(formatter);
      pane = new BorderPane();
      console = new TextArea();
      console.setPrefWidth(300);
      console.setWrapText(true);
      console.setText("Classic SinglePlayer Game has started "+"\n"+ fts);
      player1Label = new Label(" ");
      player2Label = new Label(" ");
      playerTurnLabel = new Label(" ");
      Region spacer1 = new Region();
      Region spacer2 = new Region();
      Region spacer3 = new Region();
      Region spacer4 = new Region();

      //set all nodes in root pane
      groupGrid = new VBox(grid);

      restartButton = new Button("Restart");
      restartButton.setPrefSize(100,50);
      backButton = new Button("Main Menu");
      backButton.setPrefSize(100,50);

      HBox playerBox = new HBox(backButton,spacer3,playerTurnLabel,spacer1,player1Label,spacer2,player2Label,spacer4,restartButton);
      playerBox.setHgrow(spacer1,Priority.ALWAYS);
      playerBox.setHgrow(spacer2,Priority.ALWAYS);
      playerBox.setHgrow(spacer3,Priority.ALWAYS);
      playerBox.setHgrow(spacer4,Priority.ALWAYS);
      playerBox.setAlignment(Pos.CENTER);


      pane.setCenter(groupGrid);
      pane.setRight(console);
      pane.setBottom(playerBox);

      mainPane = new Pane(pane);
      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Classic SinglePlayer Game has been started");

      scene = new Scene(mainPane);
      return scene;
   }

   //get whole Array
   public Cell[][] getCells(){ return cells; }

   //get a single Cell from Array
   public Cell getCell(int i, int j){ return cells[i][j]; }


   //method for creating a new playboard
   public void createBoard(){

      for (int i = 0;i<3;i++){
         for( int j = 0;j< 3;j++){
            cells[i][j].setGraphic(null);
            cells[i][j].setSymbol(' ');
            cells[i][j].setDisable(false);
         }
      }
   }

   //method for easy console access
   public void addToConsole(String s){
      ts = LocalDateTime.now();
      fts = ts.format(formatter);
      String temp = s+"\n"+fts+"\n"+"\n";
      temp += console.getText();
      console.setText(temp);
   }

   //Update label to display currentplayer
   public void updateTurnLabel() {
      playerTurnLabel.setText("Current turn: "+ model.getCurrentPlayer().getName());
   }

   //Draw Symbols ontop of Cell
   public void drawSymbol(char symbol, Cell c){
      if (symbol == 'X') {

         Group lineGroup = new Group();

         Line line1 = new Line();
         line1.setStartX(20);
         line1.setStartY(20);
         line1.setEndX(c.getWidth() - 20);
         line1.setEndY(c.getHeight() - 20);

         Line line2 = new Line();
         line2.setStartX(20);
         line2.setStartY(c.getHeight() - 20);
         line2.setEndX(c.getWidth() - 20);
         line2.setEndY(20);

         lineGroup.getChildren().addAll(line1,line2);

         c.setGraphic(lineGroup);
         c.setDisable(true);
         c.setSymbol('X');

      } else if (symbol == 'O') {

         Ellipse ellipse1 = new Ellipse();
         ellipse1.setCenterX(c.getWidth() / 2);
         ellipse1.setCenterY(c.getHeight() / 2);
         ellipse1.setRadiusX(c.getWidth() / 2 - 20);
         ellipse1.setRadiusY(c.getHeight() / 2 - 20);

         ellipse1.setFill(Color.BLACK);

         c.setGraphic(ellipse1);
         c.setDisable(true);
         c.setSymbol('O');
      }
   }

   public void animateWin(){
       Cell[] temp = model.getWinnerCombo();
       Timeline tl = new Timeline();

       winLine.setStartX(temp[0].getCenterX());
       winLine.setStartY(temp[0].getCenterY());
       winLine.setEndX(temp[0].getCenterX());
       winLine.setEndY(temp[0].getCenterY());
       winLine.setStrokeWidth(10);
       mainPane.getChildren().addAll(winLine);
       tl.getKeyFrames().add(new KeyFrame(Duration.millis(1500),
               new KeyValue(winLine.endXProperty(),temp[2].getCenterX()),
               new KeyValue(winLine.endYProperty(),temp[2].getCenterY())));
       tl.play();

   }

   public Button getBackButton(){
       return backButton;
   }

   public Button getRestartButton(){
       return restartButton;
   }

   public void removeLine(){
       mainPane.getChildren().remove(winLine);
   }




}
