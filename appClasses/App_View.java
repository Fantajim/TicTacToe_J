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
    private MainMenu mainMenu;
    private ClassicSingle classicSingle;
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

        mainMenu = new MainMenu();

        return mainMenu.scene;
	}

    public Button getClassicSingle() {
        return mainMenu.singlePlayer;
    }

    public Button getMultiPlayer(){
        return mainMenu.multiPlayer;
    }

    public Button getOptions(){
        return mainMenu.options;
    }

    public Button getBackSingle(){ return mainMenu.backButtonSingle; }

    public Button getBackMulti(){ return mainMenu.backButtonMulti; }

    public Button getClassic() {return mainMenu.classicButton;}

    public MainMenu getMainmenu(){
        return mainMenu;
    }


}