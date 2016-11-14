/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.Alert;

/**
 *
 * @author Project1
 */
public class Dialogs {

	/**
	 * Tools class for dialogs
	 * 
	 * @param e 
	 */	
	public static void showErrorDialog(Throwable e) {
		e.printStackTrace();
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error occured");
		alert.setHeaderText(e.getClass().getSimpleName());
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
	
}
