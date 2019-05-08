import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu{

    private Stage stage;

    Button singlePlayer;
    Button multiPlayer;
    Button options;
    private HBox hbox1;
    Scene scene;

    public MainMenu () {

       // mainPane = new GridPane();
        VBox vbox1 = new VBox();
        hbox1 = new HBox();
        singlePlayer = new Button("Singleplayer");
        multiPlayer = new Button("Multiplayer");
        options = new Button("Options");
        Label title = new Label("TicTacToe Main Menu");
        singlePlayer.setId("MainButton");
        multiPlayer.setId("MainButton");
        options.setId("MainButton");
        title.setId("MainLabel");

        vbox1.getChildren().addAll(title, singlePlayer, multiPlayer, options);
        hbox1.getChildren().add(vbox1);

        scene = new Scene(hbox1);
        scene.getStylesheets().add(getClass().getResource("TttStyle.css").toExternalForm());


    }

    public void expandSingle(){
        //mainPane.setPrefWidth(600);

        VBox vbox2 = new VBox();
        Label test = new Label("test");
        test.setMinSize(100,100);
        vbox2.getChildren().add(test);
        vbox2.setAlignment(Pos.BOTTOM_CENTER);
        hbox1.getChildren().add(vbox2);

    }

    public void expandMulti(){

    }

    public void expandOptions(){

    }

}
