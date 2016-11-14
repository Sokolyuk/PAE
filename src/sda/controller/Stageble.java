/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sda.controller;

import javafx.stage.Stage;

/**
 * Interface for assigment a prime stage to controller
 * 
 * @author Dmitry Sokolyuk 2016
 */
public interface Stageble {
	/**
	 * Assign prime stage to new controller
	 * 
	 * @param stage 
	 */
	void setStage(Stage stage);
}
