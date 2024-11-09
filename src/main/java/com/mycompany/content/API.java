package com.mycompany.content;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import javafx.concurrent.Task;

public class API {
    private String IP;
    private String latitude;
    private String longitude;

    private API() {
        setIP();
        System.out.print("IP: " + IP);
    }

    private static class BillPughSingleton {
        private static final API INSTANCE = new API();
    }

    public static API getInstance() {
        return BillPughSingleton.INSTANCE;
    }

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

    public void fetchData() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                fetchIP();
                return null;
            }
        };
        task.setOnSucceeded(e -> fetchWeatherData());
        new Thread(task).start();
    }

    public void fetchIP() {
        try {
            URL url = new URL("https://ipinfo.io/" + getIP() + "?token=bc81daa7df02fe");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());
                    String loc = jsonNode.get("loc").asText();
                    String[] latLong = loc.split(",");
                    setLatitude(latLong[0]);
                    setLongitude(latLong[1]);
                    System.out.print("Latitude: " + latitude + " Longitude: " + longitude);
                }
            } else {
                System.out.println("Failed to fetch IP info");
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
            URL url = new URL("https://api.ipify.org?format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    String jsonResponse = response.toString();
                    IP = jsonResponse.split(":")[1].replaceAll("[\"}]", "");
                }
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
        File file = new File("weather_data.json");

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("weather_data.json not found! Creating a new one...");

            fetchWeatherData();
            return 0;
        }

        // Read the JSON from the file
        JsonNode rootNode = mapper.readTree(file);
        JsonNode weatherCodeNode = rootNode.path("daily").path("weather_code");

        if (weatherCodeNode.isArray() && weatherCodeNode.size() > 0) {
            System.out.println("Today's weather code: " + weatherCodeNode.get(0).asInt());
            return weatherCodeNode.get(0).asInt();
        } else {
            System.out.println("Weather code data not found.");
            return 0;
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    return 0;
}

    public void fetchWeatherData() {
    String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&daily=weather_code,temperature_2m_max,temperature_2m_min";
    try {
        URL url = new URL(weatherUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = connection.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                Object jsonData = mapper.readValue(inputStream, Object.class);

                // Define the file path where the JSON will be saved
                File file = new File("weather_data.json");

                if (!file.exists()) {
                    file.createNewFile();  // Create the file if it doesn't exist
                    System.out.println("Created new file: weather_data.json");
                }

                // Write the JSON data to the file
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonData);

                System.out.println("Weather data saved to weather_data.json");

            }
        } else {
            System.out.println("Failed to fetch weather data."
                    + " Response Code: " + responseCode);
        }
        connection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
