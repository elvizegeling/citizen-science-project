/*
########################################################################################
## project: citizen-science project (gen + mi = 3)                                    ##
## doel: deze class is bedoeld om de url connectie te maken met de database           ##
## makers: Elvi Zegeling 14156733, Pleun Emmelot 15169979,                            ##
## Thijmen Masereeuw 15019659, Anna Drenth 14960583                                   ##
## laatste update: 19-04-2025                                                         ##
########################################################################################
*/

package com.example.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
abstract class Apicontroller {

    // waardes die nodig zijn om de database url en connectie te maken
    @Value("${hasura.baseURL}")
    private String BASE_URL;
    @Value("${hasura.secret}")
    private String SECRET;

    // maakt de connectie met de database
    protected ResponseEntity<String> getApiCallResponse(HttpURLConnection con) {
        int code = 501;

        try {
            con.connect();
            code = con.getResponseCode();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                return new ResponseEntity<String>(response.toString(), HttpStatus.valueOf(200));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Throw a server error
        return new ResponseEntity<String>("FAIL", HttpStatus.valueOf(code));
    }

    // geeft de waarde van de gevraagde tabel of rij of verwijdert de gevraagde rij
    public ResponseEntity<String> GetDelDataBase(String mapping, String type, Integer id, Integer week) {
        try {
            String completeURL;
            if (id == 0 && week == 0) {
                completeURL = BASE_URL + mapping;
            } else if (week == 0) {
                completeURL = BASE_URL + mapping + "/" + id;
            } else {
                completeURL = BASE_URL + mapping + "/" + id + "/" + week;
            }

            URL url = new URI(completeURL).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(type);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-hasura-admin-secret", SECRET);
            con.setDoOutput(true);
            return getApiCallResponse(con);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // update een rij uit de gevraagde tabel of voegt een nieuwe rij toe
    public ResponseEntity<String> UpdatePOSTDataBase(String mapping, String jsonInputString, Integer id, Integer week) {
        try {
            String completeURL;
            if (id == 0 && week == 0) {
                completeURL = BASE_URL + mapping;
            } else if (week == 0) {
                completeURL = BASE_URL + mapping + "/" + id;
            } else {
                completeURL = BASE_URL + mapping + "/" + id + "/" + week;
            }
            URL url = new URI(completeURL).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-hasura-admin-secret", SECRET);
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            return getApiCallResponse(con);
        } catch (URISyntaxException | IOException | RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }
}
