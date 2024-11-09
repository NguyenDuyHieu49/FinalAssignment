package com.mycompany.content;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.beans.property.SimpleStringProperty;

public abstract class person {

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getBirthday() {
    return birthday.get();
  }

  public SimpleStringProperty birthdayProperty() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday.set(birthday);
  }

  public String getGender() {
    return gender.get();
  }

  public SimpleStringProperty genderProperty() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender.set(gender);
  }

  public void setCCCD(String CCCD) {
    this.CCCD.set(CCCD);
  }

  public String getCCCD() {
      return CCCD.get();
  }

  public SimpleStringProperty CCCDProperty() {
      return CCCD;
  }

  private SimpleStringProperty name = new SimpleStringProperty();
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private SimpleStringProperty birthday = new SimpleStringProperty();
  private SimpleStringProperty gender = new SimpleStringProperty();
  private SimpleStringProperty CCCD = new SimpleStringProperty();
  private int age;
  public SimpleStringProperty address1 = new SimpleStringProperty();

  public String getAddress2() {
    return address2.get();
  }

  public SimpleStringProperty address2Property() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2.set(address2);
  }

  public String getAddress1() {
    return address1.get();
  }

  public SimpleStringProperty address1Property() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1.set(address1);
  }

  public SimpleStringProperty address2 = new SimpleStringProperty();
  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
