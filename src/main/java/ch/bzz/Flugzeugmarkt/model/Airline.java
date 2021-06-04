package ch.bzz.Flugzeugmarkt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;
import java.util.Vector;

/**
 * Modelklasse der Airline
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

public class Airline {
    private String name;
    private String airlineUUID;
    private String gruendungsdatum;
    @JsonIgnore
    private Vector <Flugzeug> flugzeuge;


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

        flugzeuge = new Vector<>();
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

        flugzeuge = new Vector<>();
    }

    /**
     * Konstruktor nur mit Name. Setzt UUID zufällig
     * @param name
     */
    public Airline(String name){
        setName(name);
        setAirlineUUID(UUID.randomUUID().toString());

        flugzeuge = new Vector<>();
    }

    /**
     * leerer Konstruktor, Setzt UUID zufällig
     */
    public Airline(){
        setAirlineUUID(UUID.randomUUID().toString());

        flugzeuge = new Vector<>();
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
     * gibt ein, mit Index spezifiziertes, Flugzeug vom Vector flugzeuge zurück
     * Wenn der Index out of bounds ist, wird diese Exception in eine IllegalArgumentException umgewandelt.
     * @param index
     * @return flugzeug
     * @throws IllegalArgumentException
     */
    public Flugzeug getFlugzeug(int index) throws IllegalArgumentException {
        try {
            return flugzeuge.get(index);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
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


    //Methoden

    /**
     * aktualisiert ein, mit Index spezifiziertes, Flugzeug vom Vector flugzeuge.
     * Dies geschieht, indem das alte Flugzeugobjekt mit einem neuen ersetzt wird.
     * Wenn der Index out of bounds ist, wird diese Exception in eine IllegalArgumentException umgewandelt.
     * @param flugzeug
     * @param index
     * @throws IllegalArgumentException
     */
    public void updateFlugzeug(Flugzeug flugzeug, int index) throws IllegalArgumentException {
        try {
            flugzeuge.get(index);   //Schaut, ob das Flugzeug mit dem spezifizierten Index existiert
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
        flugzeuge.set(index,flugzeug);
    }

    /**
     * aktualisiert ein, mit UUID spezifiziertes, Flugzeug vom Vector flugzeuge.
     * Dies geschieht, indem das alte Flugzeugobjekt mit einem neuen ersetzt wird.
     * Wenn kein Flugzeug mit der UUID im Vector vorhanden ist, dann throw IllegalArgumentException.
     * @param flugzeug
     * @param flugzeugUUID
     * @throws IllegalArgumentException
     */
    public void updateFlugzeug(Flugzeug flugzeug, String flugzeugUUID) throws IllegalArgumentException{
        boolean existiert = false;
        for(Flugzeug x : flugzeuge){
            if (x.getFlugzeugUUID() == flugzeugUUID){           //Schaut ob ein Flugzeug mit dieser UUID existiert
                flugzeuge.set(flugzeuge.indexOf(x),flugzeug);
                existiert = true;
            }
        }
        if (!existiert){
            throw new IllegalArgumentException();
        }
    }

    /**
     * entfernt ein Flugzeug vom Vector flugzeuge, mithilfe des Indexes
     * Wenn der Index out of bounds ist, wird diese Exception in eine IllegalArgumentException umgewandelt.
     * @param index
     * @throws IllegalArgumentException
     */
    public void rmFlugzeug(int index) throws IllegalArgumentException {
        try {
            flugzeuge.get(index);   //Schaut, ob das Flugzeug mit dem spezifizierten Index existiert
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
        flugzeuge.remove(index);
    }

    /**
     * entfernt ein Flugzeug vom Vector flugzeuge, mithilfe des Objektes
     * Wenn das Objekt nicht im Vector flugzeuge vorhanden ist, schmeisst es eine IllegalArgumentException
     * @param flugzeug
     * @throws IllegalArgumentException
     */
    public void rmFlugzeug(Flugzeug flugzeug) throws IllegalArgumentException {
        if (flugzeuge.contains(flugzeug)){  //Schaut, ob das Flugzeug mit dem spezifizierten Index existiert
            flugzeuge.remove(flugzeug);
        }
        else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * entfernt ein Flugzeug vom Vector flugzeuge, mithilfe der UUID
     * Wenn kein Flugzeug mit der UUID im Vector flugzeuge vorhanden ist, schmeisst es eine IllegalArgumentException
     * @param flugzeugUUID
     * @throws IllegalArgumentException
     */
    public void rmFlugzeug(String flugzeugUUID) throws IllegalArgumentException{
        boolean existiert = false;
        for(Flugzeug x : flugzeuge){
            if (x.getFlugzeugUUID() == flugzeugUUID){           //Schaut ob ein Flugzeug mit dieser UUID existiert
                flugzeuge.remove(x);
                existiert = true;
            }
        }
        if (!existiert){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Fügt ein Flugzeug zum Vector hinzu
     * @param flugzeug
     */
    public void addFlugzeug(Flugzeug flugzeug){
        flugzeuge.add(flugzeug);
    }

    //TODO Methode für den Kauf von Flugzeugen über den Hersteller
}
