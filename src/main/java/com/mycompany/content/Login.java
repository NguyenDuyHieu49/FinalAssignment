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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Login {

  private Stage stage;
  private Scene scene;
  private Parent root;
  
  @FXML
  TextField userName;
  @FXML
  PasswordField password;
  @FXML
  AnchorPane loginScene;
  @FXML
  ImageView loading;
  PauseTransition pause = new PauseTransition(Duration.seconds(0));
  Task<Void> task;
  Alert wrongInfo = new Alert(Alert.AlertType.ERROR);

  @FXML
  public void handleLogin(Event event) throws IOException {
    if(userName.getText().equals("admin") && password.getText().equals("123456")) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginScene.setVisible(false);
        task = new Task<>() {
          @Override
          protected Void call() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
            Platform.runLater(() -> {
                try {
                    HBox root = fxmlLoader.load();
                    root.setPrefWidth(980);
                    root.setPrefHeight(800);
                    scene = new Scene(root, 980, 800);
                } catch (IOException ex){
                    ex.printStackTrace();
                }
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            });
            return null;
          }
        };
        pause.setOnFinished(ev -> {
            new Thread(task).start();
        });
        pause.play();
    } else {
        wrongInfo.showAndWait();
    }

  }
  
  public void initialize(){
      wrongInfo.setTitle("Lỗi");
      wrongInfo.setHeaderText(null);
      wrongInfo.setContentText("Tên đăng nhập hoặc mật khẩu không đúng !");
      Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), loading);
                rotateTransition.setByAngle(360);  
                rotateTransition.setCycleCount(RotateTransition.INDEFINITE); 
                rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);  
                rotateTransition.play();

                return null;
            }
    };
    
    Thread loadingThread = new Thread(loadingTask);
        loadingThread.setDaemon(true); 
        loadingThread.start();
  }
}
