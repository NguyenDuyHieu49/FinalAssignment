package com.mycompany.content;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import javafx.concurrent.Task;

public class API {
  private String IP;
  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  private String latitude;
  private String longitude;
  private API()
  {
    // private constructor
    setIP();
    System.out.print("IP: " + IP);
  }

  // Inner class to provide instance of class
  private static class BillPughSingleton
  {
    private static final API INSTANCE = new API();
  }

  public static API getInstance()
  {

    return BillPughSingleton.INSTANCE;
  }
  public void fetchData() {
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        fetchIP();

        return null;
      }
    };
      task.setOnSucceeded(e -> {
        fetchWeatherData();
      });
      new Thread(task).start();
  }
  public void fetchIP() {
    try {
      URL url = new URL("https://ipinfo.io/" + getIP() + "?token=bc81daa7df02fe" );

      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      // Check if the request was successful
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Parse JSON response
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

          StringBuilder response = new StringBuilder();
          String inputLine;

          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();
          ObjectMapper mapper = new ObjectMapper();
          JsonNode jsonNode = mapper.readTree(response.toString());
          String loc = jsonNode.get("loc").asText();
          String[] latLong = loc.split(",");

          setLatitude(latLong[0]);
          setLongitude(latLong[1]);
          System.out.print("Latitude: " + latitude + " Longitude: " + longitude);
        }
      } else {
        System.out.println("Failed to fetch from IP");
      }

      connection.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public String getIP() {
    return IP;
  }
  public void setIP() {
    try {
      String apiUrl = "https://api.ipify.org?format=json";
      URL url = new URL(apiUrl);

      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) { // Check if request was successful
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        String jsonResponse = response.toString();
        String ip = jsonResponse.split(":")[1].replaceAll("[\"}]", "");
        IP = ip;
      } else {
        System.out.println("Failed to fetch IP, response code: " + responseCode);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  public int weathercodeToday() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/weather_data.json"));
      JsonNode weatherCodeNode = rootNode.path("daily").path("weather_code");
      System.out.println("this: " + weatherCodeNode.get(0).asInt());
      return weatherCodeNode.get(0).asInt();
    } catch (IOException e) {
      e.printStackTrace();
    }
      return 0;
  }
  public void fetchWeatherData() {
    String weather = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&daily=weather_code";
    try {
      URL url = new URL(weather);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      // Check if the request was successful
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Parse JSON response
        try (InputStream inputStream = connection.getInputStream()) {
          ObjectMapper mapper = new ObjectMapper();
          Object jsonData = mapper.readValue(inputStream, Object.class);

          // Save JSON to file
          File file = new File("src/main/resources/weather_data.json");
          mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonData);
          System.out.println("Weather data saved to " + file.getAbsolutePath());
        }
      } else {
        System.out.println("Failed to fetch weather data. Response Code: " + responseCode);
      }

      connection.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
