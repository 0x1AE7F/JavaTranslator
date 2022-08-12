package de.hgbp.denner.julian.translatorgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class TranslatorController {
    @FXML
    private TableView translationTable;
    @FXML
    private TextField inputField;

    boolean tableInitialized = false;

    private final ObservableList<Translation> data =
            FXCollections.observableArrayList(
                    new Translation("", "")
            );


    @FXML
    protected void translate_text() {
        Translation translation = new Translation("Hallo, Welt!", "Hello, World!");
        // System.out.println(inputField.getText()); <-- Debug
        data.add(new Translation(inputField.getText().strip(), Translator.translate_string(inputField.getText().strip())));
        translationTable.setItems(data);

    }
    @FXML
    protected void init_table() {
        if (!tableInitialized) {
            TableColumn originalCol = new TableColumn("Original");
            TableColumn translationCol = new TableColumn("Translation");
            originalCol.setCellValueFactory(
                    new PropertyValueFactory<Translation, String>("original"));
            translationCol.setCellValueFactory(
                    new PropertyValueFactory<Translation, String>("translation"));
            translationTable.getColumns().addAll(originalCol, translationCol);
            translationTable.setItems(data);
            tableInitialized = true;
        }
    }
}