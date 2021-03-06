package ch.bzz.Flugzeugmarkt.data;

import ch.bzz.Flugzeugmarkt.model.Airline;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;
import ch.bzz.Flugzeugmarkt.model.Hersteller;
import ch.bzz.Flugzeugmarkt.service.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

/**
 * Datahandler für das Lesen und Schreiben von JSON-Dateien
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static HashMap<String, Airline> airlineMap;
    private static HashMap<String, Hersteller> herstellerMap;
    private static HashMap<String, Flugzeug> flugzeugMap;


    //Konstruktoren

    /**
     * Defaultkonstruktor mit Instanzierungen
     */
    private DataHandler(){
        airlineMap = new HashMap<>();
        herstellerMap = new HashMap<>();
        flugzeugMap = new HashMap<>();
        readJSON("flugzeugJSON");
    }


    //Getter & Setter

    /**
     * gibt die DataHandler-Instanz zurück
     * @return DataHandler
     */
    public static DataHandler getInstance() {
        return instance;
    }

    /**
     * gibt die AirlineMap zurück
     * @return airlineMap
     */
    public static HashMap<String, Airline> getAirlineMap() {
        return airlineMap;
    }

    /**
     * gibt die HerstellerMap zurück
     * @return herstellerMap
     */
    public static HashMap<String, Hersteller> getHerstellerMap() {
        return herstellerMap;
    }

    /**
     * gibt die FlugzeugMap zurück
     * @return flugzeugMap
     */
    public static HashMap<String, Flugzeug> getFlugzeugMap() {
        return flugzeugMap;
    }

    /**
     * gibt ein Airlineobjekt der airlineMap zurück.
     * @param airlineUUID
     * @return Airline
     */
    public static Airline getAirline (String airlineUUID) {
        return getAirlineMap().get(airlineUUID);
    }

    /**
     * gibt ein Herstellerobjekt der herstellerMap zurück.
     * @param herstellerUUID
     * @return Hersteller
     */
    public static Hersteller getHersteller (String herstellerUUID) {
        return getHerstellerMap().get(herstellerUUID);
    }

    /**
     * gibt ein Flugzeugobjekt der FlugzeugMap zurück.
     * @param flugzeugUUID
     * @return Flugzeug
     */
    public static Flugzeug getFlugzeug (String flugzeugUUID){
        return getFlugzeugMap().get(flugzeugUUID);
    }

    /**
     * fügt ein Flugzeug der flugzeugMap hinzu
     * @param flugzeug
     */
    public static void insertFlugzeug(Flugzeug flugzeug){
        getFlugzeugMap().put(flugzeug.getFlugzeugUUID(), flugzeug);
        writeJSON();
    }

    /**
     * entfernt ein Flugzeug aus der HashMap
     * @param flugzeugUUID
     */
    public static void rmFlugzeug(String flugzeugUUID){
        UUID.fromString(flugzeugUUID);
        getFlugzeugMap().remove(flugzeugUUID);
        writeJSON();
    }

    /**
     * fügt eine Airline der airlineMap hinzu
     * @param airline
     */
    public static void insertAirline(Airline airline){
        getAirlineMap().put(airline.getAirlineUUID(), airline);
    }

    /**
     * entfernt eine Airline aus der HashMap
     * @param airlineUUID
     */
    public static void rmAirline(String airlineUUID){
        UUID.fromString(airlineUUID);
        getAirlineMap().remove(airlineUUID);
        writeJSON();
    }

    /**
     * fügt einen Hersteller der herstellerMap hinzu
     * @param hersteller
     */
    public static void insertHersteller(Hersteller hersteller){
        getHerstellerMap().put(hersteller.getHerstellerUUID(), hersteller);
    }

    /**
     * entfernt einen Hersteller aus der HashMap
     * @param herstellerUUID
     */
    public static void rmHersteller(String herstellerUUID){
        UUID.fromString(herstellerUUID);
        getHerstellerMap().remove(herstellerUUID);
        writeJSON();
    }


    //Methoden

    /**
     * Diese Methode ist nötig, da sich im Speicher das JSON unendlich lang hin und her referenziert.
     * Dies kann zwar im Speicher passieren, kann aber nicht so ausgedruckt werden.
     * Diese Methode schneidet die problematische Referenzierung ab, sodass das JSON ausgedruckt werden kann.
     * @return JSON-Format der Map, aber als String
     * @throws JsonProcessingException
     */
    public static String stringVonJSON(Object obj) {
        String output = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        finally {
            return output;
        }

    }


    /**
     * Diese Methode setzt die gespeicherten Daten zurück
     * Sie wird für das Testing gebraucht
     */
    public static void restoreData(){
        airlineMap = new HashMap<>();
        herstellerMap = new HashMap<>();
        flugzeugMap = new HashMap<>();
        readJSON("backupFlugzeugJSON");
    }


    /**
     * List das JSON File und wandelt dieses in Objekte um.
     * Es kann eigenständig alleine aus dem flugzeug.json File, alle Flugzeuge, Airlines mit deren Flugzeugen und Hersteller mit deren Flugzeugen herauslesen.
     */
    private static void readJSON(String filename) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty(filename)));
            ObjectMapper objectMapper = new ObjectMapper();
            Flugzeug[] flugzeuge = objectMapper.readValue(jsonData, Flugzeug[].class);

            for (Flugzeug flugzeug : flugzeuge) {
                Boolean gehoertHersteller = false;
                try {
                    if (flugzeug.getAirline() != null){        //schaut, ob eine Airline im JSON existiert
                        Airline neueAirline = flugzeug.getAirline();    //nimmt das Airlineobjekt aus der JSON-Datei
                        if (airlineMap.get(neueAirline.getAirlineUUID()) != null){      //schaut ob die Airline bereits existiert
                            neueAirline = airlineMap.get(neueAirline.getAirlineUUID());     //holt sich die Referenz der bereits existierenden Airline
                            flugzeug.setAirline(airlineMap.get(neueAirline.getAirlineUUID()));  //GANZ WICHTIG, dem neuen Flugzeugobjekt muss die Referenz der alten Airline übergegeben werden,
                            // da er sonst die bei ihm im JSON gespeicherte Airline nehmen würde. Dies hat zur Folge, dass nicht die gleiche Airline gespeichert wird,
                            // da die richtige noch eine Hashmap mit Flugzeugen hat, die neue aber nicht
                        }
                        neueAirline.addFlugzeug(flugzeug);          //fügt das neue Flugzeug der Flotte hinzu
                        airlineMap.put(neueAirline.getAirlineUUID(), neueAirline);
                    }
                    else if (flugzeug.getAirline() == null && flugzeug.getHersteller() != null){          //wenn Airline null ist (= wenn es in der Liste des Herstellers steht, anstatt einer Airline)
                        gehoertHersteller = true;
                    }

                    if (!herstellerMap.containsKey(flugzeug.getHersteller().getHerstellerUUID())){      //wenn der Hersteller noch nicht existiert
                        if (flugzeug.getHersteller() != null){          //Wenn der Hersteller im JSON vorhanden ist
                            Hersteller neuerHersteller = flugzeug.getHersteller();  //nimmt das Herstellerobjekt aus dem JSON
                            neuerHersteller.addZuverkaufendeFlugzeuge(flugzeug);    //fügt sich selber der Liste hinzu
                            herstellerMap.put(neuerHersteller.getHerstellerUUID(), neuerHersteller);   //Fügt den errstellten Hersteller der Map hinzu
                        }
                        else {
                            //es gibt keinen Hersteller
                        }
                    }
                    if (gehoertHersteller && herstellerMap.containsKey(flugzeug.getHersteller().getHerstellerUUID())){      //Wenn das Flugzeug dem Hersteller gehört & der Hersteller exsistiert
                        Hersteller hersteller = flugzeug.getHersteller();
                        hersteller = herstellerMap.get(hersteller.getHerstellerUUID());
                        flugzeug.setHersteller(herstellerMap.get(hersteller.getHerstellerUUID()));
                        herstellerMap.get(flugzeug.getHersteller().getHerstellerUUID()).addZuverkaufendeFlugzeuge(flugzeug);
                    }

                    flugzeugMap.put(flugzeug.getFlugzeugUUID(), flugzeug);      //fügt das flugzeugobjekt in die Flugzeug-Hashmap hinzu

                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //originale Version der writeJSON()-Methode von
    //https://github.com/bzz-fgict/Lektion06/blob/06_view/src/main/java/ch/bzz/bookshelf/data/DataHandler.java
    /**
     * Schreibt die Flugzeuge in das JSON
     */
    private static void writeJSON() {
        //Diese Methode funktioniert nicht richtig, es speichert es irgenwo zwischen, aber nie wirklich ins JSON

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("flugzeugJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getFlugzeugMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
