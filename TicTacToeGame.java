import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
//TODO AI can be tricked when using mirrored corners, especially hard difficulty since it chooses always the middle
//TODO Server doesnt properly close jawaw.exe
//Todo Let AI take over and implement autoplay


public class TicTacToeGame extends Application {
    private static TicTacToeGame mainProgram; // singleton
    private Splash_View splashView;
    private MainMenuView view;
    private SingleView viewSingle;
    private MultiView viewMulti;

    public static boolean cpuPlayer = true;
    public static String cpuDifficulty = "Difficulty: medium";
    public static boolean isServer = false;
    public static String serverIP ="";
    public static int clientServerPort = 55555;
    public static int serverPort = 55555;
    public static int dimension = 3;

    private ServiceLocator serviceLocator; // resources, after initialization

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Note: This method is called on the main thread, not the JavaFX
     * Application Thread. This means that we cannot display anything to the
     * user at this point. Since we want to show a splash screen, this means
     * that we cannot do any real initialization here.
     * 
     * This implementation ensures that the application is a singleton; only one
     * per JVM-instance. On client installations this is not necessary (each
     * application runs in its own JVM). However, it can be important on server
     * installations.
     * 
     * Why is it important that only one instance run in the JVM? Because our
     * initialized resources are a singleton - if two programs instances were
     * running, they would use (and overwrite) each other's resources!
     */
    @Override
    public void init() {
        if (mainProgram == null) {
            mainProgram = this;
        } else {
            Platform.exit();
        }
    }

    /**
     * This method is called after init(), and is called on the JavaFX
     * Application Thread, so we can display a GUI. We have two GUIs: a splash
     * screen and the application. Both of these follow the MVC model.
     * 
     * We first display the splash screen. The model is where all initialization
     * for the application takes place. The controller updates a progress-bar in
     * the view, and (after initialization is finished) calls the startApp()
     * method in this class.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create and display the splash screen and model
        Splash_Model splashModel = new Splash_Model();
        splashView = new Splash_View(primaryStage, splashModel);
        new Splash_Controller(this, splashModel, splashView);
        splashView.start();

        // Display the splash screen and begin the initialization
        splashModel.initialize();
    }

    /**
     * This method is called when the splash screen has finished initializing
     * the application. The initialized resources are in a ServiceLocator
     * singleton. Our task is to now create the application MVC components, to
     * hide the splash screen, and to display the application GUI.
     * 
     * Multitasking note: This method is called from an event-handler in the
     * Splash_Controller, which means that it is on the JavaFX Application
     * Thread, which means that it is allowed to work with GUI components.
     * http://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
     */



    public void startApp() {
        Stage appStage = new Stage();

        // Initialize the application MVC components. Note that these components
        // can only be initialized now, because they may depend on the
        // resources initialized by the splash screen
        MainMenuModel model = new MainMenuModel();
        view = new MainMenuView(appStage, model);
        new MainMenuController(model, view);

        // Resources are now initialized
        serviceLocator = ServiceLocator.getServiceLocator();

        // Close the splash screen, and set the reference to null, so that all
        // Splash_XXX objects can be garbage collected
        splashView.stop();
        splashView = null;

        view.start();
    }

    public void startMultiPlayer(){

        Stage multiStage = new Stage();

        MultiModel modelMulti = new MultiModel();
        viewMulti = new MultiView(multiStage,modelMulti);
        new MultiController(modelMulti, viewMulti);

        serviceLocator = ServiceLocator.getServiceLocator();

        view.stop();
        view = null;

        viewMulti.start();
    }

    public void startSinglePlayer(){

        Stage singleStage = new Stage();

        SingleModel modelSingle = new SingleModel();
        viewSingle = new SingleView(singleStage,modelSingle);
        new SingleController(modelSingle, viewSingle);

        serviceLocator = ServiceLocator.getServiceLocator();

        view.stop();
        view = null;

        viewSingle.start();
    }

    public void startMainMenu() {
        Stage appStage = new Stage();

        // Initialize the application MVC components. Note that these components
        // can only be initialized now, because they may depend on the
        // resources initialized by the splash screen
        MainMenuModel model = new MainMenuModel();
        view = new MainMenuView(appStage, model);
        new MainMenuController(model, view);

        // Resources are now initialized
        serviceLocator = ServiceLocator.getServiceLocator();

        // Close the splash screen, and set the reference to null, so that all
        // Splash_XXX objects can be garbage collected
        if (viewSingle == null){
            viewMulti.stop();
            viewMulti = null;
            view.start();

        } else if (viewMulti == null) {
            viewSingle.stop();
            viewSingle = null;

            view.start();
        }


    }




    /**
     * The stop method is the opposite of the start method. It provides an
     * opportunity to close down the program, including GUI components. If the
     * start method has never been called, the stop method may or may not be
     * called.
     * 
     * Make the GUI invisible first. This prevents the user from taking any
     * actions while the program is ending.
     */
    @Override
    public void stop() {
        if (view != null) {
            // Make the view invisible
            view.stop();
        }

        // More cleanup code as needed

        serviceLocator.getLogger().info("Application terminated");
    }

    // Static getter for a reference to the main program object
    protected static TicTacToeGame getMainProgram() {
        return mainProgram;
    }
    protected static boolean getCpuPlayer(){ return cpuPlayer;}
    protected static String getCpuDifficulty(){return cpuDifficulty;}
}
