module com.ynov {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ynov to javafx.fxml;
    exports com.ynov;
}
