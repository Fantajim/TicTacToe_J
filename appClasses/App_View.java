import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
    Label lblNumber;
    Button btnClick;

	public App_View(Stage stage, App_Model model) {
        super(stage, model);
        stage.setTitle("JavaFX Application Template");
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application view initialized");
    }

	@Override
	protected Scene create_GUI() {
		
		GridPane root = new GridPane();
		lblNumber = new Label();
        lblNumber.setText(Integer.toString(model.getValue()));
        lblNumber.setMinWidth(200);
        lblNumber.setAlignment(Pos.BASELINE_CENTER);
        root.add(lblNumber, 0, 0);
        
        btnClick = new Button();
        btnClick.setText("Click Me!");
        btnClick.setMinWidth(200);
        root.add(btnClick, 0, 1);
		
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("app.css").toExternalForm());
        return scene;
	}
}