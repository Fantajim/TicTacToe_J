import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu {

    private Stage stage;

    ServiceLocator serviceLocator;
    Button singlePlayer;
    Button multiPlayer;
    Button options;
    Button backButtonSingle;
    Button backButtonMulti;
    Button classicButton;
    Button hostButton;
    Button clientButton;
    private BorderPane mainPane;
    private VBox vbox1;
    private VBox vbox2;
    private VBox vbox3;
    Scene scene;

    public MainMenu() {

        mainPane = new BorderPane();
        vbox1 = new VBox();
        singlePlayer = new Button("Singleplayer");
        multiPlayer = new Button("Multiplayer");
        options = new Button("Options");
        backButtonSingle = new Button("Return to previous menu");
        backButtonMulti = new Button("Return to previous menu");
        classicButton = new Button("Classic");
        hostButton = new Button("Host");
        clientButton = new Button("Client");
        backButtonSingle.setId("backButton");
        backButtonMulti.setId("backButton");



        Label title = new Label("TicTacToe Main Menu");
        title.setId("MainLabel");

        vbox1.getChildren().addAll(singlePlayer, multiPlayer, options);
        mainPane.setCenter(vbox1);
        mainPane.setTop(title);

        scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Main Menu has been created");

    }

    public void expandSingle(){

        vbox2 = new VBox();
        Region spacer1 = new Region();
        vbox2.setVgrow(spacer1, Priority.ALWAYS);
        vbox2.getChildren().addAll(classicButton,spacer1, backButtonSingle);
        vbox2.setAlignment(Pos.CENTER);

        TranslateTransition slide = new TranslateTransition(Duration.millis(300),vbox1);
        slide.setFromX(0);
        slide.setByX(-400);

        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(vbox2);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300),vbox2);
            slide2.setFromX(400);
            slide2.setByX(-400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }

    public void shrinkSingle(){
        TranslateTransition slide = new TranslateTransition(Duration.millis(300),vbox2);
        slide.setFromX(0);
        slide.setByX(400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(vbox1);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300),vbox1);
            slide2.setFromX(-400);
            slide2.setByX(400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();

    }

    public void expandMulti(){

        vbox3 = new VBox();
        vbox3.getChildren().addAll(hostButton,clientButton,backButtonMulti);
        vbox3.setAlignment(Pos.CENTER);

        TranslateTransition slide = new TranslateTransition(Duration.millis(300),vbox1);
        slide.setFromX(0);
        slide.setByX(-400);

        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(vbox3);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300),vbox3);
            slide2.setFromX(400);
            slide2.setByX(-400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }

    public void shrinkMulti(){
        TranslateTransition slide = new TranslateTransition(Duration.millis(300),vbox3);
        slide.setFromX(0);
        slide.setByX(400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(vbox1);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300),vbox1);
            slide2.setFromX(-400);
            slide2.setByX(400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();

    }

    public void expandOptions(){ }

}
