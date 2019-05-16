/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class MainMenuController extends Controller<MainMenuModel, MainMenuView> {
    ServiceLocator serviceLocator;

    public MainMenuController(MainMenuModel model, MainMenuView view) {
        super(model, view);

        view.getClassicSingle().setOnAction(event -> startSingle());
        view.getMultiPlayer().setOnAction(event -> startMulti());
        view.getBackSingle().setOnAction(event -> closeSingle());
        view.getBackMulti().setOnAction(event -> closeMulti());
        view.getStartSingleGame().setOnAction(event -> startClassic());
        view.getAi_Button().setOnAction(event -> toggleAI());
        view.getDifficulty_Button().setOnAction(event -> toggleDifficulty());
        view.getHostButton().setOnAction(event -> startHostMenu());
        view.getBackButtonHost().setOnAction(event -> closeHostMenu());
        view.getClientButton().setOnAction(event -> expandClientMenu());
        view.getBackButtonClient().setOnAction(event -> closeClientMenu());

        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MainMenu controller initialized");


    }

    private void startSingle(){ view.expandSingle(); }
    private void startMulti(){ view.expandMulti();}
    private void startHostMenu(){view.expandHost();}
    private void closeSingle(){ view.shrinkSingle(); }
    private void closeMulti(){ view.shrinkMulti(); }
    private void closeHostMenu(){view.shrinkHost();}
    private void expandClientMenu(){view.expandClient();}
    private void closeClientMenu(){view.shrinkClient();}

    private void startClassic(){ TicTacToeGame.getMainProgram().startClassicSingle();}
    private void toggleAI(){view.toggleAI();}
    private void toggleDifficulty(){view.toggleDifficulty();}

}