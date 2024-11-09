module com.mycompany.content {
  requires javafx.fxml;
    requires java.sql;
  requires org.controlsfx.controls;
  requires org.kordamp.ikonli.javafx;
  requires com.almasb.fxgl.all;
  requires com.fasterxml.jackson.databind;
  requires com.jfoenix;
  requires jasperreports;
   requires org.kordamp.ikonli.fontawesome5;
  requires jfreechart;
  requires de.jensd.fx.glyphs.fontawesome;
  requires java.desktop;

  opens com.mycompany.content to javafx.fxml, javafx.base, javafx.controls;
    exports com.mycompany.content;
}
