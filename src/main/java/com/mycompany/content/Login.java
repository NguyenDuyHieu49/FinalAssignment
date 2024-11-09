package com.mycompany.content;

import javafx.animation.RotateTransition;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Login {

  private Stage stage;
  private Scene scene;
  private Parent root;
  
  @FXML
  AnchorPane loginScene;
  @FXML
  ImageView loading;


  @FXML
  public void handleLogin(Event event) throws IOException {
    
    Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                loginScene.setVisible(false);
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(5), loading);
                rotateTransition.setByAngle(360);  
                rotateTransition.setCycleCount(RotateTransition.INDEFINITE); 
                rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);  
                rotateTransition.play();

                return null;
            }
    };
    
    Thread loadingThread = new Thread(loadingTask);
        loadingThread.setDaemon(true);  // Đảm bảo thread kết thúc khi ứng dụng đóng
        loadingThread.start();

    Platform.runLater(() -> {
        try {
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
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> {
                stage.show();
            });
            pause.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
  }
}
