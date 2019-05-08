public class MainMenuController {

    private MainMenuModel model;
    private MainMenuView view;

    public MainMenuController(MainMenuModel model, MainMenuView view) {
        this.model = model;
        this.view = view;

        view.getSinglePlayer().setOnAction(event -> startSingle());
        view.getMultiPlayer().setOnAction(event -> startMulti());
        view.getOptions().setOnAction(event -> openOptions());
    }

    private void startSingle(){
    view.getMainmenu().expandSingle();
    }
    private void startMulti(){

    }

    private void openOptions(){

    }
}

