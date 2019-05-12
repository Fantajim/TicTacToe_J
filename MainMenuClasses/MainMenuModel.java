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

        //create Cells
        //create Grid
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MainMenu model initialized");
    }



}
