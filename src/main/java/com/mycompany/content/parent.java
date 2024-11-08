package com.mycompany.content;

import javafx.beans.property.SimpleStringProperty;

public class parent extends person{
  public SimpleStringProperty familyState = new SimpleStringProperty();


  public parent() {
    this.familyState = new SimpleStringProperty();
  }
  public parent(String CCCD, String name, String gender, String birthday, String address1, String address2, String familyState) {
    this.setCCCD(CCCD);
    this.setName(name);
    this.setGender(gender);
    this.setBirthday(birthday);
    this.setAddress1(address1);
    this.setAddress2(address2);
    this.setFamilyState(familyState);
    this.setAge(DatabaseManager.getInstance().currentYear - Integer.parseInt(birthday.substring(0,4)));
  }

  public String getFamilyState() {
    return familyState.get();
  }

  public SimpleStringProperty familyStateProperty() {
    return familyState;
  }

  public void setFamilyState(String familyState) {
    this.familyState.set(familyState);
  }



}
