package com.mycompany.content;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Login {

  private Stage stage;
  private Scene scene;
  private Parent root;


  @FXML
  public void handleLogin(Event event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
    HBox root = fxmlLoader.load();
    root.setPrefWidth(980);
    root.setPrefHeight(800);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root, 980, 800);
    stage.setScene(scene);
    stage.setTitle("Teenager Management System");
    stage.setResizable(false);
    stage.centerOnScreen();
    stage.show();
  }
}
