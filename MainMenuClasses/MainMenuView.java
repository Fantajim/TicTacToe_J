import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class MainMenuView extends View<MainMenuModel> {
    ServiceLocator serviceLocator;
    private Button singlePlayer;
    private Button multiPlayer;
    private Button startSingleGame;
    private Button startServer;
    private Button startClient;
    private Button hostButton;
    private Button clientButton;
    private Button backButtonSingle;
    private Button backButtonMulti;
    private Button backButtonHost;
    private Button backButtonClient;
    private BorderPane mainPane;
    private Button ai_Button;
    private Button difficulty_Button;
    private VBox rootMenu;
    private VBox singlePlayerMenu;
    private VBox multiPlayerMenu;
    private VBox serverMenu;
    private VBox clientMenu;


	public MainMenuView(Stage stage, MainMenuModel model) {
        super(stage, model);
        stage.setTitle("TicTacToe");
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MainMenu view initialized");
    }

	@Override
	protected Scene create_GUI() {

        mainPane = new BorderPane();
        rootMenu = new VBox();
        singlePlayer = new Button("Singleplayer");
        multiPlayer = new Button("Multiplayer");
        backButtonSingle = new Button("Return to previous menu");
        backButtonMulti = new Button("Return to previous menu");
        backButtonHost = new Button("Return to previous menu");
        backButtonClient = new Button("Return to previous menu");
        backButtonHost.setId("backButton");
        backButtonSingle.setId("backButton");
        backButtonMulti.setId("backButton");
        backButtonClient.setId("backButton");
        startSingleGame = new Button("Start");
        startServer = new  Button("Start Server");
        startClient = new Button("Start Client");
        hostButton = new Button("Host");
        clientButton = new Button("Client");
        ai_Button = new Button("AI: on");
        difficulty_Button = new Button ("Difficulty: hard");


        Label title = new Label("TicTacToe Main Menu");
        title.setId("MainLabel");

        rootMenu.getChildren().addAll(singlePlayer, multiPlayer);
        mainPane.setCenter(rootMenu);
        mainPane.setTop(title);

        scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());

        return scene;
	}

    public void expandClient(){

	    clientMenu = new VBox();
	    Label portLabel = new Label("Port  ");
	    TextField portField = new TextField("55555");
	    HBox portBox = new HBox(portLabel,portField);
	    portBox.setAlignment(Pos.CENTER);

	    Label ipLabel = new Label("IP:    ");
	    TextField ipField = new TextField("enter IP address here");
	    HBox ipBox = new HBox(ipLabel,ipField);
	    ipBox.setAlignment(Pos.CENTER);
	    Region clientSpacer = new Region();
        Region clientSpacer2 = new Region();
	    clientSpacer.setPrefHeight(50);
	    clientSpacer2.setPrefHeight(50);
	    clientMenu.setAlignment(Pos.CENTER);

	    clientMenu.getChildren().addAll(startClient,clientSpacer2,ipBox,portBox,clientSpacer,backButtonClient);

        TranslateTransition slide = new TranslateTransition(Duration.millis(300), multiPlayerMenu);
        slide.setFromX(0);
        slide.setByX(-400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(clientMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), clientMenu);
            slide2.setFromX(400);
            slide2.setByX(-400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }


    public void expandHost(){
	    serverMenu = new VBox();
        Label portLabel = new Label("Port:  ");
        TextField portField = new TextField("55555");
        HBox portBox = new HBox(portLabel,portField);
        portBox.setAlignment(Pos.CENTER);
        Region hostSpacer = new Region();
        Region hostSpacer2 = new Region();
        hostSpacer.setPrefHeight(60);
        hostSpacer2.setPrefHeight(60);
        serverMenu.setAlignment(Pos.CENTER);

        serverMenu.getChildren().addAll(startServer,hostSpacer,portBox,hostSpacer2, backButtonHost);

        TranslateTransition slide = new TranslateTransition(Duration.millis(300), multiPlayerMenu);
        slide.setFromX(0);
        slide.setByX(-400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(serverMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), serverMenu);
            slide2.setFromX(400);
            slide2.setByX(-400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }

    public void shrinkClient(){
        TranslateTransition slide = new TranslateTransition(Duration.millis(300), clientMenu);
        slide.setFromX(0);
        slide.setByX(400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(multiPlayerMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), multiPlayerMenu);
            slide2.setFromX(-400);
            slide2.setByX(400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }

    public void shrinkHost(){

        TranslateTransition slide = new TranslateTransition(Duration.millis(300), serverMenu);
        slide.setFromX(0);
        slide.setByX(400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(multiPlayerMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), multiPlayerMenu);
            slide2.setFromX(-400);
            slide2.setByX(400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }



    public void expandSingle(){

        singlePlayerMenu = new VBox();
        Region spacer1 = new Region();
        singlePlayerMenu.setVgrow(spacer1, Priority.ALWAYS);
        singlePlayerMenu.getChildren().addAll(startSingleGame,ai_Button,difficulty_Button,spacer1, backButtonSingle);
        singlePlayerMenu.setAlignment(Pos.CENTER);

        TranslateTransition slide = new TranslateTransition(Duration.millis(300), rootMenu);
        slide.setFromX(0);
        slide.setByX(-400);

        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(singlePlayerMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), singlePlayerMenu);
            slide2.setFromX(400);
            slide2.setByX(-400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }

    public void shrinkSingle(){
        TranslateTransition slide = new TranslateTransition(Duration.millis(300), singlePlayerMenu);
        slide.setFromX(0);
        slide.setByX(400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(rootMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), rootMenu);
            slide2.setFromX(-400);
            slide2.setByX(400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();

    }

    public void expandMulti(){

        multiPlayerMenu = new VBox();
        multiPlayerMenu.getChildren().addAll(hostButton,clientButton,backButtonMulti);
        multiPlayerMenu.setAlignment(Pos.CENTER);

        TranslateTransition slide = new TranslateTransition(Duration.millis(300), rootMenu);
        slide.setFromX(0);
        slide.setByX(-400);

        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(multiPlayerMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), multiPlayerMenu);
            slide2.setFromX(400);
            slide2.setByX(-400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();
    }

    public void shrinkMulti(){
        TranslateTransition slide = new TranslateTransition(Duration.millis(300), multiPlayerMenu);
        slide.setFromX(0);
        slide.setByX(400);
        SequentialTransition sequence = new SequentialTransition(slide);
        sequence.setOnFinished(event -> {
            mainPane.setCenter(null);
            mainPane.setCenter(rootMenu);
            TranslateTransition slide2 = new TranslateTransition(Duration.millis(300), rootMenu);
            slide2.setFromX(-400);
            slide2.setByX(400);
            SequentialTransition sequence2 = new SequentialTransition(slide2);
            sequence2.play();
        });
        sequence.play();

    }

    public void toggleDifficulty(){
	    if(difficulty_Button.getText() == "Difficulty: hard"){
	        difficulty_Button.setText("Difficulty: easy");
	        TicTacToeGame.cpuDifficulty = "easy";
        }
	    else if(difficulty_Button.getText() == "Difficulty: easy"){
	        difficulty_Button.setText("Difficulty: medium");
	        TicTacToeGame.cpuDifficulty = "medium";
        }
        else if(difficulty_Button.getText() == "Difficulty: medium"){
            difficulty_Button.setText("Difficulty: hard");
            TicTacToeGame.cpuDifficulty = "hard";
        }
    }

    public void toggleAI(){
        if(ai_Button.getText() == "AI: on"){
            ai_Button.setText("AI: off");
            TicTacToeGame.cpuPlayer = false;
        }
        else if(ai_Button.getText() == "AI: off"){
            ai_Button.setText("AI: on");
            TicTacToeGame.cpuPlayer = true;

        }
    }

    public Button getClassicSingle() { return singlePlayer; }

    public Button getMultiPlayer(){ return multiPlayer; }

    public Button getBackSingle(){ return backButtonSingle; }

    public Button getBackMulti(){ return backButtonMulti; }

    public Button getStartSingleGame() {return startSingleGame;}

    public Button getAi_Button(){ return ai_Button;}

    public Button getDifficulty_Button(){ return difficulty_Button;}

    public Button getBackButtonHost(){ return backButtonHost;}

    public Button getHostButton(){return hostButton;}

    public Button getBackButtonClient(){return backButtonClient;}

    public Button getClientButton(){ return clientButton;}

    public Button getStartClient(){return startClient;}

    public Button getStartServer(){return startServer;}
}


