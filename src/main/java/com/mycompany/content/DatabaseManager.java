package com.mycompany.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseManager {
  public final static int currentYear = LocalDate.now().getYear();
  //using BillPughSingleton
  private DatabaseManager()
  {
    // private constructor
    loadTeenagers();
  }

  // Inner class to provide instance of class
  private static class BillPughSingleton
  {
    private static final DatabaseManager INSTANCE = new DatabaseManager();
  }

  public static DatabaseManager getInstance()
  {
    return BillPughSingleton.INSTANCE;
  }
  public ArrayList<teenager> teenagers = new ArrayList<teenager>();

  public ArrayList<teenager> getTeenagers() {
    return teenagers;
  }

  public void setTeenagers(ArrayList<teenager> teenagers) {
    this.teenagers = teenagers;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  private static final String FILE_PATH = "src/main/resources/db.json";
  private final ObjectMapper objectMapper =
      new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing

  // Add a teenager to JSON database
  public void addTeenager(teenager teen) {
    try {
      teenagers.add(teen);
      objectMapper.writeValue(new File(FILE_PATH), teenagers);
    } catch (IOException e) {

      e.printStackTrace();
    }
  }

  // Load teenagers from JSON
    private void loadTeenagers() {
      try {
        teenagers = objectMapper.readValue(new File(FILE_PATH), new TypeReference<ArrayList<teenager>>() {});
      } catch (IOException e) {
        teenagers = new ArrayList<>();
        e.printStackTrace();
      }
    }

}
