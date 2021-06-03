package ch.bzz.Flugzeugmarkt.model;

/**
 * Model des Flugzeugs
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 02.06.2021
 */

public class Flugzeug {
    private String flugzeugtyp;
    private String registrationsnummer;
    private String herstellungsdatum;

    //Konstruktoren

    /**
     * ausführlicher Konstruktor
     * @param flugzeugtyp
     * @param registrationsnummer
     * @param herstellungsdatum
     */
    public Flugzeug(String flugzeugtyp, String registrationsnummer, String herstellungsdatum){
        setFlugzeugtyp(flugzeugtyp);
        setRegistrationsnummer(registrationsnummer);
        setHerstellungsdatum(herstellungsdatum);
    }

    /**
     * Konstruktor ohne herstellungsdatum
     * @param flugzeugtyp
     * @param registrationsnummer
     */
    public Flugzeug(String flugzeugtyp, String registrationsnummer){
        setFlugzeugtyp(flugzeugtyp);
        setRegistrationsnummer(registrationsnummer);
    }

    /**
     * minimalistischer Konstruktor
     * @param flugzeugtyp
     */
    public Flugzeug(String flugzeugtyp){
        setFlugzeugtyp(flugzeugtyp);
    }

    /**
     * leerer Konstruktor
     */
    public Flugzeug(){

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
    public String getRegistrationsnummer() {
        return registrationsnummer;
    }

    /**
     * setzt die Registrationsnummer
     * @param registrationsnummer
     */
    public void setRegistrationsnummer(String registrationsnummer) {
        this.registrationsnummer = registrationsnummer;
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
