/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sda.controller;

import java.io.BufferedWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import utils.CsvReader;
import static utils.CsvReader.SEPARATOR;
import static utils.Dialogs.showErrorDialog;

/**
 * FXML Controller class of table
 *
 * @author Dmitry Sokolyuk 2016
 */
public class TableController extends MyController {

	@FXML
	private TableView tableview;

	@FXML
	private MenuItem menuItemSaveAs;
	
	@FXML
	private Button clearData;
	
	@FXML
	private Button deleteRow;
	
	private final ObservableList<Map> data = FXCollections.observableArrayList();
	
	@FXML
	private void handleExitAction(ActionEvent event) {
		Timeline timeline = new Timeline();
		KeyFrame key = new KeyFrame(Duration.millis(1300), new KeyValue (stage.getScene().getRoot().opacityProperty(), 0)); 
		timeline.getKeyFrames().add(key);   
		timeline.setOnFinished((ae) -> Platform.exit()); 
		timeline.play();
	}

	@FXML
	private void handleAboutAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("Probeaufgabe Entwicklung.");
		alert.setContentText("Dmitry Sokolyuk 2016.");
		alert.showAndWait();
	}

	@FXML
	private void handleOpenAction(ActionEvent event) {
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open");
			fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("CSV file", "*.csv"));
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) openTable(selectedFile);
		}catch(Exception e){
			showErrorDialog(e);
		}
	}

	@FXML
	private void handleSaveAsAction(ActionEvent event) {
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save As");
			fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("CSV file", "*.csv"));
			File selectedFile = fileChooser.showSaveDialog(stage);
			if (selectedFile != null) saveTable(selectedFile);
		}catch(Exception e){
			showErrorDialog(e);
		}
	}

	/**
	 * I need more time to do it
	 * 
	 * @param event 
	 */
	@FXML
	private void handleAddRowAction(ActionEvent event) {
		Map<String, String> dataRow = new HashMap<>();
		dataRow.put("1", "1");
		dataRow.put("2", "2");
		dataRow.put("3", "3");
		data.add(dataRow);
	}

	@FXML
	private void handleDeleteRowAction(ActionEvent event) {
		data.remove(tableview.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void handleClearDataAction(ActionEvent event) {
		data.clear();
	}

	private static TableColumn<Map, String> getTableColumn(String property, String text) {
		return getTableColumn(property, text, 140);
	}
	
	/**
	 * Create new column by internal name and title
	 * 
	 * @param fieldName internal name of field
	 * @param fieldTitle title of field
	 * @param minWidth column's min width
	 * @return object of column
	 */
	private static TableColumn<Map, String> getTableColumn(String fieldName, String fieldTitle, double minWidth) {
		//instance of new column
		TableColumn<Map, String> column = new TableColumn<>(fieldTitle);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(new MapValueFactory(fieldName));
		//converter
		Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap =
			(TableColumn<Map, String> p) -> (
				new TextFieldTableCell(new StringConverter() {
					@Override
                    public String toString(Object t) {
						return t==null?"null":t.toString();
                    }
                    @Override
					public Object fromString(String string) {
						return string;
					}
				})
			);
        column.setCellFactory(cellFactoryForMap);
		//assign edited text from cell to data
		column.setOnEditCommit((CellEditEvent<Map, String> t) -> {
			((Map<String, String>)t.getTableView().getItems().get(t.getTablePosition().getRow())).put(fieldName, t.getNewValue());
        });
		return column;
	}
	
	protected void openTable(File file) throws Exception {
		//csvReader
		CsvReader csvReader = new CsvReader(file);
		//load new structure to columnsStructure
		List<String> columnsStructure = csvReader.readHeader();
		//..
		ObservableList<TableColumn<Map, String>> columns = tableview.getColumns();
		//clear old data
		data.clear();
		//clear columns structure
		columns.clear();
		//assign new structure
		final AtomicInteger counter = new AtomicInteger();//field's index
		columnsStructure.forEach(col->columns.add(getTableColumn(String.valueOf(counter.incrementAndGet()), col)));
		//assign rows from CSV file to dava variable
		csvReader.readRecords().forEach((List<String> row)->{
			counter.set(0);
			Map<String, String> dataRow = new HashMap<>();
			row.forEach(cell->{dataRow.put(String.valueOf(counter.incrementAndGet()), cell);});
			data.add(dataRow);
		});
		//assign data
		tableview.setItems(data);
		//enable "save as"
		menuItemSaveAs.setDisable(false);
		clearData.setDisable(false);
		deleteRow.setDisable(false);
	}

	/**
	 * To avoid checked exception in foreach
	 * 
	 * @param out
	 * @param v 
	 */	
	private void outAppend(Writer out, String v) {
		try{
			out.append(v);
		}catch(IOException e){
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Save data to CSV
	 * 
	 * @param file
	 * @throws Exception 
	 */
	protected void saveTable(File file) throws Exception {
		try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"))) {
			//..
			ObservableList<TableColumn<Map, String>> columns = tableview.getColumns();
			//save header to CSV
			final AtomicBoolean isFirst = new AtomicBoolean(true);//first separator
			columns.forEach(itm->outAppend(out, String.format("%s%s", isFirst.getAndSet(false)?"":SEPARATOR, itm.getText())));
			outAppend(out, "\n");
			//save data to CSV
			data.forEach(map->{
				isFirst.set(true);//set flag to first
				map.forEach((k,v)->outAppend(out, String.format("%s%s", isFirst.getAndSet(false)?"":SEPARATOR, v)));//write row
				outAppend(out, "\n");//new line after row
			});
		}
	}
	
}
