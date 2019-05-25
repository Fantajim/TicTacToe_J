
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MultiView extends View<MultiModel> {

   ServiceLocator serviceLocator;
   private Cell[][] cells;
   private Button backButton;
   private Button restartButton;
   private GridPane grid;
   private BorderPane pane;
   private Label player1Label;
   private Label player2Label;
   private Label playerTurnLabel;
   private TextField chat;
   private Scene scene;
   private Pane mainPane;
   private Line winLine = new Line();
   private Line winLine1 = new Line();
   private Line winLine2 = new Line();
   private Line winLine3 = new Line();
   private Line winLine4 = new Line();
   private SequentialTransition sequence = new SequentialTransition();

   private Group winLineGroup = new Group();
   private Button sendMessage;


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
      grid = model.getGrid();
      cells = model.getCells();


      pane = new BorderPane();
      player1Label = new Label("");
      player2Label = new Label("");
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

      sendMessage = new Button("Send");
      chat = new TextField();
      chat.setPromptText("Write your chat messages here");
      Platform.runLater((Runnable) () -> { model.console.requestFocus();});
      HBox chatBox = new HBox(chat, sendMessage);
      chatBox.setHgrow(chat,Priority.ALWAYS);
      VBox centerBox = new VBox(grid,chatBox);
      pane.setCenter(centerBox);
      pane.setRight(model.console);
      pane.setBottom(playerBox);

      mainPane = new Pane(pane);
      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("MultiPlayer Game has been started");
      scene = new Scene(mainPane);
      scene.getStylesheets().add(getClass().getResource("multigame.css").toExternalForm());
      return scene;
   }

   //method for resetting the playboard
   public void resetBoard(){

      for (int i = 0;i<TicTacToeGame.dimension;i++){
         for( int j = 0;j< TicTacToeGame.dimension;j++){
            cells[i][j].setGraphic(null);
            cells[i][j].setSymbol(' ');
            cells[i][j].setDisable(false);
            cells[i][j].setId("");
         }
      }
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
       ArrayList<Cell> temp = model.getWinnerCombo();
       Timeline tl = new Timeline();
       Timeline tl2 = new Timeline();
       Timeline tl3 = new Timeline();
       Timeline tl4 = new Timeline();


      if (model.isQuad()){
         winLine1.setStartX(temp.get(0).getCenterX());
         winLine1.setStartY(temp.get(0).getCenterY());
         winLine1.setEndX(temp.get(0).getCenterX());
         winLine1.setEndY(temp.get(0).getCenterY());
         winLine1.setStrokeWidth(60 / TicTacToeGame.dimension);

         tl.getKeyFrames().add(new KeyFrame(Duration.millis(1500 / 4),
                 new KeyValue(winLine1.endXProperty(), temp.get(1).getCenterX()),
                 new KeyValue(winLine1.endYProperty(), temp.get(1).getCenterY())));


         winLine2.setStartX(temp.get(1).getCenterX());
         winLine2.setStartY(temp.get(1).getCenterY());
         winLine2.setEndX(temp.get(1).getCenterX());
         winLine2.setEndY(temp.get(1).getCenterY());
         winLine2.setStrokeWidth(60 / TicTacToeGame.dimension);

         tl2.getKeyFrames().add(new KeyFrame(Duration.millis(1500 / 4),
                 new KeyValue(winLine2.endXProperty(), temp.get(2).getCenterX()),
                 new KeyValue(winLine2.endYProperty(), temp.get(2).getCenterY())));


         winLine3.setStartX(temp.get(2).getCenterX());
         winLine3.setStartY(temp.get(2).getCenterY());
         winLine3.setEndX(temp.get(2).getCenterX());
         winLine3.setEndY(temp.get(2).getCenterY());
         winLine3.setStrokeWidth(60 / TicTacToeGame.dimension);

         tl3.getKeyFrames().add(new KeyFrame(Duration.millis(1500 / 4),
                 new KeyValue(winLine3.endXProperty(), temp.get(3).getCenterX()),
                 new KeyValue(winLine3.endYProperty(), temp.get(3).getCenterY())));

         winLine4.setStartX(temp.get(3).getCenterX());
         winLine4.setStartY(temp.get(3).getCenterY());
         winLine4.setEndX(temp.get(3).getCenterX());
         winLine4.setEndY(temp.get(3).getCenterY());
         winLine4.setStrokeWidth(60 / TicTacToeGame.dimension);

         tl4.getKeyFrames().add(new KeyFrame(Duration.millis(1500 / 4),
                 new KeyValue(winLine4.endXProperty(), temp.get(0).getCenterX()),
                 new KeyValue(winLine4.endYProperty(), temp.get(0).getCenterY())));

         winLineGroup.getChildren().addAll(winLine1,winLine2,winLine3,winLine4);
         model.setIsQuad(false);
         sequence.getChildren().addAll(tl,tl2,tl3,tl4);
      }

      else {
         winLine.setStartX(temp.get(0).getCenterX());
         winLine.setStartY(temp.get(0).getCenterY());
         winLine.setEndX(temp.get(0).getCenterX());
         winLine.setEndY(temp.get(0).getCenterY());
         winLine.setStrokeWidth(60 / TicTacToeGame.dimension);
         winLineGroup.getChildren().add(winLine);


         tl.getKeyFrames().add(new KeyFrame(Duration.millis(1500),
                 new KeyValue(winLine.endXProperty(), temp.get(temp.size() - 1).getCenterX()),
                 new KeyValue(winLine.endYProperty(), temp.get(temp.size() - 1).getCenterY())));



         sequence.getChildren().addAll(tl);
         }

      mainPane.getChildren().addAll(winLineGroup);

       sequence.play();

       for(Cell c:temp){
          c.setId("win");
       }
       model.getWinnerCombo().clear();
   }

   public void updatePlayerSymbols(){
      Platform.runLater(()-> {
         player1Label.setText(model.player1.getName() + " = "+ model.player1.getSymbol());
         player2Label.setText(model.player2.getName() + " = "+ model.player2.getSymbol());
      });
   }

   public Button getBackButton(){ return backButton; }

   public Button getRestartButton(){ return restartButton; }

   public void removeLine(){
      mainPane.getChildren().remove(winLineGroup);
      winLineGroup.getChildren().clear();
      sequence.getChildren().clear();


   }

   public Cell[][] getCells(){ return cells; }

   public Cell getCell(int i, int j){ return cells[i][j]; }

   public TextField getChat(){ return chat;}

   public Button getSendMessage() { return sendMessage;}

}
