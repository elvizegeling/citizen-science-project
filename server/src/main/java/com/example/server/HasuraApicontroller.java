package com.example.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RestController
public class HasuraApicontroller extends Apicontroller {

    public static final String ALGEMEEN_MAPPING = "algemeen";

    @Value("${hasura.baseURL}")
    private String BASE_URL;
    @Value("${hasura.secret}")
    private String SECRET;

    //----------------
    // tabel algemeen
    //----------------

    // geeft alle waardes uit de algemeen tabel terug
    @GetMapping("/algemeen/getAll")
    public ResponseEntity<String> getAllAlgemeen() {
        try {
            String completeURL = BASE_URL + ALGEMEEN_MAPPING;
            System.out.println(completeURL);
            URL url = new URI(completeURL).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-hasura-admin-secret", SECRET);
            con.setDoOutput(true);
            return getApiCallResponse(con);
        } catch (URISyntaxException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // geeft de waarde van 1 enkele rij van de algemeen tabel
    @GetMapping("/algemeen/get")
    public ResponseEntity<String> getAlgemeen(@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week) {
        try {
            String completeURL = BASE_URL + ALGEMEEN_MAPPING + "/" + alg_id + "/" + week;
            System.out.println(completeURL);
            URL url = new URI(completeURL).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-hasura-admin-secret", SECRET);
            con.setDoOutput(true);
            return getApiCallResponse(con);
        } catch (URISyntaxException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

// verwijdert een rij in de algemeen tabel
    @GetMapping("/algemeen/delete")
    public ResponseEntity<String> deleteAlgemeen(@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week) {
        try {
            String completeURL = BASE_URL + ALGEMEEN_MAPPING + "/" + alg_id + "/" + week;
            URL url = new URI(completeURL).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-hasura-admin-secret", SECRET);
            con.setDoOutput(true);
            return getApiCallResponse(con);
        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // voegt een nieuwe rij toe aan de algemeen tabel
    @GetMapping("algemeen/new")
    public ResponseEntity<String> addAlgemeen(@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "leeftijd") Integer leeftijd, @RequestParam(value = "geslacht") Integer geslacht, @RequestParam(value = "lengte_cm") Integer lengte_cm, @RequestParam(value = "thuis") Boolean thuis, @RequestParam(value = "reistijd_min") Integer reistijd_min){
        try {
            JSONObject object = new JSONObject();
            JSONObject superObject = new JSONObject();

            object.put("alg_id", alg_id);
            object.put("week", week);
            object.put("leeftijd",leeftijd);
            object.put("geslacht", geslacht);
            object.put("lengte_cm", lengte_cm);
            object.put("thuis", thuis);
            object.put("reistijd_min",reistijd_min);

            superObject.put("object", object);
            String jsonInputString = superObject.toString();

            String completeURL = BASE_URL + ALGEMEEN_MAPPING;
            URL url = new URI(completeURL).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-hasura-admin-secret", SECRET);
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()){
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            return getApiCallResponse(con);
        }catch (URISyntaxException | IOException | RuntimeException | JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // aanpassen van rij in algemeen tabel
    @GetMapping("/algemeen/update")
    public ResponseEntity<String> UpdateAlgemeen (@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "leeftijd") Integer leeftijd, @RequestParam(value = "geslacht") Integer geslacht, @RequestParam(value = "lengte_cm") Integer lengte_cm, @RequestParam(value = "thuis") Boolean thuis, @RequestParam(value = "reistijd_min") Integer reistijd_min){
        try {
            JSONObject object = new JSONObject();
            if (leeftijd != null) object.put("leeftijd", leeftijd);
            if (geslacht != null) object.put("geslacht", geslacht);
            if (lengte_cm != null) object.put("lengte_cm", lengte_cm);
            if (thuis != null) object.put("thuis", thuis);
            if (reistijd_min != null) object.put("reistijd_min", reistijd_min);

            JSONObject superobject = new JSONObject();
            superobject.put("alg_id", alg_id);
            superobject.put("week", week);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();

            String completeURL = BASE_URL + ALGEMEEN_MAPPING + "/" + alg_id + "/" + week;
            URL url = new URI(completeURL).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-hasura-admin-secret", SECRET);
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()){
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            return getApiCallResponse(con);
        } catch (URISyntaxException | IOException | RuntimeException | JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }

    }
}
