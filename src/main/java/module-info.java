module de.hgbp.denner.julian.translatorgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.hgbp.denner.julian.translatorgui to javafx.fxml;
    exports de.hgbp.denner.julian.translatorgui;
}