package com.mycompany.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;


public class Home {
   DatabaseManager db = DatabaseManager.getInstance();
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
  @FXML
  StackPane age;
  @FXML
  Text day1;
  @FXML
  Text maxT;
  @FXML
  Text minT;
  @FXML
  ImageView weatherView;
  @FXML
  Text weatherType;
  @FXML
  Text time;

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
     db.deleteTeen(currentTeenager.getCCCD());
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
    //TODO: Save data to JSON - checked
    db.addTeenager(currentTeenager);
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
    updateChart();
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
        //TODO Delete data from JSON - checked
        db.deleteTeen(currentTeenager.getCCCD());
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
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH : mm : ss");
    Timeline timeline = new Timeline(
      new KeyFrame(Duration.seconds(1), event -> {
        LocalDateTime current = LocalDateTime.now();
        time.setText(current.format(formatTime));
        })
    );
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    HashMap<Integer, Pair<String,String>> weather = new HashMap<Integer, Pair<String,String>>();
    weather.put(0, new Pair<>("Trời quang", "clear-sky.png"));
    weather.put(1, new Pair<>("Nhiều mây", "cloud.png"));
    weather.put(2, new Pair<>("Nhiều mây", "cloud.png"));
    weather.put(3, new Pair<>("Nhiều mây", "cloud.png"));
    weather.put(45, new Pair<>("Có sương mù", "fog.png"));
    weather.put(48, new Pair<>("Có sương mù", "fog.png"));
    weather.put(51, new Pair<>("Có mưa phùn", "drizzle.png"));
    weather.put(53, new Pair<>("Có mưa phùn", "drizzle.png"));
    weather.put(55, new Pair<>("Có mưa phùn", "drizzle.png"));
    weather.put(56, new Pair<>("Có mưa phùn", "drizzle.png"));
    weather.put(57, new Pair<>("Có mưa phùn", "drizzle.png"));
    weather.put(61, new Pair<>("Có mưa", "rain.png"));
    weather.put(63, new Pair<>("Có mưa", "rain.png"));
    weather.put(65, new Pair<>("Có mưa", "rain.png"));
    weather.put(66, new Pair<>("Có mưa đóng băng", "freezing-rain.png"));
    weather.put(67, new Pair<>("Có mưa đóng băng", "freezing-rain.png"));
    weather.put(71, new Pair<>("Có tuyết rơi", "snowy.png"));
    weather.put(73, new Pair<>("Có tuyết rơi", "snowy.png"));
    weather.put(75, new Pair<>("Có tuyết rơi", "snowy.png"));
    weather.put(77, new Pair<>("Có tuyết rơi", "snowy.png"));
    weather.put(80, new Pair<>("Mưa lớn", "shower.png"));
    weather.put(81, new Pair<>("Mưa lớn", "shower.png"));
    weather.put(82, new Pair<>("Mưa lớn", "shower.png"));
    weather.put(85, new Pair<>("Mưa lớn", "shower.png"));
    weather.put(86, new Pair<>("Mưa lớn", "shower.png"));
    weather.put(95, new Pair<>("Mưa lớn có sấm sét", "thunderstorm.png"));
    weather.put(96, new Pair<>("Mưa lớn có sấm sét", "thunderstorm.png"));
    weather.put(99, new Pair<>("Mưa lớn có sấm sét", "thunderstorm.png"));
    ObjectMapper objectMapper = new ObjectMapper();
    File weatherFile = new File("weather_data.json");
    try {
        JsonNode rootNode = objectMapper.readTree(weatherFile);
        JsonNode dailyNode = rootNode.path("daily");
        JsonNode timeNode = dailyNode.path("time");
        JsonNode weatherCodeNode = dailyNode.path("weather_code");
        JsonNode maxTNode = dailyNode.path("temperature_2m_max");
        JsonNode minTNode = dailyNode.path("temperature_2m_min");
        LocalDate currentDate = LocalDate.parse(timeNode.get(0).asText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Ngày' dd, 'tháng' MM, 'năm' yy");
        day1.setText(currentDate.format(formatter));
        maxT.setText("Nhiệt độ cao nhất : "+maxTNode.get(0).asText()+"°C");
        minT.setText("Nhiệt độ thấp nhất : "+minTNode.get(0).asText()+"°C");
        int t = weatherCodeNode.get(0).asInt();
        if(weather.containsKey(t)) {
            weatherType.setText("Thời tiết : "+weather.get(t).getKey());
            Image image = new Image(getClass().getResource(weather.get(t).getValue()).toExternalForm());
            weatherView.setImage(image);
        }
    }catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }    
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

    teenagersList = FXCollections.observableArrayList(db.getTeenagers());
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
      FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.COG);
      adjustButton.setGraphic(icon);
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
        //TODO get data from database
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
            new Data("11 - 17", under18),
            new Data("1 - 5", oneFive),
            new Data("6 - 10", fiveTen)
        );
        pieChart.setTitle("Phân bố độ tuổi thanh thiếu niên");

        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
          chart.getChildren().clear();
          chart.getChildren().add(pieChart);
        });
        return null;
      }
    };
    new Thread(task).start();
    Task<Void> task1 = new Task<>() {
      @Override
      protected Void call() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Độ tuổi");
        yAxis.setLabel("Số lượng");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        
        barChart.setTitle("Độ tuổi của thanh thiếu niên");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Số lượng thanh thiếu niên");

        // This is sample data
        //TODO get data from database
        series.getData().add(new XYChart.Data<>("1", 1));
        series.getData().add(new XYChart.Data<>("2", 2));
        series.getData().add(new XYChart.Data<>("3", 3));
        series.getData().add(new XYChart.Data<>("4", 4));
        series.getData().add(new XYChart.Data<>("5", 5));
        series.getData().add(new XYChart.Data<>("6", 6));
        series.getData().add(new XYChart.Data<>("7", 7));
        series.getData().add(new XYChart.Data<>("8", 8));
        series.getData().add(new XYChart.Data<>("9", 9));
        series.getData().add(new XYChart.Data<>("10", 10));
        series.getData().add(new XYChart.Data<>("11", 50));
        series.getData().add(new XYChart.Data<>("12", 50));
        series.getData().add(new XYChart.Data<>("13", 60));
        series.getData().add(new XYChart.Data<>("14", 80));
        series.getData().add(new XYChart.Data<>("15", 90));
        series.getData().add(new XYChart.Data<>("16", 100));
        series.getData().add(new XYChart.Data<>("17", 110));
        series.getData().add(new XYChart.Data<>("0", 120));

        barChart.getData().add(series);



        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
          age.getChildren().add(barChart);
        });
        return null;
      }
    };
    new Thread(task1).start();
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

  public void handleReloadClick() {
      //TODO get data from database
      teenagerTableView.refresh();
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
              db.addTeenager(currentTeenager);
              addStatus.showAndWait();
              back();
              editing = false;
            }
          });
        }
      }
    }
  }
