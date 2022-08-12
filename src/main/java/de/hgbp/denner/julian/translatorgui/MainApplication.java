package de.hgbp.denner.julian.translatorgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        // Load from template
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("gui_template.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Translator");

        // Defining welcome Alert.
        Alert welcomeAlert = new Alert(Alert.AlertType.INFORMATION);
        welcomeAlert.setTitle("Welcome to my Translator!");
        welcomeAlert.setHeaderText("Generic Translator\nBy Julian Denner");
        welcomeAlert.setContentText("This calculator needs a file containing translations.\nPlease select a .ini file in the following window!");


        // Show welcome alert.
        welcomeAlert.showAndWait();


        // Init Translator and pass in selected file

        Translator.init_Translator(chooseFile(stage), stage);

        // Set & show main scene
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static File fileEmptyErr(Stage stage){ // I don't know how to access stage from here so ill just pass it a few times
        Alert emptyFileErr = new Alert(Alert.AlertType.WARNING);
        emptyFileErr.setTitle("Empty file!");
        emptyFileErr.setContentText("Inputted file got parsed without errors but its empty :(\nPlease load a different file!");
        emptyFileErr.showAndWait();
        return chooseFile(stage);
    }
    public static File fileReadErr(Stage stage) {
        Alert readFileErr = new Alert(Alert.AlertType.ERROR);
        readFileErr.setTitle("Read error!");
        readFileErr.setContentText("Error occurred while reading file!\nPlease load a different file!");
        readFileErr.showAndWait();
        return chooseFile(stage);
    }

    private static File chooseFile(Stage stage) {
        // Defining wrong file Alert.
        Alert wrongFileAlert = new Alert(Alert.AlertType.ERROR);
        wrongFileAlert.setTitle("Wrong file!");
        wrongFileAlert.setContentText("Error: Please select a different file!\nCauses: No file selected OR Wrong file ending {.ini}!");


        // Defining fileChooser.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select .ini File");

        File selectedFile = null;
        selectedFile = fileChooser.showOpenDialog(stage); // Open first file selector out of while loop to prevent instant error alert.
        while (selectedFile == null || !selectedFile.toString().endsWith(".ini")) { // Might pass because of the if!
            wrongFileAlert.showAndWait();
            selectedFile = fileChooser.showOpenDialog(stage);
        }
        return selectedFile;
    }
}