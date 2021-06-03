package ch.bzz.Flugzeugmarkt.model;

import java.util.UUID;

/**
 * Model des Flugzeugs
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 02.06.2021
 */

public class Flugzeug {
    private String flugzeugtyp;
    private String flugzeugUUID;
    private String herstellungsdatum;


    //Konstruktoren

    /**
     * ausführlicher Konstruktor
     * @param flugzeugtyp
     * @param flugzeugUUID
     * @param herstellungsdatum
     * @throws IllegalArgumentException
     */
    public Flugzeug(String flugzeugtyp, String flugzeugUUID, String herstellungsdatum) throws IllegalArgumentException{
        setFlugzeugtyp(flugzeugtyp);
        setFlugzeugUUID(flugzeugUUID);
        setHerstellungsdatum(herstellungsdatum);
    }

    /**
     * Konstruktor ohne herstellungsdatum
     * @param flugzeugtyp
     * @param flugzeugUUID
     * @throws IllegalArgumentException
     */
    public Flugzeug(String flugzeugtyp, String flugzeugUUID)throws IllegalArgumentException{
        setFlugzeugtyp(flugzeugtyp);
        setFlugzeugUUID(flugzeugUUID);
    }

    /**
     * minimalistischer Konstruktor, setzt automatisch eine zufällige UUID
     * @param flugzeugtyp
     */
    public Flugzeug(String flugzeugtyp){
        setFlugzeugtyp(flugzeugtyp);
        setFlugzeugUUID(UUID.randomUUID().toString());
    }

    /**
     * leerer Konstruktor, setzt automatisch eine zufällige UUID
     */
    public Flugzeug(){
        setFlugzeugUUID(UUID.randomUUID().toString());
    }


    // Getter & Setter

    /**
     * gibt den Flugzeugtypen
     * @return flugzeugtyp
     */
    public String getFlugzeugtyp() {
        return flugzeugtyp;
    }

    /**
     * setzt den Flugzeugtypen
     * @param flugzeugtyp
     */
    public void setFlugzeugtyp(String flugzeugtyp) {
        this.flugzeugtyp = flugzeugtyp;
    }

    /**
     * gibt die Registrationsnummer
     * @return registrationsnummer
     */
    public String getFlugzeugUUID() {
        return flugzeugUUID;
    }

    /**
     * setzt die Registrationsnummer und überprüft diese formal
     * @param flugzeugUUID
     * @throws IllegalArgumentException
     */
    public void setFlugzeugUUID(String flugzeugUUID) throws IllegalArgumentException{
        UUID.fromString(flugzeugUUID);  /*Testet, ob die UUID formal korrekt ist.
                                          Sonst schmeisst es automatisch IllegalArgumentException*/
        this.flugzeugUUID = flugzeugUUID;
    }

    /**
     * gibt das Herstellungsdatum
     * @return herstellungsdatum
     */
    public String getHerstellungsdatum() {
        return herstellungsdatum;
    }

    /**
     * setzt das Herstellungsdatum
     * @param herstellungsdatum
     */
    public void setHerstellungsdatum(String herstellungsdatum) {
        this.herstellungsdatum = herstellungsdatum;
    }

}
