package ch.bzz.Flugzeugmarkt.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.UUID;

/**
 * Modelklasse des Flugzeugs
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 02.06.2021
 */

public class Flugzeug {
    private Airline airline;

    private Hersteller hersteller;

    @FormParam("flugzeugtyp")
    @Size(min = 3, max = 30, message = "Der Typ des Flugzeugs muss 3-30 Zeichen lang sein")
    private String flugzeugtyp;

    @FormParam("flugzeugUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}", message = "Die UUID des Flugzeugs muss eine valide UUID sein")
    private String flugzeugUUID;

    @FormParam("herstellungsdatum")
    @Size(min = 4, max = 4, message = "Das Herstellungsdatum des Flugzeugs muss im Jahresformat sein")
    private String herstellungsdatum;


    //Konstruktoren

    /**
     * ausführlicher Konstruktor
     * @param hersteller
     * @param flugzeugtyp
     * @param flugzeugUUID
     * @param herstellungsdatum
     * @param airline
     * @throws IllegalArgumentException
     */
    public Flugzeug(Hersteller hersteller, String flugzeugtyp, String flugzeugUUID, String herstellungsdatum, Airline airline) throws IllegalArgumentException{
        setHersteller(hersteller);
        setFlugzeugtyp(flugzeugtyp);
        setFlugzeugUUID(flugzeugUUID);
        setHerstellungsdatum(herstellungsdatum);
        setAirline(airline);
    }

    /**
     * ausführlicher Konstruktor, ohne Airline
     * @param hersteller
     * @param flugzeugtyp
     * @param flugzeugUUID
     * @param herstellungsdatum
     * @throws IllegalArgumentException
     */
    public Flugzeug(Hersteller hersteller, String flugzeugtyp, String flugzeugUUID, String herstellungsdatum) throws IllegalArgumentException{
        setHersteller(hersteller);
        setFlugzeugtyp(flugzeugtyp);
        setFlugzeugUUID(flugzeugUUID);
        setHerstellungsdatum(herstellungsdatum);
    }

    /**
     * Konstruktor ohne herstellungsdatum
     * @param hersteller
     * @param flugzeugtyp
     * @param flugzeugUUID
     * @throws IllegalArgumentException
     */
    public Flugzeug(Hersteller hersteller, String flugzeugtyp, String flugzeugUUID) throws IllegalArgumentException{
        setHersteller(hersteller);
        setFlugzeugtyp(flugzeugtyp);
        setFlugzeugUUID(flugzeugUUID);
    }

    /**
     * Konstruktor mit hersteller und flugzeugtyp, setzt automatisch eine zufällige UUID
     * @param hersteller
     * @param flugzeugtyp
     */
    public Flugzeug(Hersteller hersteller, String flugzeugtyp){
        setHersteller(hersteller);
        setFlugzeugtyp(flugzeugtyp);
        setFlugzeugUUID(UUID.randomUUID().toString());
    }

    /**
     * Konstruktor nur mit hersteller, setzt automatisch eine zufällige UUID
     * @param hersteller
     */
    public Flugzeug(Hersteller hersteller){
        setHersteller(hersteller);
        setFlugzeugUUID(UUID.randomUUID().toString());
    }

    /**
     * Leerer Konstruktor, damit das deserialisieren vom JSON funktioniert
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

    /**
     * gibt den Hersteller zurück
     * @return hersteller
     */
    public Hersteller getHersteller() {
        return hersteller;
    }

    /**
     * setzt den Hersteller
     * @param hersteller
     */
    public void setHersteller(Hersteller hersteller) {
        this.hersteller = hersteller;
    }

    /**
     * gibt die Airline zurück
     * @return airline
     */
    public Airline getAirline() {
        return airline;
    }

    /**
     * setzt die Airline
     * @param airline
     */
    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
