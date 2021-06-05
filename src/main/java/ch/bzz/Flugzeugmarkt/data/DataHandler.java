package ch.bzz.Flugzeugmarkt.data;

import ch.bzz.Flugzeugmarkt.model.Airline;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;
import ch.bzz.Flugzeugmarkt.model.Hersteller;
import ch.bzz.Flugzeugmarkt.service.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

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
        readJSON();
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


    //Methoden

    /**
     * Diese Methode ist nötig, da ich aus irgendeinem Grund, nicht direkt das Map-Objekt in den Servieklassen returnen kann. (500 - internal Servererror, irgendwo am Arsch von Glassfish)
     * Diese Methode konvertriert eine Map in JSON-Format und gibt diese als String zurück.
     * @param map
     * @return JSON-Format der Map, aber als String
     * @throws JsonProcessingException
     */
    public static String stringVonJSON(HashMap map) {
        String output = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e){
            //nichts
        }
        finally {
            return output;
        }

    }


    /**
     * List das JSON File und wandelt dieses in Objekte um.
     * Es kann eigenständig alleine aus dem flugzeug.json File, alle Flugzeuge, Airlines mit deren Flugzeugen und Hersteller mit deren Flugzeugen herauslesen.
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("flugzeugJSON")));
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
                            // da die richtige noch ein Vector mit Flugzeugen hat, die neue aber nicht
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
                            herstellerMap.put(flugzeug.getHersteller().getHerstellerUUID(), neuerHersteller);   //Fügt den errstellten Hersteller der Map hinzu
                        }
                        else {
                            //es gibt keinen Hersteller
                        }
                    }
                    if (gehoertHersteller && herstellerMap.containsKey(flugzeug.getHersteller().getHerstellerUUID())){      //Wenn das Flugzeug dem Hersteller gehört & der Hersteller exsistiert
                        herstellerMap.get(flugzeug.getHersteller().getHerstellerUUID()).addZuverkaufendeFlugzeuge(flugzeug);
                    }

                    flugzeugMap.put(flugzeug.getFlugzeugUUID(), flugzeug);      //fügt das flugzeugobjekt in den Flugzeug-Vektor hinzu

                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeJSON(){
        //bla bla
    }


}
