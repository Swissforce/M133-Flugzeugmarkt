package ch.bzz.Flugzeugmarkt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.HashMap;
import java.util.UUID;

/**
 * Modelklasse der Airline
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

public class Airline {
    @FormParam("name")
    @Size(min = 3, max = 50, message = "Der Name der Airline muss 3-50 Zeichen lang sein")
    private String name;

    @FormParam("airlineUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}", message = "Die UUID der Airline muss eine valide UUID sein")
    private String airlineUUID;

    @FormParam("gruendungsdatum")
    @Size(min = 4, max = 4, message = "Das Gründungsdatum der Airline muss im Jahresformat sein")
    private String gruendungsdatum;

    @JsonIgnore
    private HashMap<String, Flugzeug> flugzeuge;


    //Konstruktoren

    /**
     * ausführlicher Konstruktor
     * @param name
     * @param airlineUUID
     * @param gruendungsdatum
     * @throws IllegalArgumentException
     */
    public Airline(String name, String airlineUUID, String gruendungsdatum) throws IllegalArgumentException{
        setName(name);
        setAirlineUUID(airlineUUID);
        setGruendungsdatum(gruendungsdatum);

        flugzeuge = null;
    }

    /**
     * Konstruktor ohne Gründungsdatum
     * @param name
     * @param airlineUUID
     * @throws IllegalArgumentException
     */
    public Airline(String name, String airlineUUID) throws IllegalArgumentException{
        setName(name);
        setAirlineUUID(airlineUUID);

        flugzeuge = null;
    }

    /**
     * Konstruktor nur mit Name. Setzt UUID zufällig
     * @param name
     */
    public Airline(String name){
        setName(name);
        setAirlineUUID(UUID.randomUUID().toString());

        flugzeuge = null;
    }

    /**
     * leerer Konstruktor, Setzt UUID zufällig
     */
    public Airline(){
        setAirlineUUID(UUID.randomUUID().toString());

        flugzeuge = null;
    }


    //Getter & Setter

    /**
     * gibt den Namen
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gibt das Gründungsdatum
     * @return gruendungsdatum
     */
    public String getGruendungsdatum() {
        return gruendungsdatum;
    }

    /**
     * setzt das Gründungsdatum
     * @param gruendungsdatum
     */
    public void setGruendungsdatum(String gruendungsdatum) {
        this.gruendungsdatum = gruendungsdatum;
    }

    /**
     * gibt ein Flugzeug aus der Hashmap zurück
     * Wenn es kein Flugzeug gibt, dann gibt es null zurück
     * @param flugzeugUUID
     * @return flugzeug
     */
    public Flugzeug getFlugzeug(String flugzeugUUID) {
        return flugzeuge.get(flugzeugUUID);
    }

    /**
     * gibt die UUID als String zurück
     * @return airlineUUID
     */
    public String getAirlineUUID() {
        return airlineUUID;
    }

    /**
     * setzt die UUID. Throws IllegalArgumentException, wenn die UUID formal inkorrekt ist.
     * @param airlineUUID
     * @throws IllegalArgumentException
     */
    public void setAirlineUUID(String airlineUUID) throws IllegalArgumentException{
        UUID.fromString(airlineUUID);   /*Testet, ob die UUID formal korrekt ist.
                                          Sonst schmeisst es automatisch IllegalArgumentException*/
        this.airlineUUID = airlineUUID;
    }

    /**
     * gibt die Hashmap der Flugzeuge der Airline zurück
     * @return HashMap
     */
    public HashMap<String, Flugzeug> getFlugzeuge() {
        return flugzeuge;
    }


    //Methoden

    /**
     * Fügt ein Flugzeug zur Hashmap hinzu
     * @param flugzeug
     */
    public void addFlugzeug(Flugzeug flugzeug){
        if (flugzeuge == null){
            flugzeuge = new HashMap<>();
        }
        flugzeuge.put(flugzeug.getFlugzeugUUID(), flugzeug);
    }

    /**
     * Löscht ein Flugzeug von der HashMap
     * @param flugzeugUUID
     */
    public void rmFlugzeug(String flugzeugUUID){
        UUID.fromString(flugzeugUUID);
        flugzeuge.remove(flugzeugUUID);
    }

}
