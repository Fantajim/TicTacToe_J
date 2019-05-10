/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Controller extends Controller<App_Model, App_View> {
    ServiceLocator serviceLocator;

    public App_Controller(App_Model model, App_View view) {
        super(model, view);

        view.getClassicSingle().setOnAction(event -> startSingle());
        view.getMultiPlayer().setOnAction(event -> startMulti());
        view.getOptions().setOnAction(event -> openOptions());
        view.getBackSingle().setOnAction(event -> closeSingle());
        view.getBackMulti().setOnAction(event -> closeMulti());
        view.getClassic().setOnAction(event -> startClassic());
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application controller initialized");
    }

    private void startSingle(){ view.getMainmenu().expandSingle(); }
    private void startMulti(){ view.getMainmenu().expandMulti();}
    private void closeSingle(){ view.getMainmenu().shrinkSingle(); }
    private void closeMulti(){ view.getMainmenu().shrinkMulti(); }
    private void openOptions(){ }
    private void startClassic(){ view.startClassicSingle();};
}
