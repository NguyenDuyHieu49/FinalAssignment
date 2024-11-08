module com.mycompany.content {
    requires javafx.controls;
    requires javafx.fxml;
    
    
  requires org.controlsfx.controls;
  requires org.kordamp.ikonli.javafx;
  requires com.almasb.fxgl.all;
  requires com.fasterxml.jackson.databind;
  requires com.jfoenix;
   requires org.kordamp.ikonli.fontawesome5;
    opens com.mycompany.content to javafx.fxml, javafx.base, javafx.controls;
    exports com.mycompany.content;
}
