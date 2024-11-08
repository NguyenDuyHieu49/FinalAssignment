package com.mycompany.content;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Start extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    DatabaseManager db = DatabaseManager.getInstance();
    API api = API.getInstance();
    api.fetchData();
    api.weathercodeToday();
    FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("Login.fxml"));
    Scene loginScene = new Scene(fxmlLoader.load(), 600, 800);
    stage.setTitle("Teenager Management System");
    stage.setScene(loginScene);
    stage.setResizable(false);
    stage.show();

  }

  public static void main(String[] args) {
      
    launch();

  }
}