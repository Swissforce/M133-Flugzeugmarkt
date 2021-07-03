package ch.bzz.Flugzeugmarkt.model;

import java.util.UUID;

/**
 * Modelklasse des Benutzers
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.07.2021
 */

public class Benutzer {
    private String benutzerUUID;
    private String benutzername;
    private String passwort;
    private String rolle;


    //Konstruktoren

    /**
     * Ausführlicher Konstruktor
     * @param benutzername
     * @param passwort
     * @param rolle
     * @param benutzerUUID
     */
    public Benutzer(String benutzername, String passwort, String rolle, String benutzerUUID){
        setBenutzername(benutzername);
        setPasswort(passwort);
        setRolle(rolle);
        setBenutzerUUID(benutzerUUID);
    }

    /**
     * Konstruktor mit automatischer UUID-Zuweisung
     * @param benutzername
     * @param passwort
     * @param rolle
     */
    public Benutzer(String benutzername, String passwort, String rolle){
        setBenutzername(benutzername);
        setPasswort(passwort);
        setRolle(rolle);
        setBenutzerUUID(UUID.randomUUID().toString());
    }


    // Getter & Setter

    /**
     * Gibt die benutzerUUID zurück
     * @return benutzerUUID
     */
    public String getBenutzerUUID() {
        return benutzerUUID;
    }

    /**
     * Setzt die benutzerUUID
     * Wenn die UUID invalide ist, wirft es eine IllegalArgumentException
     * @param benutzerUUID
     * @throws IllegalArgumentException
     */
    public void setBenutzerUUID(String benutzerUUID) throws IllegalArgumentException {
        UUID.fromString(benutzerUUID);
        this.benutzerUUID = benutzerUUID;
    }

    /**
     * Gibt den Benutzernamen zurück
     * @return benutzername;
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * Setzt den Benutzernamen
     * @param benutzername
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    /**
     * Gibt das Passwort als String zurück (kein Hash)
     * @return passwort
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * Setzt das Passwort
     * @param passwort
     */
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    /**
     * Gibt die Rolle des Benutzers zurück
     * @return rolle
     */
    public String getRolle() {
        return rolle;
    }

    /**
     * Setzt die Rolle des Benutzers
     * @param rolle
     */
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
}
