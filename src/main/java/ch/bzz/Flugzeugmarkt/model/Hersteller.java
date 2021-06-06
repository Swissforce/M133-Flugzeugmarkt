package ch.bzz.Flugzeugmarkt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.UUID;

/**
 * Modelklasse des Herstellers
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

public class Hersteller {
    private String name;
    private String herstellerUUID;
    private String gruendungsdatum;
    @JsonIgnore
    private HashMap<String, Flugzeug> zuverkaufendeFlugzeuge;


    //Konstruktoren

    /**
     * ausführlicher Konstruktor
     * @param name
     * @param herstellerUUID
     * @param gruendungsdatum
     * @throws IllegalArgumentException
     */
    public Hersteller(String name, String herstellerUUID, String gruendungsdatum) throws IllegalArgumentException{
        setName(name);
        setHerstellerUUID(herstellerUUID);
        setGruendungsdatum(gruendungsdatum);

        zuverkaufendeFlugzeuge = null;
    }

    /**
     * Konstruktor ohne gründungsdatum
     * @param name
     * @param herstellerUUID
     * @throws IllegalArgumentException
     */
    public Hersteller(String name, String herstellerUUID) throws IllegalArgumentException{
        setName(name);
        setHerstellerUUID(herstellerUUID);

        zuverkaufendeFlugzeuge = null;
    }

    /**
     * Konstruktor nur mit Name.
     * Setzt automatisch eine zufällige UUID
     * @param name
     */
    public Hersteller(String name){
        setName(name);
        setHerstellerUUID(UUID.randomUUID().toString());

        zuverkaufendeFlugzeuge = null;
    }

    /**
     * Leerer Konstruktor.
     * Setzt automatisch eine zufällige UUID.
     */
    public Hersteller(){
        setHerstellerUUID(UUID.randomUUID().toString());

        zuverkaufendeFlugzeuge = null;
    }


    //Getter & Setter

    /**
     * gibt den Namen des Herstellers zurück
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen des Herstellers
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gibt die UUID des Herstellers zurück
     * @return herstellerUUID
     */
    public String getHerstellerUUID() {
        return herstellerUUID;
    }

    /**
     * setzt die UUID des Herstellers.
     * wenn die UUID formal inkorrekt ist, dann schmeisst es eine IllegalArgumentException
     * @param herstellerUUID
     * @throws IllegalArgumentException
     */
    public void setHerstellerUUID(String herstellerUUID) throws IllegalArgumentException{
        UUID.fromString(herstellerUUID);   /*Testet, ob die UUID formal korrekt ist.
                                          Sonst schmeisst es automatisch IllegalArgumentException*/
        this.herstellerUUID = herstellerUUID;
    }

    /**
     * gibt das Gründungsdatum zurück
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
     * gibt die Hashmap der Flugzeuge, ohne Airline, zurück
     * @return zuverkaufendeFlugzeuge
     */
    public HashMap<String, Flugzeug> getZuverkaufendeFlugzeuge() {
        return zuverkaufendeFlugzeuge;
    }

    //Methoden

    /**
     * fügt der Hashmap ein neues Flugzeug hinzu
     * @param flugzeug
     */
    public void addZuverkaufendeFlugzeuge(Flugzeug flugzeug){
        if (zuverkaufendeFlugzeuge == null){
            zuverkaufendeFlugzeuge = new HashMap<>();
        }
        zuverkaufendeFlugzeuge.put(flugzeug.getFlugzeugUUID(), flugzeug);
    }

}
