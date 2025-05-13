package com.example.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class HasuraApicontroller extends Apicontroller {

    public static final String ALGEMEEN_MAPPING = "algemeen";
    public static final String STUDENT_MAPPING = "student";
    public static final String BEWEGING_MAPPING = "beweging";
    public static final Integer GEEN_ID = 0;
    public static final Integer GEEN_WEEK = 0;

    @Value("${hasura.baseURL}")
    private String BASE_URL;
    @Value("${hasura.secret}")
    private String SECRET;

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
            con.setRequestProperty("x-hasura-secret", SECRET);
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
        } catch(URISyntaxException |IOException |RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
}

    //----------------
    // tabel student
    //----------------

    // geeft alle rijen uit de student tabel
    @GetMapping("/student/getAll")
    public ResponseEntity<String> getAllStudent(){
       return GetDelDataBase(STUDENT_MAPPING, "GET",GEEN_ID,GEEN_WEEK);
    }

    // geeft een enkele rij uit de student tabel
    @GetMapping("/student/get")
    public ResponseEntity<String> getStudent(@RequestParam(value = "id") Integer id) {
        return GetDelDataBase(STUDENT_MAPPING, "GET", id, GEEN_WEEK);
    }

    // verwijdert een rij uit de student tabel
    @GetMapping("student/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam(value = "id") Integer id){
       return GetDelDataBase(STUDENT_MAPPING,"DELETE", id, GEEN_WEEK);
    }

    // voegt een nieuwe rij toe aan de student tabel
    @GetMapping("/student/new")
    public ResponseEntity<String> addStudent(@RequestParam(value = "id") Integer id, @RequestParam(value = "jaar") Integer jaar) {
        try{
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("id", id);
            object.put("jaar", jaar);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(STUDENT_MAPPING,jsonInputString, 0, 0);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // wijzigt een rij in de student tabel
    @GetMapping("/student/update")
    public ResponseEntity<String> updateStudent(@RequestParam(value = "id") Integer id, @RequestParam(value = "jaar") Integer jaar) {
        try {
            JSONObject object = new JSONObject();
            if (jaar != null) object.put("jaar", jaar);

            JSONObject superobject = new JSONObject();
            superobject.put("id", id);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();

            return UpdatePOSTDataBase(STUDENT_MAPPING, jsonInputString, id, 0);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //----------------
    // tabel algemeen
    //----------------

    // geeft alle waardes uit de algemeen tabel terug
    @GetMapping("/algemeen/getAll")
    public ResponseEntity<String> getAllAlgemeen() {
        return GetDelDataBase(ALGEMEEN_MAPPING, "GET", GEEN_ID,GEEN_WEEK);
    }

    // geeft de waarde van 1 enkele rij van de algemeen tabel
    @GetMapping("/algemeen/get")
    public ResponseEntity<String> getAlgemeen(@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week) {
        return GetDelDataBase(ALGEMEEN_MAPPING, "GET", alg_id, week);
    }

// verwijdert een rij in de algemeen tabel
    @GetMapping("/algemeen/delete")
    public ResponseEntity<String> deleteAlgemeen(@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week) {
        return GetDelDataBase(ALGEMEEN_MAPPING, "DELETE", alg_id, week);
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
            return UpdatePOSTDataBase(ALGEMEEN_MAPPING, jsonInputString, GEEN_ID, GEEN_WEEK);
        }catch (JSONException e){
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

            return UpdatePOSTDataBase(ALGEMEEN_MAPPING, jsonInputString, alg_id, week);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //----------------
    // tabel beweging
    //---------------

    // geeft alle rijen uit de beweging tabel
    @GetMapping("/beweging/getAll")
    public ResponseEntity<String> getAllBeweging(){
        return GetDelDataBase(BEWEGING_MAPPING,"GET", GEEN_ID, GEEN_WEEK);
    }

    // geeft enkele rij uit beweging tabel
    @GetMapping("/beweging/get")
    public ResponseEntity<String> getBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(BEWEGING_MAPPING, "GET", bew_id, week);
    }

    // verwijdert gevraagde rij uit beweging tabel
    @GetMapping("/beweging/delete")
    public ResponseEntity<String> deleteBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(BEWEGING_MAPPING, "DELETE", bew_id, week);
    }

    // voegt een nieuwe rij toe aan de beweging tabel
    @GetMapping("/beweging/new")
    public ResponseEntity<String> addBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "matig_min") Integer matig_min, @RequestParam(value = "intens_min") Integer intens_min, @RequestParam(value = "strek_min") Integer strek_min, @RequestParam(value = "zit_uur") Integer zit_uur, @RequestParam(value = "stappen_gem") Integer stappen_gem){
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("bew_id", bew_id);
            object.put("week", week);
            object.put("mating_min", matig_min);
            object.put("intens_min", intens_min);
            object.put("strek_min", strek_min);
            object.put("zit_uur", zit_uur);
            object.put("stappen_gem", stappen_gem);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(BEWEGING_MAPPING, jsonInputString, GEEN_ID, GEEN_WEEK);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // wijzigt een rij in de beweging tabel
    @GetMapping("/beweging/update")
    public ResponseEntity updateBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "matig_min") Integer matig_min, @RequestParam(value = "intens_min") Integer intens_min, @RequestParam(value = "strek_min") Integer strek_min, @RequestParam(value = "zit_uur") Integer zit_uur, @RequestParam(value = "stappen_gem") Integer stappen_gem){
        try {
            JSONObject object = new JSONObject();
            if (matig_min != null) object.put("matig_min", matig_min);
            if (intens_min != null) object.put("intens_min", intens_min);
            if (strek_min != null) object.put("strek_min", strek_min);
            if (zit_uur != null) object.put("zit_uur", zit_uur);
            if (stappen_gem != null) object.put("stappen_gem", stappen_gem);

            JSONObject superobject = new JSONObject();
            superobject.put("bew_id", bew_id);
            superobject.put("week", week);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(BEWEGING_MAPPING,jsonInputString,bew_id,week);

        }catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //----------------
    // middelengebruik
    //-----------------

    

}
