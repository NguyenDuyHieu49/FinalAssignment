package com.mycompany.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore fields Jackson can't serialize
public class teenager extends person {

  public teenager() {}
  public teenager(String CCCD,String name, String gender, String birthday, String address1, String address2, parent pr, String relation) {
    this.setCCCD(CCCD);
    this.setName(name);
    this.setGender(gender);
    this.setBirthday(birthday);
    this.setAddress1(address1);
    this.setAddress2(address2);
    this.setPr(pr);
    this.setRelation(relation);
    this.setAge(DatabaseManager.getInstance().currentYear - Integer.parseInt(birthday.substring(0,4)));
  }
  public parent getPr() {
    return pr;
  }

  public void setPr(parent pr) {
    this.pr = pr;
  }

  public parent pr = new parent();
  public String relation;
  public String getRelation() {
    return relation;
  }
  public void setRelation(String relation) {
    this.relation = relation;
  }



}
