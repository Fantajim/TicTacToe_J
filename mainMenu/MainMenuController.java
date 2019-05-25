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

        view.getClassicSingle().setOnAction(event -> expandSingle());
        view.getMultiPlayer().setOnAction(event -> expandMulti());
        view.getBackSingle().setOnAction(event -> closeSingle());
        view.getBackMulti().setOnAction(event -> closeMulti());
        view.getStartSingleGame().setOnAction(event -> startSinglePlayer());
        view.getAi_Button().setOnAction(event -> toggleAI());
        view.getDifficulty_Button().setOnAction(event -> toggleDifficulty());
        view.getHostButton().setOnAction(event -> expandHostMenu());
        view.getBackButtonHost().setOnAction(event -> closeHostMenu());
        view.getClientButton().setOnAction(event -> expandClientMenu());
        view.getBackButtonClient().setOnAction(event -> closeClientMenu());
        view.getGridSize().setOnAction(event -> view.toggleGrid());
        view.getStartClient().setOnAction(event -> {
            TicTacToeGame.isServer = false;
            TicTacToeGame.clientServerPort = Integer.parseInt(view.getClientPortField().getText());
            TicTacToeGame.serverIP = view.getIpField().getText();
            startMultiPlayer();
        });
        view.getStartServer().setOnAction(event -> {
            TicTacToeGame.isServer = true;
            TicTacToeGame.serverPort = Integer.parseInt(view.getServerPortField().getText());
            startMultiPlayer();
        });


        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MainMenu controller initialized");


    }

    private void expandSingle(){ view.expandSingle(); }
    private void expandMulti(){ view.expandMulti();}
    private void expandHostMenu(){view.expandHost();}
    private void closeSingle(){ view.shrinkSingle(); }
    private void closeMulti(){ view.shrinkMulti(); }
    private void closeHostMenu(){view.shrinkHost();}
    private void expandClientMenu(){view.expandClient();}
    private void closeClientMenu(){view.shrinkClient();}
    private void startSinglePlayer(){
        TicTacToeGame.dimension = 3;
        TicTacToeGame.getMainProgram().startSinglePlayer();
    }
    private void toggleAI(){view.toggleAI();}
    private void toggleDifficulty(){view.toggleDifficulty();}
    private void startMultiPlayer(){ TicTacToeGame.getMainProgram().startMultiPlayer(); }

}
