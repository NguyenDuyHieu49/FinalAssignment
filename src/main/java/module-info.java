module com.mycompany.content {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.content to javafx.fxml;
    exports com.mycompany.content;
}
