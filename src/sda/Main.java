/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sda;

import javafx.application.Application;
import javafx.stage.Stage;
import sda.controller.MyController;
import static utils.Dialogs.showErrorDialog;

/**
 *
 * @author Dmitry Sokolyuk 2016
 */
public class Main extends Application {
	
	private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Probeaufgabe Entwicklung. Dmitry Sokolyuk 2016.");
		stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
		stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
		//..
		try {
			stage.setScene(MyController.loadSceneContent(stage, "/sda/view/table.fxml"));
			stage.show();
		} catch (Exception e) {
            showErrorDialog(e);
        }
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
