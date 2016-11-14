/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sda.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Abstract super class for all controllers
 *
 * @author Dmitry Sokolyuk 2016
 */
public abstract class MyController implements Initializable, Stageble {
	
	protected Stage stage;
	
	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Create new scene
	 * 
	 * @param stage prime stage
	 * @param location absolute path to FXML
	 * @return new scene
	 * @throws Exception 
	 */
	public static Scene loadSceneContent(Stage stage, String fxml) throws IOException {
		FXMLLoader fXMLLoader = new FXMLLoader(MyController.class.getResource(fxml));
		Parent parent = fXMLLoader.load();
		Object controller = fXMLLoader.getController();
		if (controller instanceof Stageble) {
			((Stageble)controller).setStage(stage);
		}
		//animation
		FadeTransition ft = new FadeTransition(Duration.millis(1300), parent);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		//..
		return new Scene(parent);
    }
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
	
}
