package com.mycompany.content;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
  public final static int currentYear = LocalDate.now().getYear();

  // Using BillPughSingleton pattern
  private DatabaseManager() {
    // private constructor
    loadTeenagers();
  }

  // Inner class to provide instance of class
  private static class BillPughSingleton {
    private static final DatabaseManager INSTANCE = new DatabaseManager();
  }

  public static DatabaseManager getInstance() {
    return BillPughSingleton.INSTANCE;
  }

  private ArrayList<teenager> teenagers = new ArrayList<teenager>();

  public ArrayList<teenager> getTeenagers() {
    return teenagers;
  }

  public void setTeenagers(ArrayList<teenager> teenagers) {
    this.teenagers = teenagers;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  // File path for db.json in the local file system
  private static final String FILE_PATH = "db.json";  // Adjusted to use file system
  private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing

  // Add a teenager to JSON database
  public void addTeenager(teenager teen) {
    teenagers.add(teen);
    saveToJson();
  }

  public void deleteTeen(String cccd) {
    boolean removed = teenagers.removeIf(n -> n.getCCCD().equals(cccd));
    if (removed) {
        saveToJson();
    } else {
        System.out.println("No teenager found with CCCD: " + cccd);
    }
  }

  private void saveToJson() {
    try {
        File file = new File(FILE_PATH);
        objectMapper.writeValue(file, teenagers);
        System.out.println("Data saved to db.json at: " + file.getAbsolutePath());
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  // Load teenagers from JSON
  private void loadTeenagers() {
    try {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            teenagers = objectMapper.readValue(file, new TypeReference<>() {});
        } else {
            System.out.println("db.json not found, creating a new one.");

            teenagers = new ArrayList<>();
            objectMapper.writeValue(file, teenagers);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}
