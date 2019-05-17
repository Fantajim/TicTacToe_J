
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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

public class MultiView extends View<MultiModel> {

   ServiceLocator serviceLocator;
   private TextArea console;
   private Cell[][] cells;
   private Button backButton;
   private Button restartButton;
   private GridPane grid;
   private BorderPane pane;
   private LocalDateTime ts;
   private String fts;
   private static final String DATE_FORMATTER= "dd-MM-yyyy HH:mm:ss";
   private DateTimeFormatter formatter;
   private Label player1Label;
   private Label player2Label;
   private Label playerTurnLabel;
   private Scene scene;
   private Pane mainPane;
   private Line winLine = new Line();


   public MultiView(Stage stage, MultiModel model) {

      super(stage, model);
      stage.setTitle("MultiPlayer");
      player1Label.setText(model.player1.getName() + " = "+ model.player1.getSymbol());
      player2Label.setText(model.player2.getName() + " = "+ model.player2.getSymbol());
      playerTurnLabel.setText("Current turn: "+model.getCurrentPlayer().getName());
      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Multi view initialized");
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
      console.setText("MultiPlayer Game has started "+"\n"+ fts);
      player1Label = new Label(" ");
      player2Label = new Label(" ");
      playerTurnLabel = new Label(" ");
      Region spacer1 = new Region();
      Region spacer2 = new Region();
      Region spacer3 = new Region();
      Region spacer4 = new Region();

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


      pane.setCenter(grid);
      pane.setRight(console);
      pane.setBottom(playerBox);

      mainPane = new Pane(pane);
      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("MultiPlayer Game has been started");
      scene.getStylesheets().add(getClass().getResource("multigame.css").toExternalForm());
      scene = new Scene(mainPane);
      return scene;
   }

   //method for resetting the playboard
   public void resetBoard(){

      for (int i = 0;i<3;i++){
         for( int j = 0;j< 3;j++){
            cells[i][j].setGraphic(null);
            cells[i][j].setSymbol(' ');
            cells[i][j].setDisable(false);
            cells[i][j].setId("");
         }
      }
   }

   //method for easy console access
   public void addToConsole(String s){
      console.setText(console.getText()+s+"\n\n");
      console.setScrollTop(Double.MAX_VALUE);
   }

   //Update label to display currentplayer
   public void updateTurnLabel() {
      playerTurnLabel.setText("Current turn: "+ model.getCurrentPlayer().getName());
   }

   //Draw Symbols ontop of Cell
   public void drawSymbol(char symbol, Cell c){
      if (symbol == 'X') {

         Group lineGroup = new Group();

         Timeline tl = new Timeline();

         Line line1 = new Line();
         line1.setStartX(20);
         line1.setStartY(20);
         line1.setEndX(20);
         line1.setEndY(20);
         line1.setStrokeWidth(5);

         Line line2 = new Line();
         line2.setStartX(c.getWidth()-20);
         line2.setStartY(20);
         line2.setEndX(c.getWidth()-20);
         line2.setEndY(20);
         line2.setStrokeWidth(5);

         Line line3 = new Line();
         line3.setStartX(c.getWidth()-20);
         line3.setStartY(c.getHeight()-20);
         line3.setEndX(c.getWidth()-20);
         line3.setEndY(c.getHeight()-20);
         line3.setStrokeWidth(5);

         Line line4 = new Line();
         line4.setStartX(20);
         line4.setStartY(c.getHeight()-20);
         line4.setEndX(20);
         line4.setEndY(c.getHeight()-20);
         line4.setStrokeWidth(5);


         tl.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                  new KeyValue(line1.endXProperty(),c.getWidth()/2),
                  new KeyValue(line1.endYProperty(),c.getHeight()/2),
                  new KeyValue(line2.endXProperty(),c.getWidth()/2),
                  new KeyValue(line2.endYProperty(),c.getHeight()/2),
                  new KeyValue(line3.endXProperty(),c.getWidth()/2),
                  new KeyValue(line3.endYProperty(),c.getHeight()/2),
                  new KeyValue(line4.endXProperty(),c.getWidth()/2),
                  new KeyValue(line4.endYProperty(),c.getHeight()/2)
                  ));

         lineGroup.getChildren().addAll(line1,line2,line3,line4);
         tl.play();
         c.setGraphic(lineGroup);
         c.setDisable(true);
         c.setSymbol('X');

      } else if (symbol == 'O') {

         Timeline tl = new Timeline();

         Ellipse ellipse1 = new Ellipse();
         ellipse1.setCenterX(c.getWidth() / 2);
         ellipse1.setCenterY(c.getHeight() / 2);
         ellipse1.setRadiusX(0);
         ellipse1.setRadiusY(0);
         ellipse1.setFill(Color.BLACK);
          //ellipse1.setRadiusX(c.getWidth() / 2 - 20);
          //ellipse1.setRadiusY(c.getHeight() / 2 - 20);

         tl.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                  new KeyValue(ellipse1.radiusXProperty(),c.getWidth() / 2 - 20),
                  new KeyValue(ellipse1.radiusYProperty(),c.getHeight() / 2 - 20)));

         tl.play();
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
       winLine.setStrokeWidth(20);
       mainPane.getChildren().addAll(winLine);
       tl.getKeyFrames().add(new KeyFrame(Duration.millis(1500),
               new KeyValue(winLine.endXProperty(),temp[2].getCenterX()),
               new KeyValue(winLine.endYProperty(),temp[2].getCenterY())));
       tl.play();
      temp[0].setId("win");
      temp[1].setId("win");
      temp[2].setId("win");

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

   public Cell[][] getCells(){ return cells; }

   public Cell getCell(int i, int j){ return cells[i][j]; }

}
