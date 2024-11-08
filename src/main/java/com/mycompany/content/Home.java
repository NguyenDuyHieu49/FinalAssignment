package com.mycompany.content;

import java.time.LocalDate;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;


public class Home {

  @FXML
  public StackPane chart;
  @FXML
  public AnchorPane home;
  @FXML
  public AnchorPane data;
  @FXML
  public AnchorPane add;
  @FXML
  public AnchorPane adjust;

  public teenager currentTeenager;
  @FXML
  TableView<teenager> teenagerTableView;
  @FXML
  TableColumn<teenager, String> nameColumn;
  @FXML
  TableColumn<teenager, String> genderColumn;
  @FXML
  TableColumn<teenager, String> birthdayColumn;
  @FXML
  TableColumn<teenager, String> homeColumn;
  @FXML
  TableColumn<teenager, String> livingColumn;
  @FXML
  TableColumn<teenager, String> CCCDColumn;
  @FXML
  TableColumn<teenager, Integer> idColumn;
  @FXML
  TableColumn<teenager, Button> adjustColumn;
  @FXML
  ComboBox<String> searchType;
  @FXML
  TextField searchBox;
  @FXML
  TextField nameField;
  @FXML
  TextField newNameField;
  @FXML
  TextField cccdField;
  @FXML
  TextField newCccdField;
  @FXML
  ComboBox<String> genderField;
  @FXML
  ComboBox<String> newGenderField;
  @FXML
  DatePicker birthdayField;
  @FXML
  DatePicker newBirthdayField;
  @FXML
  TextField homeField;
  @FXML
  TextField newHomeField;
  @FXML
  TextField livingField;
  @FXML
  TextField newLivingField;
  @FXML
  TextField prNameField;
  @FXML
  TextField newPrNameField;
  @FXML
  TextField prcccdField;
  @FXML
  TextField newPrcccdField;
  @FXML
  TextField prRelationField;
  @FXML
  TextField newPrRelationField;
  @FXML
  ComboBox<String> prGenderField;
  @FXML
  ComboBox<String> newPrGenderField;
  @FXML
  DatePicker prBirthdayField;
  @FXML
  DatePicker newPrBirthdayField;
  @FXML
  TextField prHomeField;
  @FXML
  TextField newPrHomeField;
  @FXML
  TextField prLivingField;
  @FXML
  TextField newPrLivingField;
  @FXML
  TextField prFamilyStateField;
  @FXML
  TextField newPrFamilyStateField;
  @FXML
  TextField cccdCheck;
  @FXML
  AnchorPane checkScene;

  boolean change = false;

  boolean editing = false;


  public int oneFive = 0;
  public int fiveTen = 0;
  public int under18 = 0;

  public void updateScene(AnchorPane show, AnchorPane hide, AnchorPane hide2, AnchorPane hide3) {
    show.setVisible(true);
    show.setManaged(true);
    hide.setVisible(false);
    hide.setManaged(false);
    hide2.setVisible(false);
    hide2.setManaged(false);
    hide3.setVisible(false);
    hide3.setManaged(false);
  }

  AnchorPane cur, other1, other2;

  public void handleData() {
    updateScene(cur, home, other2, other1);
  }

  public void handleHome() {
    updateScene(home, data, add, adjust);
  }
  Alert checkAlert = new Alert(AlertType.ERROR);

  public void save() {
    currentTeenager.setName(nameField.getText());
    currentTeenager.setGender(genderField.getValue());
    currentTeenager.setBirthday(birthdayField.getValue().toString());
    currentTeenager.setAddress1(homeField.getText());
    currentTeenager.setAddress2(livingField.getText());
    currentTeenager.getPr().setName(prNameField.getText());
    currentTeenager.getPr().setCCCD(prcccdField.getText());
    currentTeenager.getPr().setGender(prGenderField.getValue());
    currentTeenager.getPr().setBirthday(prBirthdayField.getValue().toString());
    currentTeenager.setRelation(prRelationField.getText());
    currentTeenager.getPr().setAddress1(prHomeField.getText());
    currentTeenager.getPr().setAddress2(prLivingField.getText());
    //TODO: Save data to JSON

    change = false;
  }
  Alert cccdFound = new Alert(AlertType.INFORMATION);
  Alert newcccdfound = new Alert(AlertType.CONFIRMATION);
  ButtonType yesButton = new ButtonType("Có");

  Alert backChange = new Alert(Alert.AlertType.CONFIRMATION);
  ButtonType saveButton = new ButtonType("Lưu");
  ButtonType notSaveButton = new ButtonType("Không lưu");
  ButtonType cancelButton = new ButtonType("Hủy");
  Alert saveChange = new Alert(Alert.AlertType.CONFIRMATION);
  Alert confirmSave = new Alert(AlertType.INFORMATION);
  Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
  Alert confirmDelete = new Alert(AlertType.INFORMATION);
  ButtonType deleteButton = new ButtonType("Xóa");
  ButtonType noButton = new ButtonType("Không");
  Alert missingField = new Alert(AlertType.ERROR);
  Alert addConfirm = new Alert(AlertType.CONFIRMATION);
  Alert addStatus = new Alert(AlertType.INFORMATION);
  Alert cancelAlert = new Alert(AlertType.CONFIRMATION);
  Alert cancelConfirm = new Alert(AlertType.INFORMATION);

  public void back() {
    cur = data;
    other1 = add;
    other2 = adjust;
    handleData();
  }

  public void handleBackClick() {
    if (change) {
      backChange.showAndWait().ifPresent(r -> {
        if (r == saveButton) {
          save();
          back();
        } else if (r != cancelButton) {
          back();
        }
      });
    } else {
      back();
    }
  }

  public void handleSaveClick() {
    saveChange.showAndWait().ifPresent(r -> {
      if (r == saveButton) {
        save();
        confirmSave.showAndWait();
      }
    });
  }

  public void handelDeleteClick() {
    deleteAlert.showAndWait().ifPresent(r -> {
      if (r == deleteButton) {
        teenagersList.remove(currentTeenager);
        //TODO Delete data from JSON

        confirmDelete.showAndWait();
        back();
      }
    });
  }

  public void initAlert() {
    cancelAlert.setTitle("Xác nhận hủy");
    cancelAlert.setHeaderText(null);
    cancelAlert.setContentText("Bạn có muốn hủy không?");
    cancelAlert.getButtonTypes().setAll(yesButton, noButton);
    cancelAlert.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
      cancelAlert.hide();
      if (cancelAlert.getResult() != noButton) {
        cancelAlert.setResult(noButton);
      }
    });
    cancelConfirm.setTitle("Thông báo");
    cancelConfirm.setHeaderText(null);
    cancelConfirm.setContentText("Dữ liệu đã được hủy");
    addConfirm.setTitle("Thông báo");
    addConfirm.setHeaderText(null);
    addConfirm.setContentText("Bạn có muốn thêm dữ liệu không?");
    addConfirm.getButtonTypes().setAll(yesButton, noButton);
    addConfirm.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
      addConfirm.hide();
      if (addConfirm.getResult() != noButton) {
        addConfirm.setResult(noButton);
      }
    });
    addStatus.setTitle("Thông báo");
    addStatus.setHeaderText(null);
    addStatus.setContentText("Dữ liệu đã được thêm");
    missingField.setTitle("Lỗi");
    missingField.setHeaderText(null);
    missingField.setContentText("Vui lòng điền đầy đủ thông tin");
    newcccdfound.setTitle("Thông báo");
    newcccdfound.setHeaderText(null);
    newcccdfound.setContentText("CCCD chưa tồn tại, bạn có muốn thêm mới không?");
    newcccdfound.getButtonTypes().setAll(yesButton, noButton);
    newcccdfound.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
      newcccdfound.hide();
      if (newcccdfound.getResult() != noButton) {
        newcccdfound.setResult(noButton);
      }
    });
    cccdFound.setTitle("Thông báo");
    cccdFound.setHeaderText(null);
    cccdFound.setContentText("CCCD đã tồn tại");
    checkAlert.setTitle("Lỗi");
    checkAlert.setHeaderText(null);
    checkAlert.setContentText("CCCD không hợp lệ");
    backChange.setTitle("Xác nhận quay lại");
    backChange.setHeaderText(null);
    backChange.setContentText("Dữ liệu chưa được lưu, bạn có muốn lưu dữ liệu không?");
    backChange.getButtonTypes().setAll(saveButton, notSaveButton, cancelButton);
    backChange.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
      backChange.hide();
      if (backChange.getResult() != cancelButton) {
        backChange.setResult(cancelButton);
      }
    });
    saveChange.setTitle("Xác nhận lưu");
    saveChange.setHeaderText(null);
    saveChange.setContentText("Bạn có muốn lưu dữ liệu không?");
    saveChange.getButtonTypes().setAll(saveButton, cancelButton);
    saveChange.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
      saveChange.hide();
      if (saveChange.getResult() != notSaveButton) {
        saveChange.setResult(notSaveButton);
      }
    });
    confirmSave.setTitle("Thông báo");
    confirmSave.setHeaderText(null);
    confirmSave.setContentText("Dữ liệu đã được lưu");
    deleteAlert.setTitle("Xác nhận xóa");
    deleteAlert.setHeaderText(null);
    deleteAlert.setContentText("Bạn có chắc chắn muốn xóa dữ liệu không?");
    deleteAlert.getButtonTypes().setAll(deleteButton, cancelButton);
    deleteAlert.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
      deleteAlert.hide();
      if (deleteAlert.getResult() != cancelButton) {
        deleteAlert.setResult(cancelButton);
      }
    });
    confirmDelete.setTitle("Thông báo");
    confirmDelete.setHeaderText(null);
    confirmDelete.setContentText("Dữ liệu đã được xóa");
  }

  public ObservableList<teenager> teenagersList;

  public void initialize() {
    newGenderField.getItems().addAll("Nam", "Nữ", "Khác");
    newPrGenderField.getItems().addAll("Nam", "Nữ", "Khác");
    newPrcccdField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          newPrcccdField.setText(newValue.replaceAll("\\D", ""));
        } else {
          if (newValue.length() > 12) {
            newPrcccdField.setText(oldValue);
          }
        }
      }
    });
    nameField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getName())) {
        change = true;
      }
    });
    genderField.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getGender())) {
        change = true;
      }
    });
    birthdayField.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(LocalDate.parse(currentTeenager.getBirthday()))) {
        change = true;
      }
    });
    homeField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getAddress1())) {
        change = true;
      }
    });
    livingField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getAddress2())) {
        change = true;
      }
    });
    prNameField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getPr().getName())) {
        change = true;
      }
    });
    prGenderField.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getPr().getGender())) {
        change = true;
      }
    });
    prBirthdayField.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(
          LocalDate.parse(currentTeenager.getPr().getBirthday()))) {
        change = true;
      }
    });
    prRelationField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getRelation())) {
        change = true;
      }
    });
    prHomeField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getPr().getAddress1())) {
        change = true;
      }
    });
    prLivingField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getPr().getAddress2())) {
        change = true;
      }
    });
    prcccdField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getPr().getCCCD())) {
        change = true;
      }
    });
    prcccdField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          prcccdField.setText(newValue.replaceAll("\\D", ""));
        } else {
          if (newValue.length() > 12) {
            prcccdField.setText(oldValue);
          }
        }
      }
    });
    prcccdField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getPr().getCCCD())) {
        change = true;
      }
    });
    prFamilyStateField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.equals(currentTeenager.getPr().getFamilyState())) {
        change = true;
      }
    });
    change = false;
    initAlert();
    cur = data;
    other1 = add;
    other2 = adjust;
    updateScene(home, data, add, adjust);
    genderField.getItems().addAll("Nam", "Nữ", "Khác");
    prGenderField.getItems().addAll("Nam", "Nữ", "Khác");

    teenagersList = FXCollections.observableArrayList(DatabaseManager.getInstance().getTeenagers());
    for (var x : teenagersList) {
      if (x.getAge() <= 5) {
        oneFive++;
      } else {
        if (x.getAge() <= 10) {
          fiveTen++;
        } else {
          under18++;
        }
      }
    }
    updateChart();
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
    birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
    homeColumn.setCellValueFactory(cellData -> cellData.getValue().address1Property());
    livingColumn.setCellValueFactory(cellData -> cellData.getValue().address2Property());
    CCCDColumn.setCellValueFactory(cellData -> cellData.getValue().CCCDProperty());
    idColumn.setCellValueFactory(cellData ->
        new ReadOnlyObjectWrapper<>(teenagerTableView.getItems().indexOf(cellData.getValue()) + 1));
    adjustColumn.setCellValueFactory(cellData -> {
      Button adjustButton = new Button();
//      FontIcon icon = new FontIcon("mdi-account-settings-variant");
      adjustButton.setStyle("-fx-cursor: hand");
//      icon.setIconSize(14);
//      adjustButton.setGraphic(icon);
      adjustButton.setOnAction(event -> {
        change = false;
        currentTeenager = cellData.getValue();
        handleAdjustClick();
      });
      return new ReadOnlyObjectWrapper<>(adjustButton);
    });
    teenagerTableView.setItems(teenagersList);
    searchType.getItems().addAll("CCCD", "Tên", "Địa chỉ");
    searchType.setValue("CCCD");
    FilteredList<teenager> filteredData = new FilteredList<>(teenagersList, p -> true);
    searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
      filteredData.setPredicate(teenager -> {
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }
        String lowerCaseFilter = newValue.toLowerCase();
        if (searchType.getValue().equals("CCCD")) {
          if (teenager.getCCCD() == null) {
            return false;
          }
          return teenager.getCCCD().toLowerCase().startsWith(lowerCaseFilter);
        } else {
          if (searchType.getValue().equals("Tên")) {
            if (teenager.getName() == null) {
              return false;
            }
            return teenager.getName().toLowerCase().startsWith(lowerCaseFilter);
          } else {
            if (teenager.getAddress1() == null || teenager.getAddress2() == null) {
              return false;
            }
            return teenager.getAddress1().toLowerCase().contains(lowerCaseFilter)
                || teenager.getAddress2().toLowerCase().contains(lowerCaseFilter);
          }
        }
      });
      teenagerTableView.setItems(filteredData);
    });
    cccdCheck.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          cccdCheck.setText(newValue.replaceAll("\\D", ""));
        } else {
          if (newValue.length() > 12) {
            cccdCheck.setText(oldValue);
          }
        }
      }
    });
  }

  public void updateChart() {
    Task<Void> task = new Task<>() {
      @Override
      protected Void call() {
        // Create PieChart data
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
            new Data("10 - 17", under18),
            new Data("1 - 5", oneFive),
            new Data("5 - 10", fiveTen)
        );
        pieChart.setTitle("Teenager Age Distribution");

        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
          chart.getChildren().clear();
          chart.getChildren().add(pieChart);
        });
        return null;
      }
    };
    new Thread(task).start();
  }


  StackPane root = new StackPane();

  public void handleAddClick() {
    cur = add;
    other1 = data;
    other2 = adjust;
    updateScene(add, home, data, adjust);
    if (!editing) checkScene.setVisible(true);
  }

  public void handleCheckClick() {
    if (cccdCheck.getText().length() != 12) {
      checkAlert.showAndWait();
    } else {
      boolean found = false;
      for (var x : teenagersList) {
        if (x.getCCCD().equals(cccdCheck.getText())) {
          found = true;
          currentTeenager = x;
          break;
        }
      }
      if (found) {
        cccdFound.showAndWait();
        handleAdjustClick();
        cccdCheck.setText("");
      } else {
        newcccdfound.showAndWait().ifPresent(r -> {
          if (r == yesButton) {
            checkScene.setVisible(false);
            newCccdField.setText(cccdCheck.getText());
            cccdCheck.setText("");
            editing = true;
          }
        });
      }
    }
  }

  public void handleAdjustClick() {
    cur = adjust;
    other1 = data;
    other2 = add;
    updateScene(adjust, home, data, add);
    nameField.setText(currentTeenager.getName());
    cccdField.setText(currentTeenager.getCCCD());
    genderField.setValue(currentTeenager.getGender());
    birthdayField.setValue(LocalDate.parse(currentTeenager.getBirthday()));
    homeField.setText(currentTeenager.getAddress1());
    livingField.setText(currentTeenager.getAddress2());
    prNameField.setText(currentTeenager.getPr().getName());
    prcccdField.setText(currentTeenager.getPr().getCCCD());
    prHomeField.setText(currentTeenager.getPr().getAddress1());
    prGenderField.setValue(currentTeenager.getPr().getGender());
    prBirthdayField.setValue(LocalDate.parse(currentTeenager.getPr().getBirthday()));
    prRelationField.setText(currentTeenager.getRelation());
    prLivingField.setText(currentTeenager.getPr().getAddress2());
    prFamilyStateField.setText(currentTeenager.getPr().getFamilyState());
  }

  public void handleCancelClick() {
    cancelAlert.showAndWait().ifPresent(r -> {
      if (r == yesButton) {
        cancelConfirm.showAndWait();
        back();
        editing = false;
      }
    });
  }

  public void handleAddTClick() {
    if (newPrcccdField.getText().length() != 12) {
      checkAlert.showAndWait();
    } else {
      if (newPrNameField.getText().isBlank() || newPrcccdField.getText().isBlank()
          || newPrGenderField.getValue() == null || newPrBirthdayField.getValue() == null
          || newPrHomeField.getText().isBlank() || newPrLivingField.getText().isBlank()
          || newPrRelationField.getText().isBlank() || newNameField.getText().isBlank()
          || newCccdField.getText().isBlank() || newGenderField.getValue() == null
          || newBirthdayField.getValue() == null || newHomeField.getText().isBlank()
          || newLivingField.getText().isBlank()) {
        missingField.showAndWait();
      } else {
          addConfirm.showAndWait().ifPresent(r -> {
            if (r == yesButton) {
              currentTeenager = new teenager(newCccdField.getText(), newNameField.getText(),
                  newGenderField.getValue(), newBirthdayField.getValue().toString(),
                  newHomeField.getText(), newLivingField.getText(),
                  new parent(newPrcccdField.getText(), newPrNameField.getText(),
                      newPrGenderField.getValue(), newPrBirthdayField.getValue().toString(),
                      newPrHomeField.getText(), newPrLivingField.getText(),
                      newPrFamilyStateField.getText()), newPrRelationField.getText());
              teenagersList.add(currentTeenager);
              addStatus.showAndWait();
              back();
              editing = false;
            }
          });
        }
      }
    }
  }
