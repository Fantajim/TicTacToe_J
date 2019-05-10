import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_View extends View<App_Model> {
    ServiceLocator serviceLocator;
    private MainMenu mainmenu;
    private App_Model model;
    private Stage stage;

	public App_View(Stage stage, App_Model model) {
        super(stage, model);
        stage.setTitle("TicTacToe");
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application view initialized");
    }

	@Override
	protected Scene create_GUI() {

        mainmenu = new MainMenu();

        return mainmenu.scene;
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

    public Button getBackSingle(){ return mainmenu.backButtonSingle; }

    public Button getBackMulti(){ return mainmenu.backButtonMulti; }

    public Button getClassic() {return mainmenu.classicButton;}

    public MainMenu getMainmenu(){
        return mainmenu;
    }


}