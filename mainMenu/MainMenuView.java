import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuView {

    private MainMenu mainmenu;
    private MainMenuModel model;
    private Stage stage;

    public MainMenuView(Stage stage, MainMenuModel model) {
        this.model = model;
        this.stage = stage;

        mainmenu = new MainMenu();

        stage.setTitle("Main Menu");
        stage.setScene(mainmenu.scene);
        stage.show();

    }

    public Button getSinglePlayer() {
        return mainmenu.singlePlayer;
    }

    public Button getMultiPlayer(){
        return mainmenu.multiPlayer;
    }

    public Button getOptions(){
        return mainmenu.options;
    }

    public MainMenu getMainmenu(){
        return mainmenu;
    }
}
