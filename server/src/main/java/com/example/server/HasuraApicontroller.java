/*
########################################################################################
## project: citizen-science project (gen + mi = 3)                                    ##
## doel: deze class is bedoeld om de GET, DELETE en POST te maken en te vullen met    ##
## juiste informatie zodat deze in de database gevuld wordt.                          ##
## makers: Elvi Zegeling 14156733, Pleun Emmelot 15169979,                            ##
## Thijmen Masereeuw 15019659, Anna Drenth 14960583                                   ##
## laatste update: 19-04-2025                                                         ##
########################################################################################
*/
package com.example.server;

import com.opencsv.CSVWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


@RestController
public class HasuraApicontroller extends Apicontroller {
    public static final String ALGEMEEN_MAPPING = "algemeen";
    public static final String STUDENT_MAPPING = "student";
    public static final String BEWEGING_MAPPING = "beweging";
    public static final String MIDDELENGEBRUIK_MAPPING = "middelengebruik";
    public static final String SE_MAPPING = "subjectieve_ervaringen";
    public static final String TIJDBESTEDING_MAPPING = "tijdbesteding";
    public static final String VOEDING_MAPPING = "voeding";
    public static final String CARDIOVASCULAIR_MAPPING = "cardiovasculair";
    public static final Integer GEEN_ID = 0;
    public static final Integer GEEN_WEEK = 0;

    //----------------
    // tabel student
    //----------------

    // geeft alle rijen uit de student tabel
    @GetMapping("/student/getAll")
    public ResponseEntity<String> getAllStudent() {
        return GetDelDataBase(STUDENT_MAPPING, "GET", GEEN_ID, GEEN_WEEK);
    }

    // geeft een enkele rij uit de student tabel
    @GetMapping("/student/get")
    public ResponseEntity<String> getStudent(@RequestParam(value = "student_id") Integer student_id) {
        return GetDelDataBase(STUDENT_MAPPING, "GET", student_id, GEEN_WEEK);
    }

    // verwijdert een rij uit de student tabel
    @GetMapping("student/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam(value = "student_id") Integer student_id) {
        return GetDelDataBase(STUDENT_MAPPING, "DELETE", student_id, GEEN_WEEK);
    }

    // voegt een nieuwe rij toe aan de student tabel
    @GetMapping("/student/new")
    public ResponseEntity<String> addStudent(@RequestParam(value = "student_id") Integer student_id, @RequestParam(value = "jaar") Integer jaar) {
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("student_id", student_id);
            object.put("jaar", jaar);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(STUDENT_MAPPING, jsonInputString, GEEN_ID, GEEN_WEEK);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // wijzigt een rij in de student tabel
    @GetMapping("/student/update")
    public ResponseEntity<String> updateStudent(@RequestParam(value = "student_id") Integer student_id, @RequestParam(value = "jaar") Integer jaar) {
        try {
            JSONObject object = new JSONObject();
            if (jaar != null) object.put("jaar", jaar);

            JSONObject superobject = new JSONObject();
            superobject.put("student_id", student_id);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();

            return UpdatePOSTDataBase(STUDENT_MAPPING, jsonInputString, student_id, GEEN_WEEK);
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
        return GetDelDataBase(ALGEMEEN_MAPPING, "GET", GEEN_ID, GEEN_WEEK);
    }

    // geeft de waarde van 1 enkele rij van de algemeen tabel
    @GetMapping("/algemeen/get")
    public ResponseEntity<String> getAlgemeen(@RequestParam(value = "alg_id") Integer alg_id) {
        return GetDelDataBase(ALGEMEEN_MAPPING, "GET", alg_id, GEEN_WEEK);
    }

    // verwijdert een rij in de algemeen tabel
    @GetMapping ("/algemeen/delete")
    public ResponseEntity<String> deleteAlgemeen(@RequestParam(value = "alg_id") Integer alg_id) {
        return GetDelDataBase(ALGEMEEN_MAPPING, "DELETE", alg_id, GEEN_WEEK);
    }

    // voegt een nieuwe rij toe aan de algemeen tabel
    @GetMapping("algemeen/new")
    public ResponseEntity<String> addAlgemeen(@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "lengte_cm") Integer lengte_cm) {
        try {
            JSONObject object = new JSONObject();
            JSONObject superObject = new JSONObject();

            object.put("alg_id", alg_id);
            object.put("week", week);
            object.put("lengte_cm", lengte_cm);


            superObject.put("object", object);
            String jsonInputString = superObject.toString();
            return UpdatePOSTDataBase(ALGEMEEN_MAPPING, jsonInputString, GEEN_ID, GEEN_WEEK);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // aanpassen van rij in algemeen tabel
    @GetMapping("/algemeen/update")
    public ResponseEntity<String> UpdateAlgemeen(@RequestParam(value = "alg_id") Integer alg_id, @RequestParam(value = "week") Integer week,@RequestParam(value = "lengte_cm") Integer lengte_cm) {
        try {
            JSONObject object = new JSONObject();
            if (week != null) object.put("week", week);
            if (lengte_cm != null) object.put("lengte_cm", lengte_cm);

            JSONObject superobject = new JSONObject();
            superobject.put("alg_id", alg_id);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();

            return UpdatePOSTDataBase(ALGEMEEN_MAPPING, jsonInputString, alg_id, GEEN_WEEK);
        } catch (JSONException e) {
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
    public ResponseEntity<String> getAllBeweging() {
        return GetDelDataBase(BEWEGING_MAPPING, "GET", GEEN_ID, GEEN_WEEK);
    }

    // geeft enkele rij uit beweging tabel
    @GetMapping("/beweging/get")
    public ResponseEntity<String> getBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week) {
        return GetDelDataBase(BEWEGING_MAPPING, "GET", bew_id, week);
    }

    // verwijdert gevraagde rij uit beweging tabel
    @GetMapping("/beweging/delete")
    public ResponseEntity<String> deleteBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week) {
        return GetDelDataBase(BEWEGING_MAPPING, "DELETE", bew_id, week);
    }

    // voegt een nieuwe rij toe aan de beweging tabel
    @GetMapping("/beweging/new")
    public ResponseEntity<String> addBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "matig_min") Integer matig_min, @RequestParam(value = "intens_min") Integer intens_min, @RequestParam(value = "strek_min") Integer strek_min, @RequestParam(value = "zit_uur") Integer zit_uur, @RequestParam(value = "stappen_gem") Integer stappen_gem) {
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("bew_id", bew_id);
            object.put("week", week);
            object.put("matig_min", matig_min);
            object.put("intens_min", intens_min);
            object.put("strek_min", strek_min);
            object.put("zit_uur", zit_uur);
            object.put("stappen_gem", stappen_gem);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(BEWEGING_MAPPING,jsonInputString,GEEN_ID,GEEN_WEEK);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // wijzigt een rij in de beweging tabel
    @GetMapping("/beweging/update")
    public ResponseEntity updateBeweging(@RequestParam(value = "bew_id") Integer bew_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "matig_min") Integer matig_min, @RequestParam(value = "intens_min") Integer intens_min, @RequestParam(value = "strek_min") Integer strek_min, @RequestParam(value = "zit_uur") Integer zit_uur, @RequestParam(value = "stappen_gem") Integer stappen_gem) {
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
            return UpdatePOSTDataBase(BEWEGING_MAPPING, jsonInputString, bew_id, week);

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //-----------------------
    // middelengebruik tabel
    //-----------------------

    // vraagt de tabel van middelengebruik op
    @GetMapping("/middelengebruik/getAll")
    public ResponseEntity<String> getAllMiddelengebruik() {
        return GetDelDataBase(MIDDELENGEBRUIK_MAPPING, "GET", GEEN_ID, GEEN_WEEK);
    }

    // vraagt een enkele rij uit de middelengebruik tabel op
    @GetMapping("/middelengebruik/get")
    public ResponseEntity<String> getMiddelengebruik(@RequestParam(value = "geb_id") Integer geb_id, @RequestParam(value = "week") Integer week) {
        return GetDelDataBase(MIDDELENGEBRUIK_MAPPING, "GET", geb_id, week);
    }

    // verwijdert de gevraagde rij uit de middelengebruik tabel
    @GetMapping("/middelengebruik/delete")
    public ResponseEntity<String> deleteMiddelengebruik(@RequestParam(value = "geb_id") Integer geb_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(MIDDELENGEBRUIK_MAPPING, "DELETE", geb_id, week);
    }

    // voegt een nieuwe rij toe aan de middelengebruik tabel
    @GetMapping("/middelengebruik/new")
    public ResponseEntity<String> addMiddelengebruik(@RequestParam(value = "geb_id") Integer geb_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "consum_gem") Integer consum_gem, @RequestParam(value = "roken_gem") Integer roken_gem, @RequestParam(value = "drugs_gem") Integer drugs_gem){
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("geb_id", geb_id);
            object.put("week", week);
            object.put("consum_gem", consum_gem);
            object.put("roken_gem", roken_gem);
            object.put("drugs_gem", drugs_gem);


            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(MIDDELENGEBRUIK_MAPPING,jsonInputString,GEEN_ID,GEEN_WEEK);
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // Update een rij in de middelengebruik tabel
    @GetMapping("/middelengebruik/update")
    public ResponseEntity<String> updateMiddelengebruik(@RequestParam(value = "geb_id") Integer geb_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "consum_gem") Integer consum_gem, @RequestParam(value = "roken_gem") Integer roken_gem, @RequestParam(value = "drugs_gem") Integer drugs_gem){
        try {
            JSONObject object = new JSONObject();
            if (consum_gem != null) object.put("consum_gem", consum_gem);
            if (roken_gem != null) object.put("roken_gem", roken_gem);
            if (drugs_gem != null) object.put("drugs_gem", drugs_gem);

            JSONObject superobject = new JSONObject();
            superobject.put("geb_id", geb_id);
            superobject.put("week", week);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(MIDDELENGEBRUIK_MAPPING, jsonInputString, geb_id, week);

        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //-----------------------------
    // subjectieve ervaringen tabel
    //------------------------------

    // vraagt de subjectieve ervaringen tabel op
    @GetMapping("/subjectieve_ervaringen/getAll")
        public ResponseEntity<String> getAllSE(){
        return GetDelDataBase(SE_MAPPING,"GET", GEEN_ID, GEEN_WEEK);
    }

    // vraagt een enkele rij uit de subjectieve ervaringen tabel op
    @GetMapping("/subjectieve_ervaringen/get")
    public ResponseEntity<String>getSE(@RequestParam(value = "se_id") Integer se_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(SE_MAPPING,"GET", se_id, week);
    }

    // verwijdert een rij uit de subjectieve ervaringen tabel
    @GetMapping("/subjectieve_ervaringen/delete")
    public ResponseEntity<String> deleteSE(@RequestParam(value = "se_id") Integer se_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(SE_MAPPING, "DELETE", se_id, week);
    }

    // voegt een nieuwe rij toe aan de subjectieve ervaringen tabel
    @GetMapping("/subjectieve_ervaringen/new")
    public ResponseEntity<String> addSE(@RequestParam(value = "se_id") Integer se_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "stress_sch") Integer stress_sch, @RequestParam(value = "vermoeid_sch") Integer vermoeid_sch, @RequestParam(value = "vitaal_sch") Integer vitaal_sch, @RequestParam(value = "tevr_stud_sch") Integer tevr_stud_sch, @RequestParam(value = "druk_stud_sch") Integer druk_stud_sch, @RequestParam(value = "tevr_leef_sch") Integer tevr_leef_sch, @RequestParam(value = "tevr_soci_sch") Integer tevr_soci_sch,@RequestParam(value = "tevr_werk_sch") Integer tevr_werk_sch, @RequestParam(value = "druk_werk_sch") Integer druk_werk_sch, @RequestParam(value = "tevr_rust_sch") Integer tevr_rust_sch, @RequestParam(value = "tevr_hobb_sch") Integer tevr_hobb_sch){
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("se_id", se_id);
            object.put("week", week);
            object.put("stress_sch", stress_sch);
            object.put("vermoeid_sch", vermoeid_sch);
            object.put("vitaal_sch", vitaal_sch);
            object.put("tevr_stud_sch", tevr_stud_sch);
            object.put("druk_stud_sch", druk_stud_sch);
            object.put("tevr_leef_sch", tevr_leef_sch);
            object.put("tevr_soci_sch", tevr_soci_sch);
            object.put("tevr_werk_sch", tevr_werk_sch);
            object.put("druk_werk_sch", druk_werk_sch);

            object.put("tevr_rust_sch", tevr_rust_sch);
            object.put("tevr_hobb_sch", tevr_hobb_sch);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(SE_MAPPING, jsonInputString, GEEN_ID, GEEN_WEEK);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // wijzigt een rij uit de subjectieve ervaringen tabel
    @GetMapping("/subjectieve_ervaringen/update")
    public ResponseEntity<String> updateSE(@RequestParam(value = "se_id") Integer se_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "stress_sch") Integer stress_sch, @RequestParam(value = "vermoeid_sch") Integer vermoeid_sch, @RequestParam(value = "vitaal_sch") Integer vitaal_sch, @RequestParam(value = "tevr_stud_sch") Integer tevr_stud_sch, @RequestParam(value = "druk_stud_sch") Integer druk_stud_sch, @RequestParam(value = "tevr_leef_sch") Integer tevr_leef_sch, @RequestParam(value = "tevr_soci_sch") Integer tevr_soci_sch,@RequestParam(value = "tevr_werk_sch") Integer tevr_werk_sch, @RequestParam(value = "druk_werk_sch") Integer druk_werk_sch, @RequestParam(value = "tevr_rust_sch") Integer tevr_rust_sch, @RequestParam(value = "tevr_hobb_sch") Integer tevr_hobb_sch) {
        try {
            JSONObject object = new JSONObject();
            if (stress_sch != null) object.put("stress_sch", stress_sch);
            if (vermoeid_sch != null) object.put("vermoeid_sch", vermoeid_sch);
            if (vitaal_sch != null) object.put("vitaal_sch", vitaal_sch);
            if (tevr_stud_sch != null) object.put("tevr_stud_sch", tevr_stud_sch);
            object.put("druk_stud_sch", druk_stud_sch);
            if (tevr_leef_sch != null) object.put("tevr_leef_sch", tevr_leef_sch);
            if (tevr_soci_sch != null) object.put("tevr_soci_sch", tevr_soci_sch);
            object.put("tevr_werk_sch", tevr_werk_sch);
            object.put("druk_werk_sch", druk_werk_sch);

            if (tevr_rust_sch != null) object.put("tevr_rust_sch", tevr_rust_sch);
            if (tevr_hobb_sch != null) object.put("tevr_hobb_sch", tevr_hobb_sch);

            JSONObject superobject = new JSONObject();
            superobject.put("se_id", se_id);
            superobject.put("week", week);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(SE_MAPPING, jsonInputString, se_id, week);

        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //--------------------
    // tijdbesteding tabel
    //---------------------

    // vraagt de gehele tijdbesteding tabel op
    @GetMapping("/tijdbesteding/getAll")
    public ResponseEntity<String> getAllTijdbesteding(){
        return GetDelDataBase(TIJDBESTEDING_MAPPING,"GET", GEEN_ID, GEEN_WEEK);
    }

    // vraagt enkele rij uit tijdbesteding tabel op
    @GetMapping("/tijdbesteding/get")
    public ResponseEntity<String> getTijdbesteding(@RequestParam(value = "tbd_id") Integer tbd_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(TIJDBESTEDING_MAPPING, "GET", tbd_id, week);
    }

    // verwijdert rij uit tijdbesteding tabel
    @GetMapping("/tijdbesteding/delete")
    public ResponseEntity<String> deleteTijdbesteding(@RequestParam(value = "tbd_id") Integer tbd_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(TIJDBESTEDING_MAPPING, "DELETE", tbd_id, week);
    }

    // maakt een nieuwe rij in tijdbesteding tabel aan
    @GetMapping("/tijdbesteding/new")
    public ResponseEntity<String> addTijdbesteding(@RequestParam(value = "tbd_id") Integer tbd_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "slaap_gem_uur") Integer slaap_gem_uur, @RequestParam(value = "sociaal_gem_uur") Integer sociaal_gem_uur, @RequestParam(value = "hobby_gem_uur") Integer hobby_gem_uur, @RequestParam(value = "studie_gem_uur") Integer studie_gem_uur, @RequestParam(value = "werk_gem_uur") Integer werk_gem_uur){
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("tbd_id", tbd_id);
            object.put("week", week);
            object.put("slaap_gem_uur", slaap_gem_uur);
            object.put("sociaal_gem_uur", sociaal_gem_uur);
            object.put("hobby_gem_uur", hobby_gem_uur);
            object.put("studie_gem_uur", studie_gem_uur);
            object.put("werk_gem_uur", werk_gem_uur);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(TIJDBESTEDING_MAPPING, jsonInputString, GEEN_ID, GEEN_WEEK);

        }catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // wijzigt een rij in de tijdbesteding tabel
    @GetMapping("/tijdbesteding/update")
    public ResponseEntity<String> updateTijdbesteding(@RequestParam(value = "tbd_id") Integer tbd_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "slaap_gem_uur") Integer slaap_gem_uur, @RequestParam(value = "sociaal_gem_uur") Integer sociaal_gem_uur, @RequestParam(value = "hobby_gem_uur") Integer hobby_gem_uur, @RequestParam(value = "studie_gem_uur") Integer studie_gem_uur, @RequestParam(value = "werk_gem_uur") Integer werk_gem_uur){
        try {
            JSONObject object = new JSONObject();
            if (slaap_gem_uur != null) object.put("slaap_gem_uur", slaap_gem_uur);
            if (sociaal_gem_uur != null) object.put("sociaal_gem_uur", sociaal_gem_uur);
            if (hobby_gem_uur != null) object.put("hobby_gem_uur", hobby_gem_uur);
            object.put("studie_gem_uur", studie_gem_uur);
            object.put("werk_gem_uur", werk_gem_uur);


            JSONObject superobject = new JSONObject();
            superobject.put("tbd_id", tbd_id);
            superobject.put("week", week);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(TIJDBESTEDING_MAPPING, jsonInputString,tbd_id, week);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //------------------
    // voeding tabel
    //------------------

    @GetMapping("/voeding/getAll")
    public ResponseEntity<String> getAllVoeding(){
        return GetDelDataBase(VOEDING_MAPPING, "GET", GEEN_ID, GEEN_WEEK);
    }

    @GetMapping("/voeding/get")
    public ResponseEntity<String> getVoeding(@RequestParam(value = "voed_id") Integer voed_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(VOEDING_MAPPING, "GET", voed_id, week);
    }

    @GetMapping("/voeding/delete")
    public  ResponseEntity<String> deleteVoeding(@RequestParam(value = "voed_id") Integer voed_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(VOEDING_MAPPING,"DELETE", voed_id, week);
    }

    @GetMapping("/voeding/new")
    public ResponseEntity<String> addVoeding(@RequestParam(value = "voed_id") Integer voed_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "groente_gem") Integer groente_gem, @RequestParam(value = "fruit_por") Integer fruit_por, @RequestParam(value = "fris_sap_gls") Integer fris_sap_gls, @RequestParam(value = "ong_snack") Integer ong_snack, @RequestParam(value = "kant_klaar") Integer kant_klaar, @RequestParam(value = "vlees_vogel") Integer vlees_vogel, @RequestParam(value = "vis") Integer vis, @RequestParam(value = "gezond_sch") Integer gezond_sch){
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("voed_id", voed_id);
            object.put("week", week);
            object.put("groente_gem", groente_gem);
            object.put("fruit_por", fruit_por);
            object.put("fris_sap_gls", fris_sap_gls);
            object.put("ong_snack", ong_snack);
            object.put("kant_klaar", kant_klaar);
            object.put("vlees_vogel", vlees_vogel);
            object.put("vis", vis);
            object.put("gezond_sch", gezond_sch);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(VOEDING_MAPPING,jsonInputString,GEEN_ID,GEEN_WEEK);

        }catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    @GetMapping("/voeding/update")
    public ResponseEntity<String> updateVoeding(@RequestParam(value = "voed_id") Integer voed_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "groente_gem") Integer groente_gem, @RequestParam(value = "fruit_por") Integer fruit_por, @RequestParam(value = "fris_sap_gls") Integer fris_sap_gls, @RequestParam(value = "ong_snack") Integer ong_snack, @RequestParam(value = "kant_klaar") Integer kant_klaar, @RequestParam(value = "vlees_vogel") Integer vlees_vogel, @RequestParam(value = "vis") Integer vis, @RequestParam(value = "gezond_sch") Integer gezond_sch){
        try {
            JSONObject object = new JSONObject();
            if (groente_gem != null) object.put("groente_gem", groente_gem);
            if (fruit_por != null) object.put("fruit_por", fruit_por);
            if (fris_sap_gls != null) object.put("fris_sap_gls", fris_sap_gls);
            if (ong_snack != null) object.put("ong_snack", ong_snack);
            if (kant_klaar != null) object.put("kant_klaar", kant_klaar);
            if (vlees_vogel != null) object.put("vlees_vogel", vlees_vogel);
            if (vis != null) object.put("vis", vis);
            if (gezond_sch != null) object.put("gezond_sch", gezond_sch);

            JSONObject superobject = new JSONObject();
            superobject.put("voed_id", voed_id);
            superobject.put("week", week);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(VOEDING_MAPPING, jsonInputString, voed_id, week);

        }catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //----------------------
    // Cardiovascular tabel
    //-----------------------

    // vraagt de gehele cardiovasculair tabel op
    @GetMapping("/cardiovasculair/getAll")
    public ResponseEntity<String> getAllCardiovasculair(){
        return GetDelDataBase(CARDIOVASCULAIR_MAPPING, "GET", GEEN_ID, GEEN_WEEK);
    }

    // vraagt een enkele rij op in de cardiovasculair tabel
    @GetMapping("/cardiovasculair/get")
    public ResponseEntity<String> getCardiovasculair(@RequestParam(value = "car_id") Integer car_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(CARDIOVASCULAIR_MAPPING, "GET", car_id, week);
    }

    // verwijdert een rij in de cardiovasculair tabel
    @GetMapping("/cardiovasculair/delete")
    public ResponseEntity<String> deleteCardiovasculair(@RequestParam(value = "car_id") Integer car_id, @RequestParam(value = "week") Integer week){
        return GetDelDataBase(CARDIOVASCULAIR_MAPPING, "DELETE", car_id, week);
    }

    // voegt een nieuwe rij toe in de cardiovasculair tabel
    @GetMapping("/cardiovasculair/new")
    public ResponseEntity<String> addCardiovasculair(@RequestParam(value = "car_id") Integer car_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "gewicht") Integer gewicht, @RequestParam(value = "bovengrens") Integer bovengrens, @RequestParam(value = "ondergrens") Integer ondergrens, @RequestParam(value = "hartfrequentie") Integer hartfrequentie, @RequestParam(value = "apparaat_bloed") String apparaat_bloed, @RequestParam(value = "apparaat_hart") String apparaat_hart){
        try {
            JSONObject object = new JSONObject();
            JSONObject superobject = new JSONObject();

            object.put("car_id", car_id);
            object.put("week", week);
            object.put("gewicht", gewicht);
            object.put("bovengrens", bovengrens);
            object.put("ondergrens", ondergrens);
            object.put("hartfrequentie", hartfrequentie);
            object.put("apparaat_bloed", apparaat_bloed);
            object.put("apparaat_hart", apparaat_hart);

            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(CARDIOVASCULAIR_MAPPING, jsonInputString, GEEN_ID, GEEN_WEEK);
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    // wijzigt een rij in de cardiovasculair tabel
    @GetMapping("/cardiovasculair/update")
    public ResponseEntity<String> updateCardiovasculair(@RequestParam(value = "car_id") Integer car_id, @RequestParam(value = "week") Integer week, @RequestParam(value = "gewicht") Integer gewicht, @RequestParam(value = "bovengrens") Integer bovengrens, @RequestParam(value = "ondergrens") Integer ondergrens, @RequestParam(value = "hartfrequentie") Integer hartfrequentie, @RequestParam(value = "apparaat_bloed") String apparaat_bloed, @RequestParam(value = "apparaat_hart") String apparaat_hart){
        try {
            JSONObject object = new JSONObject();
            object.put("gewicht", gewicht);
            object.put("bovengrens", bovengrens);
            object.put("ondergrens", ondergrens);
            if (hartfrequentie != null) object.put("hartfrequentie", hartfrequentie);
            object.put("apparaat_bloed", apparaat_bloed);
            object.put("apparaat_hart", apparaat_hart);

            JSONObject superobject = new JSONObject();
            superobject.put("car_id", car_id);
            superobject.put("week", week);
            superobject.put("object", object);
            String jsonInputString = superobject.toString();
            return UpdatePOSTDataBase(CARDIOVASCULAIR_MAPPING,jsonInputString, car_id, week);

        } catch (JSONException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Fail", HttpStatus.valueOf(500));
        }
    }

    //--------------------------------------------
    // csv file maken
    //--------------------------------------------
    @GetMapping("tabellentocsv/post")
    public String DatatoCSV(@RequestParam(value = "is_in") String is_in){
        System.out.println(is_in);
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(new File("src/main/resources/static","CSstudentData.csv")));
            JSONObject StudentID = new JSONObject((getAllStudent().getBody()));
            JSONArray Studenten = StudentID.getJSONArray("student");

            String[] header = {"ID", "jaar","week", "lengte_cm", "matig_min", "intens_min", "strek_min", "zit_uur", "stappen_gem", "consum_gem", "roken_gem", "drugs_gem", "groente_gem", "fruit_por", "fris_sap_gls", "ong_snack", "kant_klaar", "vlees_vogel", "vis", "gezond_sch", "slaap_gem_uur", "sociaal_gem_uur", "hobby_gem_uur", "studie_gem_uur", "werk_gem_uur", "stress_sch", "vermoeid_sch", "vitaal_sch", "tevr_stud_sch", "druk_stud_sch","tevr_leef_sch", "tevr_soci_sch", "druk_werk_sch", "tevr_werk_sch","tevr_rust_sch", "tevr_hobb_sch", "gewicht", "bovendruk", "onderdruk", "hartfrequentie", "apparaat_bloeddruk", "apparaat_hartslag"};
            writer.writeNext(header);

            for (int i = 0; i<=Studenten.length();i++){
                for (int j = 1; j<=6;j++){
                    JSONObject Student = new JSONObject(String.valueOf(Studenten.getJSONObject(i)));
                    Integer id = Integer.valueOf(Student.getString("student_id"));
                    String jaar = Student.getString("jaar");
                    Integer week = j;

                    JSONObject Algemeen = new JSONObject((getAlgemeen(id).getBody()));

                    String lengte_cm = null;
                    if (!Algemeen.isNull("algemeen_by_pk")){
                        JSONObject algemeen = Algemeen.getJSONObject("algemeen_by_pk");
                        lengte_cm = algemeen.getString("lengte_cm");
                    }

                    JSONObject Beweging = new JSONObject((getBeweging(id,week).getBody()));
                    String matig_min = null;
                    String intens_min = null;
                    String strek_min = null;
                    String zit_uur = null;
                    String stappen_gem = null;
                    if (!Beweging.isNull("beweging_by_pk")) {
                        JSONObject beweging = Beweging.getJSONObject("beweging_by_pk");
                        matig_min = beweging.getString("matig_min");
                        intens_min = beweging.getString("intens_min");
                        strek_min = beweging.getString("strek_min");
                        zit_uur = beweging.getString("zit_uur");
                        stappen_gem = beweging.getString("stappen_gem");
                    }

                    JSONObject Middelengebruik = new JSONObject((getMiddelengebruik(id,week).getBody()));
                    String consum_gem = null;
                    String roken_gem = null;
                    String drugs_gem = null;
                    if (!Middelengebruik.isNull("middelengebruik_by_pk")) {
                        JSONObject middelengebruik = Middelengebruik.getJSONObject("middelengebruik_by_pk");
                        consum_gem = middelengebruik.getString("consum_gem");
                        roken_gem = middelengebruik.getString("roken_gem");
                        drugs_gem = middelengebruik.getString("drugs_gem");
                    }

                    JSONObject Voeding = new JSONObject((getVoeding(id, week).getBody()));
                    String groente_gem = null;
                    String fruit_por = null;
                    String fris_sap_gls = null;
                    String ong_snack = null;
                    String kant_klaar = null;
                    String vlees_vogel = null;
                    String vis = null;
                    String gezond_sch = null;
                    if (!Voeding.isNull("voeding_by_pk")) {
                        JSONObject voeding = Voeding.getJSONObject("voeding_by_pk");
                        groente_gem = voeding.getString("groente_gem");
                        fruit_por = voeding.getString("fruit_por");
                        fris_sap_gls = voeding.getString("fris_sap_gls");
                        ong_snack = voeding.getString("ong_snack");
                        kant_klaar = voeding.getString("kant_klaar");
                        vlees_vogel = voeding.getString("vlees_vogel");
                        vis = voeding.getString("vis");
                        gezond_sch = voeding.getString("gezond_sch");
                    }

                    JSONObject Tijdbesteding = new JSONObject(getTijdbesteding(id,week).getBody());
                    String slaap_gem_uur = null;
                    String sociaal_gem_uur = null;
                    String hobby_gem_uur = null;
                    String studie_gem_uur = null;
                    String werk_gem_uur = null;
                    if (!Tijdbesteding.isNull("tijdbesteding_by_pk")){
                        JSONObject tijdbesteding = Tijdbesteding.getJSONObject("tijdbesteding_by_pk");
                        slaap_gem_uur = tijdbesteding.getString("slaap_gem_uur");
                        sociaal_gem_uur = tijdbesteding.getString("sociaal_gem_uur");
                        hobby_gem_uur = tijdbesteding.getString("hobby_gem_uur");
                        studie_gem_uur = tijdbesteding.getString("studie_gem_uur");
                        werk_gem_uur = tijdbesteding.getString("werk_gem_uur");
                    }


                    JSONObject SE = new JSONObject(getSE(id,week).getBody());
                    String stress_sch = null;
                    String vermoeid_sch = null;
                    String vitaal_sch = null;
                    String tevr_stud_sch = null;
                    String druk_stud_sch = null;
                    String tevr_leef_sch = null;
                    String tevr_soci_sch = null;
                    String druk_werk_sch = null;
                    String tevr_werk_sch = null;
                    String tevr_rust_sch = null;
                    String tevr_hobb_sch = null;
                    if (!SE.isNull("subjectieve_ervaringen_by_pk")) {
                        JSONObject se = SE.getJSONObject("subjectieve_ervaringen_by_pk");
                        stress_sch = se.getString("stress_sch");
                        vermoeid_sch = se.getString("vermoeid_sch");
                        vitaal_sch = se.getString("vitaal_sch");
                        tevr_stud_sch = se.getString("tevr_stud_sch");
                        druk_stud_sch = se.getString("druk_stud_sch");
                        tevr_leef_sch = se.getString("tevr_leef_sch");
                        tevr_soci_sch = se.getString("tevr_soci_sch");
                        druk_werk_sch = se.getString("druk_werk_sch");
                        tevr_werk_sch = se.getString("tevr_werk_sch");
                        tevr_rust_sch = se.getString("tevr_rust_sch");
                        tevr_hobb_sch = se.getString("tevr_hobb_sch");
                    }

                    JSONObject Cardiovasculair = new JSONObject(getCardiovasculair(id,week).getBody());
                    String gewicht = null;
                    String bovengrens = null;
                    String ondergrens = null;
                    String hartfrequentie = null;
                    String apparaat_bloed = null;
                    String apparaat_hart = null;
                    if (!Cardiovasculair.isNull("cardiovasculair_by_pk")) {
                        JSONObject cardiovasculair = Cardiovasculair.getJSONObject("cardiovasculair_by_pk");
                        gewicht = cardiovasculair.getString("gewicht");
                        bovengrens = cardiovasculair.getString("bovengrens");
                        ondergrens = cardiovasculair.getString("ondergrens");
                        hartfrequentie = cardiovasculair.getString("hartfrequentie");
                        apparaat_bloed = cardiovasculair.getString("apparaat_bloed");
                        apparaat_hart = cardiovasculair.getString("apparaat_hart");
                    }

                    String[] Studentdata = {String.valueOf(id), jaar, String.valueOf(week), lengte_cm, matig_min, intens_min, strek_min, zit_uur, stappen_gem, consum_gem, roken_gem, drugs_gem, groente_gem, fruit_por, fris_sap_gls, ong_snack, kant_klaar, vlees_vogel, vis, gezond_sch, slaap_gem_uur, sociaal_gem_uur, hobby_gem_uur, studie_gem_uur, werk_gem_uur,stress_sch, vermoeid_sch, vitaal_sch, tevr_stud_sch, druk_stud_sch, tevr_leef_sch, tevr_soci_sch, druk_werk_sch, tevr_werk_sch, tevr_rust_sch, tevr_hobb_sch, gewicht, bovengrens, ondergrens, hartfrequentie, apparaat_bloed, apparaat_hart };
                    writer.writeNext(Studentdata);
                }
            }

            writer.close();
        }catch (IOException | JSONException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "test";
    }

}
