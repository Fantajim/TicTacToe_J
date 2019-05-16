/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class MainMenuModel extends Model {
    ServiceLocator serviceLocator;
    private int value;
    
    public MainMenuModel() {

        TicTacToeGame.cpuPlayer = true;
        TicTacToeGame.cpuDifficulty = "default";
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MainMenu model initialized");
    }



}
