package ch.bzz.Flugzeugmarkt.data;

import ch.bzz.Flugzeugmarkt.model.Airline;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;
import ch.bzz.Flugzeugmarkt.model.Hersteller;
import ch.bzz.Flugzeugmarkt.service.Config;
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
    //private static HashMap<String, Vector<Flugzeug>> airlineMap;
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
    }


    //Methoden


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
                try {
                    flugzeugMap.put(flugzeug.getFlugzeugUUID(), flugzeug);      //fügt das flugzeugobjekt in den Flugzeug-Vektor hinzu
                    if (airlineMap.get(flugzeug.getAirline().getAirlineUUID()) != null){        //schaut, ob die Airline schon existiert
                        flugzeug.getAirline().addFlugzeug(flugzeug);            //nimmt aus JSON das flugzeug, nimmt die im JSON geschriebene Airline und fügt das JSON-Flugzeug in die Airline hinzu
                        airlineMap.put(flugzeug.getAirline().getAirlineUUID(), flugzeug.getAirline());   //aktualisiert die Liste
                    }
                    else if (flugzeug.getAirline() != null){          //wenn Airline nicht null ist (= wenn es in der Liste des Herstellers steht, anstatt einer Airline)
                        Airline neueAirline = flugzeug.getAirline();    //nimmt das Airlineobjekt aus JSON
                        neueAirline.addFlugzeug(flugzeug);          //fügt das neue Flugzeug der Flotte hinzu
                        airlineMap.put(flugzeug.getAirline().getAirlineUUID(), neueAirline);     //Fügt die neu erstellte Airline der Map hinzu
                    }
                    else {      //wenn das Flugzeug noch in der Liste des Herstellers ist
                        if (herstellerMap.containsKey(flugzeug.getHersteller().getHerstellerUUID())){       //wenn der Hersteller bereits exisitert
                            herstellerMap.get(flugzeug.getHersteller().getHerstellerUUID()).addZuverkaufendeFlugzeuge(flugzeug);
                        }
                        else {      // Wenn der Hersteller noch nicht existiert
                            Hersteller neuerHersteller = flugzeug.getHersteller();  //nimmt das Herstellerobjekt aus dem JSON
                            neuerHersteller.addZuverkaufendeFlugzeuge(flugzeug);    //fügt es dem Lager des Herstellers hinzu
                            herstellerMap.put(flugzeug.getHersteller().getHerstellerUUID(), neuerHersteller);
                        }
                    }

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
